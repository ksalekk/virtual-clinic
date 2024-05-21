package com.jsalek.pw.virtualclinic.security.authorization;

import com.jsalek.pw.virtualclinic.security.user.Principal;
import com.jsalek.pw.virtualclinic.security.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class AuthGuard {

    private final HashSet<Endpoint> whitelist;
    private final HashMap<Role, HashSet<Endpoint>> restricted;

    private final PathMatcher antPathMatcher;


    @Autowired
    public AuthGuard(AntPathMatcher antPathMatcher) {
        this.antPathMatcher = antPathMatcher;
        this.whitelist = whitelistInit();
        this.restricted = restrictedInit();
    }




    public boolean isEndpointWhitelisted(String endpoint) {
        for(Endpoint whitelistedEndpoint : whitelist) {
            if(this.antPathMatcher.match(whitelistedEndpoint.antPattern, endpoint)) {
                return true;
            }
        }
        return false;
    }


    public boolean hasProperRole(HttpMethod method, String endpoint, Role role) {
        for(Endpoint restrictedEndpoint : this.restricted.get(role)) {
            if(this.antPathMatcher.match(restrictedEndpoint.antPattern, endpoint)
                    && restrictedEndpoint.allowedMethods.contains(method)
            ) {
                return true;
            }
        }
            return false;
    }

    public boolean checkIdorOccurence(Principal principal, Role targetRole, Long requestId) {
        return principal.role() == targetRole & principal.id() != requestId;
    }



    private HashSet<Endpoint> whitelistInit() {
        return new HashSet<>(Arrays.asList(
                new Endpoint("/h2-console/**", Endpoint.ANY_METHOD),
                new Endpoint("/auth/**", Endpoint.ANY_METHOD)
        ));
    }


    private HashMap<Role, HashSet<Endpoint>> restrictedInit() {

        HashMap<Role, HashSet<Endpoint>> restrictedEndpoints = new HashMap<>();

        restrictedEndpoints.put(
                Role.PATIENT,
                new HashSet<>(List.of(
                        new Endpoint("/patients/*", Endpoint.GET_OR_PUT),
                        new Endpoint("/patients/*/details", Endpoint.GET),
                        new Endpoint("/patients/*/appointments/**", Endpoint.GET)
                ))
        );


        restrictedEndpoints.put(
                Role.DOCTOR,
                new HashSet<>(List.of(
                        new Endpoint("/doctors/*", Endpoint.GET_OR_PUT),
                        new Endpoint("/doctors/*/appointments/**", Endpoint.GET_OR_PUT)
                ))
        );


        restrictedEndpoints.put(
                Role.STAFF,
                new HashSet<>(List.of(
                        new Endpoint("/patients", Endpoint.GET),
                        new Endpoint("/patients/*", Endpoint.GET),
                        new Endpoint("/doctors", Endpoint.GET),
                        new Endpoint("/doctors/*", Endpoint.GET),
                        new Endpoint("/doctors/*/appointments", Endpoint.GET),
                        new Endpoint("/appointments", Endpoint.POST)
                ))
        );

        return restrictedEndpoints;
    }



}
