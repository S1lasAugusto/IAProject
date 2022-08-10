package application;

import com.google.gson.Gson;
import models.Dataset;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {

        try {
            //Lê o conteudo de um arquivo e guarda em uma String.
            String json = String.join(" ", Files.readAllLines(Paths.get("C:\\Users\\augus\\Desktop\\IAProject\\input\\Dataset.json"), StandardCharsets.UTF_8));
            System.out.println("lendo");

            //Desserialização do conteúdo em um novo objeto Dataset, usando o método fromJson do Gson.
            Dataset dataset = new Gson().fromJson(json, Dataset.class);


            System.out.println(dataset);


        } catch (IOException e) {
            System.out.println("Arquivo não encontrado");
        }
    }


}
