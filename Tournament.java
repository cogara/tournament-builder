import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Tournament {
    String name;
    int numTeams;
    ArrayList<Team> teamList = new ArrayList<Team>();

    public Tournament(String n, int t) {
        name = n;
        numTeams = t;
        // tournamentList = null;
    }
    public void go() {
        System.out.println(name + " " + numTeams);
    }

    public String getName() {
        return name;
    }

    public int getNumTeams() {
        return numTeams;
    }

    public void addTeam(String n) {
        Team team = new Team(n);
        teamList.add(team);
    }
    
    public void listTeams() {
        teamList.forEach(team->{
            System.out.println(team.getName());
        });
    }
}