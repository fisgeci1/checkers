import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    private static  final int col = 8;
    private MoveChecker moveChecker ;
    private Tilles[][] tilles = new Tilles[col][col];
    private int width ,height;
    public Board(MoveChecker moveChecke,int width,int height){

        this.width = width;
        this.height = height;
        setSize(width,height);
        GridLayout grid = new GridLayout(col,col);
        setLayout(grid);
        this.moveChecker = moveChecke;

        createTable(grid);




    }

private void createTable(GridLayout grid){
        for(int i = 0;i<col;i++){
            for(int j = 0; j<col;j++){
                if((i+j)%2 == 0){
                        add(tilles[i][j] = new Tilles(i,j,Color.RED,moveChecker,width/col,height/col));
                }else{
                    add(tilles[i][j] = new Tilles(i,j,Color.GRAY,moveChecker,width/col,height/col));
                }

            }

        }
}

    public Tilles[][] getTilles() {
        return tilles;
    }
}

