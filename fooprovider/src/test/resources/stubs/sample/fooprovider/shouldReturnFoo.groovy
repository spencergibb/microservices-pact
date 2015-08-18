package stubs.provider

import io.codearte.accurest.dsl.GroovyDsl

GroovyDsl groovyDsl = GroovyDsl.make {
    request {
        method 'GET'
        url '/foo'
    }
    response {
        status 200
        headers {
            header 'Content-Type' : 'application/json;charset=UTF-8'
        }
        /*body(
            { value : 42 },
            { value : 100 }
        )*/
        body '''{ "value" : 43 }'''
    }
}