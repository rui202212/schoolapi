Hibernate:
create table schoolclass (
                             id bigint generated by default as identity,
                             class_name varchar(255),
                             schoolsubject_id bigint,
                             teacher_id bigint,
                             primary key (id)
)
    Hibernate:
create table schoolsubject (
                               id bigint generated by default as identity,
                               description varchar(255),
                               subject_name varchar(255),
                               primary key (id)
)
    Hibernate:
create table student (
                         id bigint generated by default as identity,
                         created_date date,
                         email varchar(255),
                         first_name varchar(255),
                         last_name varchar(255),
                         password varchar(255),
                         updated_date date,
                         birth_date date,
                         primary key (id)
)
    Hibernate:
create table student_roles (
                               student_id bigint not null,
                               roles enum ('ADMIN','STUDENT','TEACHER')
)
    Hibernate:
create table student_schoolclass (
                                     schoolclass_id bigint not null,
                                     student_id bigint not null,
                                     primary key (schoolclass_id, student_id)
)
    Hibernate:
create table teacher (
                         id bigint generated by default as identity,
                         created_date date,
                         email varchar(255),
                         first_name varchar(255),
                         last_name varchar(255),
                         password varchar(255),
                         updated_date date,
                         primary key (id)
)
    Hibernate:
create table teacher_roles (
                               teacher_id bigint not null,
                               roles enum ('ADMIN','STUDENT','TEACHER')
)
    Hibernate:
create table teacher_schoolsubject (
                                       schoolsubject_id bigint not null,
                                       teacher_id bigint not null,
                                       primary key (schoolsubject_id, teacher_id)
)
    Hibernate:
alter table if exists schoolclass
drop constraint if exists UKtd7dp07yeu9d0h3bnowpybqcc
Hibernate:
alter table if exists schoolclass
    add constraint UKtd7dp07yeu9d0h3bnowpybqcc unique (schoolsubject_id)
    Hibernate:
alter table if exists schoolclass
    add constraint FKsflhxqjjryvdk2gtl5jgn8o5j
    foreign key (schoolsubject_id)
    references schoolsubject
    Hibernate:
alter table if exists schoolclass
    add constraint FKhcidrvngve41t4r0bn7rys3fg
    foreign key (teacher_id)
    references teacher
    Hibernate:
alter table if exists student_roles
    add constraint FK5wsgmwcdh1mu2aakbatae9ouh
    foreign key (student_id)
    references student
    Hibernate:
alter table if exists student_schoolclass
    add constraint FKjmnpa8elsyr0r1b79dyend2pk
    foreign key (student_id)
    references student
    Hibernate:
alter table if exists student_schoolclass
    add constraint FK9f8gueafxgb22ora5kt2059g4
    foreign key (schoolclass_id)
    references schoolclass
    Hibernate:
alter table if exists teacher_roles
    add constraint FKo493s5wgjjhub8ss115g6y3xu
    foreign key (teacher_id)
    references teacher
    Hibernate:
alter table if exists teacher_schoolsubject
    add constraint FK65bfpq52jofhf59918aisikmh
    foreign key (teacher_id)
    references teacher
    Hibernate:
alter table if exists teacher_schoolsubject
    add constraint FKo1s574ffuo0fsllux1l4jtbbj
    foreign key (schoolsubject_id)
    references schoolsubject