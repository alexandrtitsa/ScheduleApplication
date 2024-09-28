INSERT INTO groups (group_name) VALUES ('Group 1'), ('Group 2');

INSERT INTO courses (id, course_name, description) VALUES 
(1, 'Math 101', 'Basic Mathematics'),
(2, 'History 101', 'World History');

INSERT INTO roles (name) VALUES ('STUDENT'), ('TEACHER'), ('ADMIN');

TRUNCATE TABLE users RESTART IDENTITY CASCADE;

INSERT INTO users (email, password, first_name, last_name, role) VALUES
('john.stud@example.com', '$2a$12$owM8asOHBmgu7OZdrGcarukvA9GSNPGLo90ybvaRb3ACvAaF2bwLi', 'John', 'Stud', 'STUDENT'),  -- пароль: 1234
('jane.teach@example.com', '$2a$12$FwE7N0kO2uIss90dIQ0kHefvC8YFwxYtaToVb1UwP51Cyb/EArUNS', 'Jane', 'Teach', 'TEACHER'),  -- пароль: qwer
('jacob.use@example.com', '$2a$12$.Aksh/Mq.gF5j47Ab39MEOeV5XF8iDbnEhyYD23fPfbUv.EYysSRi', 'Jacob', 'Use', 'USER'),      -- пароль: asdf
('jack.admi@example.com', '$2a$12$t8obp6gahRtnTK4NQufxYerKciSzHWUEVarNn01d7Aqj358DqPfsu', 'Jack', 'Admi', 'ADMIN');      -- пароль: zxcv

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON u.role = r.name;

INSERT INTO students (user_id, first_name, last_name, email, group_id)
SELECT u.id, u.first_name, u.last_name, u.email, g.id
FROM users u
JOIN user_roles ur ON u.id = ur.user_id
JOIN roles r ON ur.role_id = r.id
JOIN groups g ON g.group_name = 'Group 1'
WHERE r.name = 'STUDENT';

INSERT INTO teachers (id, email, first_name, last_name)
SELECT u.id, u.email, u.first_name, u.last_name
FROM users u
JOIN user_roles ur ON u.id = ur.user_id
JOIN roles r ON ur.role_id = r.id
WHERE r.name = 'TEACHER';

INSERT INTO schedules (date, time_slot, room, course_name, course_id, teacher_id)
VALUES ('2024-05-15', '09:00:00', 'Room 101', 'Math 101', 1, 2);