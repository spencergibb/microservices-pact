package sample.provider

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import spock.lang.Specification

/**
 * @author Spencer Gibb
 */
abstract class BaseMockMvcSpec extends Specification {

    def setup() {
        RestAssuredMockMvc.standaloneSetup(new ProviderApplication())
    }
}
