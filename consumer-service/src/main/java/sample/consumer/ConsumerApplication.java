package sample.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ConsumerApplication {

    @Autowired
    ConsumerPort consumerPort;

    @RequestMapping("/foos")
    public List<Foo> foos() {
        return consumerPort.foos();
    }

    @RequestMapping("/")
    public String hello() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
