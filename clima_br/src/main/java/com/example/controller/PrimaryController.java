package com.example.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.utils.CalculaDuracaoPrecipitacao;
import com.example.utils.GeraDadosSinteticos;
import com.example.utils.ImportaDados;
import com.example.utils.ManipulaArquivo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class PrimaryController implements Initializable{

    @FXML
    private BorderPane telaPrincipal;

    @FXML
    private Menu menuEntrada,menuCalcular,menuResultados,menuSalvarRes,
    menuAjuda,menuErosividade,menuConfiguracoes;

    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }

    public void abrirArquivo(ActionEvent event){

        ImportaDados importarDados = new ImportaDados();
        ManipulaArquivo manipulador = new ManipulaArquivo();
        
        File f = importarDados.lerDadosTxt(telaPrincipal);
        GeraDadosSinteticos dms = new GeraDadosSinteticos(manipulador.manipularArquivo(f));
        dms.geraDadosSinteticos();
        //dms.imprime();   
    }
}
