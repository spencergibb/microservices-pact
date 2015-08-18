package sample.stubrunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.zookeeper.ZookeeperProperties;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.cloud.zookeeper.discovery.dependency.ZookeeperDependencies;
import org.springframework.cloud.zookeeper.discovery.dependency.ZookeeperDependency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ofg.infrastructure.discovery.MicroserviceConfigurationNotPresentException;
import com.ofg.stub.BatchStubRunnerFactory;
import com.ofg.stub.Collaborators;
import com.ofg.stub.StubRunnerOptions;
import com.ofg.stub.StubRunning;

/**
 * @author Spencer Gibb
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnProperty(value = "stubrunner.enabled")
@ConditionalOnClass(BatchStubRunnerFactory.class)
public class StubRunnerAutoConfiguration {

	@Autowired(required = false)
	private ZookeeperDependencies zookeeperDependencies;
			
	@Autowired(required = false)
	private ZookeeperDiscoveryProperties zookeeperDiscoveryProperties;
	
	@Autowired
	private ZookeeperProperties zookeeperProperties;

	@Bean
	public StubRunnerProperties stubRunnerProperties() {
		return new StubRunnerProperties();
	}

	@Bean(initMethod = "runStubs", destroyMethod = "close")
	StubRunning batchStubRunner(StubRunnerProperties properties) {
		checkIfConfigurationIsPresent();
		boolean shouldWorkOnline = isPropertySetToWorkOnline(properties.isWorkOffline(), properties.isSkipLocalRepo());
		String connectString = zookeeperProperties.getConnectString();
		String[] parts = connectString.split(":");
		int port = Integer.parseInt(parts[1]);
		StubRunnerOptions stubRunnerOptions = new StubRunnerOptions(properties.getMinPortValue(), properties.getMaxPortValue(), properties.getStubRepositoryRoot(), properties.getStubsGroup(), properties.getStubsModule(), shouldWorkOnline,
				properties.isUseMicroserviceDefinitions(), connectString, port, properties.getStubsSuffix(), properties.isWaitForService(), properties.getWaitTimeout());
		List<String> dependenciesPath = getDependenciesPaths();
		Collaborators dependencies = new Collaborators(getBasePath(), dependenciesPath);
		return new BatchStubRunnerFactory(stubRunnerOptions, dependencies).buildBatchStubRunner();
	}

	private void checkIfConfigurationIsPresent() {
		if (zookeeperDependencies == null) {
			throw new MicroserviceConfigurationNotPresentException();
		}
	}

	private String getBasePath() {
		if (zookeeperDiscoveryProperties != null) {
			return zookeeperDiscoveryProperties.getRoot();
		}
		return null;
	}

	private List<String> getDependenciesPaths() {
		if (zookeeperDependencies != null) {
			ArrayList<String> list = new ArrayList<>();
			for (ZookeeperDependency dep : zookeeperDependencies.getDependencyConfigurations()) {
				list.add(dep.getPath());
			}
			return list;
		}
		return Collections.emptyList();
	}

	private boolean isPropertySetToWorkOnline(boolean workOffline, boolean skipLocalRepo) {
		return (!workOffline) && skipLocalRepo;
	}

}
