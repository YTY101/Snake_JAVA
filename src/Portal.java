package src;

public class Portal {
    private Node portA, portB;
    
    public Portal(){
        portA = new Node();
        portB = new Node();
    }

    public Portal(int A_X, int A_Y, int B_X, int B_Y){
        portA = new Node();
        portB = new Node();
        portA.setX(A_X);
        portA.setY(A_Y);
        portB.setX(B_X);
        portB.setY(B_Y);
    }

    protected Node getA(){
        return portA;
    }
    
    protected Node getB(){
        return portB;
    }
}
