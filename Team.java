import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Team {
    String name = " ";
    boolean isWinner = false;
    boolean isLoser = false;
    boolean isSet = false;
    Color backgroundColor = (Color) new JButton().getBackground();
    JLabel teamName = new JLabel();

    public Team() {
        // setCursor(new Cursor(Cursor.HAND_CURSOR));
        // add(teamName);
        
    }
    public Team(Team t) {
        this();
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
        // teamName.setText(name);
        isSet = true;
        // revalidate();
        // repaint();
        // setEnabled(true);
    }
    public void setBgColor(Color c) {
        backgroundColor = c;
    }
    public Color getBgColor() {
        return backgroundColor;
    }
}

class TeamPanel extends JPanel {
    boolean selected = false;
    boolean isWinner = false;
    boolean isLoser = false;
    boolean isSet = false;

    public TeamPanel() {

    }

    public Boolean isSelected() {
        return selected;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
