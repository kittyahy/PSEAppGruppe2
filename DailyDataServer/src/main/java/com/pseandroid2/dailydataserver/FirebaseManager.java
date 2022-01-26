package com.pseandroid2.dailydataserver;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FirebaseManager {
    private FirebaseOptions firebaseOptions;

    FirebaseManager() {
        try {
            ClassPathResource res = new ClassPathResource("daily-data-69b6a-firebase-adminsdk-skzcn-f851c90c36.json");
            firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(res.getInputStream()))
                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param authToken: The authentication Token
     * @return String: The UserID of the user who send this token. Returns "" on error / invalid token
     */
    public String getUserIDFromToken(String authToken) {


        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(authToken);
            return firebaseToken.getUid();
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        return "";
    }
}
