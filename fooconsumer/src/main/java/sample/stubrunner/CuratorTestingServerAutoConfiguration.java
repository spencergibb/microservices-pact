package sample.stubrunner;

import lombok.SneakyThrows;
import org.apache.curator.test.TestingServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Spencer Gibb
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnProperty(value = "curator.testingserver.enabled", matchIfMissing = true)
@ConditionalOnClass(TestingServer.class)
@AutoConfigureBefore(StubRunnerAutoConfiguration.class)
public class CuratorTestingServerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@SneakyThrows
	public TestingServer testingServer(@Value("${curator.testingserver.port:2181}") int port) {
		return new TestingServer(port);
	}

}
