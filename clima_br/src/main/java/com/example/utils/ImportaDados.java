package com.example.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

// NESSA CLASSE EU FAÇO A ABERTURA DE UM ARQUIVO QUE ESTÁ NO COMPUTADOR
// TRANSFORMANDO ESSE ARQUIVO EM UM OBJETO DO TIPO FILE , COM ESSE OBJETO
// EU POSSO FAZER AS MANIPULAÇÕES NECESSÁRIAS PARA ANALISAR OS DADOS QUE ESTÃO LÁ 

public class ImportaDados {

    private File file;
    private File parentFile;

    public File lerDadosTxt(BorderPane tela){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir arquivo");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)",
                    "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        if (parentFile != null && parentFile.canExecute() && parentFile.exists()) {
            fileChooser.setInitialDirectory(parentFile);
        }

        File aux = fileChooser.showOpenDialog(tela.getScene().getWindow());

        if (aux != null) {
            this.file = aux;
        }

        return file;
    }

    /*public void imprimeDados(TextArea text){

        try{
            if (file != null) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String input;
                    int c = 0;
                    while ((input = reader.readLine()) != null) {
                        if (!input.trim().isEmpty()) {
                            if (c == 0) {
                                text.setText(input);
                            } else {
                                text.setText(text.getText() + "\n" + input);
                            }
                            c++;
                        }
                    }
                    text.positionCaret(text.getText().length());
                    parentFile = file.getParentFile();
                } catch (IOException e){
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            }catch(Exception e){
                e.getMessage();
            }
    }*/
    
}
