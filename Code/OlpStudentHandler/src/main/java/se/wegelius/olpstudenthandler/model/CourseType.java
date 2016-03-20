/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author asawe
 */
public class CourseType  implements Serializable {
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "course_type_id")
    private int courseTypeId;
    
    @Column(name = "course_type__name")
    private String courseTypeName;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course")
    private Set<Course> courses;

    public CourseType() {
    }

    public int getCourseTypeId() {
        return courseTypeId;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourseTypeId(int courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
    
    
}
