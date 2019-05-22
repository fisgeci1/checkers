import javax.swing.*;

public class Cotnroller {

    public static void main(String[] args) {
        int width = 500;
        MoveChecker move = new MoveChecker();
        Board board = new Board(move, width, width);


        int answer = JOptionPane.showConfirmDialog(null, "Play against computer", "Answeer", JOptionPane.YES_OPTION);
        RandomBot random = null;
        if (answer == 0) {
            random = new RandomBot(move);
        }

        move.setTilles(board.getTilles());
        move.setBot(random);
        Frame frame = new Frame(board, width, width);


    }
}
