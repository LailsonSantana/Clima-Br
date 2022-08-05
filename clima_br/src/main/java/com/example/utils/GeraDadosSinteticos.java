package com.example.utils;

import java.util.ArrayList;
import java.util.Random;

import com.example.model.Precipitacao;

public class GeraDadosSinteticos extends VerificaFalhas{

    private ArrayList<DadosAnuais> dadosSemF = new ArrayList<>();
    private SerieSintetica serieSintetica = new SerieSintetica();
    
    public GeraDadosSinteticos(ArrayList<String[]> d){

        super(d);
        dadosSemF = super.getDadosSFalha();
    }

    public void geraDadosSinteticos(){
        
        /*for(DadosAnuais dadoAnual : dadosSemF){
            
            int mes  = -1;
            for(DadosMensais dadosMensais : dadoAnual.getDadosAnuais()){

                mes++;
                DadosMensais dm = dadoAnual.getDadosAnuais()[mes];
                Double[][] d = new Double[dm.getQuantDiasT()][10];
                Precipitacao p = dm.getPrecipitacao();
                d[0][6] = 0.0;
                double mediaMensalSintetica = 0;
                int diasChuvososSinteticos = 0;

                for(int i=0;i<dadosMensais.getQuantDiasT();i++){

                    for(int k=0;k<5;k++){ d[i][k] = gerarNumeroAleatorio(); }

                }
            }
        }*/
        for(int i=0;i<dadosSemF.size();i++){ // pega cada ano
            
            for(int k=0;k<dadosSemF.get(i).getDadosAnuais().length;k++){ // pega cada mês
                DadosMensais dm = dadosSemF.get(i).getDadosAnuais()[k];
                Double[][] d = new Double[dm.getQuantDiasT()][10];
                Precipitacao p = new Precipitacao();

                p = dm.getPrecipitacao();
                d[0][6] = 0.0;
                double mediaMensalSintetica = 0;
                int diasChuvososSinteticos = 0;
                for(int j=0;j<dm.getQuantDiasT();j++){

                    for(int m=0;m<5;m++){

                        d[j][m] = gerarNumeroAleatorio();
                    }

                    if(j == 0 || d[j-1][6] == 0.0){ // primeiro dia simulado
                        // PCS
                        // Dia anterior seco
                        
                        if(d[j][0] <= p.getPCS()){
                            
                            d[j][6] = 1.0; // passamos a considerar o dia como chuvoso
                            double z = serieSintetica.calcularZ(d[j][1]);
                            double x1 = serieSintetica.calcularVariavelAleatoria(z);
                            double prec = serieSintetica.calcularDistribuicaoPerson(p.getDesvioPadrao(), p.getCoefAssimetria()
                            , p.getMedia(),x1);
                            if(prec < 0.2){ // Lamina menor que 0.2
                                d[j][6] = 0.0;
                            } 
                            else{
                                d[j][7] = prec;
                                mediaMensalSintetica = mediaMensalSintetica + prec;
                                diasChuvososSinteticos = diasChuvososSinteticos + 1;
                            }
                        }
                        else{
                            
                            d[j][6] = 0.0;
                        }
                    }
                    
                    else{
                        //PCC
                        // Dia anterior chuvoso

                        if(d[j][0] <= p.getPCC()){

                            d[j][6] = 1.0;
                            double z = serieSintetica.calcularZ(d[j][1]);
                            double x1 = serieSintetica.calcularVariavelAleatoria(z);
                            double prec = serieSintetica.calcularDistribuicaoPerson(p.getDesvioPadrao(), p.getCoefAssimetria()
                            , p.getMedia(),x1);
                            if(prec < 0.2){ // Lamina menor que 0.2
                                d[j][6] = 0.0;
                            }
                            else{
                                d[j][7] = prec;
                                mediaMensalSintetica = mediaMensalSintetica + prec;
                                diasChuvososSinteticos = diasChuvososSinteticos + 1;
                            } 
                            
                        }
                        else{
                            d[j][6] = 0.0; 
                        }
                    }
                }
                if(diasChuvososSinteticos > 0){
                    dm.setMediaSintetica(mediaMensalSintetica/diasChuvososSinteticos);
                }
                else{
                    dm.setMediaSintetica(0);
                }

                if(diasChuvososSinteticos > 1){
                    double valor = serieSintetica.calcularDesvioPadraoSintetico(diasChuvososSinteticos, d,
                     mediaMensalSintetica/diasChuvososSinteticos);
                    dm.setDesvioPadraoSintetico(valor);
                }
                else{
                    dm.setDesvioPadraoSintetico(0);
                }
                dm.setMesSintetico(d);
                dadosSemF.get(i).inserirMesSint(dm, k);
            }
        }
        serieSintetica.corrigirMediasDiarias(dadosSemF);
    }

    /*public void verificarSeChoveu(int dia,double numAleatorio,Double[][] d,Precipitacao p){

        if(dia == 0 || d[dia-1][6] == 0.0){

            if(d[dia][0] <= p.getPCS()){
                            
                d[dia][6] = 1.0; // passamos a considerar o dia como chuvoso
                double z = serieSintetica.calcularZ(d[j][1]);
                double x1 = serieSintetica.calcularVariavelAleatoria(z);
                double prec = serieSintetica.calcularDistribuicaoPerson(p.getDesvioPadrao(), p.getCoefAssimetria()
                , p.getMedia(),x1);
                if(prec < 0.2){ // Lamina menor que 0.2
                    d[dia][6] = 0.0;
                } 
                else{
                    d[dia][7] = prec;
                    mediaMensalSintetica = mediaMensalSintetica + prec;
                    diasChuvososSinteticos = diasChuvososSinteticos + 1;
                }
            }
            else{
                
                d[j][6] = 0.0;
            }
        }
        else{

        }
    }*/


    public double gerarNumeroAleatorio(){

        Random random = new Random();

        return random.nextDouble();
    }

    public void imprime(){
        
        for(DadosAnuais d : dadosSemF){

            d.imprimirAnoSintetico();
            System.out.println();
        }
    }
}
