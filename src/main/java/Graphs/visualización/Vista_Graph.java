package Graphs.visualización;


import List.List;
import Graphs.Vertex;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.*;

public class Vista_Graph extends Canvas {
    private Circulo[] circulos;
    private List usus;
    private float HEIGHT = 700;
    private float WIDTH = 700;

    public Vista_Graph(List usus){
        int n_nodes = usus.size(); //Número de nodos del graph
        circulos = new Circulo[n_nodes];
        int divisor = 1; //Multiplicador que se aplicarà al divisor de pi de circle_draw

        this.usus = usus;

        //Bucle que dibuja los círculos que representan los nodos en un círculo
        for(float i = 0; i < n_nodes;i++){
            Graphics g;

            circle_draw(i, divisor, ((Vertex) usus.get(i)), 1);

            //En cas que el nombre sigui divisor de cuatre, s'augmenta el divisor
            if( (((i+1) % 4) == 0 )&&(i!=0)){
                if(divisor == 1){
                    divisor = 2;
                }else{
                  divisor = divisor * 2;
                }
            }
        }

        //Bucle que dibujará todas las conexiones entre usuarios
        /*
        for(int i = 0; i < usus.size(); i++){
            String origen =  ((Vertex)usus.get(i)).getUsername();
            for(int j = 0; j < ((Vertex)usus.get(i)).getRelations().size() ; j++){
                String destino = ((Vertex)((Vertex)usus.get(i)).getRelations().get(j)).getUsername();

                line_draw(origen, destino, circulos, usus);

            }
        }
        */
    }

    private void circle_draw(float i, int divisor, Vertex v, float multiplicador) {
//        float multiplicador;
 //       Graphics2D g2d = (Graphics2D) g;
        if(multiplicador == 1){
            //Se asigna el multiplicador de Pi en función de cuántos círculos haya
            if( (((i+1) % 4) == 0 )&&(i!=0)){
                multiplicador = i%4;
            }else{
                multiplicador = i - ((int)(i/4));
            }
            if ((i+1) < 5){
                multiplicador = i;
            }
        }


        //Se crea el nuevo círculo
        circulos[(int)i] = new Circulo(WIDTH/2 + cos((multiplicador*PI)/(divisor*2))*200,HEIGHT/2 + sin((multiplicador*PI)/(divisor*2))*200);

        for(int j = 0; j < i; j++){
            if((circulos[j].getX() <= circulos[(int)i].getX()+30)&&(circulos[j].getX() >= circulos[(int)i].getX()-30)){
                if((circulos[j].getY() <= circulos[(int)i].getY()+30)&&(circulos[j].getY() >= circulos[(int)i].getY()-30)){
                    circle_draw(i, divisor, v, multiplicador+1);
                    //multiplicador++;
                    //circulos[(int)i] = new Circulo(WIDTH/2 + cos((multiplicador*PI)/(divisor*2))*200,HEIGHT/2 + sin((multiplicador*PI)/(divisor*2))*200);
                }
            }
        }

        //Se dibuja dicho círculo y el texto de su interior
            //g.fillOval((int)circulos[i].getX(), (int)circulos[i].getY(), 50, 50);
            //((Graphics2D) g).drawString(v.getUsername(), (float) circulos[i].getX(), (float) circulos[i].getY());
    }

    private void line_draw(String origen, String destino, Circulo[] circulos, List usus, Graphics g, float[] nomx, float[] nomy) {
        int inicio = 0;
        int fin = 0;
        Random rand = new Random();
        int sum = rand.nextInt(17);
        int sumx = rand.nextInt(15);
        Graphics2D g2 = (Graphics2D) g;

        if(rand.nextInt(100) >= 50){
            sum = sum*(-1);
        }

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
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine((int)nomx[inicio]+sumx, (int)nomy[inicio]+sum, (int)nomx[fin]+sumx, (int)nomy[fin]+sum);

        //Se dibuja el triángulo
        int x[]={(int)nomx[fin], (int)nomx[fin]-25, (int)nomx[fin]+25};
        int y[]={(int)nomy[fin], (int)nomy[fin]-25, (int)nomy[fin]+25};
        g.setColor(Color.GRAY);
        g.fillOval((int)(nomx[fin]-2)+sumx, (int)(nomy[fin]-2)+sum, 5, 5);
    }

    @Override
    public void paint(Graphics g) {
        float nomx[] = new float[usus.size()];
        float nomy[] = new float[usus.size()];
        //Se dibuja dicho círculo y el texto de su interior
        for (int i = 0; i < circulos.length; i++){
            g.setColor(Color.BLACK);
            g.fillOval((int)circulos[i].getX(), (int)circulos[i].getY(), 50, 50);
            g.setColor(Color.WHITE);
            nomx[i]=(float) circulos[i].getX()+5;
            nomy[i]=(float) circulos[i].getY()+30;
            ((Graphics2D) g).drawString(((Vertex) usus.get(i)).getUsername(), (float) circulos[i].getX()+10, (float) circulos[i].getY()+30);
        }

        for(int i = 0; i < usus.size(); i++){
            String origen =  ((Vertex)usus.get(i)).getUsername();
            for(int j = 0; j < ((Vertex)usus.get(i)).getRelations().size() ; j++){
                String destino = ((Vertex)((Vertex)usus.get(i)).getRelations().get(j)).getUsername();

                line_draw(origen, destino, circulos, usus, g, nomx, nomy);

            }
        }

    }
}
