package sample.consumer;

//import au.com.dius.pact.consumer.*;
//import au.com.dius.pact.model.PactFragment;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class ConsumerPortTest {

    /*@Rule
    public PactRule rule = new PactRule("localhost", 8080, this);

    @Pact(state="Foo_State", provider="Foo_Provider", consumer="Foo_Consumer")
    public PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider.PactDslWithState builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.uponReceiving("a request for Foos")
                .path("/foos")
                .method("GET")

                .willRespondWith()
                .headers(headers)
                .status(200)
                .body("[{\"value\":42}, {\"value\":100}]").toFragment();
    }

    @Test
    @PactVerification("Foo_State")
    public void runTest() {
        assertEquals(new ConsumerPort("http://localhost:8080").foos(), Arrays.asList(new Foo(42), new Foo(100)));
    }*/
}
