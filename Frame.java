import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame(Board board,int width,int height){
        setSize(width,height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.add(board);
        setResizable(false);
        setLocation(900,height/2);
        setVisible(true);
    }
}
