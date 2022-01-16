package com.pseandroid2.dailydataserver.onlineproject;

import javax.persistence.IdClass;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;


public class DeltaID  implements Serializable {
    private Date addedToServer;
    private String user;

    public DeltaID(){}

    public DeltaID(Date addedToServer, String user){
        this.addedToServer = addedToServer;
        this.user = user;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o== null || this.getClass()!= o.getClass()) return false;
        DeltaID deltaID =(DeltaID) o;
        return addedToServer.equals(deltaID.addedToServer) && user.equals(deltaID.user);

    }

    @Override
    public int hashCode(){
        return Objects.hash(addedToServer,user);
    }

}
