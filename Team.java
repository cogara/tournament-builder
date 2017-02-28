import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Team {
    String name;
    boolean isEliminated = false;
    // ArrayList<Team> teamList = new ArrayList<Team>();

    public Team(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void eliminate() {
        isEliminated = true;
    }

}
