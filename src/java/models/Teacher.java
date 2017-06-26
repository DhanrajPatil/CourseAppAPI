/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dhanraj Patil
 */
@Entity
@XmlRootElement
public class Teacher extends Users{
    
    private String education;
    private int noOfCoursesThought;    
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date careerStartDate;
    
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "teacher")
    private Set<Course> courses = new HashSet<>();

    public String getEducation() {
        return education;
    }

    public void setEducation(String Education) {
        this.education = Education;
    }

    public Date getCareerStartDate() {
        return careerStartDate;
    }

    public void setCareerStartDate(Date careerStartDate) {
        this.careerStartDate = careerStartDate;
    }

    public int getNoOfCoursesThought() {
        return noOfCoursesThought;
    }

    public void setNoOfCoursesThought(int noOfCoursesThought) {
        this.noOfCoursesThought = noOfCoursesThought;
    }

    @XmlTransient
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
