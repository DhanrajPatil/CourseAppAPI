/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import models.Course;
import models.StudentCourse;
import models.Teacher;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.HibernateUtil;

/**
 *
 * @author Dhanraj Patil
 */
public class CourseService {
    private Session session;
    
    public CourseService(){
        session = null;
    }
    
    public List<Course> getCourses(){
        List<Course> list = new ArrayList<>();
        List<Course> courseList = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Course");
            list = query.list();
            for(Course course: list){
                course.getTeacher().setPassword("");
                course.getTeacher().setEmailId("");
                courseList.add(course);
            }
        } catch (Exception e) {
            System.out.println("Error Occured during retriving course ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return courseList;
    }
    
    public Course getCourse(long courseId){
        Course course = new Course();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            course = (Course) session.get(Course.class, courseId);
            course.getTeacher().setPassword("");
            course.getTeacher().setEmailId("");
        }
        catch(Exception e){
            course = null;
            e.printStackTrace();
        }
        finally{
            session.close();
        }        
        return course;
    }
    
    public Course updateCourse(long courseId, Course course){
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            course.setCourseId(courseId);
            session.update(course);
            tx.commit();
        }
        catch(Exception e){
            course = null;
            System.out.println("Error Occured" + e);
        }
        finally{
            session.close();
        }
        return course;
    }
    
    public Course createCourse(Course course){
        try{
            if(course.getEndDate() == null){
                System.out.println(" End date is null");
                Calendar calendar = new GregorianCalendar(2019, 4, 21);
                course.setEndDate(calendar.getTime());
            }
            if(course.getStartDate() == null){
                System.out.println(" start date is null");
                Calendar calendar = new GregorianCalendar(2017, 2, 20);
                Date date = calendar.getTime();
                course.setStartDate(date);
            }
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Teacher teacher = (Teacher) session.get(Teacher.class, course.getTeacher().getUserId());
            teacher.setNoOfCoursesThought(course.getTeacher().getNoOfCoursesThought());
            session.update(teacher);
            session.save(course);
            tx.commit();
        }
        catch(Exception e){
            course = null;
            System.out.println("Error Occured" + e);
        }
        finally{
            session.close();
        }
        return course;
    }
    
    public Course deleteCourse(long courseId){
        Course course = new Course();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            course = (Course) session.get(Course.class, courseId);
            session.delete(course);
            tx.commit();
        }
        catch(Exception e){
            course = null;
            System.out.println("Error Occured " + e);
        }
        finally{
            session.close();
        }
        return course;
    }
    
    public Course saveCourse(long courseId, Course course){
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            course.setCourseId(courseId);
            Transaction tx = session.beginTransaction();
            session.save(course);
            tx.commit();
        }
        catch(Exception e){
            System.err.println("Error occured " + e);
            course = null;
        }
        finally{
            session.close();
        }
        return course;
    }
    
    public Set<StudentCourse> getStudents(long courseId){
        Set<StudentCourse> studCourses;
        Set<StudentCourse> studCoursesList = new HashSet<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Course course = (Course) session.get(Course.class, courseId);
            Hibernate.initialize(course.getStudCourses());
            studCourses = course.getStudCourses();
            for(StudentCourse studCourse : studCourses){
                studCourse.getCourse().getTeacher().setPassword("");
                studCourse.getCourse().getTeacher().setEmailId("");
                studCourse.getStudent().setPassword("");
                studCourse.getStudent().setEmailId("");
                studCoursesList.add(studCourse);
            }
        }
        catch(Exception e){
            System.err.println("Error occured " + e);
        }
        finally{
            session.close();
        }
        return studCoursesList;
    }
}
