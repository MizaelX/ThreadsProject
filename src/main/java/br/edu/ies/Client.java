package br.edu.ies;

import lombok.extern.java.Log;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Log
public class Client {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        Socket server = null;
        Integer PORT = 2022;

        try {
            log.info("[CLIENT] Trying to connect to server...");
            server = new Socket("localhost", PORT);
            log.info("[CLIENT] Connection established...");

            while(server.isConnected()) {
                PrintStream printStream = new PrintStream(server.getOutputStream());
                // Pede ao usuário para inserir a quantidade de alunos a ser processada.
                System.out.println("Input the amount of students you would like to process");
                Integer amountStudents = Integer.valueOf(scan.nextLine());
                printStream.println(amountStudents);

                for (int i = 0; i < amountStudents; i++) {
                    // Pede ao usuário o nome do aluno
                    System.out.println(String.format("[%d/%d] Enter the student name:", i + 1, amountStudents));
                    String studentName = scan.nextLine();
                    printStream.println(studentName);

                    // Pede ao usuário a nota do aluno
                    System.out.println(String.format("[%d/%d] Enter the student score:", i + 1, amountStudents));
                    String studentScore = scan.nextLine();
                    printStream.println(studentScore);
                }
                scan.close();

                String inputLine;
                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }

            }
        } catch (Exception e) {
            if(e.getMessage() != null && !e.getMessage().isEmpty()) {
                log.info("[CLIENT] Connection failed... Reason: " + e.getMessage() + ".");
            }
            else{
                log.info("[CLIENT] Connection failed... Trace: ");
                e.printStackTrace();
            }
        }

    }
}
