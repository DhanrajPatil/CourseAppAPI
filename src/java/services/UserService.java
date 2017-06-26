/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import models.Student;
import models.Teacher;
import models.UserRole;
import models.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.HibernateUtil;

/**
 *
 * @author Dhanraj Patil
 */
public class UserService {

    private Session session;
    private static final StudentService STUD_SERVICE = new StudentService();
    private static final TeacherService TEACHER_SERVICE = new TeacherService();
    final static private String AUTH_KEY_PREFIX = "Basic ";

    public UserService() {
        session = null;
    }

    public List<Users> getAllUsers() {
        System.out.print("getAllUsers Service Called");
        List<Users> list = new ArrayList<>();
        List<Users> userList = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Users");
            list = query.list();
            for( Users user: list){
                user.setEmailId("");
                user.setPassword("");
                userList.add(user);
            }
        } catch (Exception e) {
            System.out.println("Error Occured during retriving data ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userList;
    }

    public Users saveUser(Users user) {
        System.out.print("saveUser Service Called");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error Occured during saving user ");
            user = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public Users updateUser(Users user) {
        System.out.print("updateUser Service Called");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error Occured during updating user ");
            user = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public Users getUser(Long userId) {
        System.out.print("getUser Service Called");
        Users user = new Users();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            user = (Users) session.get(Users.class, userId);
            user.setPassword("");
            user.setEmailId("");
        } catch (Exception e) {
            System.out.println("Error Occured during retriving user ");
            user = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public Users deleteUser(Long userId) {
        System.out.print("getUser Service Called");
        Users user = new Users();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            user = (Users) session.get(Users.class, userId);
            session.delete(user);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error Occured during deleting user");
            user = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }
    
    public Users logInCheck(String authValue){
        
        StringTokenizer tokens = getDecodedAuthKey(authValue);
        String emailId = tokens.nextToken();
        String password = tokens.nextToken();
        System.out.println("EmailID :" + emailId + " Password :" + password);
        List<Users> users = null;
        
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("From Users where emailId = ? and password = ?");
            query.setString(0, emailId);
            query.setString(1, password);
            users = (List<Users>) query.list();
        } catch (Exception e) {
            System.out.println("Error Occured while authorizing Student");
            e.printStackTrace();
        } finally {
            session.close();
        }

        if(users != null && users.size() > 0){
            Users user = users.get(0);
            if(user.getEmailId().equals(emailId) && user.getPassword().equals(password)){
                System.out.println("User Found Id is "+ user.getUserId());
                return user;
            }
        }
        return null;
    }
    
    public Users login(String authKey){
        return logInCheck(authKey);
    }
    
    private StringTokenizer getDecodedAuthKey(String authValue){
        authValue = authValue.replaceFirst(AUTH_KEY_PREFIX, "");
        authValue = new String (Base64.getDecoder().decode(authValue));
        StringTokenizer tokens = new StringTokenizer(authValue, ":");
        return tokens;
    }
    
    public Users signUp(Users user, String headerAuthKey ){
        StringTokenizer tokens = getDecodedAuthKey(headerAuthKey);
        String emailId = tokens.nextToken();
        String password = tokens.nextToken();
        System.out.println("EmailID :" + emailId + " Password :" + password);
        
        if( user.getUserRole() == UserRole.STUDENT ){
            Student stud = new Student();
            stud.setFirstName(user.getFirstName());
            stud.setLastName(user.getLastName());
            stud.setEmailId(emailId);
            stud.setPassword(password);
            stud.setUserRole(UserRole.STUDENT);
            user = STUD_SERVICE.saveStudent(stud);
        }else if( user.getUserRole() == UserRole.TEACHER ){
            Teacher teacher = new Teacher();
            teacher.setFirstName(user.getFirstName());
            teacher.setLastName(user.getLastName());
            teacher.setEmailId(emailId);
            teacher.setPassword(password);
            teacher.setUserRole(UserRole.TEACHER);
            teacher.setCareerStartDate(new Date());
            user = TEACHER_SERVICE.saveTeacher(teacher);
        }else{
            user = saveUser(user);
        }
        return user;
    }
    
    public boolean checkEmailId(String emailId, String context){
        List<String> ids;
        boolean isValid = true;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("select emailId From Users");
            ids = (List<String>) query.list();
        } catch (Exception e) {
            System.out.println("Error Occured during deleting user");
            ids = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        if( ids != null ){
            if( context.equals("SIGNUP") ){
                for( String id : ids){
                    if( emailId.equals(id) ){
                        return false;
                    }
                }
            }
            else{
                for( String id : ids){
                    if( isValid = emailId.equals(id) ){
                        break;
                    }
                }
            }
        }
        return isValid;
    }
    
}
