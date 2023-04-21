package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "Job")

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Job_gen")
    @SequenceGenerator(
            name = "Job_gen",
            sequenceName = "job_sequence",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "position")
    private String position;
    @Column(name = "profession")
    private String profession;
    @Column(name = "description")
    private String description;
    private int experience;

    public Job(String position, String profession, String description, int experience) {
        this.position = position;
        this.profession = profession;
        this.description = description;
        this.experience = experience;
    }
    public Job(){

    }
}
