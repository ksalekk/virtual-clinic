package com.jsalek.pw.virtualclinic.security.authorization;

import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Endpoint {
    public static final HashSet<HttpMethod> ANY_METHOD = new HashSet<>(Arrays.asList(
            HttpMethod.POST,
            HttpMethod.GET,
            HttpMethod.PUT,
            HttpMethod.DELETE
    ));

    public static final HashSet<HttpMethod> GET = new HashSet<>(List.of(
            HttpMethod.GET
    ));

    public static final HashSet<HttpMethod> POST = new HashSet<>(List.of(
            HttpMethod.POST
    ));

    public static final HashSet<HttpMethod> GET_OR_DELETE = new HashSet<>(List.of(
            HttpMethod.GET,
            HttpMethod.DELETE
    ));

    public static final HashSet<HttpMethod> GET_OR_PUT = new HashSet<>(List.of(
            HttpMethod.GET,
            HttpMethod.PUT
    ));


    public String antPattern;
    public HashSet<HttpMethod> allowedMethods;


    public Endpoint(String antPattern, HashSet<HttpMethod> allowedMethods) {
        this.allowedMethods = allowedMethods;
        this.antPattern = antPattern;
        if(this.allowedMethods == null) {
            this.allowedMethods = ANY_METHOD;
        }
    }
}
