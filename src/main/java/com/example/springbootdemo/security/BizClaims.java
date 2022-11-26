package com.example.springbootdemo.security;

import io.jsonwebtoken.Claims;
import java.util.HashMap;

/**
 * A class that holds application-specific information.
 */
public class BizClaims extends HashMap<String, Object> {

    private static final String USERNAME = "username";

    public static BizClaims getBizClaimsFromClaims(Claims claims) {
        BizClaims bizClaims = new BizClaims();
        bizClaims.put(USERNAME, claims.get(USERNAME));
        return bizClaims;
    }

    public String getUsername() {
        return (String) get(USERNAME);
    }
}
