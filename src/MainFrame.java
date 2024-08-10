package src;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
    //定义成员变量
    private Snake snake = new Snake();
    JPanel jPanel = null;
    private Timer timer;
    protected static Map map = new Map();
    protected static int Height = map.getHeight(), Width = map.getWidth();
    protected static String Map = map.getMap();
    protected static Food food = new Food();


    public static void main(String[] args){
        new MainFrame().setVisible(true);
    }

    //初始化游戏
    public MainFrame() throws HeadlessException{
        // System.out.println(Map);
        initFrame();
        initGamePanel();
        initTimer();
        //添加键盘监听
        setkeyListener();
    }

    //检测墙壁
    protected static boolean isWall(int x, int y){
        if(Map.charAt(y * Width + x) == '@') return true;
        else return false;
    }
    
    //检测传送门
    protected static boolean isPortalA(int x, int y){
        if(Map.charAt(y * Width + x) == '$') return true;
        else return false;
    }
    protected static boolean isPortalB(int x, int y){
        if(Map.charAt(y * Width + x) == '￥') return true;
        else return false;
    }
    protected static boolean isPortal(int x, int y){
        return (isPortalA(x, y) || isPortalB(x, y));
    }

    //添加键盘监听
    private void setkeyListener(){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                Direction dir = null;
                Direction self_dir = snake.getDirection();
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if(self_dir != Direction.DOWN) dir = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        if(self_dir != Direction.UP) dir = Direction.DOWN;
                        break;
                    case KeyEvent.VK_LEFT:
                        if(self_dir != Direction.RIGHT) dir = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(self_dir != Direction.LEFT) dir = Direction.RIGHT;
                        break;
                    default:
                        break;
                }
                if(dir != null){
                    snake.setDirection(dir);
                }

            }
        });
    }
    //初始化窗口
    private void initFrame(){
        setTitle("Snake");
        setSize(610, 640);
        setLocation(400, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    //初始化游戏棋盘,自定义绘图
    private void initGamePanel(){
        jPanel = new JPanel(){
            @Override
            public void paint(Graphics g){
                g.clearRect(0, 0, getWidth(), getHeight());
                //画背景格
                g.setColor(Color.black);
                for(int i = 1; i <=40; i++){
                    g.drawLine(0, 15 * i, 600, 15 * i);
                }

                for(int i = 1; i <= 40; i++){
                    g.drawLine(15 * i, 0, 15 * i, 600);
                }
                //画蛇
                g.setColor(Color.gray);
                for(Node node : snake.getBody()){
                    g.fillRect(node.getX() * 15, node.getY() * 15, 15, 15);
                }
                g.setColor(Color.darkGray);
                g.fillRect(snake.getBody().get(0).getX() * 15, snake.getBody().get(0).getY() * 15, 15, 15);
                
                //画墙壁
                g.setColor(Color.black);
                for(int i = 0; i < Height; i++){
                    for(int j = 0; j  < Width; j++){
                        if(isWall(j, i)){
                            g.fillRect(j * 15, i * 15, 15, 15);
                        }
                    }
                }
                //画传送门A
                g.setColor(Color.blue);
                for(int i = 0; i < Height; i++){
                    for(int j = 0; j  < Width; j++){
                        if(isPortalA(j, i)){
                            g.fillRect(j * 15, i * 15, 15, 15);
                        }
                    }
                }
                //画传送门B
                g.setColor(Color.orange);
                for(int i = 0; i < Height; i++){
                    for(int j = 0; j  < Width; j++){
                        if(isPortalB(j, i)){
                            g.fillRect(j * 15, i * 15, 15, 15);
                        }
                    }
                }
                //画食物
                g.setColor(Color.red);
                g.fillRect(food.getX() * 15, food.getY() * 15, 15, 15);
            }
        };
        add(jPanel);
    }
    //初始化循环任务
    private void initTimer(){
        TimerTask task = new TimerTask() {
            @Override
            public void run(){
                snake.move();
                snake.checkOver();
                snake.Transport();
                snake.eatFood();
                jPanel.repaint();
                System.out.println(snake.isLive);
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 100);
    }
}