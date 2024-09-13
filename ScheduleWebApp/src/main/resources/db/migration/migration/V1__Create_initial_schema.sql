CREATE TABLE users (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    role VARCHAR(20) NOT NULL
);

CREATE TABLE groups (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    group_name VARCHAR(50) NOT NULL
);

CREATE TABLE students (
    id BIGINT PRIMARY KEY,
    group_id BIGINT,
    FOREIGN KEY (id) REFERENCES users(id),
    FOREIGN KEY (group_id) REFERENCES groups(id)
);

CREATE TABLE teachers (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE courses (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    course_name VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE schedules (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    date DATE NOT NULL,
    room VARCHAR(50) NOT NULL,
    course_id BIGINT,
    teacher_id BIGINT,
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

CREATE TABLE student_schedules (
    student_id BIGINT,
    schedule_id BIGINT,
    PRIMARY KEY (student_id, schedule_id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (schedule_id) REFERENCES schedules(id)
);

CREATE TABLE teacher_courses (
    teacher_id BIGINT,
    course_id BIGINT,
    PRIMARY KEY (teacher_id, course_id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);
