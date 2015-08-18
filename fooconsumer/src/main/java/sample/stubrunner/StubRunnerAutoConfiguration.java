package sample.stubrunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.zookeeper.ZookeeperProperties;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.cloud.zookeeper.discovery.dependency.ZookeeperDependencies;
import org.springframework.cloud.zookeeper.discovery.dependency.ZookeeperDependencies.ZookeeperDependency;
import org.springframework.context.annotation.Bean;

import com.ofg.infrastructure.discovery.MicroserviceConfigurationNotPresentException;
import com.ofg.stub.*;
import org.springframework.context.annotation.Configuration;

/**
 * @author Spencer Gibb
 */
@Configuration
@EnableConfigurationProperties
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

	/**
	 * Bean that initializes stub runners, runs them and on shutdown closes them. Upon its instantiation
	 * JAR with stubs is downloaded and unpacked to a temporary folder. Next, {@link StubRunner} instance are
	 * registered for each collaborator.
	 *
	 * @param minPortValue min port value of the Wiremock instance for the given collaborator
	 * @param maxPortValue max port value of the Wiremock instance for the given collaborator
	 * @param stubRepositoryRoot root URL from where the JAR with stub mappings will be downloaded
	 * @param @Deprecated stubsGroup group name of the dependency containing stub mappings
	 * @param @Deprecated stubsModule module name of the dependency containing stub mappings
	 * @param stubsSuffix suffix for the jar containing stubs
	 * @param workOffline forces offline work
	 * @param @Deprecated skipLocalRepo avoids local repository in dependency resolution
	 * @param useMicroserviceDefinitions use per microservice stub resolution rather than one jar for all microservices
	 * @param waitForService wait for connection from service
	 * @param waitTimeout wait timeout in seconds
	 * @param serviceConfigurationResolver object that wraps the microservice configuration
	 */
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
