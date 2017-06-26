/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.net.URI;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Users;
import services.UserService;

/**
 * REST Web Service
 *
 * @author Dhanraj Patil
 */
@Path("authorize")
public class AuthorizeResource {
    
    private static final UserService SERVICE = new UserService();
    
    @Context private UriInfo context;
    private @HeaderParam("Authorization") String authKey;

    public AuthorizeResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response login() {
        if(authKey != null){
            Users user = SERVICE.login(authKey);
            if(user != null){
                user.setEmailId("");
                user.setPassword("");
                System.out.println("Got the user UserId: " + user.getUserId() + " " + user.getFirstName());
                return Response.status(Response.Status.OK).entity(user).build();
            }
        }
        System.out.println("did'nt get user ");
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signUp(Users user) {
        if(authKey != null){
            user = SERVICE.signUp(user, authKey);     
            if( user != null){
                user.setEmailId("");
                user.setPassword("");
                URI url = context.getAbsolutePathBuilder().path(Long.toString(user.getUserId())).build();
                return Response.created(url).entity(user).build();
            }
        }
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .build();
    }
    
    @GET
    @Path("/emailIdCheck")
    public Response emailIdCheck(@QueryParam("emailId") String emailId, @QueryParam("context") String context){
        Boolean isValid = SERVICE.checkEmailId(emailId, context);
        if( isValid ){
            return Response
                .status(Response.Status.ACCEPTED)
                .build();
        }
        else{
            return Response
                .status(Response.Status.NOT_ACCEPTABLE)
                .build();
        }
    }
}
