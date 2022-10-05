package br.edu.ies.services;

import br.edu.ies.entities.Student;

import java.util.Comparator;
import java.util.List;

public class ScoreProcessing {

    // Obtém, baseado na lista passada como parâmetro, a maior nota dentre os alunos.
    public Double getHighestScore(List<Student> students) {
        Double highestScore = students.stream().max(Comparator.comparing(Student::getScore)).get().getScore();
        return highestScore;
    }
    // Normaliza a nota do aluno, multiplicando a mesma por 10 (que seria a nota máxima) e dividindo o resultado pela maior nota.
    public Double normalizeScore(Double score, Double topScore){
        return (score * 10) / topScore;
    }

}
