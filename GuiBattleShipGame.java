package tps.tp4;


import java.util.Arrays;

public class GuiBattleShipGame {

    private GuiBoard boardH;
    private GuiBoard boardC;
    public Agent agent;

    public boolean HumanWon;
    public boolean AgentWon;

    private int[] topSecret = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};


    public GuiBattleShipGame(){
        HumanWon = false;
        AgentWon = false;
        boardH = new GuiBoard();
        boardC = new GuiBoard();


    }

    // method GUI --> Game --> Board
    public void placeBoatsHuman(int[] boatLocation){
        placeBoats(boatLocation,boardH);
        informAgent(boatLocation); // needed for extreme agent algorithm
    }

    private void informAgent(int[] boatLocation) {
        topSecret = boatLocation;
    }

    //method Game --> Board
    public void placeBoatsComputer(){
        placeBoats(agent.generateBoatLocation(), boardC);
    }

    //method Game --> Board
    private void placeBoats(int[] boatLocation,GuiBoard board){
        System.out.println();
        for (int i = 0; i<boatLocation.length; i++) {
            System.out.print(boatLocation[i] + "  ");
            int x = (int) Math.floor(boatLocation[i] / 10.0f);
            int y = boatLocation[i] % 10;
            board.placeBoat(x, y);
        }
        System.out.println();
    }

    //method GUI --> Game --> Agent
    public void setDiff(char diff){
        agent = new Agent();
        agent.setCurrent_dif(diff);
        agent.setTopSecret(topSecret);
        placeBoatsComputer();
        boardC.printBoard();
        System.out.println();
        boardH.printBoard();
    }

    //method GUI --> Game
    public boolean[] turn(int shotLocation){
        boolean[] turn_outcome = new boolean[2];

        boolean shot_human = shoot(shotLocation,boardC); //Human Shot
        boolean shot_computer = shoot(agent.generateShotLocation(),boardH); //Computer Shot

        turn_outcome[0] = shot_human;
        turn_outcome[1] = shot_computer;

        if(boardC.getBoard_health() == 0){
            HumanWon = true;
        }else if(boardH.getBoard_health() == 0){
            AgentWon = true;
        }

        return turn_outcome;

    }

    //method Game --> Board
    private boolean shoot(int shotLocation, GuiBoard board){
        int x = (int) Math.floor(shotLocation / 10.0f);
        int y = shotLocation % 10;

        if(board.board[x][y] == 1){
            board.board[x][y] = -1;
            board.board_health--;
            if(board == boardH){ //needed for medium and hard agent algorithm
                agent.setLast_shot_hit(true);
            }
            return true;
        }else{
            return false;
        }

    }

    public int getAgentLastShot(){
       return agent.getLast_shot();
    }

}
