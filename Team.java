import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Team {
    String name;
    boolean isEliminated = false;
    JButton assignTeam;
    // ArrayList<Team> teamList = new ArrayList<Team>();

    // public Team() {
    //     name = null;
    //     assignTeam = new JButton(name);
    // }
    public Team(String n) {
        name = n;
        assignTeam = new JButton(name);
    }

    public String getName() {
        return name;
    }

    public void eliminate() {
        isEliminated = true;
    }

}
