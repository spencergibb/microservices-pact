package sample.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sample.stubrunner.StubRunnerAutoConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ConsumerApplication {

	@Configuration
	@ConditionalOnProperty(value = "stubrunner.enabled")
	@Import(StubRunnerAutoConfiguration.class)
	protected static class StubRunnerConfig { }

    @Autowired
	ConsumerService consumerService;

	@RequestMapping("/foos")
	public List<Foo> foos() {
		return consumerService.foos();
	}

	@RequestMapping("/foo")
	public Foo foo() {
		return consumerService.foo();
	}

    @RequestMapping("/")
    public String hello() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
