package br.edu.ies;

import br.edu.ies.entities.Student;
import br.edu.ies.services.ScoreProcessing;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log
@Getter
@Setter
public class Processor implements Runnable {
    private Socket client = null;
    private List<Student> studentList = new ArrayList<>();
    @Override
    public void run() {
        Scanner scanner = null;

        try {
            log.info("Starting processing");
            log.info("Waiting for input...");
            scanner = new Scanner(client.getInputStream());

            ScoreProcessing processing = new ScoreProcessing();
            PrintStream printStream = new PrintStream(client.getOutputStream(), true);
            Integer amountStudents = Integer.valueOf(scanner.nextLine());

            // Enquanto o usuário puder inserir novas informações e não tiver passado o número de alunos especificados,
            // percorre o while para obter o nome e nota dos alunos;
            while(scanner.hasNextLine()) {
                String studentName = scanner.nextLine();
                Student student = new Student(studentName);

                Double score = Double.parseDouble(scanner.nextLine());
                student.setScore(score);
                studentList.add(student);

                // Se a quantidade total de alunos já adicionados for maior que a quantidade de alunos a serem adicionados, sai do while
                if (studentList.size() >= amountStudents)
                    break;
            }
            // Chama a função para obter a maior nota.
            Double highestScore = processing.getHighestScore(studentList);
            printStream.println("Students normalized score:");

            // Chama a função para normalizar a nota de todos os alunos e imprime na tela o resultado.
            studentList.forEach(student -> {
                Double normalizedScore = processing.normalizeScore(student.getScore(), highestScore);
                student.setFinalScore(normalizedScore);
                printStream.println(String.format("Name: %s | Score: %.2f", student.getName(), student.getFinalScore()));
            });

        }
        catch (IOException e) {
            log.info("Processing error");
            throw new RuntimeException(e);
        }
        finally {
            log.info("Processing done");
        }
    }
}
