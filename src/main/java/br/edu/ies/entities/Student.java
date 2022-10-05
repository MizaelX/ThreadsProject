package br.edu.ies.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Student {
    private String name;
    private Double score;
    private Double finalScore;

    public Student(String name) {
        this.name = name;
    }

}
