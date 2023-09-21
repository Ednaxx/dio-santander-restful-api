package ednax.dio.santander.restapi.models;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "workout_users")
@Entity(name = "workout_users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String surname;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, precision = 2)
    private Float weight;

    @Column(nullable = false, precision = 2)
    private Float height;

    @OneToMany
    private ArrayList<WorkoutProgramModel> workoutPrograms;
    
}
