package sample.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConsumerService {

	private static final ParameterizedTypeReference<List<Foo>> LIST_FOOS_TYPE = new ParameterizedTypeReference<List<Foo>>() { };

	private static final ParameterizedTypeReference<List<Bar>> LIST_BARS_TYPE = new ParameterizedTypeReference<List<Bar>>() { };

	@Value("${fooproducer-url:http://fooprovider}")
	private String fooUrl;

	@Value("${barproducer-url:http://barprovider}")
	private String barUrl;

	@Autowired(required = false)
	private RestTemplate restTemplate = new RestTemplate();

	public ConsumerService() { }

	public List<Foo> foos() {
		return restTemplate.exchange(fooUrl + "/foos", HttpMethod.GET, null, LIST_FOOS_TYPE)
				.getBody();
	}

	public Foo foo() {
		return restTemplate.getForObject(fooUrl + "/foo", Foo.class);
	}

	public List<Bar> bars() {
		return restTemplate.exchange(barUrl + "/bars", HttpMethod.GET, null, LIST_BARS_TYPE)
				.getBody();
	}

}
