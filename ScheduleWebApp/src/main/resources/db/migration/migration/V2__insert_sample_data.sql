INSERT INTO groups (group_name) VALUES ('Group 1'), ('Group 2'), ('Group 3');

INSERT INTO courses (course_name, description) VALUES 
('Math 101', 'Basic Mathematics'),
('History 101', 'World History'),
('Physics 101', 'Basic Physics');

INSERT INTO roles (name) VALUES ('STUDENT'), ('TEACHER'), ('ADMIN'), ('STAFF');

TRUNCATE TABLE users RESTART IDENTITY CASCADE;

INSERT INTO users (email, password, first_name, last_name, role) VALUES
('sabrina.stud@example.com', '$2a$12$owM8asOHBmgu7OZdrGcarukvA9GSNPGLo90ybvaRb3ACvAaF2bwLi', 'Sabrina', 'Stud', 'STUDENT'),  -- пароль: 1234
('steven.stud@example.com', '$2a$12$owM8asOHBmgu7OZdrGcarukvA9GSNPGLo90ybvaRb3ACvAaF2bwLi', 'Steven', 'Stud', 'STUDENT'),  -- пароль: 1234
('selena.stud@example.com', '$2a$12$owM8asOHBmgu7OZdrGcarukvA9GSNPGLo90ybvaRb3ACvAaF2bwLi', 'Selena', 'Stud', 'STUDENT'),  -- пароль: 1234
('simon.stud@example.com', '$2a$12$owM8asOHBmgu7OZdrGcarukvA9GSNPGLo90ybvaRb3ACvAaF2bwLi', 'Simon', 'Stud', 'STUDENT'),  -- пароль: 1234
('jane.teach@example.com', '$2a$12$FwE7N0kO2uIss90dIQ0kHefvC8YFwxYtaToVb1UwP51Cyb/EArUNS', 'Jane', 'Teach', 'TEACHER'),  -- пароль: qwer
('scot.teach@example.com', '$2a$12$FwE7N0kO2uIss90dIQ0kHefvC8YFwxYtaToVb1UwP51Cyb/EArUNS', 'Scot', 'Teach', 'TEACHER'),  -- пароль: qwer
('piter.teach@example.com', '$2a$12$FwE7N0kO2uIss90dIQ0kHefvC8YFwxYtaToVb1UwP51Cyb/EArUNS', 'Piter', 'Teach', 'TEACHER'),  -- пароль: qwer
('jacob.use@example.com', '$2a$12$.Aksh/Mq.gF5j47Ab39MEOeV5XF8iDbnEhyYD23fPfbUv.EYysSRi', 'Jacob', 'Use', 'USER'),      -- пароль: asdf
('jolene.staf@example.com', '$2a$12$mDpZH9SKkcUpvB9g0F7Wbe1L0RirM6s0zgV8kNJuNrpij.Isik2w6', 'Jolene', 'Staf', 'STAFF'),  -- пароль: poiu
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
VALUES (
    '2024-05-15', 
    '09:00:00', 
    'Room 101', 
    'Math 101', 
    (SELECT id FROM courses WHERE course_name = 'Math 101'), 
    (SELECT id FROM teachers WHERE email = 'jane.teach@example.com')
);

INSERT INTO teacher_courses (teacher_id, course_id, group_id)
VALUES (
    (SELECT id FROM teachers WHERE email = 'jane.teach@example.com'), 
    (SELECT id FROM courses WHERE course_name = 'Math 101'), 
    (SELECT id FROM groups WHERE group_name = 'Group 2')
);