package com.students.security.jwt;

public class JwtToken {
    private final String tokenAsString;
    private final String username;

    /**
     * Package-private. Use JwtTokenProvider to create an instance;
     *
     * @param tokenAsString
     * @param username
     */
    JwtToken(String tokenAsString, String username) {
        this.tokenAsString = tokenAsString;
        this.username = username;
    }

    public String getTokenAsString() {
        return tokenAsString;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "JwtToken{" +
                "tokenAsString='" + tokenAsString + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
