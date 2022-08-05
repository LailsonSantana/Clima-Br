package com.example.utils;

public class CalculaDuracaoPrecipitacao {

    float alfa = 2.3250f;
    float beta = 0.1603f;
    float funcaoGama = 0.1845f;
    double constante = 1 / (funcaoGama * Math.pow(beta, alfa));
    double area = 0;
    double x = 0;
    double dx = 0.001;

    public double calcularIntegral(double random){
        while((area * constante) < random){
            double y1 = Math.pow(x, alfa-1) * Math.exp(-x/beta);
            x = x + dx;
            double y2 = Math.pow(x, alfa-1) * Math.exp(-x/beta);
            area = area + (dx * (y1 + y2) / 2);
            
        }
        if(x > 24){
            x = 24;
        }
        else if(x < 0.2){
            x = 0.2;
        }
        return x;
    }

    public double calcularDuracao(double alfaIntegral){
        double duracao = 3.33 / (-2 * Math.log(1 - alfaIntegral));
        return duracao;
    }
    
}
