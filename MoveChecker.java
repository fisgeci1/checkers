public class MoveChecker {

    private BoardM[][] board = {{BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red},
            {BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank},
            {BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red, BoardM.Blank, BoardM.Red},
            {BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank},
            {BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank, BoardM.Blank},
            {BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank},
            {BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black},
            {BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank, BoardM.Black, BoardM.Blank},};
    private Tilles[][] tilles;
    private boolean tileSelected;
    private BoardM turn = BoardM.Black;
    private static int[][] allowedMoves = new int[8][2];
    private static int selectedRow, selectedCol;


    public void checkMove(int row, int col) {
//        System.out.println("In here");

        if (tileSelected) {
            if (board[row][col] == BoardM.Blank) {

                boolean valid = false;
                boolean jumped = false;
                for (int i = 0; i < allowedMoves.length; i++) {
                    if (row == allowedMoves[i][0] && col == allowedMoves[i][1]) {
                        valid = true;
                    }
                }
                if (valid) {

                    //red pieces
                    if (selectedRow - row < 0) {

                        if (allowedMoves[7][0] != -1 || allowedMoves[6][0] != -1) {
                            if (selectedCol - col < 0) {
                                jumped = true;
                                board[row][col] = board[selectedRow][selectedCol];
                                tilles[row][col].setType(board[selectedRow][selectedCol]);
                                board[row - 1][col - 1] = BoardM.Blank;
                                tilles[row - 1][col - 1].setType(BoardM.Blank);
                                tilles[row - 1][col - 1].repaint();
                            } else {
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
                        for (int i = 0; i < 8; i++) {
                            if (allowedMoves[i][0] != -1) {
                                tilles[allowedMoves[i][0]][allowedMoves[i][1]].allowedMove = false;
                                tilles[allowedMoves[i][0]][allowedMoves[i][1]].repaint();
                            }
                        }


                    }
                    //gray Pieces
                    else {
                        if (allowedMoves[5][0] != -1 || allowedMoves[4][0] != -1) {
                            if (selectedCol - col < 0) {
                                jumped = true;
                                board[row][col] = board[selectedRow][selectedCol];
                                tilles[row][col].setType(board[selectedRow][selectedCol]);
                                board[row + 1][col - 1] = BoardM.Blank;
                                tilles[row + 1][col - 1].setType(BoardM.Blank);
                                tilles[row + 1][col - 1].repaint();
                            } else {
                                jumped = true;
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
                        for (int i = 0; i < 8; i++) {
                            if (allowedMoves[i][0] != -1) {
                                tilles[allowedMoves[i][0]][allowedMoves[i][1]].allowedMove = false;
                                tilles[allowedMoves[i][0]][allowedMoves[i][1]].repaint();
                            }
                        }


                    }

                    if (row == 7 || row == 0) {
                        tilles[row][col].setType(getKing(turn));
                        board[row][col] = getKing(turn);
                        tilles[row][col].repaint();
                    }
                    tileSelected = false;
                    if(board[row][col] == BoardM.RedKing || board[row][col] == BoardM.BlackKing){
                        if (!anotherJump(row, col)) {
                            turn = getOpositeTurn(turn);
                        }
                    }else{
                        if (!jumped || !anotherJump(row, col)) {
                            turn = getOpositeTurn(turn);
                        }
                    }

                }


            }
        } else {
            selectedRow = row;
            selectedCol = col;
            allowedMoves = new int[8][2];

            if (turn == getColorOfTille(board[row][col])) {
                tileSelected = true;
                if (board[row][col] == BoardM.Red) {
//                    System.out.println("Here");
                    movesRed(row, col);
                } else if (board[row][col] == BoardM.Black) {
                    moveBlackPiece(row, col);
                } else {
                    moveKings(row, col);
                }

            }
        }


    }


    private void movesRed(int row, int col) {


        boolean jumping = false;
        boolean check = true;
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
        if (check) {
            tileSelected = false;
        }

    }

    private void moveBlackPiece(int row, int col) {
        boolean jumping = false;
        boolean check = true;
        if ((row - 2 > -2 && col + 2 < 8)) {
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
        if (check) {
            tileSelected = false;
        }

    }

    private void moveKings(int row, int col) {
        boolean jumping = false;
        boolean check = true;
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
        if (check) {
            tileSelected = false;
        }

    }

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

    public void setTileSelected(boolean tileSelected) {
        this.tileSelected = tileSelected;
    }

    public void setTilles(Tilles[][] tilles) {
        this.tilles = tilles;
    }


    public BoardM getTile(int row, int col) {
        return board[row][col];
    }
}
