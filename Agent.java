package tps.tp4;

import java.util.Arrays;
import java.util.Random;

public class Agent {

    private Random random;

    private char[] dificulty;
    private char current_dif;

    private int[] extreme_cheater = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

    private int[] shot_history;
    private int last_shot;
    private boolean last_shot_hit;
    private int[] probable_locations;



    public Agent(){
        random = new Random();
        dificulty = new char[]{'E', 'M','H','X'};
        current_dif = dificulty[0];
        last_shot_hit = false;
        shot_history = new int[100];
        probable_locations = new int[300];
        for (int i = 0; i<shot_history.length;i++){
            shot_history[i] = -1;

        }
        for (int i = 0; i<probable_locations.length;i++){
            probable_locations[i] = -1;

        }
    }

    public int getLast_shot() {
        return last_shot;
    }

    public int[] generateBoatLocation (){
        int [] boatLocation = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        int lower = 0;
        int upper = 0;


        if (current_dif == 'E'){
            int choice = random.nextInt(3);

            if(choice == 0){
                upper = 30;
            }else if(choice == 1){
                lower = 30;
                upper = 60;
            }else{
                lower = 60;
                upper = 100;
            }
        } else if(current_dif == 'M'){
            int choice = random.nextInt(2);

            if(choice == 0) {
                upper = 50;
            }else{
                lower = 50;
                upper = 100;
            }
        }else if(current_dif == 'H' || current_dif == 'X' ){
            upper = 100;
        }

        for (int i = 0; i<boatLocation.length;i++){
            boatLocation[i] = (int) (Math.random() * (upper - lower)) + lower;
            for(int j = i-1 ; j>=0; j--){
                if(i==0){i++;}
                if(boatLocation[i] == boatLocation[j]){
                    i--;
                }
            }

        }


        return boatLocation;
    }

    public int generateShotLocation(){

        if (current_dif == 'E'){
            int shot = (int)(Math.random()*100);

            for (int i = 0; i<shot_history.length;i++){
                if(shot_history[i] == -1){
                    shot_history[i] = shot;
                    last_shot = shot;
                    System.out.println(shot);
                    return shot;
                }
                if(shot==shot_history[i]){
                    System.out.println("tried "+shot);
                    shot = (int)(Math.random()*100);
                    i=0;

                }
            }
        }else if(current_dif == 'M'||current_dif == 'H' ){

            System.out.print("Probable: ");
            System.out.println(Arrays.toString((probable_locations)));
            System.out.print("ShotHistory: ");
            System.out.println(Arrays.toString((shot_history)));

            int shot;

            if (last_shot_hit){
                setProbable_locations(last_shot);
            }

            for(int i = 0; i< probable_locations.length;i++){
                if(probable_locations[i] == -1){
                    break;
                }
                if(probable_locations[i] == -2){
                    continue;
                }else{
                    shot = probable_locations[i];
                    probable_locations[i] = -2;
                    for (int j= 0; j < shot_history.length; j++) {
                        if (shot_history[j] == -1) {
                            shot_history[j] = shot;
                            last_shot = shot;
                            last_shot_hit= false;
                            System.out.println(shot);
                            return shot;
                        }
                        if (shot == shot_history[j]) {
                            shot = (int) (Math.random() * 100);

                            for (int k = 0; k < shot_history.length; k++) {
                                if (shot_history[k] == -1) {
                                    shot_history[k] = shot;
                                    last_shot = shot;
                                    System.out.println(shot);
                                    return shot;
                                }
                                if (shot == shot_history[k]) {
                                    System.out.println("triedbelow " + shot);
                                    shot = (int) (Math.random() * 100);
                                    k = 0;

                                }

                            }

                        }

                    }


                }
            }

            shot = (int) (Math.random() * 100);

            for (int i = 0; i < shot_history.length; i++) {
                if (shot_history[i] == -1) {
                    shot_history[i] = shot;
                    last_shot = shot;
                    System.out.println(shot);
                    return shot;
                }
                if (shot == shot_history[i]) {
                    System.out.println("triedbelow " + shot);
                    shot = (int) (Math.random() * 100);
                    i = 0;

                }

            }



        }else if(current_dif == 'X') {
            int shot;

            int choice = random.nextInt(2);

            if (choice == 0) {
                for (int i = 0; i < extreme_cheater.length; i++) {

                    if (extreme_cheater[i] != -1) {

                        for (int j = 0; j < shot_history.length; j++) {
                            if (shot_history[j] == -1) {
                                shot_history[j] = extreme_cheater[i];
                                break;
                            }
                            if (extreme_cheater[i] == shot_history[j]) {
                                i++;
                            }
                        }
                        if (extreme_cheater[i] != -1) {
                            System.out.println("print " + extreme_cheater[i] + "index " + i);
                            shot = extreme_cheater[i];
                            last_shot = shot;
                            extreme_cheater[i] = -1;
                            System.out.println("cheater "+ shot);
                            return shot;
                        }

                    }
                }
            } else {
                shot = (int) (Math.random() * 100);

                for (int i = 0; i < shot_history.length; i++) {
                    if (shot_history[i] == -1) {
                        shot_history[i] = shot;
                        last_shot = shot;
                        System.out.println(shot);
                        return shot;
                    }
                    if (shot == shot_history[i]) {
                        System.out.println("tried " + shot);
                        shot = (int) (Math.random() * 100);
                        i = 0;

                    }

                }
            }
        }

        return 0;
    }

    public void setLast_shot_hit(boolean last_shot_hit) {
        this.last_shot_hit = last_shot_hit;
    }

    public char getCurrent_dif() {
        return current_dif;
    }

    public void setProbable_locations(int last_shot) {
        int[] newprobable_locations = {-1,-1,-1,-1,-1,-1,-1,-1};
        int x = (int) Math.floor(last_shot / 10.0f);
        int y = last_shot % 10;

        if(0<x && x<9 && 0<y && y<9 ){
            newprobable_locations[0]= last_shot-1;
            newprobable_locations[1]= last_shot+1;
            newprobable_locations[2]= last_shot-10;
            newprobable_locations[3]= last_shot+10;
            newprobable_locations[4]= last_shot+11;
            newprobable_locations[5]= last_shot+9;
            newprobable_locations[6]= last_shot-11;
            newprobable_locations[7]= last_shot-9;
        }

        for(int i=0; i< probable_locations.length;i++){
           if(probable_locations[i]==-1){
               for(int j=0; j< newprobable_locations.length;j++){

                   probable_locations[i+j]= newprobable_locations[j];
               }
               System.out.println();
               break;
           }
        }

    }

    public void setCurrent_dif(char current_dif) {
        this.current_dif = current_dif;
    }

    public void setTopSecret(int[] extreme_cheater) {
        this.extreme_cheater = extreme_cheater;
    }
}
