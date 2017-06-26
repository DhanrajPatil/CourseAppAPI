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
import models.Student;
import models.StudentCourse;
import services.StudentService;

/**
 * REST Web Service
 *
 * @author Dhanraj Patil
 */
@Path("students")
public class StudentResource {

    @Context private UriInfo context;
    @PathParam("studentId") Long studentId;
    final private static StudentService SERVICE = new StudentService();

    /**
     * Creates a new instance of StudentResource
     */
    public StudentResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getAllStudents() {
        List<Student> studList = new ArrayList<>(SERVICE.getAllStudents());
        return studList;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveStudent(Student stud) {
        stud = SERVICE.saveStudent(stud);
        if(stud != null){
            URI url = context.getAbsolutePathBuilder().path(Long.toString(stud.getUserId())).build();
            return Response.created(url).entity(stud).build();
        }else{
            return Response.status(Response.Status.REQUEST_TIMEOUT).build();
        }
    }
    
    @PUT
    @Path("/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(Student stud) {
        stud.setUserId(studentId);
        stud = SERVICE.updateStudent(stud);
        if(stud != null){
            return Response.status(Response.Status.ACCEPTED).entity(stud).build();
        }else{
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
    
    @GET
    @Path("/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent() {
        Student stud = SERVICE.getStudent(studentId);
        if(stud != null){
            return Response.ok(stud).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @DELETE
    @Path("/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent() {
        Student stud = SERVICE.deleteStudent(studentId);
        if(stud != null){
            return Response.status(Response.Status.ACCEPTED).entity(stud).build();
        }else{
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
    
    @GET
    @Path("/{studentId}/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<StudentCourse> getCourses(){
        return SERVICE.getCourses(studentId);
    }
    
//    @GET  // unusable as all courses will have teacher object so teacher can be obteained from course
//    @Path("/{studentId}/teachers")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Teacher> getTeachers(){
//        return SERVICE.getTeachers(studentId);
//    }
    
    @POST
    @Path("/{studentId}/applyCourse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response applyCourse(StudentCourse studCourse){
        studCourse = SERVICE.applyCourse(studCourse);
        if(studCourse != null){
            System.out.println("APlly Service is called " + studCourse.getCourse().getCourseName());
            URI url = context.getAbsolutePathBuilder().path(Long.toString(studCourse.getStudCourseId())).build();
            return Response.created(url).entity(studCourse).build();
        }else{
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
}
