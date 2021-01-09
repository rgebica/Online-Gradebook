package authTests

import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification

import static groovyx.net.http.ContentType.*

class LoginSpec extends Specification {
    @Shared
    def url = new RESTClient("http://localhost:8080/");

    def "should return 200 status when login data valid"() {
        when: "try to login"
        def response = url.post(path: "api/auth/login", body: [
                username   : "test",
                password: 'lEhTX&XwL4a$'
        ],
                contentType: JSON);
        then: 'should return 200 status'
        with(response) {
            status == 200
            contentType == "application/json"
        }
    }

    def "should return status 401 when login data is not valid"() {
        when: "try to login"
        def response = url.post(path: "api/auth/login", body: [
                username   : "test",
                password: 'bad data'
        ],
                contentType: JSON);
        then: 'should return 401 status'
            HttpResponseException e = thrown(HttpResponseException);
            assert e.response.status == 401
    }
}