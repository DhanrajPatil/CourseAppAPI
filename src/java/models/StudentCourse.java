/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dhanraj Patil
 */
@Entity
@Table(name = "STUDENT_COURSE",  uniqueConstraints = { @UniqueConstraint(columnNames = {"STUDENT_ID", "COURSE_ID"}) } )
@XmlRootElement
public class StudentCourse implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studCourseId;
    
    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date joinedOn;
    
    private int feesPaid;
    private int courseMarks;
    
    @Enumerated(EnumType.STRING)
    private Result resultStatus;

    public long getStudCourseId() {
        return studCourseId;
    }

    public void setStudCourseId(long studCourseId) {
        this.studCourseId = studCourseId;
    }

    public Date getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(Date joinedOn) {
        this.joinedOn = joinedOn;
    }

    public int getFeesPaid() {
        return feesPaid;
    }

    public void setFeesPaid(int feesPaid) {
        this.feesPaid = feesPaid;
    }    

    public int getCourseMarks() {
        return courseMarks;
    }

    public void setCourseMarks(int marks) {
        this.courseMarks = marks;
    }

    public Student getStudent(){
        return student;
    }
    
    public void setStudent(Student student){
        this.student = student;
    }

    public Course getCourse(){
        return course;
    }
    
    public void setCourse(Course course){
        this.course = course;
    }
    
    public Result getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Result resultStatus) {
        this.resultStatus = resultStatus;
    }
}
