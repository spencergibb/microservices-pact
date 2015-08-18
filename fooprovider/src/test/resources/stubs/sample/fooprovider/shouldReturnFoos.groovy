import io.codearte.accurest.dsl.GroovyDsl

GroovyDsl groovyDsl = GroovyDsl.make {
    request {
        method 'GET'
        url '/foos'
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
        body ''' [{
        "value" : 43
      }, {
        "value" : 101
      } ]'''
    }
}