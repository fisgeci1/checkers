import java.awt.*;

public class MoveChecker {

    private Tilles[][] tilles;
    private boolean tileSelected;
    private BoardM turn = BoardM.Black;
    private static int[][] allowedMoves = new int[8][2];
    private static int selectedRow, selectedCol;
    private RandomBot bot = null;
    // 1 player  -1 bot
    private int botTurn = 1;
    private static BoardM[][] board = {{BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red},
            {BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank},
            {BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red},
            {BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank},
            {BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank},
            {BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank},
            {BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black},
            {BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank}};


    public void checkMove(int row, int col) {
//        System.out.println("In here");

        if (tileSelected || botTurn == -1) {
//            if (botTurn == -1) {
//                System.out.println("called from bot");
//            }
            if (row != -10 && col != -10) {
                takePiece(row, col);
            }
            if (bot != null && botTurn == -1) {
                bot.setBoard(board);
                allowedMoves = new int[8][2];
                bot.robotMove();
            }
            if (winCondition()) {
                System.out.println("Game over");
            }


        } else {
            selectedRow = row;
            selectedCol = col;
            allowedMoves = new int[8][2];

            if (turn == getColorOfTille(board[row][col])) {
                tileSelected = true;
                checkMovesForPiece(row, col);
            }

        }
    }

    //returns the number of pieces of player(whose turn it is)
    private int numOfPieces() {
        int num = 0;
        for (int i = 0; i < tilles.length; i++)
            for (int j = 0; j < tilles[0].length; j++) {
                if ((board[i][j] != BoardM.Blank) && getColorOfTille(board[i][j]) == turn) {
                    num++;
                }
            }

        return num;
    }

    //checks if player has won
    private boolean winCondition() {
        boolean won = true;
        if (numOfPieces() > 0) {
            for (int i = 0; i < tilles.length; i++)
                for (int j = 0; j < tilles[0].length; j++) {
                    if (!canMove(i, j)) {
                        won = false;
                        break;
                    }
                }
        }

        return won;
    }

    //checks if the player whose turn it is has at least  a piece that can be moved!
    private boolean canMove(int row, int col) {
        if ((board[row][col] != BoardM.Blank) && getColorOfTille(board[row][col]) == turn) {
            boolean jumping = false;

            if (board[row][col] != BoardM.Black) {
                if ((row + 2 < 8 && col + 2 < 8)) {
                    if ((board[row + 1][col + 1] != BoardM.Blank) && getColorOfTille(board[row + 1][col + 1]) != getColorOfTille(board[row][col]) && board[row + 2][col + 2] == BoardM.Blank) {
                        return false;
                    }
                }
            }
            if (board[row][col] != BoardM.Black) {
                if ((row + 2 < 8 && col - 2 > -1)) {
                    if ((board[row + 1][col - 1] != BoardM.Blank) && getColorOfTille(board[row + 1][col - 1]) != getColorOfTille(board[row][col]) && board[row + 2][col - 2] == BoardM.Blank) {
                        return false;
                    }
                }
            }
            if (board[row][col] != BoardM.Red) {
                if ((row - 2 > -1 && col + 2 < 8)) {
                    if ((board[row - 1][col + 1] != BoardM.Blank) && getColorOfTille(board[row - 1][col + 1]) != getColorOfTille(board[row][col]) && board[row - 2][col + 2] == BoardM.Blank) {
                        return false;
                    }
                }
            }
            if (board[row][col] != BoardM.Red) {
                if ((row - 2 > -1 && col - 2 > -1)) {
                    if ((board[row - 1][col - 1] != BoardM.Blank) && getColorOfTille(board[row - 1][col - 1]) != getColorOfTille(board[row][col]) && board[row - 2][col - 2] == BoardM.Blank) {
                        return false;
                    }
                }
            }
            if (board[row][col] != BoardM.Black) {
                if (row + 1 < 8 && col + 1 < 8) {
                    if (!jumping && board[row + 1][col + 1] == BoardM.Blank) {
                        return false;
                    }
                }
            }
            if (board[row][col] != BoardM.Black) {
                if (row + 1 < 8 && col - 1 > -1) {
                    if (!jumping && board[row + 1][col - 1] == BoardM.Blank) {
                        return false;
                    }
                }
            }

            if (board[row][col] != BoardM.Red) {
                if (row - 1 > -1 && col + 1 < 8) {
                    if (!jumping && board[row - 1][col + 1] == BoardM.Blank) {
                        return false;
                    }
                }
            }
            if (board[row][col] != BoardM.Red) {
                if (row - 1 > -1 && col - 1 > -1) {
                    if (!jumping && board[row - 1][col - 1] == BoardM.Blank) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    //checks the valid moves of selected king pieces
    private void checkMovesForPiece(int row, int col) {
        boolean jumping = false;
        boolean check = true;
        if (board[row][col] != BoardM.Black) {
            if ((row + 2 < 8 && col + 2 < 8)) {
                if ((board[row + 1][col + 1] != BoardM.Blank) && getColorOfTille(board[row + 1][col + 1]) != getColorOfTille(board[row][col]) && board[row + 2][col + 2] == BoardM.Blank) {
                    tilles[row + 2][col + 2].allowedMove = true;
                    tilles[row + 2][col + 2].repaint();
                    allowedMoves[7][0] = row + 2;
//                System.out.println("Here");
                    allowedMoves[7][1] = col + 2;
                    jumping = true;
                    check = false;
                }
            }
        }
        if (board[row][col] != BoardM.Black) {
            if ((row + 2 < 8 && col - 2 > -1)) {
                if ((board[row + 1][col - 1] != BoardM.Blank) && getColorOfTille(board[row + 1][col - 1]) != getColorOfTille(board[row][col]) && board[row + 2][col - 2] == BoardM.Blank) {
                    tilles[row + 2][col - 2].allowedMove = true;
                    tilles[row + 2][col - 2].repaint();
//                System.out.println("Here");
                    allowedMoves[6][0] = row + 2;
                    allowedMoves[6][1] = col - 2;
                    jumping = true;
                    check = false;
                }
            }
        }
        if (board[row][col] != BoardM.Red) {
            if ((row - 2 > -1 && col + 2 < 8)) {
                if ((board[row - 1][col + 1] != BoardM.Blank) && getColorOfTille(board[row - 1][col + 1]) != getColorOfTille(board[row][col]) && board[row - 2][col + 2] == BoardM.Blank) {
                    tilles[row - 2][col + 2].allowedMove = true;
                    tilles[row - 2][col + 2].repaint();
                    allowedMoves[5][0] = row - 2;
//                System.out.println("Here");
                    allowedMoves[5][1] = col + 2;
                    jumping = true;
                    check = false;
                }
            }
        }
        if (board[row][col] != BoardM.Red) {
            if ((row - 2 > -1 && col - 2 > -1)) {
                if ((board[row - 1][col - 1] != BoardM.Blank) && getColorOfTille(board[row - 1][col - 1]) != getColorOfTille(board[row][col]) && board[row - 2][col - 2] == BoardM.Blank) {
                    tilles[row - 2][col - 2].allowedMove = true;
                    tilles[row - 2][col - 2].repaint();
//                System.out.println("Here");
                    allowedMoves[4][0] = row - 2;
                    allowedMoves[4][1] = col - 2;
                    jumping = true;
                    check = false;
                }
            }
        }
        if (board[row][col] != BoardM.Black) {
            if (row + 1 < 8 && col + 1 < 8) {
                if (!jumping && board[row + 1][col + 1] == BoardM.Blank) {
                    tilles[row + 1][col + 1].allowedMove = true;
//                System.out.println("Here");
                    tilles[row + 1][col + 1].repaint();
                    allowedMoves[3][0] = row + 1;
                    allowedMoves[3][1] = col + 1;
                    allowedMoves[6][0] = -1;
                    allowedMoves[6][1] = -1;
                    allowedMoves[7][0] = -1;
                    allowedMoves[7][1] = -1;
                    check = false;
                }
            }
        }
        if (board[row][col] != BoardM.Black) {
            if (row + 1 < 8 && col - 1 > -1) {
                if (!jumping && board[row + 1][col - 1] == BoardM.Blank) {
                    tilles[row + 1][col - 1].allowedMove = true;
//                System.out.println("Here");
                    tilles[row + 1][col - 1].repaint();
                    allowedMoves[2][0] = row + 1;
                    allowedMoves[2][1] = col - 1;
                    allowedMoves[6][0] = -1;
                    allowedMoves[6][1] = -1;
                    allowedMoves[7][0] = -1;
                    allowedMoves[7][1] = -1;
                    check = false;
                }
            }
        }

        if (board[row][col] != BoardM.Red) {
            if (row - 1 > -1 && col + 1 < 8) {
                if (!jumping && board[row - 1][col + 1] == BoardM.Blank) {
                    tilles[row - 1][col + 1].allowedMove = true;
//                System.out.println("Here");
                    tilles[row - 1][col + 1].repaint();
                    allowedMoves[1][0] = row - 1;
                    allowedMoves[1][1] = col + 1;
                    allowedMoves[5][0] = -1;
                    allowedMoves[5][1] = -1;
                    allowedMoves[4][0] = -1;
                    allowedMoves[4][1] = -1;
                    check = false;
                }
            }
        }
        if (board[row][col] != BoardM.Red) {
            if (row - 1 > -1 && col - 1 > -1) {
                if (!jumping && board[row - 1][col - 1] == BoardM.Blank) {
                    tilles[row - 1][col - 1].allowedMove = true;
//                System.out.println("Here");
                    tilles[row - 1][col - 1].repaint();
                    allowedMoves[0][0] = row - 1;
                    allowedMoves[0][1] = col - 1;
                    allowedMoves[5][0] = -1;
                    allowedMoves[5][1] = -1;
                    allowedMoves[4][0] = -1;
                    allowedMoves[4][1] = -1;
                    check = false;
                }
            }
        }
        if (check) {
            tileSelected = false;
        }

    }

    //checks to see if another jump(piece can be taken).
    private boolean anotherJump(int row, int col) {
        boolean antoherJump = false;
        if (board[row][col] == BoardM.Red || board[row][col] == BoardM.RedKing || board[row][col] == BoardM.BlackKing) {
            if ((row + 2 < 8 && col + 2 < 8)) {
                if ((board[row + 1][col + 1] != BoardM.Blank) && getColorOfTille(board[row + 1][col + 1]) != getColorOfTille(board[row][col]) && board[row + 2][col + 2] == BoardM.Blank) {
                    tilles[row + 2][col + 2].allowedMove = true;
                    tilles[row + 2][col + 2].repaint();
                    allowedMoves[7][0] = row + 2;
//                System.out.println("Here");
                    allowedMoves[7][1] = col + 2;
                    antoherJump = true;
                }
            }
            if ((row + 2 < 8 && col - 2 > -1)) {
                if ((board[row + 1][col - 1] != BoardM.Blank) && getColorOfTille(board[row + 1][col - 1]) != getColorOfTille(board[row][col]) && board[row + 2][col - 2] == BoardM.Blank) {
                    tilles[row + 2][col - 2].allowedMove = true;
                    tilles[row + 2][col - 2].repaint();
//                System.out.println("Here");
                    allowedMoves[6][0] = row + 2;
                    allowedMoves[6][1] = col - 2;
                    antoherJump = true;
                }
            }
        }
        if (board[row][col] == BoardM.Black || board[row][col] == BoardM.RedKing || board[row][col] == BoardM.BlackKing) {
            if ((row - 2 > -1 && col + 2 < 8)) {
                if ((board[row - 1][col + 1] != BoardM.Blank) && getColorOfTille(board[row - 1][col + 1]) != getColorOfTille(board[row][col]) && board[row - 2][col + 2] == BoardM.Blank) {
                    tilles[row - 2][col + 2].allowedMove = true;
                    tilles[row - 2][col + 2].repaint();
                    allowedMoves[5][0] = row - 2;
//                System.out.println("Here");
                    allowedMoves[5][1] = col + 2;
                    antoherJump = true;
                }
            }
            if ((row - 2 > -1 && col - 2 > -1)) {
                if ((board[row - 1][col - 1] != BoardM.Blank) && getColorOfTille(board[row - 1][col - 1]) != getColorOfTille(board[row][col]) && board[row - 2][col - 2] == BoardM.Blank) {
                    tilles[row - 2][col - 2].allowedMove = true;
                    tilles[row - 2][col - 2].repaint();
//                System.out.println("Here");
                    allowedMoves[4][0] = row - 2;
                    allowedMoves[4][1] = col - 2;
                    antoherJump = true;
                }
            }
        }

        return antoherJump;
    }

    private void takePiece(int row, int col) {
        if (board[row][col] == BoardM.Blank) {
            boolean valid = false;
            boolean jumped = false;
            for (int i = 0; i < allowedMoves.length; i++) {
                if (row == allowedMoves[i][0] && col == allowedMoves[i][1]) {
                    valid = true;
                }
            }

            if (valid || botTurn == -1) {

                //red pieces
                if (selectedRow - row < 0) {

                    if (allowedMoves[7][0] != -1 || allowedMoves[6][0] != -1) {
                        if (selectedCol - col < 0) {
                            jumped = true;
//                            System.out.println("row -1 col -1");
                            board[row][col] = board[selectedRow][selectedCol];
                            tilles[row][col].setType(board[selectedRow][selectedCol]);
                            board[row - 1][col - 1] = BoardM.Blank;
                            tilles[row - 1][col - 1].setType(BoardM.Blank);
                            tilles[row - 1][col - 1].repaint();
                        } else {
//                            System.out.println("row -1 col +1");

                            jumped = true;
                            board[row][col] = board[selectedRow][selectedCol];
                            tilles[row][col].setType(board[selectedRow][selectedCol]);
                            board[row - 1][col + 1] = BoardM.Blank;
                            tilles[row - 1][col + 1].setType(BoardM.Blank);
                            tilles[row - 1][col + 1].repaint();
                        }
                    } else {


                        tilles[row][col].setType(board[selectedRow][selectedCol]);
                        board[row][col] = board[selectedRow][selectedCol];

                    }
                    board[selectedRow][selectedCol] = BoardM.Blank;
                    tilles[selectedRow][selectedCol].setType(BoardM.Blank);

                    tilles[selectedRow][selectedCol].repaint();

                    if (botTurn != -1) {
                        for (int i = 0; i < 8; i++) {
                            if (allowedMoves[i][0] != -1) {
                                tilles[allowedMoves[i][0]][allowedMoves[i][1]].allowedMove = false;
                                tilles[allowedMoves[i][0]][allowedMoves[i][1]].repaint();
                            }
                        }
                    }


                }
                //gray Pieces
                else {
                    if (allowedMoves[5][0] != -1 || allowedMoves[4][0] != -1) {
                        if (selectedCol - col < 0) {
                            jumped = true;
//                            System.out.println("row +1 col +1");

                            board[row][col] = board[selectedRow][selectedCol];
                            tilles[row][col].setType(board[selectedRow][selectedCol]);
                            board[row + 1][col - 1] = BoardM.Blank;
                            tilles[row + 1][col - 1].setType(BoardM.Blank);
                            tilles[row + 1][col - 1].repaint();
                        } else {
                            jumped = true;
//                            System.out.println("row +1 col +1");

                            board[row][col] = board[selectedRow][selectedCol];
                            tilles[row][col].setType(board[selectedRow][selectedCol]);
                            board[row + 1][col + 1] = BoardM.Blank;
                            tilles[row + 1][col + 1].setType(BoardM.Blank);
                            tilles[row + 1][col + 1].repaint();
                        }
                    } else {


                        tilles[row][col].setType(board[selectedRow][selectedCol]);
                        board[row][col] = board[selectedRow][selectedCol];

                    }
                    board[selectedRow][selectedCol] = BoardM.Blank;
                    tilles[selectedRow][selectedCol].setType(BoardM.Blank);

                    tilles[selectedRow][selectedCol].repaint();

                    if (botTurn != -1) {
                        for (int i = 0; i < 8; i++) {
                            if (allowedMoves[i][0] != -1) {
                                tilles[allowedMoves[i][0]][allowedMoves[i][1]].allowedMove = false;
                                tilles[allowedMoves[i][0]][allowedMoves[i][1]].repaint();
                            }
                        }
                    }


                }

                if (row == 7 || row == 0) {
                    tilles[row][col].setType(getKing(turn));
                    board[row][col] = getKing(turn);
                    tilles[row][col].repaint();
                }
                tileSelected = false;

                switch (botTurn) {
                    case 1:
                        if (botTurn == 1 && (!jumped || !anotherJump(row, col))) {
                            turn = getOpositeTurn(turn);
                            if (bot != null) {
                                botTurn = -1;
                            }
                        }
                        break;
                    case -1:
                        bot.setBoard(board);
                        if (jumped && bot.checkAnotherJump(row, col)) {
                            checkMove(-10, -10);
                        } else {
                            turn = getOpositeTurn(turn);
                            botTurn = 1;
                            break;

                        }

                }


            }
        }
        repaintBoard();
    }

    private BoardM getOpositeTurn(BoardM turn) {
        if (turn == BoardM.Black) {
            return BoardM.Red;
        } else {
            return BoardM.Black;
        }
    }

    private BoardM getKing(BoardM tile) {
        if (tile == BoardM.Red) {
            return BoardM.RedKing;
        } else {
            return BoardM.BlackKing;
        }
    }

    private BoardM getColorOfTille(BoardM test) {
        if (test == BoardM.Red || test == BoardM.RedKing) {
            return BoardM.Red;
        } else if (test == BoardM.Black || test == BoardM.BlackKing) {
            return BoardM.Black;
        } else {
            return BoardM.Blank;
        }
    }

    public static void setAllowedMoves(int[][] allowedMoves) {
        MoveChecker.allowedMoves = allowedMoves;
    }

    public static void setSelectedRow(int selectedRow) {
        MoveChecker.selectedRow = selectedRow;
    }

    public static void setSelectedCol(int selectedCol) {
        MoveChecker.selectedCol = selectedCol;
    }

    public void setTilles(Tilles[][] tilles) {
        this.tilles = tilles;
    }

    public BoardM getTile(int row, int col) {
        return board[row][col];
    }

    public static BoardM[][] getBoard() {
        return board;
    }

    public void setBot(RandomBot bot) {
        this.bot = bot;
    }

    private void repaintBoard() {
        for (int i = 0; i < tilles.length; i++) {
            for (int j = 0; j < tilles[0].length; j++) {
                tilles[i][j].repaint();

            }

        }
    }

    public void setTileSelected(boolean tileSelected) {
        this.tileSelected = tileSelected;
    }
}
