package com.example.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;

// NESSA CLASSE EU FAÇO O TRATAMENTO DO ARQUIVO FILE QUE VEM DA CLASSE "ImportaDados"

public class ManipulaArquivo {

    String[] dadosMensais = new String[80];
    ArrayList<String[]> dadosAnuais = new ArrayList<>();

    public ArrayList<String[]> manipularArquivo(File file){

        try{
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String linha = bf.readLine();
            int contador = 0;
            while(linha != null){

                linha = bf.readLine();
        
                dadosMensais = linha.split(";");

                if(contador > 11){

                    int quant_dias = calcularDias(dadosMensais[2]);

                    if(dadosAnuais.size() == 0){ 
                        dadosAnuais.add(dadosMensais);
                    }
                    else{
                        for(int i=0 ; i < dadosAnuais.size() ; i++){
                            if(quant_dias < calcularDias(dadosAnuais.get(i)[2])){
                                dadosAnuais.add(i,dadosMensais);
                                break;
                            }
                            if(i == dadosAnuais.size()-1){
                                dadosAnuais.add(dadosMensais);
                            }
                        }
                    }
                }
                contador++;
            }
        }catch(Exception e){
            e.getStackTrace();
        }

        return this.dadosAnuais;
    }

    public int calcularDias(String data) throws ParseException{

        String[] data_dividida = new String[3];
        data_dividida = data.split("/");
        
        int dias = (Integer.parseInt(data_dividida[2]) * 365) + 
        (Integer.parseInt(data_dividida[1]) * 30) + Integer.parseInt(data_dividida[0]);

        return dias;

    }
    
}
