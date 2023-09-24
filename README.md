# Dio Santander Restful Api
Restful API developed for Santander's Java BackEnd Bootcamp.

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
