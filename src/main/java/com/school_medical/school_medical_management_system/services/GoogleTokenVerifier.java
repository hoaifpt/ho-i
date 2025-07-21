package com.school_medical.school_medical_management_system.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.stereotype.Service;
import com.google.api.client.json.jackson2.JacksonFactory;


import java.util.Collections;

@Service
public class GoogleTokenVerifier {

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifier() {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList("493912650211-kqoj7t293bdhfgepv1q7kh7vik3o0852.apps.googleusercontent.com")) // Thay bằng ID của bạn
                .build();
    }

    public GoogleIdToken.Payload verify(String token) throws Exception {
        GoogleIdToken idToken = verifier.verify(token);
        return (idToken != null) ? idToken.getPayload() : null;
    }
}
