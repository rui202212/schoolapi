# schoolapi
A SpringBoot School Management API

Here is the URL of the project's remote repository: https://github.com/rui202212/schoolapi.git

Locally, please access Swagger at: http://localhost:8080/api/v1/swagger-ui.html


**Exercise: Develop a School Management API**

**Objective:**
Create a RESTful API to manage a school's students, teachers, classes, and subjects. The API should allow for CRUD (Create, Read, Update, Delete) operations on these entities and provide endpoints to handle various relationships between them.

**Requirements:**

1. **Entities:**

    - **Student:** Represents a student with attributes like `id`, `firstName`, `lastName`, `birthDate`, `email`, and `enrolledSchoolClasses`.
    - **Teacher:** Represents a teacher with attributes like `id`, `firstName`, `lastName`, `email`, `subjectsTaught`, and `assignedSchoolClasses`.
    - **SchoolClass:** Represents a class with attributes like `id`, `name`, `subject`, `teacher` and `students`.
    - **SchoolSubject:** Represents a subject with attributes like `id`, `name`, `description` and `teachers`.

2. **Endpoints:**

    - **Students:**
        - `GET /students`: Retrieve a list of all students.
        - `GET /students/{id}`: Retrieve details of a specific student.
        - `POST /students`: Create a new student.
        - `PUT /students/{id}`: Update details of a specific student.
        - `DELETE /students/{id}`: Delete a specific student.
    - **Teachers:**
        - `GET /teachers`: Retrieve a list of all teachers.
        - `GET /teachers/{id}`: Retrieve details of a specific teacher.
        - `POST /teachers`: Create a new teacher.
        - `PUT /teachers/{id}`: Update details of a specific teacher.
        - `DELETE /teachers/{id}`: Delete a specific teacher.
    - **Classes:**
        - `GET /classes`: Retrieve a list of all classes.
        - `GET /classes/{id}`: Retrieve details of a specific class.
        - `POST /classes`: Create a new class.
        - `PUT /classes/{id}`: Update details of a specific class.
        - `DELETE /classes/{id}`: Delete a specific class.
    - **Subjects:**
        - `GET /subjects`: Retrieve a list of all subjects.
        - `GET /subjects/{id}`: Retrieve details of a specific subject.
        - `POST /subjects`: Create a new subject.
        - `PUT /subjects/{id}`: Update details of a specific subject.
        - `DELETE /subjects/{id}`: Delete a specific subject.

3. **Relationships:**

    - A student can be enrolled in multiple classes.
    - A teacher can teach multiple subjects and be assigned to multiple classes.
    - A class is associated with one subject and one teacher.
    - A subject can be taught by multiple teachers.

4. **Additional Features:**
    - Implement pagination for listing endpoints.
    - Add validation for input data.
    - Implement error handling and return appropriate HTTP status codes.
    - Secure the API with JWT (JSON Web Token) authentication.
    - Implement role-based access control (RBAC):
      **Admin**: Can create, update, and delete any entity.
      **Teacher**: Can update and delete their assigned classes and students.
      **Student**: Can view their own details and enrolled classes.
    - Document the API using Swagger or a similar tool.
