import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class BracketRound {
    ArrayList<Game> games = new ArrayList<Game>();
    public int tempId;
    static int numRounds = 0;
    public BracketRound() {
        tempId = ++numRounds;
        // System.out.println("Total # of Rds: " + numRounds);
    }
    public BracketRound(int numGames) {
        this();
        for (int i = 0; i < numGames; i++) {
            games.add(new Game());
        }
    }
    public BracketRound(BracketRound r) {
        this();
        games = new ArrayList<Game>();
        for (int i = 0; i < r.games.size(); i++) {
            Game newGame = new Game((Game) r.games.get(i));
            games.add(newGame);
        }
    }
    public void addGame(Game g) {
        games.add(g);
    }
    
    
}
