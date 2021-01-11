package com.index.presenceTests

import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spock.lang.Specification

import static groovyx.net.http.ContentType.*

class PresenceSpec extends Specification {
    def client = new RESTClient("http://localhost:8080/");

    def "should add new presence" () {
        when: "try to add new presence"
        def response = client.post(path: "/api/add-presence",
                body: [
                        presence: presence,
                        status: status,
                        subjectId: subjectId,
                        userId: userId,
                ],
                requestContentType : JSON,
                headers: ["bearer-token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjE3NDI4NzYyLCJpYXQiOjE2MTAyMjg3NjJ9.HQ0949iVPFFbwVFkRQo9gPsj_96RoQFZBDwg9Diov-pk5aPX7OgIuCFSIoJjbQFxizHRVM-3pJHeBt-i4UVujw"]);
        then: "should return 200 status and add presence"
        HttpResponseException e = thrown(HttpResponseException);
        assert e.response.status == 200
        where:
        presence | status                    | subjectId   | userId
        false    | 'NIEUSPRAWIEDLIWIONY'     | 1           | 1
    }

    def "should get presences by user" () {
        when: "try to get presences"
        def response = client.get(path: "/api/presence/1/subjects");
        then: "should get presences by user"
        with (response) {
            status == 200
            data.size > 0
        }
    }

    def "should get presences by subject" () {
        when: "try to get presences"
        def response = client.get(path: "/api/1/presences");
        then: "should get presences by subject"
        with (response) {
            status == 200
            data.size > 0
        }
    }

    def "should edit presence" () {
        when: "try to edit presence"
        def response = client.put(path: "/api/presence/1",
                body: [
                        presence: presence,
                        status: status,
                ],
                requestContentType : JSON);
        then: "should return status 200 and edit presence"
        with(response) {
            status == 200
        }
        where:
        presence | status
        true    | 'NIEUSPR'
    }
}
