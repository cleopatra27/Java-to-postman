package com.javatopostman.controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

public class Test {

    @Path("/test")
    @Produces()
    @POST
    public Response doTest(Response data){
        return null;
    }

    // /base/test
}
