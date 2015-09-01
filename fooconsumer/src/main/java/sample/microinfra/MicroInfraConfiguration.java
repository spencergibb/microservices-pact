package sample.microinfra;

import com.ofg.infrastructure.discovery.ServiceResolver;
import com.ofg.infrastructure.discovery.SpringCloudZookeeperServiceResolver;
import com.ofg.infrastructure.healthcheck.HealthCheckConfiguration;
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClientConfiguration;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServiceDiscovery;
import org.springframework.cloud.zookeeper.discovery.dependency.ZookeeperDependencies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @author Spencer Gibb
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({//ServiceDiscoveryConfiguration.class,
		ServiceRestClientConfiguration.class,
		HealthCheckConfiguration.class})
public class MicroInfraConfiguration {
	@Bean
	public ServiceResolver zooKeeperServiceResolver(ZookeeperDependencies zookeeperDependencies,
													DiscoveryClient discoveryClient,
													ZookeeperServiceDiscovery zookeeperServiceDiscovery,
													CuratorFramework curatorFramework,
													ZookeeperDiscoveryProperties zookeeperDiscoveryProperties) {
		return new SpringCloudZookeeperServiceResolver(zookeeperDependencies,
				discoveryClient, curatorFramework, zookeeperServiceDiscovery, zookeeperDiscoveryProperties);
	}
}
