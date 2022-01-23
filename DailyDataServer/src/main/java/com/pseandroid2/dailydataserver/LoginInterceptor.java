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

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * #TODO javadoc,Test, implemetierung
 */

/**
 * Authenticates the user and adds the user id to the RequestAttributes.
 * Most of the requests get interrupted by this Interceptor.
 * <p>
 * The authentication works with firebase. The user name ist the uid from firebase.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //so halb getestet, ich hoffe, dass es funktioniert..
        InputStream inputStream = request.getInputStream();
        byte[] byteBody = StreamUtils.copyToByteArray(inputStream);
        String body = new String(byteBody); //müsste den body auslesen

        //aus dem Body herausparsen, was der Token ist: ganz ungetestet:
        //ich hab jetzt mal eifnach aus dem wissen heraus agiert, dass der token immer ganz vorne steht..., kann man sicher hüscher machen

        //Ich hoffe, dass der Token mit token= eingeleitet wird, und nach dem Token eine } kommt....
        int indexofTokenstart = body.indexOf("token")+6;
        String splitted = body.substring(indexofTokenstart);
        int indexOfTokenEnd =  splitted.indexOf("}");
        String firebaseToken = splitted.substring(0,indexOfTokenEnd).trim();

        // Firebase auth
        String name = "TODO"; // firebasetoken.getUid(); Bitte austauschen, sobald firebase steht.
        request.setAttribute("user", name);
        return HandlerInterceptor.super.preHandle(request, response, handler);

    }
}
