package sample.provider;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class BarProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarProviderApplication.class, args);
	}

	@RequestMapping(value = "/bar", method = RequestMethod.GET)
	public ResponseEntity<Bar> bar() {
		return new ResponseEntity<>(new Bar(13), HttpStatus.OK);
	}

	@RequestMapping(value = "/bars", method = RequestMethod.GET)
	public ResponseEntity<List<Bar>> bars() {
		return new ResponseEntity<>(Arrays.asList(new Bar(13), new Bar(17)),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String ping() {
		return "OK";
	}
}
