package Graphs.visualización;


import Graphs.List;
import Graphs.Vertex;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static java.lang.Math.*;

public class Vista_Graph extends Canvas {
    private Circulo[] circulos;
    private float HEIGHT = 700;
    private float WIDTH = 700;

    public Vista_Graph(List usus){
        int n_nodes = usus.size(); //Número de nodos del graph
        circulos = new Circulo[n_nodes];
        int divisor = 1; //Multiplicador que se aplicarà al divisor de pi de circle_draw

        //Bucle que dibuja los círculos que representan los nodos en un círculo
        for(int i = 0; i < n_nodes;i++){

            //circle_draw(i, divisor, ((Vertex) usus.get(i)).getUsername(),Graphics);

            //En cas que el nombre sigui divisor de cuatre, s'augmenta el divisor
            if( (i % 4) == 0 ){
                if(divisor == 1){
                    divisor = 2;
                }else{
                  divisor = divisor * 2;
                }
            }
        }

        //Bucle que dibujará todas las conexiones entre usuarios
        for(int i = 0; i < usus.size(); i++){
            String origen =  ((Vertex)usus.get(i)).getUsername();
            for(int j = 0; j < ((Vertex)usus.get(i)).getRelations().size() ; j++){
                String destino = ((Vertex)((Vertex)usus.get(i)).getRelations().get(j)).getUsername();

                //line_draw(origen, destino, usus ,circulos);

            }
        }

    }

    private void line_draw(String origen, String destino, Circulo[] circulos, List usus ,Graphics g) {
        int inicio = 0;
        int fin = 0;

        //Bucle para buscar los índices de cada extremo de la flecha
        for(int i = 0; i < usus.size(); i++){
            if(((Vertex)usus.get(i)).getUsername().equals(origen)){
                inicio = i;
            }else{
                if(((Vertex)usus.get(i)).getUsername().equals(destino)){
                    fin = i;
                }
            }

        }

        //Se dibuja la linea
        g.drawLine((int)circulos[inicio].getX(), (int)circulos[inicio].getY(), (int)circulos[fin].getX(), (int)circulos[fin].getY());
        //Se dibuja el triángulo
        int x[]={(int)circulos[fin].getX(), (int)circulos[fin].getX()+20, (int)circulos[fin].getX()+20};
        int y[]={(int)circulos[fin].getY(), (int)circulos[fin].getY()+20, (int)circulos[fin].getY()+20};
        g.drawPolygon(x, y, 3);
    }

    private void circle_draw(int i, int divisor, Vertex v ,Graphics g) {
        int multiplicador;
        Graphics2D g2d = (Graphics2D) g;

        //Se asigna el multiplicador de Pi en función de cuántos círculos haya
        if(i%4 != 0){
            multiplicador = i%4;
        }else{
            multiplicador = 4;
        }
        if (i < 5){
            multiplicador = i;
        }

        //Se crea el nuevo círculo
        circulos[i] = new Circulo(WIDTH/2 + cos((multiplicador*PI)/(divisor*2))*200,HEIGHT/2 + sin((multiplicador*PI)/(divisor*2))*200);

        //Se dibuja dicho círculo y el texto de su interior
        g.fillOval((int)circulos[i].getX(), (int)circulos[i].getY(), 50, 50);
        ((Graphics2D) g).drawString(v.getUsername(), (float) circulos[i].getX(), (float) circulos[i].getY());
    }
}
