package behaviourTests

import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static groovyx.net.http.ContentType.*

class BehaviourSpec extends Specification {
    def client = new RESTClient("http://localhost:8080/");

    def "should add new behaviour" () {
        when: "try to add new behaviour"
        def response = client.post(path: "/api/add-behaviour",
                body: [
                        description: description,
                        grade: grade,
                        userId: userId,
                ],
                requestContentType : JSON,
                headers: ["bearer-token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjE3NDI4NzYyLCJpYXQiOjE2MTAyMjg3NjJ9.HQ0949iVPFFbwVFkRQo9gPsj_96RoQFZBDwg9Diov-pk5aPX7OgIuCFSIoJjbQFxizHRVM-3pJHeBt-i4UVujw"]);
        then: "should return 200 status and add behaviour"
        HttpResponseException e = thrown(HttpResponseException);
        assert e.response.status == 200
        where:
        description | grade | userId
        'test'      | 5     | 1
    }

    def "should get behaviours by user" () {
        when: "try to get behaviours"
        def response = client.get(path: "/api/user-behaviours/1");
        then: "should get behaviours by user"
        with (response) {
            status == 200
            data.size > 0
        }
    }

    def "should edit behaviour" () {
        when: "try to edit behaviour"
        def response = client.put(path: "/api/behaviour/2",
                body: [
                        description: description,
                        grade: grade,
                        userId: userId,
                ],
                requestContentType : JSON);
        then: "should return status 200 ang edit behaviours"
        with(response) {
            status == 200
        }
        where:
        description | grade | userId
        'test'      | 2     | 1
    }

    def "should add response" () {
        when: "try to add response"
        def response = client.post(path: "/api/add-response",
                body: [
                        behaviourId: behaviourId,
                        responseContent: responseContent,
                        subject: subject,
                ],
                requestContentType : JSON,
                headers: ["auth-token": 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjE3NDI4NzYyLCJpYXQiOjE2MTAyMjg3NjJ9.HQ0949iVPFFbwVFkRQo9gPsj_96RoQFZBDwg9Diov-pk5aPX7OgIuCFSIoJjbQFxizHRVM-3pJHeBt-i4UVujw']);
        then: "should return status 200 ang send behaviour"
        with(response) {
            status == 200
        }
        where:
        behaviourId | responseContent | subject
        1           | 'Test'          | 'Test'
    }
}

