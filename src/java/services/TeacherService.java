/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import models.Course;
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
public class TeacherService {
    private Session session;

    public TeacherService() {
        session = null;
    }

    public List<Teacher> getAllTeaches() {
        System.out.print("getAllTeaches Service Called");
        List<Teacher> teacherList = new ArrayList<>();
        List<Teacher> securedTeacherList = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Teacher");
            teacherList = query.list();
            session.close();
            for(Teacher teacher: teacherList){
                teacher.setPassword("");
                teacher.setEmailId("");
                securedTeacherList.add(teacher);
            }
        } catch (Exception e) {
            System.out.println("Error Occured during retriving data ");
            e.printStackTrace();
        } finally {
            if(session.isOpen())
                session.close();
        }
        return securedTeacherList;
    }

    public Teacher saveTeacher(Teacher tech) {
        System.out.print("saveTeacher Service Called");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(tech);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error Occured during saving tech ");
            tech = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tech;
    }

    public Teacher updateTeacher(Teacher tech) {
        System.out.print("updateTeacher Service Called");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(tech);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error Occured during updating tech ");
            tech = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tech;
    }

    public Teacher getTeacher(Long techId) {
        System.out.print("getTeacher Service Called");
        Teacher tech = new Teacher();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tech = (Teacher) session.get(Teacher.class, techId);
            tech.setEmailId("");
            tech.setPassword("");
        } catch (Exception e) {
            System.out.println("Error Occured during retriving tech ");
            tech = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tech;
    }

    public Teacher deleteTeacher(Long techId) {
        System.out.print("getTeacher Service Called");
        Teacher tech = new Teacher();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            tech = (Teacher) session.get(Teacher.class, techId);
            session.delete(tech);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error Occured during deleting tech");
            tech = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tech;
    }
    
    public Set<Course> getCourses(Long teacherId){
        Set<Course> courses = new HashSet<>();
        Set<Course> courseList = new HashSet<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Teacher tech = (Teacher) session.get(Teacher.class, teacherId);
            Hibernate.initialize(tech.getCourses());
            courses = tech.getCourses();
            for(Course course: courses){
                course.getTeacher().setPassword("");
                course.getTeacher().setEmailId("");
                courseList.add(course);
            }
            
        } catch (Exception e) {
            System.out.println("Error Occured during retrieving course");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return courseList;
    }
}
