package sample.stubrunner;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ofg.stub.server.StubServer;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import com.ofg.stub.BatchStubRunner;
import com.ofg.stub.StubRunner;
import com.ofg.stub.StubRunnerExecutor;

/**
 * @author Spencer Gibb
 */
@ConfigurationProperties(prefix = "endpoints.stubrunner", ignoreUnknownFields = false)
public class StubRunnerEndpoint extends AbstractEndpoint<Map<String, String>> {

	@Autowired
	ApplicationContext context;

	private final Field stubRunnersField = findField(BatchStubRunner.class, "stubRunners");
	private final Field stubRunnerExecutorField = findField(StubRunner.class, "localStubRunner");
	private final Field stubServersField = findField(StubRunnerExecutor.class, "stubServers");

	public StubRunnerEndpoint() {
		super("stubrunner", false);
	}

	private Field findField(Class type, String name) {
		Field field = ReflectionUtils.findField(type, name);
		ReflectionUtils.makeAccessible(field);
		return field;
	}

	@Override
	public Map<String, String> invoke() {
		Map<String, String> stubs = new LinkedHashMap<>();
		BatchStubRunner batchStubRunner = context.getBean(BatchStubRunner.class);

		Iterable<StubRunner> stubRunners = get(stubRunnersField, batchStubRunner);

		for (StubRunner stubRunner : stubRunners) {
			StubRunnerExecutor executor = get(stubRunnerExecutorField, stubRunner);
			List<StubServer> stubServers = get(stubServersField, executor);
			for (StubServer stubServer : stubServers) {
				URL url = stubServer.getStubUrl();
				String name = stubServer.getProjectMetadata().getProjectName();
				stubs.put(name, url.toString());
			}
		}

		return stubs;
	}

	@SneakyThrows
	@SuppressWarnings("unchecked")
	private <T> T get(Field field, Object o) {
		return (T)field.get(o);
	}
}
