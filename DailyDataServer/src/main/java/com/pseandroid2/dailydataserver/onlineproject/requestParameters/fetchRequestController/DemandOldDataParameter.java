package com.pseandroid2.dailydataserver.onlineproject.requestParameters.fetchRequestController;

import com.pseandroid2.dailydataserver.RequestParameter;

public class DemandOldDataParameter extends RequestParameter {
    private String requestInfo;

    @Override
    public String toString() {
        return "DemandOldData{" +
                "requestInfo='" + requestInfo + '\'' +
                "} " + super.toString();
    }

    /***
     *
     * @param token       the token, to verify the user, provided by the client
     * @param requestInfo the request, which is usefull for another user. Contains information for recommended Delta, provided by the client
     */
    public DemandOldDataParameter(String token, String requestInfo) {
        super(token);
        this.requestInfo = requestInfo;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }
}
