/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.net.URI;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Course;
import models.StudentCourse;
import services.CourseService;

/**
 * REST Web Service
 *
 * @author Dhanraj Patil
 */
@Path("courses")
public class CourseResource {

    @Context
    private UriInfo context;
    @PathParam("courseId") Long courseId;
    final private static CourseService SERVICE = new CourseService();

    /**
     * Creates a new instance of CourseResource
     */
    public CourseResource() {
    }

    /**
     * Retrieves representation of an instance of resources.CourseResource
     * @return an instance of java.util.List which will contain set of Course Resource
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> getCourses() {
        return SERVICE.getCourses();
    }

    /**
     * PUT method for updating or creating an instance of CourseResource
     * @param course representation for the resource
     */
    @PUT
    @Path("/{courseId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCourse(Course course) {
        course = SERVICE.updateCourse(courseId, course);
        if(course != null){
            return Response.status(Response.Status.ACCEPTED).entity(course).build();            
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }
    
    @GET
    @Path("/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourse(){
        Course course = SERVICE.getCourse(courseId);
        if(course != null){
            return Response.status(Response.Status.OK).entity(course).build();            
        }
        return  Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCourse(Course course){
        course = SERVICE.createCourse(course);
        if(course != null){
            URI url = context.getAbsolutePathBuilder().path(Long.toString(course.getCourseId())).build();
            return Response.created(url).entity(course).build();       
        }
        return  Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    
    @DELETE
    @Path("/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCourse(){
        Course course = SERVICE.deleteCourse(courseId);
        if(course != null){
            return Response.status(Response.Status.ACCEPTED).entity(course).build();            
        }
        return  Response.status(Response.Status.NOT_MODIFIED).build();
    }
    
    @GET
    @Path("/{courseId}/students")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<StudentCourse> getStudents(){
        return SERVICE.getStudents(courseId);
    }
}
