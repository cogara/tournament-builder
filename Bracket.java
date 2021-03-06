import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   
import java.io.*;  
import javax.imageio.*;
import java.awt.image.BufferedImage;


public class Bracket {
    String name;
    // BufferedImage bgimage;
    ArrayList<BracketRound> rounds = new ArrayList<BracketRound>();
    ArrayList<Team> teams = new ArrayList<Team>();
    int numGames = 1;
    Team champion = new Team();
    // String userAgent = "blah";
    // public static boolean saveHistory = true;

    public Bracket() {
        // try {
        //     bgimage = ImageIO.read(new File("./bgimage.png"));
        // } catch (Exception e) {}
    }

    public Bracket(String name) {
        this();
        champion.teamName.setText("-----");
        this.name = name;    
    }
    
    public Bracket(String name, int numRds) {
        this(name);
        for (int i = 0; i < numRds; i++) {
            rounds.add(0, new BracketRound(numGames));
            numGames *= 2;
        }
    }

    public Bracket(int numRds, String name) {
        this(name, numRds);
    }

    public Bracket(Bracket b) {
        this();
        name = b.name;
        for (Team team : b.teams) {
            teams.add(team);
        }
        rounds = new ArrayList<BracketRound>();
        for (int i = 0; i < b.rounds.size(); i++) {
            BracketRound newRound = new BracketRound((BracketRound) b.rounds.get(i));
            rounds.add(newRound);   
        }
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void addRound() {
        int prevNumGames = rounds.get(0).games.size();
        // for (BracketRound round : rounds) {
        //     round.roundNumber++;
        // }
        rounds.add(0, new BracketRound(prevNumGames * 2));
    }

    public void removeRound() {
        // System.out.println(rounds.get(0).tempId);
        rounds.remove(0);
    }

    public void addRounds(int n) {
        for (int i = 0; i < n; i++) {
            int prevNumGames = rounds.get(0).games.size();
            rounds.add(0, new BracketRound(prevNumGames*2));
        }
    }

    public String getName() {
        return name;
    }
   
    // public void paintComponent(Graphics g) {
    //     super.paintComponent(g);
    //     int iw = bgimage.getWidth(this);
    //     int ih = bgimage.getHeight(this);
    //     if (iw > 0 && ih > 0) {
    //         for (int x = 0; x < getWidth(); x += iw) {
    //             for (int y = 0; y < getHeight(); y += ih) {
    //                 g.drawImage(bgimage, x, y, iw, ih, this);
    //             }
    //         }
    //     } 
    // }



}

class BracketPanel extends JPanel {
    BufferedImage bgimage;
    
    public BracketPanel() {
        try {
            bgimage = ImageIO.read(new File("./bgimage.png"));
        } catch (Exception e) {}
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int iw = bgimage.getWidth(this);
        int ih = bgimage.getHeight(this);
        if (iw > 0 && ih > 0) {
            for (int x = 0; x < getWidth(); x += iw) {
                for (int y = 0; y < getHeight(); y += ih) {
                    g.drawImage(bgimage, x, y, iw, ih, this);
                }
            }
        } 
    }
}
