package sample.stubrunner;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Spencer Gibb
 */
@ConfigurationProperties("stubrunner")
@Data
public class StubRunnerProperties {
	private int minPortValue = 10000;
	private int maxPortValue = 15000;
	private String stubRepositoryRoot = "http://nexus.4finance.net/content/repositories/Pipeline";
	private String stubsGroup = "com.ofg";
	private String stubsModule = "stub-definitions";
	private String stubsSuffix = "stubs";
	private boolean workOffline = false;
	private boolean skipLocalRepo = true;
	private boolean useMicroserviceDefinitions = false;
	private boolean waitForService = false;
	private int waitTimeout = 1;
	private boolean enabled = false;
}
