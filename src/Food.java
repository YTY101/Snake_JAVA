package src;

import java.util.Random;

public class Food extends Node {
    public Food(){
        reset();
    }

    public void reset(){
        Random r = new Random();
        int X, Y;
        // System.out.println(MainFrame.Width);
        // System.out.println(MainFrame.Height);

        do{
            X = r.nextInt(MainFrame.Width);
            Y = r.nextInt(MainFrame.Height);
        }while(MainFrame.isWall(X, Y) || MainFrame.isPortal(X, Y));
        
        setX(X);
        setY(Y);
    }
}
