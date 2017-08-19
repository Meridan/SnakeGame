import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZEw = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int delay;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean down = false;
    private boolean up = false;
    private boolean inGame = true;
    private boolean pause = false;

    public GameField(){
    setBackground(Color.white);
    loadImages();
    initGame();
    addKeyListener(new FieldKeyListener());
    setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        delay = 500;
        for (int i=0; i<dots; i++){
            x[i]=48 - i*DOT_SIZE;
            y[i]=48;
        }
        timer = new Timer(delay,this);
        timer.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("apple.png");
    apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
    dot = iid.getImage();
    }
    public void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }


    @Override
    protected void paintComponent(Graphics g) {
        int speed = (510-(timer.getDelay())) / 5;
        super.paintComponent(g);
        g.drawRect(0,0,340,340);
        drawString(g, "Your score:" + (dots-3) + "\nCurrent speed:" + speed + "%", 50, SIZEw+50);
        drawString(g, "Press P to pause, press O to resume" , 100, SIZEw+80);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i<dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        } else {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            drawString(g, "Game Over \nYour score:" + (dots-3) + "\n\nPress R to restart", 50, SIZEw/2);
        }

    }

    public void move(){
        for (int i = dots; i>0; i--) {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
   }

   public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
            int delay = timer.getDelay();
            timer.setDelay(delay-5);
        }
   }

   public void checkCollisions(){
       for (int i = dots; i>0; i-- ) {
           if (i > 4 && x[0] == x[i] && y[0] == y[i]){
               inGame = false;
           }
       }
       if (x[0] > SIZEw){
           inGame = false;
       }
       if (x[0] < 0){
           inGame = false;
       }
       if (y[0] > SIZEw){
           inGame = false;
       }
       if (y[0] < 0){
           inGame = false;
       }
   }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkApple();
            checkCollisions();
            move();

        }
        repaint();


    }
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                left = false;
                up = true;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                left = false;
                down = true;
            }
            if (key == KeyEvent.VK_P && !pause) {
                timer.stop();
                pause = !pause;
            }
            if (key == KeyEvent.VK_O && pause) {
                timer.start();
                pause = !pause;
            }

            if (key == KeyEvent.VK_R) {
                timer.stop();
                inGame = true;
                initGame();
                dots=3;
                delay=500;
                right = true;
                left = false;
                up = false;
                down = false;
            }

        }
    }
}

