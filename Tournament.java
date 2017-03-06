// JFrame Tournament
// | JLabel Bracket
// | | JPanel BracketRound
// | | | JPanel Game
// | | | | JButton Team 1
// | | | | JButton Team 2

import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   
import javax.swing.border.*;
import javax.swing.UIManager;

// import com.sun.media.jfxmedia.events.NewFrameEvent;   

public class Tournament extends JFrame {
    // String name;
    // Bracket bracket;    
    // JFrame frame;
    Bracket bracket;
    public static int idCounter;
    int id;
    // JLabel contentContainer = new JLabel(new ImageIcon("lol.jpg"));
    JPanel headerContainer = new JPanel();
    JPanel footerContainer = new JPanel();
    // GridBagConstraints gbc;
    JPanel championPanel = new JPanel();
    JLabel championLabel = new JLabel("Champion:");
    JLabel champion = new JLabel(" ----- ");
    ArrayList<Team> teams = new ArrayList<Team>();
    ArrayList<JButton> junkButtons = new ArrayList<JButton>();
    TeamButtonListener teamButtonL = new TeamButtonListener();
    // RightMouseListener rml;
    int count = 1;

    public Tournament(String name, int numRds) {
        
        id = ++idCounter;
        bracket = new Bracket(name, numRds);

        String[] teams = {"CLG","C9","EF", "TSM", "TL", "FQ", "IMT", "DIG"};
        for (String teamName : teams) {
            Team team = new Team();
            team.setName(teamName);
            bracket.addTeam(team);
        }
            
        makeAndDrawFrame();
    }
    
    public Color randomColor() {
        int r = (int) (Math.random() * 250);
        int g = (int) (Math.random() * 250);
        int b = (int) (Math.random() * 250);
        
        Color color = new Color(r, g, b);
        return color;
    }

    public void makeAndDrawFrame() {
        JButton editBracketButton = new JButton("Edit Bracket");
        editBracketButton.addActionListener(new EditBracketListener());
        footerContainer.add(editBracketButton);
        JButton addRoundButton = new JButton("Add Round");
        addRoundButton.addActionListener(new AddRoundListener());
        footerContainer.add(addRoundButton);
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new UndoButtonListener());
        footerContainer.add(undoButton);
        setTitle("Tournament Builder - " + bracket.name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        headerContainer.setBackground(Color.WHITE);
        footerContainer.setBackground(Color.WHITE);
        headerContainer.setPreferredSize(new Dimension(100, 40));
        JTextField newName = new JTextField(15);
        footerContainer.add(newName);
        getContentPane().add(headerContainer, BorderLayout.NORTH);
        getContentPane().add(footerContainer, BorderLayout.SOUTH);

        makeAndDrawBracket();    
    
        pack();
        setSize(600,400);
        // setResizable(false);
        setVisible(true);

    }
    public void setTeamBG(Team t) {
        if (t.isSet) {
            if (t.isWinner) t.setBgColor(new Color(235,255,235));
            if (t.isLoser) t.setBgColor(new Color(255,235,235));
            t.setText(t.getName());
            t.setBackground(t.getBgColor());
        } else {
            // t.setText("-----");
        }
    }
    ArrayList<Bracket> bracketHistory = new ArrayList<Bracket>();
    public void makeAndDrawBracket() {
        if (bracket.saveHistory) bracketHistory.add(new Bracket(bracket));
        GridLayout gridLayout = new GridLayout(1,0);
        GridBagConstraints gbc = new GridBagConstraints();
    
        bracket.setLayout(new GridBagLayout());
        bracket.setBackground(new Color(220,200,255));
        System.out.println("Bracket ID: " + bracket.tempId);
        System.out.println("Bracket ID: " + bracket.rounds.get(0).tempId);
        for (int i = 0; i < bracket.rounds.size(); i++) {
            // loops through each round of bracket
            BracketRound round = (BracketRound) bracket.rounds.get(i); // creates container element for each round
            round.setLayout(new GridBagLayout());
            System.out.println("\t Round ID: " + bracket.rounds.get(i).tempId);

            
            for (int j = 0; j < round.games.size(); j++) { 
                // loops through each game in the current round
                Game game = (Game) round.games.get(j);
                System.out.println("\t \t Game Number: " + bracket.rounds.get(i).games.get(j).gameNumber);
                System.out.println("\t \t \t Team ID: " + bracket.rounds.get(i).games.get(j).teams.get(0).getName());
                System.out.println("\t \t \t Team ID: " + bracket.rounds.get(i).games.get(j).teams.get(1).getName());
                Team teamOne = (Team) game.teams.get(0); // get team 1
                Team teamTwo = (Team) game.teams.get(1); // get team 2
                // set team's button text
                setTeamBG(teamOne);
                setTeamBG(teamTwo);
                
                // create new container for current game in current round
                game.setLayout(new GridBagLayout());
                GridBagConstraints gc = new GridBagConstraints();
                
                // action listeners defined for future use - declaring winner, setting team, etc.
                teamOne.addActionListener(teamButtonL); 
                teamTwo.addActionListener(teamButtonL);
                teamOne.addMouseListener(new RightMouseListener(teamOne)); 
                teamTwo.addMouseListener(new RightMouseListener(teamTwo));
                // add buttons to the game for display in GUI
                System.out.println(UIManager.getUI(teamOne));

                GridBagConstraints tc = new GridBagConstraints(); 
                tc.weightx = .5;
                tc.ipady = 10;
                tc.gridx = 0;
                tc.gridy = GridBagConstraints.RELATIVE;
                tc.fill = GridBagConstraints.HORIZONTAL;
                game.add(teamOne, tc);
                game.add(teamTwo, tc);
        
                gc.fill = GridBagConstraints.HORIZONTAL;
                gc.weightx = 1;
                gc.weighty = 1;
                gc.gridy = GridBagConstraints.RELATIVE;
                gc.gridx = 0;
                gc.insets = new Insets(2, 0, 2, 0);
                
                //add game to current round
                round.add(game, gc);
            }
            round.setBackground(new Color(255,255,255,0));
        
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = GridBagConstraints.RELATIVE;
            gbc.insets = new Insets(10,10,10,10);
            
            bracket.add(round, gbc);            
        }
        
        GridBagConstraints champc = new GridBagConstraints();
        champc.fill = GridBagConstraints.HORIZONTAL;
        champc.gridx = GridBagConstraints.RELATIVE;
        champc.weighty = .5;
        champc.weightx = .2;
        champc.gridwidth = 5;
        
        championPanel.setLayout(new GridLayout(0,1));
        championPanel.add(championLabel);
        championPanel.add(champion);
        bracket.add(championPanel, champc);
        
        getContentPane().add(bracket, BorderLayout.CENTER);
        revalidate();
        repaint();
        
    }

    

    


    public void declareWinner(BracketRound round, Game game, Team team) {
        int teamNumber = game.teams.indexOf(team);
        int gameNumber = round.games.indexOf(game);
        int roundNumber = bracket.rounds.indexOf(round);

        int newTeamNumber = ((gameNumber % 2) == 0) ? 0 : 1;
        int newGameNumber = (int) (Math.floor(gameNumber / 2));
        int newRoundNumber = roundNumber + 1;
        Team newTeam = new Team(team);
        newTeam.isLoser = false;
        newTeam.isWinner = false;

        game.setWinner(team);

        if ((bracket.rounds.indexOf(round) + 1) == bracket.rounds.size()) {
            bracket.champion = new Team(team);
            System.out.println("Champion! : " + team.getName());
            // resetBracketGUI();
            champion.setText(bracket.champion.getName());
        } else {
            bracket.rounds.get(newRoundNumber).games.get(newGameNumber).setTeam(newTeamNumber, newTeam);
        }
    }


    public void resetBracketGUI() {
        
        for (BracketRound round : bracket.rounds) {            
            for (Game game : round.games) {
                for (Team team : game.teams) {
                    team.removeActionListener(teamButtonL);
                    for (MouseListener ml : team.getMouseListeners()) {
                        if (ml instanceof RightMouseListener){
                            team.removeMouseListener(ml);
                        }
                    }
                }
                game.removeAll();
            }
            round.removeAll();
        }

        bracket.removeAll();
        championPanel.removeAll();

        makeAndDrawBracket();
        // revalidate();
        // repaint();
    }
    

    public void resetTeamBG() {
        for (BracketRound round : bracket.rounds) {            
            for (Game game : round.games) {
                for (Team team : game.teams) {
                    team.setBackground(team.getBgColor());
                }
            }
        }
    }

    public void clearHeader() {
        editId = 0;
        // JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, bracket);
        resetTeamBG();
        headerContainer.removeAll();
        // frame.revalidate();
        // frame.repaint();
        revalidate();
        repaint();
    }

    public void makeHeaderGUI(Team team) {
        Game game = (Game) team.getParent();
        BracketRound round = (BracketRound) game.getParent();
        // JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, team);
        resetTeamBG();
        team.setBackground(new Color(255,255,200));
        System.out.println(team.getName());
        JLabel teamName = new JLabel(team.getName() + ": ");
        JButton teamWinButton = new JButton("Winner");
        JButton teamEditButton = new JButton("Edit Team");
        String changeText = "Select Team";
        if (team.isSet) changeText = "Change Team";
        JButton teamChangeButton = new JButton(changeText);
        JButton teamRemoveButton = new JButton("Remove Team");
        JButton teamCancelButton = new JButton("Cancel");
        TeamWinListener winListener = new TeamWinListener(team);
        TeamEditListener editListener = new TeamEditListener(team);
        TeamChangeListener changeListener = new TeamChangeListener(team);
        TeamRemoveListener removeListener = new TeamRemoveListener(team);
        TeamCancelListener cancelListener = new TeamCancelListener(team);
        
        teamWinButton.addActionListener(winListener);
        teamEditButton.addActionListener(editListener);
        teamChangeButton.addActionListener(changeListener);
        teamRemoveButton.addActionListener(removeListener);
        teamCancelButton.addActionListener(cancelListener);
        headerContainer.add(Box.createRigidArea(new Dimension(0,10)));
        if (team.isSet) headerContainer.add(teamName, BorderLayout.NORTH);
        if (team.isSet) headerContainer.add(teamWinButton, BorderLayout.NORTH);
        headerContainer.add(teamEditButton, BorderLayout.NORTH);
        headerContainer.add(teamChangeButton, BorderLayout.NORTH);
        if (team.isSet) headerContainer.add(teamRemoveButton, BorderLayout.NORTH);
        headerContainer.add(new JLabel(" | "), BorderLayout.NORTH);
        headerContainer.add(teamCancelButton, BorderLayout.NORTH);

        if (editId == team.id) {
            clearHeader();
        } else {
            editId = team.id;
        }
        // frame.revalidate();
        // frame.repaint();
        revalidate();
        repaint();
    }
    class EditBracketListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {    
            for (BracketRound round : bracket.rounds) {
                for (Game game : round.games) {
                    for (Team team : game.teams) {
                        team.setEnabled((team.isEnabled()) ? false : true);
                    }
                }
            }
            
        }
    }
    class UndoButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (BracketRound round : bracket.rounds) {            
                for (Game game : round.games) {
                    for (Team team : game.teams) {
                        team.removeActionListener(teamButtonL);
                        for (MouseListener ml : team.getMouseListeners()) {
                            if (ml instanceof RightMouseListener){
                                team.removeMouseListener(ml);
                            }
                        }
                    }
                    game.removeAll();
                }
                round.removeAll();
            }

            bracket.removeAll();
            championPanel.removeAll();            
            getContentPane().remove(bracket);
            int i = bracketHistory.size()-2; // gets the 2nd to last history
            System.out.println("History: " + bracketHistory.get(i).rounds.get(0).games.get(0).teams.get(0).getName());
            System.out.println("Current: " + bracket.rounds.get(0).games.get(0).teams.get(0).getName());
            bracket = null;
            bracket = new Bracket(bracketHistory.get(i));
            System.out.println("Bracket rewrite: " + bracket.rounds.get(0).games.get(0).teams.get(0).getName());
            bracketHistory.remove(bracketHistory.size()-1);

            champion.setText("-----");
            bracket.saveHistory = false;
            // resetBracketGUI();

            
            makeAndDrawBracket();
            // bracketHistory.remove(i+1);
            bracket.saveHistory = true;
        }
    }

    class RightMouseListener implements MouseListener {
        Team team;
        public RightMouseListener(Team team) {
            this.team = team;
        }
        public void mousePressed(MouseEvent e) {
            // System.out.println("Mouse Pressed! " + e.getButton());
            if ((e.getButton() == MouseEvent.BUTTON3) && team.isEnabled()) {
                Game game = (Game) team.getParent();
                BracketRound round = (BracketRound) game.getParent();
                declareWinner(round, game, team);
                clearHeader();
                resetBracketGUI();
            }
        }
   
        public void mouseExited(MouseEvent e) {

        }
        public void mouseEntered(MouseEvent e) {

        }
        public void mouseReleased(MouseEvent e) {

        }
        public void mouseClicked(MouseEvent e) {

        }
    }
    int editId = 0;
    class TeamButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {    
            // clearJunkListeners();
            headerContainer.removeAll();
            Team team = (Team) e.getSource();
            
            makeHeaderGUI(team);
        }
    }

    class TeamEditListener implements ActionListener {
        Team team;
        public TeamEditListener(Team team) {
            this.team = team;
        }
        public void actionPerformed(ActionEvent e) {
        //    System.out.println("editing team: " + team.getName());
        //    for (Team team : bracket.teams) {
        //        System.out.println("Team: " + team.getName());
        //    }
        // System.out.println(team.getUI());

        }
    }

    class TeamWinListener implements ActionListener {
        Team team;
        public TeamWinListener(Team team) {
            this.team = team;
        }
        public void actionPerformed(ActionEvent e) {
            Game game = (Game) team.getParent();
            BracketRound round = (BracketRound) game.getParent();
            declareWinner(round, game, team);
            clearHeader();
            resetBracketGUI();
        }
    }

    int tn = 0;
    class TeamChangeListener implements ActionListener {
        Team team;
        public TeamChangeListener(Team team) {
            this.team = team;
        }
        public void actionPerformed(ActionEvent e) {
            Game game = (Game) team.getParent();
            BracketRound round = (BracketRound) game.getParent();

            int teamNumber = game.teams.indexOf(team);
            int gameNumber = round.games.indexOf(game);
            int roundNumber = bracket.rounds.indexOf(round);

            if (tn > (bracket.teams.size()-1)) {
                tn = 0;
            }
            Team tempTeam = new Team(bracket.teams.get(tn));
            tn++;

            bracket.rounds.get(roundNumber).games.get(gameNumber).teams.set(teamNumber, tempTeam);
            resetBracketGUI();
            headerContainer.removeAll();
            makeHeaderGUI(tempTeam);
        }
    }
    class TeamRemoveListener implements ActionListener {
        Team team;
        public TeamRemoveListener(Team team) {
            this.team = team;
        }
        public void actionPerformed(ActionEvent e) {
            Game game = (Game) team.getParent();
            BracketRound round = (BracketRound) game.getParent();
            // JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, team);
            game.teams.set(game.teams.indexOf(team), new Team());
            clearHeader();
            resetBracketGUI();
        }
    }
    
    class TeamCancelListener implements ActionListener {
        Team team;
        public TeamCancelListener(Team team) {
            this.team = team;
        }
        public void actionPerformed(ActionEvent e) {
            // clearJunkListeners();
            clearHeader();
        }
    }

    class AddRoundListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // int prevNumGames = bracket.rounds.get(0).games.size();

            // BracketRound bracketRound = new BracketRound(prevNumGames * 2);
            bracket.addRound();
            resetBracketGUI();
            // championPanel.removeAll();
            // bracket.removeAll();

            

            // JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JButton) e.getSource());

            // frame.revalidate();
            // frame.repaint();
        }
    }




}

class TournamentTest {
    public static void main (String[] args) {
        int numRds = Integer.parseInt(args[1]);
        String name = args[0];
        Tournament t = new Tournament(name, numRds);        
    }
}