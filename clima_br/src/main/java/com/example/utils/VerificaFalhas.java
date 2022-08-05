package com.example.utils;

import java.time.Year;
import java.util.ArrayList;

//COMO HÁ A NECESSIDADE DE ELIMINAR OS ANOS QUE POSSUEM ALGUM MÊS COM FALHA
//CRIEI ESSA CLASSE PARA ANALISAR TODOS OS ANOS DISPONÍVEIS NO ARQUIVO
// ELA TAMBÉM ELIMINA OS ANOS QUE NÃO POSSUEM OS 12 MESES


public class VerificaFalhas{

    private ArrayList<DadosAnuais> dadosSFalha = new ArrayList<>();
    //private DadosMensais[] dadosM = new DadosMensais[12];
    private DadosMensais dadosM;
    private DadosAnuais dadosA;

    //void verificarFalhas

    public VerificaFalhas(ArrayList<String[]> dados){

        String ano = getAno(dados.get(0)[2]);
        int inicio = 0;
        for(int i=0;i<dados.size();i++){
            
            if((!getAno(dados.get(i)[2]).equals(ano)) || (i == dados.size()-1)){
                if((i - inicio) == 12){

                    if(! procurarFalhas(inicio,i, dados)){

                        int indice = 0;
                        dadosA = new DadosAnuais();
                        dadosA.setAno(this.getAno(dados.get(inicio)[2]));
                        for(int j=inicio;j<i;j++){

                            int diasMes = verificaMes(dados.get(j)[2]);
                            dadosM = new DadosMensais();
                            dadosM.setQuantDiasTotais(diasMes);
                            dadosM.setMes(this.getMes(dados.get(j)[2]));
                            dadosM.setDadosMensais(dados.get(j));
                            dadosM.calcularDiasChuvosos();

                            dadosA.inserirMes(indice, dadosM);
                            indice = indice + 1;
                            
                        }
                        dadosSFalha.add(dadosA);
                        indice = 0;
                    }
                }
                inicio = i;
                ano = getAno(dados.get(i)[2]);
            }
        }
    }

    public ArrayList<DadosAnuais> getDadosSFalha(){ return dadosSFalha; }
  
    private boolean procurarFalhas(int inicio,int fim,ArrayList<String[]> dados){

        for(int i=inicio;i<fim;i++){

            int limite = 0;
            int mes = verificaMes(dados.get(i)[2]);

            if(mes == 31){
                limite = 44;
            }
            else if(mes == 30){
                limite = 43;
            }
            else if(mes == 29){
                limite = 42;
            }
            else{
                limite = 41; 
            }

            for(int k=13;k<limite;k++){

                if(dados.get(i)[k].isEmpty()){
                    return true;// Foi encontrado alguma falha nesse mês
                }
            }
        }

        return false;
    }

    public int verificaMes(String data){

        int dias = 0;

        if(Year.isLeap(Long.parseLong(getAno(data)))){ // Verifica se o ano é bissexto

            dias = 1;
        }

        String mes = this.getMes(data);
        
        if(mes.equals("04") || mes.equals("06") || mes.equals("09") || 
            mes.equals("11")){
            return 30;
        }
        else if(mes.equals("01") || mes.equals("03") || mes.equals("05")
             || mes.equals("07") || mes.equals("08") || mes.equals("10") 
             || mes.equals("12")){
            return 31;
        }
        else{
            return 28 + dias;
        }
        
    }

    public String getAno(String data){

        String[] dataDividida = new String[3];
        dataDividida = data.split("/");
        return dataDividida[2];

    }

    public String getMes(String data){

        String[] dataDividida = new String[3];
        dataDividida = data.split("/");
        return dataDividida[1];

    }

    public String getDia(String data){

        String[] dataDividida = new String[3];
        dataDividida = data.split("/");
        return dataDividida[0];

    }

    public DadosMensais getDadosMensais(){ return dadosM; }

    public DadosAnuais getDadosAnuais(){ return dadosA; }

    public ArrayList<DadosAnuais> getDadosSemFalha(){ return dadosSFalha; }
          
}
