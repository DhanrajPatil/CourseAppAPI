/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Users;
import services.UserService;

/**
 * REST Web Service
 *
 * @author Dhanraj Patil
 */
@Path("users")
public class UserResource {

    @Context private UriInfo context;
    @PathParam("userId") Long userId;
    final private static UserService SERVICE = new UserService();
    
    public UserResource() {        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Users> getAllUsers() {
        List<Users> userList = new ArrayList<>(SERVICE.getAllUsers());
        return userList;
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        Users user = SERVICE.getUser(userId);
        if(user != null){
            return Response.ok(user).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUsers(Users user) {
        user = SERVICE.saveUser(user);
        if(user != null){
            URI url = context.getAbsolutePathBuilder().path(Long.toString(user.getUserId())).build();
            return Response.created(url).entity(user).build();
        }else{
            return Response.status(Response.Status.REQUEST_TIMEOUT).build();
        }
    }
    
    @PUT
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsers(Users user) {
        user.setUserId(userId);
        user = SERVICE.updateUser(user);
        if(user != null){
            return Response.status(Response.Status.ACCEPTED).entity(user).build();
        }else{
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
    
    @DELETE
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsers() {
        Users user = SERVICE.deleteUser(userId);
        if(user != null){
            return Response.status(Response.Status.ACCEPTED).entity(user).build();
        }else{
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
}
