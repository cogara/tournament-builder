import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Team extends JButton {
    String name = " ";
    public static int numTeams = 0;
    int id;
    boolean isSet = false;
    Color backgroundColor = (Color) new JButton().getBackground();

    public Team() {
        // setEnabled(false);
        id = ++numTeams;
    }
    public Team(Team t) {
        this.name = t.name;
        this.isSet = true;
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

    // public void eliminate() {
    //     // isEliminated = true;
    // }

}
