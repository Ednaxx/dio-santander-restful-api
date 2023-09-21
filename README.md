# Dio Santander Restful Api
Restful API developed for Santander's Java BackEnd Bootcamp.

```mermaid
 classDiagram
    User <--> WorkoutProgram
    Teacher <--> WorkoutProgram

    class User {
        UUID id
        String firstName
        String surname
        String sex
        Int age
        Float weight
        Float height
        ArrayList~Int~ workoutProgramIds
    }
    class Teacher {
        UUID id
        String firstName
        String surname
        ArrayList~Int~ workoutProgramIds
    }
    class WorkoutProgram {
        UUID id
        Int userId
        Int teacherId
        HashMap~String.ArrayList~String~~ workouts
    }
```
