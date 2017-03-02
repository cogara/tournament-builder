import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Bracket {
    ArrayList<BracketRound> rounds = new ArrayList<BracketRound>();
    // int numTeams = 8;
    ArrayList<JPanel> panels = new ArrayList<JPanel>();

    public Bracket(int numRds) {
        // panels = new ArrayList<JPanel>();
        // rounds = new ArrayList<BracketRound>();
        int numGames = 1;
        for (int i = 0; i < numRds; i++) {
            rounds.add(0, new BracketRound(numGames));
            numGames *= 2;
        }
            
        // addRound();
        // addRound();
        // addRound();
        // drawRounds();
    }

    public void addRound(BracketRound round) {
        rounds.add(0, round);
        // int numGames = rounds.get(rounds.size()-1).numGames;
        // rounds.add(new BracketRound(numGames*2));
    }

    public void drawRounds() {
        
        for (int i = (rounds.size()); i > 0; i--) {
            JPanel newPanel = new JPanel();
            newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.PAGE_AXIS));
            newPanel.setBackground(new Color(255-(i*6),235-(i*5),225-(i*4)));

            Dimension min = new Dimension(200,0);
            Dimension pref = new Dimension(200,0);
            Dimension max = new Dimension(200, 0);
            newPanel.add(new Box.Filler(min, pref, max));
            

            newPanel.add(Box.createVerticalGlue());
            BracketRound currentRound = rounds.get(i-1);
            int nGames = rounds.get(i-1).numGames;
            for (int j = 0; j < nGames; j++) {
                JLabel newLabel = new JLabel("Game: " + (j+1));
                JButton teamOne = currentRound.games.get(j).teams.get(0).assignTeam;
                JButton teamTwo = currentRound.games.get(j).teams.get(1).assignTeam;
                newLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                teamOne.setAlignmentX(Component.CENTER_ALIGNMENT);
                teamTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
                newPanel.add(newLabel);
                newPanel.add(teamOne);
                newPanel.add(teamTwo);
                // newPanel.add(newButton);
                // newPanel.add(newButtonTwo);
                // if (j < (nGames+1)) {
                    newPanel.add(Box.createVerticalGlue());
                // }
            }
            // newPanel.add(Box.createVerticalGlue());
            newPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            
            
            panels.add(newPanel);
        }
    }

 

    

    public void addRound(int numGames) {
        BracketRound newRound = new BracketRound(numGames);
        rounds.add(newRound);
        for (int i = 0; i < numGames; i++) {
            int index = rounds.indexOf(newRound);
            rounds.get(index).addGame(new Game());
        }
    }
    
    public void declareWinner(Team a) {
        winnerloop:
        for (BracketRound round : rounds) {
            for (Game game : round.games) {
                for (Team team : game.teams) {
                    if (team == a) {
                        int index = rounds.indexOf(round);
                        if ((index + 1) == rounds.size()) {
                            System.out.println("The champion is " + team.name);
                            break winnerloop;
                        }
                        int gameNumber = rounds.get(index).games.indexOf(game);
                        int teamNumber = rounds.get(index).games.get(gameNumber).teams.indexOf(team);
                        rounds.get(index).games.get(gameNumber).teams.set(teamNumber, new Team(team.name));
                        try {
                            rounds.get(index+1).games.get((int) Math.floor((double) gameNumber /2 )).addTeam(team);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break winnerloop;
                    }
                }
            }
        }  
    }

    public void go() {
        for (BracketRound round : rounds) {
            System.out.println("Round: " + (rounds.indexOf(round) + 1));
            for (Game game : round.games) {
                System.out.println("Game#: " + game.gameNumber + "/" + game.totalGames);
                for (Team team : game.teams) {
                    System.out.println(team.name);
                }
            }
            System.out.println("------------");
        }
    }
    
}



// class BracketTest {
//     public static void main(String[] args) {
//         Bracket bracket = new Bracket();
//         bracket.addRound(4);
//         bracket.addRound(2);
//         bracket.addRound(1);
//         Team TSM = new Team("TSM");
//         Team CLG = new Team("CLG");
//         Team C9 = new Team("C9");
//         Team EF = new Team("Echo Fox");
//         Team TL = new Team("Team Liquid");
//         Team NV = new Team("Envy");
//         Team DIG = new Team("Dignitas");
//         Team FQ = new Team("Fly Quest");
//         bracket.rounds.get(0).games.get(0).addTeam(TSM);
//         bracket.rounds.get(0).games.get(0).addTeam(CLG);
//         bracket.rounds.get(0).games.get(1).addTeam(C9);
//         bracket.rounds.get(0).games.get(1).addTeam(EF);
//         bracket.rounds.get(0).games.get(2).addTeam(TL);
//         bracket.rounds.get(0).games.get(2).addTeam(NV);
//         bracket.rounds.get(0).games.get(3).addTeam(DIG);
//         bracket.rounds.get(0).games.get(3).addTeam(FQ);
//         bracket.declareWinner(CLG);
//         bracket.declareWinner(C9);
//         bracket.declareWinner(FQ);
//         bracket.declareWinner(NV);
//         bracket.declareWinner(C9);
//         bracket.declareWinner(FQ);
//         bracket.declareWinner(C9);
//         bracket.go();
//     }
// }