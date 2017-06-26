/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dhanraj Patil
 */
@Entity
@Table(name = "COURSE")
@XmlRootElement
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long courseId;
    
    @Column(nullable = false)
    private String courseName;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseType courseType;
    
    @Column(nullable = false)
    private int fees;
    
    private int minAdvancedFee = 100;
    private int maxStudents = 40; 
    private int minMarksToApply = 50;
    private int minPassingMarks = 40;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endDate;
    
    @ManyToOne
    @JoinColumn(name="teacherId")
    private Teacher teacher;
    
    @XmlTransient
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<StudentCourse> studCourses = new HashSet<>();
    
    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getMinMarksToApply() {
        return minMarksToApply;
    }

    public void setMinMarksToApply(int minMarks) {
        this.minMarksToApply = minMarks;
    }    

    public int getMinPassingMarks() {
        return minPassingMarks;
    }

    public void setMinPassingMarks(int minPassingMarks) {
        this.minPassingMarks = minPassingMarks;
    }    

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getMinAdvancedFee() {
        return minAdvancedFee;
    }

    public void setMinAdvancedFee(int minAdvancedFee) {
        this.minAdvancedFee = minAdvancedFee;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @XmlTransient
    public Set<StudentCourse> getStudCourses() {
        return studCourses;
    }

    public void setStudCourses(Set<StudentCourse> studCourses) {
        this.studCourses = studCourses;
    }
    
    public void addStudentCourse(StudentCourse studCourse){
        this.studCourses.add(studCourse);
    }
}
