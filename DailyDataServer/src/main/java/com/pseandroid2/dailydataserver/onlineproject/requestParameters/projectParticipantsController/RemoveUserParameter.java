package com.pseandroid2.dailydataserver.onlineproject.requestParameters.projectParticipantsController;

import com.pseandroid2.dailydataserver.RequestParameter;

/**
 * #TODO Testen, JavaDoc
 */
public class RemoveUserParameter extends RequestParameter {
    private String userToRemove;

    /**
     *
     * @param token
     * @param userToRemove the user, which should be removed.
     */
    public RemoveUserParameter(String token, String userToRemove) {
        super(token);
        this.userToRemove = userToRemove;
    }

    public String getUserToRemove() {
        return userToRemove;
    }

    public void setUserToRemove(String userToRemove) {
        this.userToRemove = userToRemove;
    }

    @Override
    public String toString() {
        return "RemoveUserParameter{" +
                "userToRemove='" + userToRemove + '\'' +
                "} " + super.toString();
    }
}
