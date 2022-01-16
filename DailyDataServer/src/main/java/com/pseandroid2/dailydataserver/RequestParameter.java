package com.pseandroid2.dailydataserver;

public  class RequestParameter {
    private String token;

  public  RequestParameter(String token){
      this.token = token;
  }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RequestParameter{" +
                "token='" + token + '\'' +
                '}';
    }
}
