package gradesTests

import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static groovyx.net.http.ContentType.*

class GradeSpec extends Specification {
    def client = new RESTClient("http://localhost:8080/");

    def "should add new grade" () {
        when: "try to add new grade"
        def response = client.post(path: "/api/add-grade",
                body: [
                        comment: comment,
                        grade: grade,
                        gradeWeight: gradeWeight,
                        subjectId: subjectId,
                        userId: userId,
                ],
                requestContentType : JSON,
                headers: ["auth-token": 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjE3NDI4NzYyLCJpYXQiOjE2MTAyMjg3NjJ9.HQ0949iVPFFbwVFkRQo9gPsj_96RoQFZBDwg9Diov-pk5aPX7OgIuCFSIoJjbQFxizHRVM-3pJHeBt-i4UVujw']);
        then: "should return 200 status and add grade"
        HttpResponseException e = thrown(HttpResponseException);
        assert e.response.status == 200
        where:
        comment | grade | gradeWeight | subjectId | userId
        'test'  | 5     | 5           | 1         | 2
    }

    def "should get grades by user" () {
        when: "try to get grades"
        def response = client.get(path: "/api/grades/1/subjects");
        then: "should get grades by user"
        with (response) {
            status == 200
            data.size > 0
        }
    }

    def "should get grades by subject" () {
        when: "try to get grades"
        def response = client.get(path: "/api/1/users");
        then: "should get grades by subject"
        with (response) {
            status == 200
            data.size > 0
        }
    }

    def "should delete list of grades" () {
        when: "try to delete grades"
        def response = client.delete(path: "/api/grades/1,2");
        then: "should delete grades"
        with (response) {
            status == 200
        }
    }
}
