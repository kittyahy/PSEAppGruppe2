package com.pseandroid2.dailydataserver.onlineDatabase;

import java.io.Serializable;
import java.util.Objects;

/**
 * #TODO JavaDoc
 */

public class ProjectParticipantsID implements Serializable {
    private String user;
    private long project;


    public ProjectParticipantsID(String user, long project) {
        this.user = user;
        this.project = project;
    }

    public ProjectParticipantsID() {
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o== null || this.getClass()!= o.getClass()) return false;
        ProjectParticipantsID proPaId =(ProjectParticipantsID) o;
        return user.equals(proPaId.user) && proPaId.project == project;
    }

    @Override
    public int hashCode(){
        return Objects.hash(project,user);
    }
}
