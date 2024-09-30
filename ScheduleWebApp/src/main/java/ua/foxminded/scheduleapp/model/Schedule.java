package ua.foxminded.scheduleapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time_slot", nullable = false)
    private String timeSlot;

    @Column(name = "room", nullable = false)
    private String room;
    
    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
        name = "student_schedules",
        joinColumns = @JoinColumn(name = "schedule_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @ToString.Exclude
    private Set<Student> students = new HashSet<>();
}
