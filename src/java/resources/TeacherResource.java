/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
import models.Course;
import models.Teacher;
import services.TeacherService;

/**
 * REST Web Service
 *
 * @author Dhanraj Patil
 */
@Path("teachers")
public class TeacherResource {

    @Context private UriInfo context;
    @PathParam("teacherId") Long teacherId;
    final private static TeacherService SERVICE = new TeacherService();
    
    public TeacherResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Teacher> getAllTeachers() {
        return SERVICE.getAllTeaches();
    }

    @GET
    @Path("/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher() {
        Teacher teacher = SERVICE.getTeacher(teacherId);
        if(teacher != null){
            return Response.ok(teacher).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveTeacher(Teacher teacher) {
        teacher = SERVICE.saveTeacher(teacher);
        if(teacher != null){
            URI url = context.getAbsolutePathBuilder().path(Long.toString(teacher.getUserId())).build();
            return Response.created(url).entity(teacher).build();
        }else{
            return Response.status(Response.Status.REQUEST_TIMEOUT).build();
        }
    }
    
    @PUT
    @Path("/{teacherId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeacher(Teacher teacher) {
        teacher.setUserId(teacherId);
        teacher = SERVICE.updateTeacher(teacher);
        if(teacher != null){
            return Response.status(Response.Status.ACCEPTED).entity(teacher).build();
        }else{
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
    
    @DELETE
    @Path("/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeacher() {
        Teacher teacher = SERVICE.deleteTeacher(teacherId);
        if(teacher != null){
            return Response.status(Response.Status.ACCEPTED).entity(teacher).build();
        }else{
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
    
    @GET
    @Path("/{teacherId}/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Course> getCourses(){
        return SERVICE.getCourses(teacherId);
    }
}
