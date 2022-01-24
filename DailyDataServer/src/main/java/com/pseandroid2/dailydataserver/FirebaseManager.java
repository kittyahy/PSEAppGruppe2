package com.pseandroid2.dailydataserver;

public class FirebaseManager {
    private FirebaseOptions firebaseOptions;

    FirebaseManager() {
        firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build();

        FirebaseApp.initializeApp(options);
    }

    /**
     * @param authToken: The authentication Token
     * @return String: The UserID of the user who send this token. Returns "" on error / invalid token
     */
    public String getUserIDFromToken(String authToken) {





        return "";
    }
}
