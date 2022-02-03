/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/
package com.pseandroid2.dailydataserver;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Logic for Firebase Tasks (Token authentication)
 */
@Slf4j
@Component
public class FirebaseManager {
    private FirebaseOptions firebaseOptions;

    /**
     * Constructor for the firebase Manager.
     */
    FirebaseManager() {
        try {
            ClassPathResource res = new ClassPathResource("daily-data-69b6a-firebase-adminsdk-skzcn-f851c90c36.json");
            firebaseOptions =
                    FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(res.getInputStream())).build();

            FirebaseApp.initializeApp(firebaseOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns the userID of the user that belongs to the authentication token
     *
     * @param authToken The authentication Token
     * @return String The UserID of the user who send this token. Returns "" on error / invalid token
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
