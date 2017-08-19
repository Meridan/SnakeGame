import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow(){
        setTitle("Snake Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(348,461);
        setLocation(100,100);
        add(new GameField());
        setVisible(true);
        setResizable(false);
    }
    public static void main(String[] args){
        MainWindow mw = new MainWindow();
    }
}
