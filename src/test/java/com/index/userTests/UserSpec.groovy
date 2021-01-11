package com.index.userTests

import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

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
        ''          | ''               | 'Rafal'   | 'Gebica' | 'ROLE_STUDENT' | 'testgebica' | 1
    }

    @Unroll
    def "should change password" () {
        when: "try to change password"
        def response = client.put(path: "/api/user-password/7",
                body: [
                        oldPassword: oldPassword,
                        newPassword: newPassword
                ],
                requestContentType : JSON);
        then: "should return status then 200"
        with(response) {
            status == 200
        }
        where:
        oldPassword     | newPassword
        'test'  | '12345'
    }

    def "should return list of students" () {
        when: "try to find students"
        def response = client.get(path: "/api/students");
        then: "should return 200 status and list of students"
        with (response) {
            status == 200
            data.size > 0
        }
    }

    def "should return list of parents" () {
        when: "try to find students"
        def response = client.get(path: "/api/all-parents");
        then: "should return 200 status and list of parents"
        with (response) {
            status == 200
            data.size > 0
        }
    }

    def "should delete list of users" () {
        when: "try to delete users"
        def response = client.delete(path: "/api/user/14,15");
        then: "should delete students"
        with (response) {
            status == 200
        }
    }
}
