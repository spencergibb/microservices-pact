package sample.stubrunner;

import com.ofg.stub.StubRunner;
import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Bean that initializes stub runners, runs them and on shutdown closes them. Upon its instantiation
 * JAR with stubs is downloaded and unpacked to a temporary folder. Next, {@link StubRunner} instance are
 * registered for each collaborator.
 *
 * @author Spencer Gibb
 */
@ConfigurationProperties("stubrunner")
@Data
public class StubRunnerProperties {
	/**
	 * min port value of the Wiremock instance for the given collaborator
	 */
	private int minPortValue = 10000;
	/**
	 * max port value of the Wiremock instance for the given collaborator
	 */
	private int maxPortValue = 15000;
	/**
	 * root URL from where the JAR with stub mappings will be downloaded
	 */
	private String stubRepositoryRoot = "http://nexus.4finance.net/content/repositories/Pipeline";
	/**
	 * group name of the dependency containing stub mappings
	 */
	private String stubsGroup = "com.ofg";
	/**
	 * module name of the dependency containing stub mappings
	 */
	private String stubsModule = "stub-definitions";
	/**
	 * suffix for the jar containing stubs
	 */
	private String stubsSuffix = "stubs";
	/**
	 * forces offline work
	 */
	private boolean workOffline = false;
	/**
	 * avoids local repository in dependency resolution
	 */
	private boolean skipLocalRepo = true;
	/**
	 * use per microservice stub resolution rather than one jar for all microservices
	 */
	private boolean useMicroserviceDefinitions = false;
	/**
	 * wait for connection from service
	 */
	private boolean waitForService = false;
	/**
	 * wait timeout in seconds
	 */
	private int waitTimeout = 1;

	private boolean enabled = false;
}
