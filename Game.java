import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Game {
    int gameNumber = -1;
    ArrayList<Team> teams = new ArrayList<Team>();
    Team gameWinner = new Team();
    Team gameLoser = new Team();
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
        for (Team team : teams) {
            if (team == t) {
                gameWinner = team;
            } else {
                gameLoser = team;
            }
        }
        // System.out.println(gameLoser.getName());
        // System.out.println(gameWinner.getName());
    }
    public void clearWinner() {
        gameWinner = new Team();
        gameLoser = new Team();
    }
    
}
