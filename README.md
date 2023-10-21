# Workout Management API
This is a Workout Management API where Teachers can create and assign Workout Programs
to other Users. Feel free to fork and contribute :D

## Technologies used: 
- Java 17
- Spring Boot
- Spring security
- JWT
- JUnit and Mockito
- PostgreSQL
- And hosted on Railway on: https://workout-management.up.railway.app

## API Endpoints documentation

https://documenter.getpostman.com/view/27378065/2s9YJdXNuj

## Installation

Java 17 JDK required.

1. Set your PostgreSQL database;

2. Install maven dependencies

3. Set the Environment Variables according to the application.yml 
(you can use ```ddl-auto: create``` on your first run, to automatically create the db tables. Then you can set it as ```validate``` again);

4. You will have to manually insert the first Teacher on database.

5. Run app at source folder with ```mvn spring-boot:run``` or run it through the IDE of your choice.

The API will be available at http://localhost:8080

## Class Diagram (API's domain)

```mermaid
 classDiagram
    User "1" <-- "N" WorkoutProgram
    Teacher "1" <-- "N" WorkoutProgram
    WorkoutProgram "1" <-- "N" Workout
    Workout "1" <-- "N" Exercise

    class User {
        UUID id
        String login
        String firstName
        String surname
        String sex
        Date birthday
        Float weight
        Float height
        List~WorkoutProgram~ workoutPrograms
    }
    class Teacher {
        UUID id
        String username
        String password
        String firstName
        String surname
        List~WorkoutPrograms~ workoutPrograms
    }
    class WorkoutProgram {
        UUID id
        User user
        Teacher teacher
        List~Workout~ workouts
    }
    class Workout {
        Long id
        List~Exercise~ exercises
    }
    class Exercise {
        Long id
        String exercise
        String duration
        String observation
    }
```
