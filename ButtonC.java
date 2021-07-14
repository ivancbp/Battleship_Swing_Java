package tps.tp4;

import javax.swing.*;
import java.awt.*;

public class ButtonC extends JButton{

    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private Color disabledBackgroundColor;

    public boolean Clicked = false;

    public void setClicked(boolean clicked) {
        Clicked = clicked;
    }

    public ButtonC() {
        this(null);
    }

    public ButtonC(String text) {
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

    public void buttonClick(){
        if(Clicked){
            setBackground(Color.white);
            setClicked(false);
        }
        else{
            setBackground(new Color(0,133,93));
            setClicked(true);
        }
    }


}
