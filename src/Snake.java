package src;

import java.util.ArrayList;
import java.util.LinkedList;

public class Snake {
    //蛇身
    private LinkedList<Node> body;

    //蛇头运动方向
    private Direction direction;

    //初始化蛇
    public Snake(){
        initSnake();
    }

    protected boolean isLive = true;

    public void initSnake(){
        this.direction = Direction.LEFT;
        body = new LinkedList<>();
        body.add(new Node(20, 20));
        body.add(new Node(21, 20));
        body.add(new Node(22, 20));
    }

    //移动方法
    public void move(){
        Node head = body.getFirst();
        Node newHead = null;
        int dx = 0, dy = 0;
        switch(direction){
            case LEFT:
                dx = -1;
                break;
            case RIGHT:
                dx = 1;
                break;
            case UP:
                dy = -1;
                break;
            case DOWN:
                dy = 1;
                break;
        }
        int newX = head.getX() + dx;
        int newY = head.getY() + dy;
        if(newX < 0) newX = 40 + newX;
        if(newY < 0) newY = 40 + newY;
        newHead = new Node(newX % 40, newY % 40);
        body.addFirst(newHead);
        body.removeLast();
    }

    //吃食物
    public void eatFood(){
        Node head = body.getFirst();
        if(head.getX() == MainFrame.food.getX() && head.getY() == MainFrame.food.getY()){
            Node newHead = null;
            int dx = 0, dy = 0;
            switch(direction){
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
            }
            newHead = new Node(head.getX() + dx, head.getY() + dy);
            body.addFirst(newHead);
            MainFrame.food.reset();
        }
    }

    //空间折跃
    protected void Transport(){
        Node head = body.getFirst();
        ArrayList<Portal> portals = MainFrame.map.getPortals();
        int x = head.getX();
        int y = head.getY();
        for(Portal portal : portals){
            int A_X = portal.getA().getX();
            int A_Y = portal.getA().getY();
            int B_X = portal.getB().getX();
            int B_Y = portal.getB().getY();
            int Another_X = -1, Another_Y = -1;
            if(x == A_X && y == A_Y){
                Another_X = B_X;
                Another_Y = B_Y;
            }
            if(x == B_X && y == B_Y){
                Another_X = A_X;
                Another_Y = A_Y;
            }
            if(Another_X >= 0 && Another_Y >= 0){
                int dx = 0, dy = 0;
                switch(direction){
                    case LEFT:
                        dx = -1;
                        break;
                    case RIGHT:
                        dx = 1;
                        break;
                    case UP:
                        dy = -1;
                        break;
                    case DOWN:
                        dy = 1;
                        break;
                }
                Node newHead = new Node(Another_X + dx, Another_Y + dy);
                body.set(0, newHead);
            }
        }
    }


    //碰撞检测
    protected void checkOver(){
        Node head = body.getFirst();
        int x = head.getX();
        int y = head.getY();
        //自我碰撞检测
        for(int i = 1; i < body.size(); i++){
            if(x == body.get(i).getX() && y == body.get(i).getY()){
                if(this.isLive) this.isLive = false;
                //else this.isLive = true;
            }
        }
        //墙壁碰撞检测
        if(MainFrame.isWall(x, y)){
            if(this.isLive) this.isLive = false;
        }
    }

    public Direction getDirection(){
        return this.direction;
    }


    public void setDirection(Direction dir){
        this.direction = dir;
    }


    //访问接口
    public LinkedList<Node> getBody(){
        return body;
    }
}
