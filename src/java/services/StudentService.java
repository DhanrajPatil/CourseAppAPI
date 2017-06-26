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
import models.Student;
import models.StudentCourse;
import models.Teacher;
import models.UserRole;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.HibernateUtil;

/**
 *
 * @author Dhanraj Patil
 */
public class StudentService {
    private Session session;

    public StudentService() {
        session = null;
    }

    public List<Student> getAllStudents() {
        System.out.print("getAllStudents Service Called");
        List<Student> list = new ArrayList<>();
        List<Student> studList = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Student");
            list = query.list();
            for( Student stud: list){
                stud.setPassword("");
                stud.setEmailId("");
                studList.add(stud);
            }
        } catch (Exception e) {
            System.out.println("Error Occured during retriving studnets ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return studList;
    }

    public Student saveStudent(Student stud) {
        System.out.print("saveStudent Service Called");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            stud.setUserRole(UserRole.STUDENT);
            Transaction tx = session.beginTransaction();
            session.save(stud);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error Occured during saving Student ");
            stud = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return stud;
    }

    public Student updateStudent(Student stud) {
        System.out.print("updateStudent Service Called");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            stud.setUserRole(UserRole.STUDENT);
            Transaction tx = session.beginTransaction();
            session.update(stud);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error Occured during updating Student ");
            stud = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return stud;
    }

    public Student getStudent(Long studId) {
        System.out.print("getStudent Service Called");
        Student stud = new Student();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            stud = (Student) session.get(Student.class, studId);
            stud.setPassword("");
            stud.setEmailId("");
        } catch (Exception e) {
            System.out.println("Error Occured during retriving user ");
            stud = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return stud;
    }

    public Student deleteStudent(Long studId) {
        System.out.print("deleteUser Service Called");
        Student stud = new Student();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            stud = (Student) session.get(Student.class, studId);
            session.delete(stud);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error Occured during deleting user");
            stud = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return stud;
    }
    
    public Set<StudentCourse> getCourses(Long studId){
        System.out.print("getCourses for student Service Called");
        Set<StudentCourse> courses = new HashSet<>();
        Set<StudentCourse> studCoursesList = new HashSet<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Student stud = (Student) session.get(Student.class, studId);
            Hibernate.initialize(stud.getStudCourses());
            courses = stud.getStudCourses();
            for(StudentCourse studCourse : courses){
                studCourse.getCourse().getTeacher().setPassword("");
                studCourse.getCourse().getTeacher().setEmailId("");
                studCourse.getStudent().setPassword("");
                studCourse.getStudent().setEmailId("");
                studCoursesList.add(studCourse);
            }
        } catch (Exception e) {
            courses = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return studCoursesList;
    }
    
//    public List<Teacher> getTeachers(Long studId){
//        System.out.print("getTeachers for student Service Called");
//        List<Teacher> teachers = new ArrayList<>();
//        Set<StudentCourse> courses = getCourses(studId);
//        for(StudentCourse studCourse: courses){
//            teachers.add(studCourse.getCourse().getTeacher());
//        }
//        return teachers;
//    }
    
    public StudentCourse applyCourse(StudentCourse studCourse){
        System.out.print("applyCourse Service Called");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(studCourse);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error Occured during saving Student course ");
            studCourse = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return studCourse;
    }
}
