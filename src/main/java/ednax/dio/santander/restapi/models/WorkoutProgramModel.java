package ednax.dio.santander.restapi.models;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "workout_program")
@Entity(name = "workout_program")
public class WorkoutProgramModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @OneToOne
    @Column(nullable = false)
    private UserModel user;

    @OneToOne
    @Column(nullable = false)
    private TeacherModel teacher;

    @OneToMany
    @Column(nullable = false)
    private ArrayList<WorkoutModel> workouts;

}
