import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Team {
    String name = " ";
    public static int numTeams = 0;
    int id;
    boolean isWinner = false;
    boolean isLoser = false;
    boolean isSet = false;
    Color backgroundColor = (Color) new JButton().getBackground();
    JLabel teamName = new JLabel();

    public Team() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        // setFocusPainted(false);
        // setText("-");
        // setBorder(BorderFactory.createLineBorder(Color.yellow));
        // setEnabled(false);
        id = ++numTeams;
        // setOpaque(true);
        // setForeground(Color.black);
        add(teamName);
        
    }
    public Team(Team t) {
        this();
        this.id = t.id;
        this.name = t.name;
        this.isWinner = t.isWinner;
        this.isLoser = t.isLoser;
        this.isSet = t.isSet;
        teamName.setText(this.name);
        // id = ++numTeams;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        teamName.setText(name);
        isSet = true;
        revalidate();
        repaint();
        // setEnabled(true);
    }
    public void setBgColor(Color c) {
        backgroundColor = c;
    }
    public Color getBgColor() {
        return backgroundColor;
    }
    public void setEnabled(boolean enabled){
        setForeground(Color.RED);
        // setStyle("-fx-opacity: 1.0;");
        // setDisabledTextColor(Color.BLACK);
        super.setEnabled(enabled);
    }
}
