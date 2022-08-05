package com.example.utils;

import com.example.model.Precipitacao;

public class DadosMensais {

    private String[] dadosMensais;
    private String mes;
    private int quant_dias_chuvosos;
    private int quant_dias_secos;
    private int quant_dias_totais;
    private int dca;
    private Double[] precipitacoesDiarias;
    private Precipitacao precipitacaoMensal;
    private Double[][] mesSintetico;
    private double mediaSintetica;
    private double desvioPadraoSintetico;

    public DadosMensais(){
        
    }

    public double getDesvioPadraoSintetico() {
        return desvioPadraoSintetico;
    }

    public void setDesvioPadraoSintetico(double desvioPadraoSintetico) {
        this.desvioPadraoSintetico = desvioPadraoSintetico;
    }


    public double getMediaSintetica() {
        return mediaSintetica;
    }

    public void setMediaSintetica(double mediaSintetica) {
        this.mediaSintetica = mediaSintetica;
    }

    public void setMesSintetico(Double[][] m){

        mesSintetico = m;
    }

    public Double[][] getMesSintetico(){
        return this.mesSintetico;
    }

    public void setDadosMensais(String[] dadosM){

        this.dadosMensais = dadosM;
    }

    public void setQuantDiasTotais(int dias){

        this.quant_dias_totais = dias;
    }

    public void setQuantDiasChuvosos(int dias){

        this.quant_dias_chuvosos = dias;
    }

    public void setQuantDiasSecos(int dias){

        this.quant_dias_secos = dias;
    }

    public void setMes(String m){

        this.mes = m;
    }

    public int getQuantDiasChuv(){

        return this.quant_dias_chuvosos;
    }

    public int getQuantDiasSec(){

        return this.quant_dias_secos;
    }

    public int getQuantDiasT(){

        return this.quant_dias_totais;
    }

    public Double[] getPrecitacoesDiarias(){

        return this.precipitacoesDiarias;
    }

    public String getMes(){

        return this.mes;
    }

    public void calcularDiasChuvosos(){

        int diasChuv = 0;
        precipitacoesDiarias = new Double[quant_dias_totais];
        for(int i = 13; i < quant_dias_totais + 13; i++){

            String pr = dadosMensais[i].replace(",", ".");
            Double precipitacao = Double.parseDouble(pr);

            if(precipitacao > 0){

                diasChuv = diasChuv + 1;
            }
            precipitacoesDiarias[i-13] = precipitacao;
        }
        setQuantDiasChuvosos(diasChuv);
        setQuantDiasSecos(quant_dias_totais - quant_dias_chuvosos);
        precipitacaoMensal = new Precipitacao(this);
        dca = Integer.parseInt(dadosMensais[7]);
    }

    public void imprimeMesSint(){

        for(int i=0;i<this.mesSintetico.length;i++){
            System.out.print(i + " ");
            for(int k=0;k<2;k++){

                System.out.print(mesSintetico[i][k] + " ");
            }
            System.out.println();
        }
    }

    public Precipitacao getPrecipitacao(){

        return this.precipitacaoMensal;
    }

    public boolean verFalha(){

        for(int i=0;i<mesSintetico.length;i++){

            for(int k=0;k<5;k++){
                
                if(k < 0 || k > 1){
                    return false;
                }
            }
        }
        return true;
    }
} 

