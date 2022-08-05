package com.example.model;

import java.text.DecimalFormat;

import com.example.utils.DadosMensais;

public class Precipitacao{

    private double media;
    private double desvioPadrao;
    private double coeficienteAssimetria;
    private double pcc;
    private double pcs;
    private DadosMensais dadosMensais;

    public Precipitacao(DadosMensais dados){

        dadosMensais = dados;
        this.calcularMediaMensal();
        this.calcularDesvioPadrao();
        this.calcularCoeficienteAssimetria();
        this.calcularDCC();
        this.calcularDCS();
        this.calcularPCS();
        this.calcularPCC();
    }

    public Precipitacao(){
        
    }

    public void setMedia(double m){

        this.media = m;
    }

    public void setDesvioPadrao(double d){

        this.desvioPadrao = d;
    }

    public void setCoeficienteAssimetria(double c){
        this.coeficienteAssimetria = c;
    }

    public double getMedia(){
        return media;
    }

    public double getDesvioPadrao(){
        return this.desvioPadrao;
    }

    public double getCoefAssimetria(){
        return this.coeficienteAssimetria;
    }

    public double getPCC(){
        return pcc;
    }

    public double getPCS(){
        return pcs;
    }

    public void calcularMediaMensal(){

        double somatorio = 0;
        if(dadosMensais.getQuantDiasChuv() > 0){

            for(Double prec : dadosMensais.getPrecitacoesDiarias()){

                somatorio = somatorio + prec;
            }
            this.media = somatorio / dadosMensais.getQuantDiasChuv();
        }
        else{
            this.media = 0;
        } 
    }

    public void calcularDesvioPadrao(){

        if(dadosMensais.getQuantDiasChuv() > 1){
            
            double somatorio = 0;
            for(Double prec : dadosMensais.getPrecitacoesDiarias()){
                if(prec > 0){ // precipitação deve ser maior que 0

                    somatorio = somatorio + Math.pow((prec - this.media), 2);
                } 
            }
            somatorio = Math.sqrt(somatorio / (dadosMensais.getQuantDiasChuv() - 1));
            this.desvioPadrao = somatorio;
        }
        else{
            this.desvioPadrao = 0;
        } 
    }

    public void calcularCoeficienteAssimetria(){

        if(dadosMensais.getQuantDiasChuv() > 2){

            double somatorio = 0;
            double n = dadosMensais.getQuantDiasChuv();
            n = n / ((n-1) * (n-2)); //pag 27 da dissertação

            for(Double prec : dadosMensais.getPrecitacoesDiarias()){

                if(prec > 0){
                    somatorio = somatorio + Math.pow(((prec - this.media) / this.desvioPadrao), 3);
                } 
            }
            somatorio = somatorio * n;
            this.coeficienteAssimetria = somatorio;
        }
        else{
            this.coeficienteAssimetria = 0;
        }
    }
    
    public void calcularPCC(){

        double pcc1 = 0;
        if(dadosMensais.getQuantDiasChuv() > 0){
            pcc1 = calcularDCC() / dadosMensais.getQuantDiasChuv();
        }
        this.pcc = pcc1;
    }

    public void calcularPCS(){

        double pcs1 = 0;
        if(dadosMensais.getQuantDiasSec() > 0){
            pcs1 = calcularDCS() / dadosMensais.getQuantDiasSec();
        }
        
        this.pcs = pcs1;
    }
    // para o primeiro dia simulado considera-se o dia anterior seco pag 31
    public double calcularDCC(){ // Dia chuvoso sendo o anterior também chuvoso

        double contador = 0;
        for(int i=0; i < dadosMensais.getPrecitacoesDiarias().length; i++){

            if(dadosMensais.getPrecitacoesDiarias()[i] > 0 && i > 0){

                if(dadosMensais.getPrecitacoesDiarias()[i-1] > 0){

                    contador = contador + 1;
                }
            }
        }
        return contador;
    }


    public double calcularDCS(){ // Dia chuvoso sendo o anterior seco

        double contador = 0;
        for(int i=0; i < dadosMensais.getPrecitacoesDiarias().length; i++){
            
            if(dadosMensais.getPrecitacoesDiarias()[i] > 0 && i > 0){

                if(dadosMensais.getPrecitacoesDiarias()[i-1] == 0){

                    contador = contador + 1;
                }
            }
        }
        return contador;
    }

    public void toStringPrecitacao(){
        
        System.out.println("MEDIA: " + f(this.media) + "- DESVIO: " + f(this.desvioPadrao) + "- COEF: " + 
        f(this.coeficienteAssimetria));
    }

    public String f(double d){
        return new DecimalFormat("#,##0.00").format(d);
    }
}
