import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class BracketRound {
    int numGames;
    ArrayList<Game> games = new ArrayList<Game>();
    public BracketRound(int numOfGames) {
        numGames = numOfGames;
    }
    public void addGame(Game g) {
        games.add(g);
    }
    
}
