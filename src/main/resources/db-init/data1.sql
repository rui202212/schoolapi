INSERT INTO student (first_name, last_name, email, password, birth_date, created_date, updated_date)
VALUES
    ('Harry', 'Potter', 'harry.potter@hogwarts.edu', 'Xpe11iarmus', '1980-07-31', CURRENT_DATE, CURRENT_DATE),
    ('Ronald', 'Weasley', 'ron.weasley@hogwarts.edu', 'BloodyHe11', '1980-03-01', CURRENT_DATE, CURRENT_DATE),
    ('Hermione', 'Granger', 'hermione.granger@hogwarts.edu', 'Crookshanks9', '1979-09-19', CURRENT_DATE, CURRENT_DATE);

INSERT INTO teacher (first_name, last_name, email, password, created_date, updated_date)
VALUES
    ('Minerva', 'McGonagall', 'mcgonagall@hogwarts.edu', 'Animagus!1', CURRENT_DATE, CURRENT_DATE),
    ('Rubeus', 'Hagrid', 'hagrid@hogwarts.edu', '2Graup@Bro', CURRENT_DATE, CURRENT_DATE),
    ('Severus', 'Snape', 'snape@hogwarts.edu', '4Always!', CURRENT_DATE, CURRENT_DATE);

INSERT INTO schoolsubject (subject_name, description)
VALUES
    ('Transfiguration', 'The art of changing the form of an object.'),
    ('Care of Magical Creatures', 'Learning about magical beasts and creatures.'),
    ('Potions', 'The study of magical brews and mixtures.');

INSERT INTO schoolclass (class_name, schoolsubject_id, teacher_id)
VALUES
    ('Gryffindor Transfiguration', 1, 1),
    ('Magical Creatures', 2, 2),
    ('Advanced Potions', 3, 3);

INSERT INTO student_schoolclass (schoolclass_id, student_id)
VALUES
    (1, 1), -- Harry is in Gryffindor Transfiguration
    (1, 2), -- Ron is in Gryffindor Transfiguration
    (1, 3), -- Hermione is in Gryffindor Transfiguration
    (2, 1), -- Harry is in Magical Creatures
    (3, 3); -- Hermione is in Advanced Potions

INSERT INTO student_roles (student_id, roles)
VALUES
    (1, 'STUDENT'),
    (2, 'STUDENT'),
    (3, 'STUDENT');

INSERT INTO teacher_roles (teacher_id, roles)
VALUES
    (1, 'TEACHER'),
    (2, 'TEACHER'),
    (3, 'TEACHER');