package ua.foxminded.scheduleapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.Set;

@Entity
public class Teacher extends User {

    private String department;

    @ManyToMany
    @JoinColumn(name = "teacher_courses")
    private Set<Course> courses;

    @OneToMany(mappedBy = "teacher")
    private Set<Schedule> schedules;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
}
