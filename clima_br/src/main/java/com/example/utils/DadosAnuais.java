package com.example.utils;

public class DadosAnuais {

    private DadosMensais[] dadosAnuais = new DadosMensais[12];
    private DadosMensais[] dadosAnuaisSint = new DadosMensais[12];
    
    private String ano;

    public void inserirMesSint(DadosMensais dm,int indice){
        dadosAnuaisSint[indice] = dm;
    }

    public DadosMensais[] getDadosAnuaisSint() {
        return dadosAnuaisSint;
    }

    public void setDadosAnuaisSint(DadosMensais[] dadosAnuaisSint) {
        this.dadosAnuaisSint = dadosAnuaisSint;
    }

    public DadosMensais[] getDadosAnuais(){
        return dadosAnuais;
    }

    public void setAno(String a){
        this.ano = a;
    }

    public String getAno(){
        return this.ano;
    }

    public void inserirMes(int mes, DadosMensais d){
        dadosAnuais[mes] = d;
    }

    public void imprimirAnoSintetico(){

        for(DadosMensais d : dadosAnuais){
            
            for(int i=0;i<d.getMesSintetico().length;i++){
                System.out.println((i+1) + "/" + d.getMes() + "/" + this.getAno() + " = " + d.getMesSintetico()[i][8]);
            }
        }
    }
}
