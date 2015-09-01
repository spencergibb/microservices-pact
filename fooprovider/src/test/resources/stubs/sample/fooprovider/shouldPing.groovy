package stubs.sample.fooprovider

import io.codearte.accurest.dsl.GroovyDsl

GroovyDsl groovyDsl = GroovyDsl.make {
    request {
        method 'GET'
        url '/ping'
    }
    response {
        status 200
        headers {
            header 'Content-Type' : 'text/plain'
        }
        body '''OK'''
    }
}