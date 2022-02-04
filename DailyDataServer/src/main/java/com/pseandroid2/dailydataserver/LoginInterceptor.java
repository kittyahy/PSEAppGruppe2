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

import com.google.firebase.auth.FirebaseAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Authenticates the user and adds the user id to the RequestAttributes.
 * Most of the requests get interrupted by this Interceptor.
 * <p>
 * The authentication works with firebase. The user id ist the uid from firebase.
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private FirebaseManager firebaseManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        //only for testing:
        if (token.equals("MeinToken")) {
            request.setAttribute("user", request.getHeader("name"));
            return true;
        }
        String userID = "";
        // Firebase token authentication
        try {
            userID = firebaseManager.getUserIDFromToken(token);
        } catch (FirebaseAuthException e) {
            return false;
        }
        // Update attribute with the computed UserID
        request.setAttribute("user", userID);
        System.out.println(request.getAttribute("user"));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
