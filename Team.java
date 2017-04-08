import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Team {
    int teamNumber = -1;
    String name = " ";
    boolean isSet = false;
    transient Color backgroundColor = (Color) new JButton().getBackground();
    transient JLabel teamName = new JLabel();

    public Team() {

    }
    public Team(String name) {
        this();
        this.name = name;
        this.isSet = true;
    }
    public Team(Team t) {
        this();
        this.name = t.name;
        this.isSet = t.isSet;
    }

    public String getName() {
        return name;
    }
    public String toString() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        isSet = true;
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
    Color backgroundColor = (Color) new JButton().getBackground();
    Color defaultBackground = (Color) new JButton().getBackground();
    
    public TeamPanel() {

    }

    public Boolean isSelected() {
        return selected;
    }

    public void select() {
        selected = (selected) ? false : true;
        if (selected) setBackground(new Color(255,255,200));
        if (!selected) setBackground(backgroundColor);
    }

    public void select(boolean toggle) {
        selected = (toggle) ? true : false;
        if (selected) setBackground(new Color(255,255,200));
        if (!selected) setBackground(backgroundColor);
    }

    public void setAsWinner() {
        isWinner = true;
        isLoser = false;
        backgroundColor = new Color(235,255,235);
        setBackground(backgroundColor);
    }
    public void setAsLoser() {
        isWinner = false;
        isLoser = true;
        backgroundColor = new Color(255,235,235);
        setBackground(backgroundColor);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
