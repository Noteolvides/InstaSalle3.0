package Graphs.visualización;


import Graphs.List;
import Graphs.Vertex;

import java.awt.*;

public class Vista_Graph extends Canvas {
    private Circulo[] circulos;

    public Vista_Graph(List usus){
        int n_nodes = usus.size(); //Número de nodos del graph
        circulos = new Circulo[n_nodes];
        int divisor = 1; //Multiplicador que se aplicarà al divisor de pi de circle_draw

        //Bucle que dibuja los círculos que representan los nodos en un círculo
        for(int i = 0; i < n_nodes;i++){
            circle_draw(i, divisor);
            //En cas que el nombre sigui divisor de cuatre, s'augmenta el divisor
            if( (i % 4) == 0 ){
                if(divisor == 1){
                    divisor = 2;
                }else{
                  divisor = divisor * 2;
                }
            }
        }

        for(int i = 0; i < usus.size(); i++){
            String origen =  ((Vertex)usus.get(i)).getUsername();
            for(int j = 0; j < ((Vertex)usus.get(i)).getRelations().size() ; j++){
              String destino = ((Vertex)((Vertex)usus.get(i)).getRelations().get(j)).getUsername();
              line_draw(origen, destino, circulos);                
            }
        }

    }

    private void line_draw(String origen, String destino, Circulo[] circulos) {
    }

    private void circle_draw(int i, int divisor) {
    }
}
