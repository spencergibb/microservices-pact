package sample.provider;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class, args);
	}

	@RequestMapping(value = "/foo", method = RequestMethod.GET)
	public ResponseEntity<Foo> foo() {
		return new ResponseEntity<>(new Foo(42), HttpStatus.OK);
	}

	@RequestMapping(value = "/foos", method = RequestMethod.GET)
	public ResponseEntity<List<Foo>> foos() {
		return new ResponseEntity<>(Arrays.asList(new Foo(42), new Foo(100)),
				HttpStatus.OK);
	}
}
