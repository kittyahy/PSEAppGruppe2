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
package com.pseandroid2.dailydataserver.onlineDatabase;

import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantID;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Interceptor. Checks if the user may access to the given project.
 * <p>
 * if not, rejects the request and the response is empty.
 * <p>
 * Gets called before all methods, which depends on an existing project and the user wants to change something or
 * wants information.
 */
@Component
public class AccessToProjectInterceptor implements HandlerInterceptor {

    @Autowired
    private ProjectParticipantsRepository repo;

    public AccessToProjectInterceptor(ProjectParticipantsRepository repo) { //muss nicht übergeben werden.
        this.repo = repo;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String user = (String) request.getAttribute("user");
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        String projectIDString = pathVariables.get("id").toString();

        long projectId = Long.parseLong(projectIDString);
        if (!repo.existsById(new ProjectParticipantID(user, projectId))) {
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
