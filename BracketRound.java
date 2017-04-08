import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class BracketRound {
    int roundNumber = -1;
    ArrayList<Game> games = new ArrayList<Game>();
    public BracketRound() {
        // roundNumber = -1;
    }
    public BracketRound(int numGames) {
        this();
        for (int i = 0; i < numGames; i++) {
            games.add(new Game());
        }
    }
    // public BracketRound(int numGames, int roundNum) {
    //     this();
    //     roundNumber = roundNum;
    // }

    public BracketRound(BracketRound r) {
        this();
        games = new ArrayList<Game>();
        for (int i = 0; i < r.games.size(); i++) {
            Game newGame = new Game((Game) r.games.get(i));
            games.add(newGame);
        }
    }

    // public BracketRound(BracketRound r, int roundNum) {
    //     this();
    //     roundNumber = roundNum;
    // }
    public void addGame(Game g) {
        games.add(g);
    }
    
    
}
