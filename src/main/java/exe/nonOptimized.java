package exe;

import Data.Post;
import Data.User;

import static java.lang.Math.abs;

public class nonOptimized {

    long startTime, endTime;
    boolean found = false;

    public nonOptimized(){

    }

    public void busquedas(User[] users, Post[] posts, String name, long publish){

        startTime = System.nanoTime();
        for(int i = 0; i< users.length && !found ; i++){
            if(users[i].username.equals(name)){
                users[i].toString();
                found = true;
            }
        }
        endTime = System.nanoTime();
        System.out.println("BÚSQUEDA NO OPTIMIZADA DE USER:" + (endTime-startTime));
        found = false;

        startTime = System.nanoTime();
        for(int i = 0; i< posts.length && !found ; i++){
            if(posts[i].publishedWhen == (publish)){
                posts[i].toString();
                found = true;
            }
        }
        endTime = System.nanoTime();
        System.out.println("BÚSQUEDA NO OPTIMIZADA DE POSTS:" + (endTime-startTime));
    }

    public void cincoPosts(Post[] posts, double puntoX, double puntoY){
        double worstDistX = Double.MAX_VALUE;
        double worstDistY = Double.MAX_VALUE;
        Post[] bests = new Post[5];
        for(int i = 0; i < 5; i++) {
            bests[i] = new Post();
        }
        startTime = System.nanoTime();
        for(int i = 0; i< posts.length && !found ; i++){
            if(abs(posts[i].location[0] - puntoX) < worstDistX || abs(posts[i].location[0] - puntoX) <  worstDistY){
                for(int j = 0; j < bests.length; j++){
                    if(abs(bests[j].location[0] - puntoX) == worstDistX || abs(bests[j].location[1] - puntoY) == worstDistY){
                        bests[j] = posts[i];
                        for(int k = 0; k < bests.length ;k++){
                            if(abs(bests[k].location[0] - puntoX) > worstDistX){
                                worstDistX = abs(bests[k].location[0] - puntoX);
                            }
                            if(abs(bests[k].location[1] - puntoY) > worstDistY){
                                worstDistY = abs(bests[k].location[1] - puntoY);
                            }
                        }
                        break;
                    }
                }
            }
        }
        endTime = System.nanoTime();
        for(int k = 0; k < bests.length ;k++) {
            System.out.println(k+"-("+bests[k].location[0]+","+bests[k].location[0]+")");
        }
        System.out.println("BÚSQUEDA NO OPTIMIZADA DE POSTS:" + (endTime-startTime));
    }
}
