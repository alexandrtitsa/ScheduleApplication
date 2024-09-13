INSERT INTO groups (group_name) VALUES ('Group 1'), ('Group 2');

INSERT INTO courses (course_name, description) VALUES 
('Math 101', 'Basic Mathematics'),
('History 101', 'World History');

INSERT INTO users (email, password, first_name, last_name, role) VALUES
('john.doe@example.com', 'password', 'John', 'Doe', 'STUDENT'),
('jane.doe@example.com', 'password', 'Jane', 'Doe', 'TEACHER');

INSERT INTO students (id, group_id) VALUES (1, 1);
INSERT INTO teachers (id) VALUES (2);
