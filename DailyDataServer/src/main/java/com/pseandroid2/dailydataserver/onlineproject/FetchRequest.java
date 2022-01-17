package com.pseandroid2.dailydataserver.onlineproject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


/**
 * #TODO JavaDoc
 */
@Entity
@Table(name="FetchRequest_Table")
public class FetchRequest {

    private @Id @GeneratedValue int id;
    private String user;
    private long project;
    private String requestInfo;

    public FetchRequest(String user, long project, String requestInfo) {
        this.user = user;
        this.project = project;
        this.requestInfo = requestInfo;
    }

    public FetchRequest() {
    }

    public int getId() {
        return id;
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

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FetchRequest that = (FetchRequest) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
