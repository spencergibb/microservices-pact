package sample.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ConsumerPort {

    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public ConsumerPort(@Value("${producer}") String url) {
        this.url = url;
        this.restTemplate = new RestTemplate();
    }

    public List<Foo> foos() {
        ParameterizedTypeReference<List<Foo>> responseType = new ParameterizedTypeReference<List<Foo>>() {};
        return restTemplate.exchange(url + "/foos", HttpMethod.GET, null, responseType).getBody();
    }
}
