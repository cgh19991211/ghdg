package com.gh.ghdg.businessMgr.bean.entities.sub;

public class Response {
    String responseId;
    
    public Response() {
    }
    
    public Response(String responseId) {
        this.responseId = responseId;
    }
    
    public String getResponseId() {
        return responseId;
    }
    
    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }
    
    @Override
    public String toString() {
        return "Response{" +
                "responseId='" + responseId + '\'' +
                '}';
    }
}
