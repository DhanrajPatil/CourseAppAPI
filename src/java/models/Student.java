/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dhanraj Patil
 */
@Entity
@Table(name = "STUDENT")
@XmlRootElement
public class Student extends Users{
    private int marks;
    private String collegeName;
    
    @XmlTransient
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<StudentCourse> studCourses = new HashSet<>();

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
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
