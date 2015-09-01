package sample.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sample.microinfra.MicroInfraConfiguration;

import com.ofg.config.BasicProfiles;

@SpringBootApplication
@Import(MicroInfraConfiguration.class)
@EnableDiscoveryClient
@RestController
public class ConsumerApplication {

	@Autowired
	private ConsumerService consumerService;

	@RequestMapping("/foos")
	public List<Foo> foos() {
		return consumerService.foos();
	}

	@RequestMapping("/foo")
	public Foo foo() {
		return consumerService.foo();
	}

	@RequestMapping("/bars")
	public List<Bar> bars() {
		return consumerService.bars();
	}

	@RequestMapping("/")
	public String hello() {
		return "Hello World!";
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConsumerApplication.class)
				.profiles(BasicProfiles.SPRING_CLOUD)
				.run(args);
	}
}
