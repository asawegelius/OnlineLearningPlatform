package se.wegelius.olpstudenthandler.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import se.wegelius.olpstudenthandler.model.Course;
import se.wegelius.olpstudenthandler.model.CourseType;

/**
 *
 * @author asawe
 */
@Entity
@Table(name = "course_branch")
public class CourseBranch implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "course_branch_id")
    private int branchId;

    @Column(name = "course_branch_name")
    private String branchName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course_type")
    private Set<CourseType> courseTypes;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course")
    private Set<Course> courses;

    public CourseBranch() {
        this.courses = new HashSet<>();
    }

    public CourseBranch(String branchName) {
        this.branchName = branchName;
    }


    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

}
