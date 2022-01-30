package com.pseandroid2.dailydataserver.onlineDatabase;

import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectParticipantService {
    private static final int MAX_PARTICIPANTS = 24;
    private final ProjectParticipantsRepository ppRepo;
    private final ProjectRepository projectRepo;
    private long projectIDGenerator;

    public ProjectParticipantService(ProjectParticipantsRepository ppRepo, ProjectRepository projectRepo) {
        this.ppRepo = ppRepo;
        this.projectRepo = projectRepo;
        projectIDGenerator = 1;
    }


    public long addProject(String user, String projectInfo) {
        Project project = new Project(projectIDGenerator, projectInfo);
        projectIDGenerator++;
        projectRepo.save(project);
        ppRepo.save(new ProjectParticipants(user, project.getProjectId(), Role.ADMIN, project.getParticipantId()));
        project.setParticipantId(project.getParticipantId()+1);
        projectRepo.save(project);
        return project.getProjectId();
    }

    public String addUser(String user, long projectID) {
        if (ppRepo.existsById(new ProjectParticipantsID(user, projectID))) {
            return projectRepo.findById(projectID).get().getProjectInfo();
        } else {
            if (!(ppRepo.countByProject(projectID) < MAX_PARTICIPANTS)) {
                return "";
            } else {
                Project project = projectRepo.findById(projectID).get();
                ppRepo.save(new ProjectParticipants(user, projectID, Role.PARTICIPANT, project.getParticipantId()));

                project.setParticipantId(project.getParticipantId()+1);
                projectRepo.save(project);
                System.out.println(ppRepo.findById(new ProjectParticipantsID(user,projectID)).get());

                return project.getProjectInfo() ;
            }

        }
    }

    public boolean leaveProject(String user, long projectId) {
        System.out.println("Gleicher");
        ProjectParticipants participant = ppRepo.findById(new ProjectParticipantsID(user, projectId)).get();
        System.out.println(participant);
        if (participant.getRole().equals(Role.ADMIN)) {
            if (ppRepo.countByProject(projectId) == 1) {
                projectRepo.deleteById(projectId);
                System.out.println("Project gelöscht");

            } else {
                List<ProjectParticipants> projectParticipantsList = ppRepo.findByProjectOrderByNumberOfJoinAsc(projectId);
                projectParticipantsList.get(1).setRole(Role.ADMIN);
                System.out.println(projectParticipantsList);
                System.out.println("neuer Admin: "+projectParticipantsList.get(1));

                Project projectToUpdate = projectRepo.getById(projectId);
                projectToUpdate.setLastUpdated(LocalDateTime.now());
                projectRepo.save(projectToUpdate);
            }
        }
        ppRepo.deleteById(new ProjectParticipantsID(user, projectId));

        return true;
    }

    public boolean removeOtherUser(String user, long projectId, String userToRemove) {
        System.out.println("Anderer");
        ProjectParticipants participant = ppRepo.findById(new ProjectParticipantsID(user, projectId)).get();
        if (!participant.getRole().equals(Role.ADMIN)) {
            return false;
        } else {
            ppRepo.deleteById(new ProjectParticipantsID(userToRemove, projectId));
            Project projectToUpdate = projectRepo.getById(projectId);
            projectToUpdate.setLastUpdated(LocalDateTime.now());
            projectRepo.save(projectToUpdate);
            return true;
        }
    }

    public List<String> getParticipants(long projectId) {
        List<ProjectParticipants> participantsList = ppRepo.findByProject(projectId);
        List<String> participantName = new ArrayList<>();
        for (ProjectParticipants participant : participantsList) {
            participantName.add(participant.getUser());
        }
        return participantName;
    }

    public String getAdmin(long projectId) {
        return ppRepo.findByProjectAndRoleIs(projectId, Role.ADMIN).getUser();
    }
}
