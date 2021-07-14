package tps.tp4;


import java.util.Arrays;

public class GuiBoard {

    public int[][] board ;

    public int board_health;

    public GuiBoard(){
        board_health = 15;
        board = new int[10][10];
    }

    public void placeBoat(int x, int y){
        board[x][y] = 1;
    }

    public void printBoard(){
        System.out.println(Arrays.deepToString(board).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }


    public int getBoard_health() {
        return board_health;
    }
}
