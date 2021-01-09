package userTests

import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification
import static groovyx.net.http.ContentType.*

class UserSpec extends Specification {
    @Shared
    def client = new RESTClient("http://localhost:8080/");

    def "should add new user" () {
        when: "try to add new user"
        def response = client.post(path: "api/users",
                body: [
                        childrenIds: childrenIds,
                        email: email,
                        firstName: firstName,
                        lastName: lastName,
                        role: role,
                        username: username,
                        classId: classId
                ],
                requestContentType : JSON);
        then: "should return 201 status and create user"
        assert response.status == 201: 'response code should be 201 if provided all required parameters'
        where:
        childrenIds | email            | firstName | lastName | role           | username     | classId
        ''          | "test@gmail.com" | 'Rafal'   | 'Gebica' | 'ROLE_STUDENT' | 'testgebica' | 1
    }

    def "should not add new user" () {
        when: "try to add new user"
        def response = client.post(path: "api/users",
                body: [
                        childrenIds: childrenIds,
                        email: email,
                        firstName: firstName,
                        lastName: lastName,
                        role: role,
                        username: username,
                        classId: classId
                ],
                requestContentType : JSON);
        then: "should return other status then 201"
        HttpResponseException e = thrown(HttpResponseException);
        assert e.response.status != 201
        where:
        childrenIds | email            | firstName | lastName | role           | username     | classId
        ''          | ""               | 'Rafal'   | 'Gebica' | 'ROLE_STUDENT' | 'testgebica' | 1
    }
}
