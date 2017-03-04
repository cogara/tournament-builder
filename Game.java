import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Game {
    public static int totalGames = 0;
    int gameNumber;
    ArrayList<Team> teams = new ArrayList<Team>();
    public Game() {
        totalGames++;
        gameNumber = totalGames;
        teams.add(new Team("Team Foo"));
        teams.add(new Team("Team Bar"));
    }

    public void addTeam(Team a) {
        teams.add(a);
    }

    // public void declareWinner(Team a) {
    //     for (Team team : teams) {
    //         team.isEliminated = true;
    //     }
    //     a.isEliminated = false;
    // }
    
    
}
