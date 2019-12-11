package com.ssz.framejava.account;

/**
 * @author : zsp
 * time : 2019 11 2019/11/12 13:00
 */
public final class TokenHolder {

    private String accessToken;
    private String session;

    public TokenHolder(String token, String session) {
        this.accessToken = token;
        this.session = session;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
