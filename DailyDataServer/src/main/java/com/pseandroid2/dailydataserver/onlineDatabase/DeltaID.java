package com.pseandroid2.dailydataserver.onlineDatabase;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
/**
 * #TODO JavaDoc
 */
public class DeltaID  implements Serializable {
    private LocalDateTime addedToServer;
    private String user;

    public DeltaID(){}

    public DeltaID(LocalDateTime addedToServer, String user){
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
