import io.codearte.accurest.dsl.GroovyDsl

GroovyDsl groovyDsl = GroovyDsl.make {
    request {
        method 'GET'
        url '/bars'
    }
    response {
        status 200
        headers {
            header 'Content-Type' : 'application/json;charset=UTF-8'
        }
        /*body(
            { bar : 13 },
            { bar : 17 }
        )*/
        body ''' [{
        "bar" : 13
      }, {
        "bar" : 17
      } ]'''
    }
}
