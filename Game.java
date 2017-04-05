import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Game {
    ArrayList<Team> teams = new ArrayList<Team>();
    Team gameWinner = null;
    public Game() {
        teams.add(new Team());
        teams.add(new Team());
    }

    public Game(Game g) {
        teams = new ArrayList<Team>();
        for (int i = 0; i < g.teams.size(); i++) {
            Team newTeam = new Team((Team) g.teams.get(i));
            teams.add(newTeam);
        }
    }

    public void addTeam(Team a) {
        teams.add(a);
    }

    public void setTeam(int index, Team a) {
        teams.set(index, a);
    }

    public void setWinner(Team t) {
        // for (Team team : teams) {
        //     team.isWinner = false;
        //     team.isLoser = true;
        // }
        // int i = teams.indexOf(t);
        // teams.get(i).isWinner = true;
        // teams.get(i).isLoser = false;
        gameWinner = (Team) t;
    }
    
}
