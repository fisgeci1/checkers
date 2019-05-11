import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tilles extends JPanel {
    private int row, col;
    private BoardM type = BoardM.Blank;
    private int width, height;
    private Color tilleCol;
    private MoveChecker moveChecker;
    public boolean allowedMove;

    public Tilles(int row, int col, Color color, MoveChecker moveChecker, int width, int height) {
        this.row = row;
        this.width = width;
        this.height = height;
        this.col = col;
        tilleCol = color;
        this.moveChecker = moveChecker;


        type = moveChecker.getTile(row, col);
//        System.out.println(type);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                moveChecker.checkMove(row, col);
            }
        });
    }

    public void paintComponent(Graphics g) {
        g.setColor(tilleCol);
        g.fillRect(0, 0, width, height);
//        System.out.println(type);
        switch (type) {
            case Red:
//                System.out.println("In here");
                g.setColor(Color.BLUE);
                g.fillOval(0, 0, width - 5, width - 5);

                break;
            case Black:
//                System.out.println("In here");
                g.setColor(Color.BLACK);
                g.fillOval(0, 0, width - 5, width - 5);
                break;
            case RedKing:
                g.setColor(Color.BLUE);
                g.fillOval(0, 0, width - 5, width - 5);
                g.setColor(Color.yellow);
                g.fillRect(0, height / 2 - 5, width - 5, 5);
                break;
            case BlackKing:
                g.setColor(Color.BLACK);
                g.fillOval(0, 0, width - 5, width - 5);
                g.setColor(Color.yellow);
                g.fillRect(0, height / 2 - 5, width - 5, 5);
                break;
            case Blank:
                break;
        }
        if (allowedMove) {
            g.setColor(Color.pink);
            g.fillOval(0, 0, width - 5, width - 5);
        }

    }

    public void setType(BoardM type) {
        this.type = type;
    }
}
