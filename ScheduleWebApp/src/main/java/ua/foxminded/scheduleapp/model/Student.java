package ua.foxminded.scheduleapp.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "students")
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_courses")
    private Set<Course> courses;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private Set<Schedule> schedules;
}
