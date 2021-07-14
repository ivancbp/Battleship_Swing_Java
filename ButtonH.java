package tps.tp4;

import javax.swing.*;
import java.awt.*;

class ButtonH extends JButton {

    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private Color disabledBackgroundColor;

    public boolean Clicked = false;
    public boolean boatPlacementFinished = false;

    public void setClicked(boolean clicked) {
        Clicked = clicked;
    }

    public ButtonH() {
        this(null);
    }

    public ButtonH(String text) {
        super(text);
        super.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g.setColor(hoverBackgroundColor);
        }else if (!getModel().isEnabled()) {
                g.setColor(disabledBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    public Color getDisabledBackgroundColor() {
        return disabledBackgroundColor;
    }

    public void setDisabledBackgroundColor(Color disabledBackgroundColor) {
        this.disabledBackgroundColor = disabledBackgroundColor;
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }


    public void setBoatAction(){

        if(boatPlacementFinished){
            return;
        }

        if(Clicked){
            setBackground(Color.white);
            setClicked(false);
        }
        else{
            setBackground(new Color(194,27,4));
            setClicked(true);
        }
    }

}
