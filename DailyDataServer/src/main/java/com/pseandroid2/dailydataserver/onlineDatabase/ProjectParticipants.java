package com.pseandroid2.dailydataserver.onlineDatabase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

/**
 * #TODO JavaDoc
 */
@Entity
@Table(name="ProjectParticipants_Table")
@IdClass(ProjectParticipantsID.class)
public class ProjectParticipants {
    private @Id String user;
    private @Id long project;
    private Role role;
    private int numberOfJoin;

    public ProjectParticipants(String user, long project, Role role, int numberOfJoin) {
        this.user = user;
        this.project = project;
        this.role = role;
        this.numberOfJoin = numberOfJoin;
    }

    public ProjectParticipants() {
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getProject() {
        return project;
    }

    public void setProject(long project) {
        this.project = project;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getNumberOfJoin() {
        return numberOfJoin;
    }

    public void setNumberOfJoin(int numberOfJoin) {
        this.numberOfJoin = numberOfJoin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectParticipants that = (ProjectParticipants) o;
        return project == that.project && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, project);
    }
}
