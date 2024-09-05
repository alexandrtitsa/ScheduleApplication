package ua.foxminded.scheduleapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@Entity
@Table(name = "teachers")
@Data
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_courses")
    private Set<Course> courses;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Set<Schedule> schedules;

}
