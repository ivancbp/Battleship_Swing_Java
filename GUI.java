package tps.tp4;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {

    private JPanel contentPane;
    private GuiBattleShipGame battleShipGame;
    private ButtonH[] Humanbuttons = new ButtonH[100];
    private ButtonC[] Computerbuttons = new ButtonC[100];
    private boolean btnEnabler = true;
    private Color hitColor;
    private Color waterColor;

    private void setBoat(){

        int[] boatLocation = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        int counter = 0;
        int passcounter = 0;

        for(int i=0; i< Humanbuttons.length; i++){
            if(Humanbuttons[i].Clicked){
                passcounter++;
            }
        }

        if(passcounter!= 15){

            return;
        }

        for(int i=0; i< Humanbuttons.length; i++){
            if(Humanbuttons[i].Clicked){
                Humanbuttons[i].setDisabledBackgroundColor(new Color(0,133,93));
                Humanbuttons[i].setEnabled(false);
                boatLocation[counter] = i;
                counter++;
            }

            Humanbuttons[i].setHoverBackgroundColor(Color.white);
            Humanbuttons[i].boatPlacementFinished = true;


        }

        battleShipGame.placeBoatsHuman(boatLocation);
        btnEnabler = false;
        infoBox("Please select the difficulty!","Well done Captain!");

    }

    public void setDiff(char diff){
        battleShipGame.setDiff(diff);
    }

    public void handleTurn(){
        int shotLocation = 0;
        int passcounter = 0;
        boolean[] turn_outcome;

        for(int i=0; i< Computerbuttons.length; i++){
            if(Computerbuttons[i].Clicked){
                passcounter++;
                if (passcounter>1){
                    warningBox("Please choose only 1 square to shoot, Captain!","Too Many Shots");
                    return;
                }

                shotLocation = i;
            }

        }

        if(passcounter==0) {
            warningBox("Please choose 1 square to shoot, Captain!", "No Shots Detected");
            return;
        }

        turn_outcome = battleShipGame.turn(shotLocation);
        int agentShotLocation = battleShipGame.getAgentLastShot();

        if(turn_outcome[0]){
            Computerbuttons[shotLocation].setClicked(false);
            Computerbuttons[shotLocation].setDisabledBackgroundColor(hitColor);
            Computerbuttons[shotLocation].setEnabled(false);


        }else{
            Computerbuttons[shotLocation].setClicked(false);
            Computerbuttons[shotLocation].setDisabledBackgroundColor(waterColor);
            Computerbuttons[shotLocation].setEnabled(false);
        }
        if(turn_outcome[1]){
            Humanbuttons[agentShotLocation].setEnabled(true);
            Humanbuttons[agentShotLocation].setClicked(false);
            Humanbuttons[agentShotLocation].setDisabledBackgroundColor(hitColor);
            Humanbuttons[agentShotLocation].setEnabled(false);
        }else{
            Humanbuttons[agentShotLocation].setClicked(false);
            Humanbuttons[agentShotLocation].setDisabledBackgroundColor(waterColor);
            Humanbuttons[agentShotLocation].setEnabled(false);
        }

        if(battleShipGame.HumanWon){
            infoBox("Congratulations Captain, you won!","Victory!");
            System.exit(0);
        }else if(battleShipGame.AgentWon){
            infoBox("We are going down Captain!","Defeat!");
            System.exit(0);
        }

    }


    public static void warningBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "Warning: " + titleBar, JOptionPane.WARNING_MESSAGE);
    }

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "Info: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public GUI(){
        battleShipGame = new GuiBattleShipGame();
        hitColor = new Color(255,25,27);
        waterColor = new Color(50, 82, 255);
        String soundName = "Sound.wav";
        AudioInputStream audioInputStream = null;
       try {
           audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
       } catch (UnsupportedAudioFileException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       Clip clip = null;
       try {
           clip = AudioSystem.getClip();
       } catch (LineUnavailableException e) {
           e.printStackTrace();
       }
       try {
           clip.open(audioInputStream);
       } catch (LineUnavailableException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       clip.start();

        setTitle("BattleShip");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1400, 800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);



       JLabel lblNewLabel = new JLabel("BattleShip!");
       lblNewLabel.setBounds(0, 0, 1400, 50);
       lblNewLabel.setBackground(new Color(25, 25, 25));
       lblNewLabel.setForeground(new Color(50, 82, 123));
       lblNewLabel.setFont(new Font("Ink Free", Font.BOLD, 50));
       lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
       lblNewLabel.setText("BattleShip");
       lblNewLabel.setOpaque(true);
       contentPane.add(lblNewLabel);



       JPanel boardHpanel = new JPanel();
       boardHpanel.setBounds(150, 105, 300, 300);
       contentPane.add(boardHpanel);
       boardHpanel.setLayout(new GridLayout(10,10));

       JPanel boardCpanel = new JPanel();
       boardCpanel.setBounds(950, 105, 300, 300);
       contentPane.add(boardCpanel);
       boardCpanel.setLayout(new GridLayout(10,10));

       for (int i = 0; i < 100; i++) {

           ButtonH newButtonH = new ButtonH();
           newButtonH.setHoverBackgroundColor(new Color(126,25,27));
           newButtonH.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   newButtonH.setBoatAction();
               }
           });
           Humanbuttons[i] = newButtonH;
           boardHpanel.add(newButtonH);

           ButtonC newButtonC = new ButtonC();
           newButtonC.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                 newButtonC.buttonClick();
               }
           });
           Computerbuttons[i] = newButtonC;
           boardCpanel.add(newButtonC);
       }





       // Shoot Button
        JButton btnShoot = new JButton("Shoot Here!");
        btnShoot.setEnabled(false);
        btnShoot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                handleTurn();
            }
        });

        JPanel btnShootpanel = new JPanel();
        btnShootpanel.setBounds(1050, 420, 120, 20);
        contentPane.add(btnShootpanel);
        btnShootpanel.setLayout(new GridLayout(1,0));
        btnShootpanel.add(btnShoot);



        //Difficulty Buttons
        JRadioButton btnEasyDiff = new JRadioButton("Easy");
        JRadioButton btnMedDiff = new JRadioButton("Medium");
        JRadioButton btnHardDiff = new JRadioButton("Hard");
        JRadioButton btnXtremeDiff = new JRadioButton("Extreme");

        ButtonGroup diffbtngroup = new ButtonGroup();
        diffbtngroup.add(btnEasyDiff);
        diffbtngroup.add(btnMedDiff);
        diffbtngroup.add(btnHardDiff);
        diffbtngroup.add(btnXtremeDiff);

        JPanel btndiffpanel = new JPanel();
        btndiffpanel.setBounds(650, 200, 110, 100);
        contentPane.add(btndiffpanel);
        btndiffpanel.setLayout(new GridLayout(0,1));
        btndiffpanel.add(btnEasyDiff);
        btndiffpanel.add(btnMedDiff);
        btndiffpanel.add(btnHardDiff);
        btndiffpanel.add(btnXtremeDiff);
        btnEasyDiff.setSelected(true);

        JButton btnSetDiff = new JButton("Set Difficulty");
        btnSetDiff.setEnabled(false);
        btnSetDiff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(btnEasyDiff.isSelected()){
                    setDiff('E');
                }else if(btnMedDiff.isSelected()){
                    setDiff('M');
                }else if(btnHardDiff.isSelected()){
                    setDiff('H');
                }else if(btnXtremeDiff.isSelected()){
                    setDiff('X');
                }

                btnSetDiff.setEnabled(false);
                btnEasyDiff.setEnabled(false);
                btnMedDiff.setEnabled(false);
                btnHardDiff.setEnabled(false);
                btnXtremeDiff.setEnabled(false);
                btnShoot.setEnabled(true);

                infoBox("Let's Begin!","Good Luck Captain!");

            }
        });

        JPanel btnsetDiffpanel = new JPanel();
        btnsetDiffpanel.setBounds(650, 305, 110, 20);
        contentPane.add(btnsetDiffpanel);
        btnsetDiffpanel.setLayout(new GridLayout(1,0));
        btnsetDiffpanel.add(btnSetDiff);


        //Set Boat Button
        JButton btnSetBoat = new JButton("Set Boat!");
        btnSetBoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setBoat();
                btnSetBoat.setEnabled(btnEnabler);
                if(btnEnabler){
                    warningBox("Please take up 15 spaces on the board!","Invalid Configuration");
                }else{
                    btnSetDiff.setEnabled(true);
                }
            }
        });

        JPanel btnpanel = new JPanel();
        btnpanel.setBounds(250, 420, 100, 20);
        contentPane.add(btnpanel);
        btnpanel.setLayout(new GridLayout(1,0));
        btnpanel.add(btnSetBoat);





        // Background img
        PicPanel panelBackground = new PicPanel("bs.jpg");
        panelBackground.setBounds(0, 50, 1400, 800);
        contentPane.add(panelBackground);




   }

    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setVisible(true);
    }

}
