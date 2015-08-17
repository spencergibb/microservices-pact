package sample.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ConsumerService {

	@Value("${producer-url:http://fooprovider}")
    private String url;
	@Autowired(required = false)
    private RestTemplate restTemplate = new RestTemplate();

    public ConsumerService() {
    }

    public List<Foo> foos() {
        ParameterizedTypeReference<List<Foo>> responseType = new ParameterizedTypeReference<List<Foo>>() {};
        return restTemplate.exchange(url + "/foos", HttpMethod.GET, null, responseType).getBody();
    }
}
