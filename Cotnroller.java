public class Cotnroller {

    public static void main(String[] args){
            int width = 500;
        MoveChecker move  = new MoveChecker();
        Board board = new Board(move,width,width);
        move.setTilles(board.getTilles());

        Frame frame  = new Frame(board,width,width);


    }
}
