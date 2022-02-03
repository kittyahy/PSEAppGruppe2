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
        String token = request.getHeader("token"); //da der Token jetzt in den Header kommt.
//only for test: name als Header falls ein bestimmter usergebraucht ist
        if(token.equals("MeinToken")){
            request.setAttribute("user", request.getHeader("name"));
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        //Ende Test workaround

        
        // Firebase token authentication
        String userID = firebaseManager.getUserIDFromToken(token);

        // Update attribute with the computed UserID
        request.setAttribute("user", userID);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
