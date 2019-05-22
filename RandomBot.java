import java.util.ArrayList;

public class RandomBot {


    private ArrayList<String> jumps = new ArrayList<>();
    private ArrayList<String> regularMoves = new ArrayList<>();
    private MoveChecker moveChecker;
    private BoardM[][] board;

    public RandomBot(MoveChecker moveChecker) {
        this.moveChecker = moveChecker;
    }


    private int numOfPieces() {
        int num = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++) {
                if ((board[i][j] != BoardM.Blank) && getColorOfTille(board[i][j]) == BoardM.Red) {
                    num++;
                }
            }

        return num;
    }


    public void robotMove() {
        int count = 0;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (getColorOfTille(board[row][col]) == BoardM.Red) {
                    movesForPiece(row, col);
                    count++;
                    if (count > numOfPieces()) {
                        break;
                    }
                }

            }
        }
        String move = randomMove();

        if (jumps.size() != 0) {

            String[] moveS = move.split(",");
            MoveChecker.setSelectedCol(Integer.parseInt(moveS[1]));
            MoveChecker.setSelectedRow(Integer.parseInt(moveS[0]));
//            System.out.println(move);
            jumps.clear();
            moveChecker.checkMove(Integer.parseInt(moveS[2]), Integer.parseInt(moveS[3]));
        } else {
//            System.out.println(move);
            String[] moveS = move.split(",");
            regularMoves.clear();
            int selectedRow = Integer.parseInt(moveS[0]);
            int selectedCol = Integer.parseInt(moveS[1]);
            int row = Integer.parseInt(moveS[2]);
            int col = Integer.parseInt(moveS[3]);


            int[][] tempAllowed = new int[8][2];

            tempAllowed[7][0] = -1;
            tempAllowed[7][1] = -1;
            tempAllowed[6][0] = -1;
            tempAllowed[6][1] = -1;
            tempAllowed[5][0] = -1;
            tempAllowed[5][1] = -1;
            tempAllowed[4][0] = -1;
            tempAllowed[4][1] = -1;

            MoveChecker.setAllowedMoves(tempAllowed);

            MoveChecker.setSelectedCol(selectedCol);
            MoveChecker.setSelectedRow(selectedRow);

            moveChecker.checkMove(row, col);


        }


    }


    private String randomMove() {
        if (jumps.size() != 0) {
            int randomNum = (int) (Math.random() * jumps.size());

            return jumps.get(randomNum);
        } else {
            int randomNum = (int) (Math.random() * regularMoves.size());

            return regularMoves.get(randomNum);
        }
    }

    public void movesForPiece(int row, int col) {
        boolean jumping = false;
        boolean check = false;
        if ((row + 2 < 8 && col + 2 < 8)) {
            if ((board[row + 1][col + 1] != BoardM.Blank) && getColorOfTille(board[row + 1][col + 1]) != getColorOfTille(board[row][col]) && board[row + 2][col + 2] == BoardM.Blank) {

                jumps.add(row + "," + col + "," + (row + 2) + "," + (col + 2));
                check = true;
                jumping = true;
            }
        }
        if ((row + 2 < 8 && col - 2 > -1)) {
            if ((board[row + 1][col - 1] != BoardM.Blank) && getColorOfTille(board[row + 1][col - 1]) != getColorOfTille(board[row][col]) && board[row + 2][col - 2] == BoardM.Blank) {

//                System.out.println("Here");
                jumps.add(row + "," + col + "," + (row + 2) + "," + (col - 2));
                jumping = true;
                check = true;
            }
        }
        if (board[row][col] != BoardM.Red) {
            if ((row - 2 > -1 && col + 2 < 8)) {
                if ((board[row - 1][col + 1] != BoardM.Blank) && getColorOfTille(board[row - 1][col + 1]) != getColorOfTille(board[row][col]) && board[row - 2][col + 2] == BoardM.Blank) {

                    jumps.add(row + "," + col + "," + (row - 2) + "," + (col + 2));
//                System.out.println("Here");
                    jumping = true;
                    check = true;
                }
            }
        }
        if (board[row][col] != BoardM.Red) {
            if ((row - 2 > -1 && col - 2 > -1)) {
                if ((board[row - 1][col - 1] != BoardM.Blank) && getColorOfTille(board[row - 1][col - 1]) != getColorOfTille(board[row][col]) && board[row - 2][col - 2] == BoardM.Blank) {
//                System.out.println("Here");
                    jumps.add(row + "," + col + "," + (row - 2) + "," + (col - 2));
                    jumping = true;
                    check = true;
                }
            }
        }
        if (row + 1 < 8 && col + 1 < 8) {
            if (!jumping && board[row + 1][col + 1] == BoardM.Blank) {


                regularMoves.add(row + "," + col + "," + (row + 1) + "," + (col + 1));
                check = true;
            }
        }
        if (row + 1 < 8 && col - 1 > -1) {
            if (!jumping && board[row + 1][col - 1] == BoardM.Blank) {
                regularMoves.add(row + "," + col + "," + (row + 1) + "," + (col - 1));
                check = true;
            }
        }

        if (board[row][col] != BoardM.Red) {
            if (row - 1 > -1 && col + 1 < 8) {
                if (!jumping && board[row - 1][col + 1] == BoardM.Blank) {

                    regularMoves.add(row + "," + col + "," + (row - 1) + "," + (col + 1));
                    check = true;
                }
            }
        }
        if (board[row][col] != BoardM.Red) {
            if (row - 1 > -1 && col - 1 > -1) {
                if (!jumping && board[row - 1][col - 1] == BoardM.Blank) {
                    regularMoves.add(row + "," + col + "," + (row - 1) + "," + (col - 1));
                    check = true;
                }
            }
        }
    }

    public boolean checkAnotherJump(int row, int col) {
        if ((row + 2 < 8 && col + 2 < 8)) {
            if ((board[row + 1][col + 1] != BoardM.Blank) && getColorOfTille(board[row + 1][col + 1]) != getColorOfTille(board[row][col]) && board[row + 2][col + 2] == BoardM.Blank) {
                jumps.add(row + "," + col + "," + (row + 2) + "," + (col + 2));
                return true;

            }
        }
        if ((row + 2 < 8 && col - 2 > -1)) {
            if ((board[row + 1][col - 1] != BoardM.Blank) && getColorOfTille(board[row + 1][col - 1]) != getColorOfTille(board[row][col]) && board[row + 2][col - 2] == BoardM.Blank) {
                jumps.add(row + "," + col + "," + (row + 2) + "," + (col - 2));
                return true;
//                System.out.println("Here");
            }
        }
        if (board[row][col] != BoardM.Red) {
            if ((row - 2 > -1 && col + 2 < 8)) {
                if ((board[row - 1][col + 1] != BoardM.Blank) && getColorOfTille(board[row - 1][col + 1]) != getColorOfTille(board[row][col]) && board[row - 2][col + 2] == BoardM.Blank) {
                    jumps.add(row + "," + col + "," + (row - 2) + "," + (col + 2));
                    return true;
//                System.out.println("Here");
                }
            }
        }
        if (board[row][col] != BoardM.Red) {
            if ((row - 2 > -1 && col - 2 > -1)) {
                if ((board[row - 1][col - 1] != BoardM.Blank) && getColorOfTille(board[row - 1][col - 1]) != getColorOfTille(board[row][col]) && board[row - 2][col - 2] == BoardM.Blank) {
                    jumps.add(row + "," + col + "," + (row - 2) + "," + (col - 2));
//                System.out.println("Here");
                    return true;
                }
            }
        }
        return false;
    }

    public void setBoard(BoardM[][] board) {
        this.board = board;
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

}
