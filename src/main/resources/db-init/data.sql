-- Insert common data into the schooluser table
INSERT INTO schooluser (id, first_name, last_name, email, password, created_date, updated_date)
VALUES
    (1, 'Harry', 'Potter', 'harry.potter@hogwarts.edu', 'Xpe11iarmus', CURRENT_DATE, CURRENT_DATE),
    (2, 'Ronald', 'Weasley', 'ron.weasley@hogwarts.edu', 'BloodyHe11', CURRENT_DATE, CURRENT_DATE),
    (3, 'Hermione', 'Granger', 'hermione.granger@hogwarts.edu', 'Crookshanks9', CURRENT_DATE, CURRENT_DATE),
    (4, 'Minerva', 'McGonagall', 'mcgonagall@hogwarts.edu', 'Animagus!1', CURRENT_DATE, CURRENT_DATE),
    (5, 'Rubeus', 'Hagrid', 'hagrid@hogwarts.edu', '2Graup@Bro', CURRENT_DATE, CURRENT_DATE),
    (6, 'Severus', 'Snape', 'snape@hogwarts.edu', '4Always!', CURRENT_DATE, CURRENT_DATE);

-- Insert student-specific data into the Student table
INSERT INTO student (id, birth_date)
VALUES
    (1, '1980-07-31'),
    (2, '1980-03-01'),
    (3, '1979-09-19');

-- Insert teacher-specific data into the Teacher table
INSERT INTO teacher (id)
VALUES
    (4),
    (5),
    (6);

-- Insert other data
INSERT INTO schoolsubject (subject_name, description)
VALUES
    ('Transfiguration', 'The art of changing the form of an object.'),
    ('Care of Magical Creatures', 'Learning about magical beasts and creatures.'),
    ('Potions', 'The study of magical brews and mixtures.');

INSERT INTO schoolclass (class_name, schoolsubject_id, teacher_id)
VALUES
    ('Gryffindor Transfiguration', 1, 4),
    ('Magical Creatures', 2, 5),
    ('Advanced Potions', 3, 6);

INSERT INTO student_schoolclass (schoolclass_id, student_id)
VALUES
    (1, 1), -- Harry is in Gryffindor Transfiguration
    (1, 2), -- Ron is in Gryffindor Transfiguration
    (1, 3), -- Hermione is in Gryffindor Transfiguration
    (2, 1), -- Harry is in Magical Creatures
    (3, 3); -- Hermione is in Advanced Potions

-- Insert user roles into the user_roles table
INSERT INTO user_roles (user_id, roles)
VALUES
    (1, 'STUDENT'),
    (2, 'STUDENT'),
    (3, 'STUDENT'),
    (4, 'TEACHER'),
    (5, 'TEACHER'),
    (6, 'TEACHER');