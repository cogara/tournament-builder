import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Team extends JButton {
    String name = " ";
    public static int numTeams = 0;
    int id;
    boolean isWinner = false;
    boolean isLoser = false;
    boolean isSet = false;
    Color backgroundColor = (Color) new JButton().getBackground();

    public Team() {
        setFocusPainted(false);
        setText("-");
        // setEnabled(false);
        id = ++numTeams;
    }
    public Team(Team t) {
        this();
        this.name = t.name;
        this.isWinner = t.isWinner;
        this.isLoser = t.isLoser;
        this.isSet = t.isSet;
        setText(this.name);
        id = ++numTeams;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        isSet = true;
        // setEnabled(true);
    }
    public void setBgColor(Color c) {
        backgroundColor = c;
    }
    public Color getBgColor() {
        return backgroundColor;
    }
}
