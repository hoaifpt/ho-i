package com.school_medical.school_medical_management_system.services.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.school_medical.school_medical_management_system.config.JwtUtil;
import com.school_medical.school_medical_management_system.repositories.IUserRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleAuthService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, Object> processGoogleLogin(String googleToken) throws Exception {
        // Verify Google token
        GoogleIdToken.Payload payload = verifyGoogleToken(googleToken);
        if (payload == null) {
            throw new Exception("Token Google không hợp lệ");
        }

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        // Kiểm tra user đã tồn tại chưa
        Appuser user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new Exception("Tài khoản chưa được đăng ký");
        }

        // Tạo JWT
        String jwt = jwtUtil.generateToken(email, user.getRoleName(), user.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("jwt", jwt);
        result.put("name", user.getFirstName() + " " + user.getLastName());
        result.put("role", user.getRoleName());
        result.put("userId", user.getId());

        return result;
    }

    private GoogleIdToken.Payload verifyGoogleToken(String token) {
        try {
            final NetHttpTransport transport = new NetHttpTransport();
            final JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList("493912650211-kqoj7t293bdhfgepv1q7kh7vik3o0852.apps.googleusercontent.com")) // 🔁 Replace this!
                    .build();

            GoogleIdToken idToken = verifier.verify(token);
            return (idToken != null) ? idToken.getPayload() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
