package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Employee")

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Employee")
    @SequenceGenerator(
            name = "Employee_gen",
            sequenceName = "Employee_sequence",
            allocationSize = 1
    )
    private Long id;
    private String first_name;
    private String last_name;
    private int age;
    private String email;
    private int job_id;

    public Employee(Long id, String firstName, String lastName, int age, String email, int jobId) {
        this.id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.age = age;
        this.email = email;
        this.job_id = jobId;
    }

    public Employee(String firstName, String lastName, int age, String email, int jobId) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.age = age;
        this.email = email;
        this.job_id = jobId;
    }

    public Employee() {

    }
}
