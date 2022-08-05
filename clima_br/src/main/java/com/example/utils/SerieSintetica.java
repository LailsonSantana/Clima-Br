package com.example.utils;

import java.util.ArrayList;

import com.example.model.Precipitacao;

public class SerieSintetica {


    public double calcularZ(double numeroAleat){

        double hp = numeroAleat;
        //System.out.println(numeroAleat);
        if(numeroAleat >= 0.5){

            hp = 1.0 - numeroAleat; // aqui eh 1 - numeroAleatorio ?
        }
        return Math.sqrt(Math.log(1/(hp*hp)));
    }

    public double calcularVariavelAleatoria(double z){ // Variável aleatória padronizada admissional

        double x1 = (z - (2.30753 + (0.27061 * z)));
        x1 = x1 / (1 + (0.99229 * z) + (0.04481 * Math.pow(z, 2)));

        return x1;
    }

    public double calcularDistribuicaoPerson(double desvioPad, double cofAss, double mediaDiar,double variavelAleatoriaPad){

        variavelAleatoriaPad = - variavelAleatoriaPad;
        double parteA = 0.0;
        if(cofAss != 0){
            parteA = mediaDiar + ((2 * desvioPad) / cofAss);
            double parteB = ((cofAss / 6) * (variavelAleatoriaPad - (cofAss / 6)) + 1);
            parteB = Math.pow(parteB, 3) - 1;
            parteA = parteA * parteB;
        }
        

        return parteA;  
    }

    public double calcularDesvioPadraoSintetico(int quant_dias_chuvosos,Double[][] dadosSint,double media){
        double somatorio = 0;
        try{
            for(int i=0;i<dadosSint.length;i++){

                if(dadosSint[i][7] != null && dadosSint[i][7] > 0){
                    somatorio = somatorio + Math.pow((dadosSint[i][7] - media), 2);
                }
            
            }
            somatorio = Math.sqrt(somatorio / (quant_dias_chuvosos - 1));
        
        }catch(Exception e){
            e.getMessage();
        }
        return somatorio;
    }

    public void corrigirMediasDiarias(ArrayList<DadosAnuais> dadosSinteticos){

        for(DadosAnuais dadosAnuaisSint : dadosSinteticos){

            DadosMensais[] dados =  dadosAnuaisSint.getDadosAnuaisSint();

            for(int i=0;i<dados.length;i++){

                Double[][] mesSint = dados[i].getMesSintetico();
                Precipitacao p = dados[i].getPrecipitacao();
                CalculaDuracaoPrecipitacao duracao = new CalculaDuracaoPrecipitacao();
                for(int k=0;k<mesSint.length;k++){
                    double precCorrigida = 0;
                    if(mesSint[k][7] != null && mesSint[k][7] > 0){
                        double y = 0;
                        precCorrigida = (p.getMedia() / dados[i].getMediaSintetica()) * mesSint[k][7];
                        y = precCorrigida;
                        precCorrigida = (precCorrigida - p.getMedia());
                        precCorrigida = precCorrigida * (p.getDesvioPadrao() / dados[i].getDesvioPadraoSintetico());
                        precCorrigida = precCorrigida + p.getMedia();
                        mesSint[k][8] = precCorrigida;
                        double r = duracao.calcularIntegral(mesSint[i][3]);
                        mesSint[k][9] = duracao.calcularIntegral(r);
                        System.out.println("Duração :" + mesSint[k][9]);
                        // Falta testar quais valores estão sendo impressos nessa parte
                        //System.out.println(y + " " + p.getMedia() + " " + dados[i].getDesvioPadraoSintetico() + " "
                        //+ p.getDesvioPadrao() + " " + precCorrigida);
                    }
                    else{ mesSint[k][8] = 0.0; }
                }
            }
        }
    }

    public void calcularDuracaoPrec(){
        
    }
}
