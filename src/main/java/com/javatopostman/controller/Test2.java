package com.javatopostman.controller;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

public class Test2 {

    @Path("/test2")
    @Produces()
    @PUT
    public Response doTest2(){
        return null;
    }

}
