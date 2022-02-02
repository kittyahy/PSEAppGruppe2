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

import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.Project;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipant;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantID;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantsRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.Role;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The Service for the ProjectParticipantsController which provides the logic.
 * <p>
 * It also generates the ids for projects.
 */
@Service
public class ProjectParticipantService {
    private static final int MAX_PARTICIPANTS = 24;
    private final ProjectParticipantsRepository ppRepo;
    private final ProjectRepository projectRepo;
    private long projectIDGenerator;

    /**
     * The Constructor for the ProjectParticipantsService.
     *
     * @param ppRepo      the ProjectParticipantsRepository, which provides operations to add and remove
     *                    ProjectParticipants.
     * @param projectRepo the projectRepository, to create, update and delete projects.
     */
    public ProjectParticipantService(ProjectParticipantsRepository ppRepo, ProjectRepository projectRepo) {
        this.ppRepo = ppRepo;
        this.projectRepo = projectRepo;
        this.projectIDGenerator = 1;
    }


    /**
     * a new project is going to be created. The user, who creates the project gets the admin.
     *
     * @param user        the user, who creates the project.
     * @param projectInfo the initial for the project.
     * @return the id for the new project.
     */
    public long addProject(String user, String projectInfo) {
        Project project = new Project(projectIDGenerator, projectInfo);
        projectIDGenerator++;
        projectRepo.save(project);
        ppRepo.save(new ProjectParticipant(user, project.getProjectId(), Role.ADMIN, project.getParticipantJoin()));
        project.setParticipantJoin(project.getParticipantJoin() + 1);
        projectRepo.save(project);
        return project.getProjectId();
    }

    /**
     * Adds a user to a project, if the project has less than MAX_PARTICIPANTS participants.
     * If the user already participates the project, they also get the projectInitial.
     *
     * @param user      the user who wants to join a project.
     * @param projectID the project, to which the user wants to join.
     * @return an empty String, if the user can not join, the project initial, if the user joined the project.
     */
    public String addUser(String user, long projectID) {
        if (ppRepo.existsById(new ProjectParticipantID(user, projectID))) {
            return projectRepo.findById(projectID).get().getProjectInfo();
        } else {
            if (!(ppRepo.countByProject(projectID) < MAX_PARTICIPANTS)) {
                return "";
            } else {
                Project project = projectRepo.findById(projectID).get();
                ppRepo.save(new ProjectParticipant(user, projectID, Role.PARTICIPANT, project.getParticipantJoin()));

                project.setParticipantJoin(project.getParticipantJoin() + 1);
                project.setLastUpdated(LocalDateTime.now());
                projectRepo.save(project);

                return project.getProjectInfo();
            }
        }
    }

    /**
     * a user leaves the project. If the admin leaves the project, the participant going to be the new admin, which
     * participates for the longest time in the project.
     * If the admin was the last person in the project, the project gets deleted.
     * If the project still exists, it gets updated.≈
     *
     * @param user      the user, who wants to leave the project.
     * @param projectId the project, which the user wants to leave.
     * @return if the user could leave the project.
     */
    public boolean leaveProject(String user, long projectId) {
        ProjectParticipant participant = ppRepo.findById(new ProjectParticipantID(user, projectId)).get();
        if (participant.getRole().equals(Role.ADMIN)) {
            if (1 == ppRepo.countByProject(projectId)) {
                projectRepo.deleteById(projectId);

            } else {
                List<ProjectParticipant> projectParticipantList = ppRepo.findByProjectOrderByNumberOfJoinAsc(projectId);
                projectParticipantList.get(1).setRole(Role.ADMIN);
                ppRepo.save(projectParticipantList.get(1));

                ppRepo.deleteById(new ProjectParticipantID(user, projectId));
                Project projectToUpdate = projectRepo.getById(projectId);
                projectToUpdate.setLastUpdated(LocalDateTime.now());
                projectRepo.save(projectToUpdate);
            }
        } else {
            ppRepo.deleteById(new ProjectParticipantID(user, projectId));
            Project projectToUpdate = projectRepo.getById(projectId);
            projectToUpdate.setLastUpdated(LocalDateTime.now());
            projectRepo.save(projectToUpdate);
        }

        return true;
    }

    /**
     * The admin can remove another user from their project.
     * <p>
     * If the user, which should be removed do not participate in the project, the method returns true as well.
     * <p>
     * This method checks, if the user, who wants to remove another user, is admin.
     *
     * @param user         the user, who wants to remove a user.
     * @param projectId    the project where the user should be removed from.
     * @param userToRemove the user, who is going to be removed.
     * @return true, if the user could get removed, false, if the user can not get removed.
     */
    public boolean removeOtherUser(String user, long projectId, String userToRemove) {
        ProjectParticipant participant = ppRepo.findById(new ProjectParticipantID(user, projectId)).get();
        if (!participant.getRole().equals(Role.ADMIN)) {
            return false;
        } else {
            if (ppRepo.existsById(new ProjectParticipantID(userToRemove, projectId))) {
                ppRepo.deleteById(new ProjectParticipantID(userToRemove, projectId));
            }
            Project projectToUpdate = projectRepo.getById(projectId);
            projectToUpdate.setLastUpdated(LocalDateTime.now());
            projectRepo.save(projectToUpdate);
            return true;
        }
    }

    /**
     * returns all users, who participate on a project. It also returns the admin.
     *
     * @param projectId the projectId, from which the user where recommended.
     * @return all users, who participates in the project.
     */
    public List<String> getParticipants(long projectId) {
        List<ProjectParticipant> participantsList = ppRepo.findByProject(projectId);
        List<String> participantName = new ArrayList<>();
        for (ProjectParticipant participant : participantsList) {
            participantName.add(participant.getUser());
        }
        return participantName;
    }

    /**
     * Provides the admin of a specified project.
     *
     * @param projectId the project, from which the admin is recommended.
     * @return the admin.
     */
    public String getAdmin(long projectId) {
        return ppRepo.findByProjectAndRoleIs(projectId, Role.ADMIN).getUser();
    }
}
