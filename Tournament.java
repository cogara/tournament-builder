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
    Bracket bracket;
    JPanel headerContainer = new JPanel();
    JPanel footerContainer = new JPanel();
    JPanel contentContainer = new JPanel();
    JPanel championPanel = new JPanel();
    JLabel championLabel = new JLabel("Champion:");
    JLabel champion = new JLabel(" ----- ");
    // ArrayList<Bracket> bracketHistory = new ArrayList<Bracket>();
    // ArrayList<Bracket> savedBrackets = new ArrayList<Bracket>();
    int count = 1;

    // JButton addRoundButton = new JButton("Add Round");
    // // AddRoundListener addRoundL = new AddRoundListener();
    // JButton removeRoundButton = new JButton("Remove Round");
    // RemoveRoundListener removeRoundL = new RemoveRoundListener();
    // JButton undoButton = new JButton("Undo");
    // UndoButtonListener undoButtonL = new UndoButtonListener();

    public Tournament(String name, int numRds) {

        bracket = new Bracket(name, numRds);
        // addRoundButton.addActionListener(addRoundL);
        // removeRoundButton.addActionListener(removeRoundL);
        // undoButton.addActionListener(undoButtonL);        
        
        String[] teams = {"Twins","White Sox","Tigers", "Indians", "Mariners", "Yankees", "Astros", "Giants"};
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
        JButton addTeamButton = new JButton("Add Teams");
        addTeamButton.addActionListener(new AddTeamListener());
        footerContainer.add(addTeamButton);
        JButton addRoundButton = new JButton("Add Round");
        addRoundButton.addActionListener(new AddRoundListener());
        footerContainer.add(addRoundButton);
        JButton removeRoundButton = new JButton("Remove Round");
        removeRoundButton.addActionListener(new RemoveRoundListener());
        footerContainer.add(removeRoundButton);
        setTitle("Tournament Builder - " + bracket.name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        headerContainer.setBackground(Color.WHITE);
        footerContainer.setBackground(Color.WHITE);
        contentContainer.setLayout(new GridLayout(1,1));
        headerContainer.setPreferredSize(new Dimension(100, 40));
        getContentPane().add(headerContainer, BorderLayout.NORTH);
        getContentPane().add(footerContainer, BorderLayout.SOUTH);
        getContentPane().add(contentContainer, BorderLayout.CENTER);

        resetBracketGUI();
        // toggleDisabled("disable");  
    
        pack();
        // int w = getWidth();
        // int h = getHeight();
        // System.out.println("Width: " + w);
        // System.out.println("height: " + h);
        // setSize(600,h);
        setSize(600,400);
        // setResizable(false);
        setVisible(true);

    }
    class AddTeamListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JTextField newName = new JTextField(5);
            // JTextField yField = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(2,0));
            myPanel.add(new JLabel("Team Name: "));
            myPanel.add(newName);
            // myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            // myPanel.add(new JLabel("y:"));
            // myPanel.add(yField);

            int result = JOptionPane.showConfirmDialog(
                    Tournament.this, 
                    myPanel, 
                    "Team Information", 
                    JOptionPane.OK_CANCEL_OPTION
                );
            if (result == JOptionPane.OK_OPTION) {
                System.out.println("Team Name: " + newName.getText());
                // System.out.println("y value: " + yField.getText());
                Team newTeam = new Team();
                newTeam.setName(newName.getText());
                bracket.teams.add(new Team(newTeam));
                for (Team team : bracket.teams) {
                    System.out.println(team.getName());
                }
            }
            
        }
    }

    public void setTeamBG(Team t) {
        if (t.isSet) {
            if (t.isWinner) t.setBgColor(new Color(235,255,235));
            if (t.isLoser) t.setBgColor(new Color(255,235,235));
            t.teamName.setText(t.getName());
            t.setBackground(t.getBgColor());
        } else {
            // t.setText("-----");
        }
    }

    
    public void makeAndDrawBracket() {
        GridLayout gridLayout = new GridLayout(1,0);
        GridBagConstraints gbc = new GridBagConstraints();
        bracket.setLayout(new GridBagLayout());
        for (BracketRound round : bracket.rounds) { // loops through each round of bracket
            round.setLayout(new GridBagLayout());
            for (Game game : round.games) { // loops through each game in the current round
                game.setLayout(new GridBagLayout());
                for (Team team : game.teams) { // Loops through each team in the current game
                    setTeamBG(team);
                    if (!team.isSet) team.teamName.setText(" --- ");
                    team.addMouseListener(new TeamMouseListener(team)); 
                    GridBagConstraints tc = new GridBagConstraints(); 
                    tc.weightx = .5;
                    tc.ipady = 10;
                    tc.gridx = 0;
                    tc.gridy = GridBagConstraints.RELATIVE;
                    tc.fill = GridBagConstraints.HORIZONTAL;
                    game.add(team, tc);
                }
                GridBagConstraints gc = new GridBagConstraints();
                gc.fill = GridBagConstraints.HORIZONTAL;
                gc.weightx = 1;
                gc.weighty = 1;
                gc.gridy = GridBagConstraints.RELATIVE;
                gc.gridx = 0;
                gc.insets = new Insets(2, 0, 2, 0);
                
                round.add(game, gc); //add game to current round

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
        contentContainer.add(bracket);
        // getContentPane().add(contentContainer, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void declareWinner(BracketRound round, Game game, Team team) {
        int teamNumber = game.teams.indexOf(team);
        int gameNumber = round.games.indexOf(game);
        int roundNumber = bracket.rounds.indexOf(round);
        System.out.println(teamNumber + " " + gameNumber + " " + roundNumber);

        int newTeamNumber = ((gameNumber % 2) == 0) ? 0 : 1;
        int newGameNumber = (int) (Math.floor(gameNumber / 2));
        int newRoundNumber = roundNumber + 1;
        Team newTeam = new Team(team);
        newTeam.isLoser = false;
        newTeam.isWinner = false;

        game.setWinner(team);

        if ((bracket.rounds.indexOf(round) + 1) == bracket.rounds.size()) {
            bracket.champion = new Team(team);
            // System.out.println("Champion! : " + team.getName());
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
                    for (MouseListener ml : team.getMouseListeners()) {
                        if (ml instanceof TeamMouseListener){
                            team.removeMouseListener(ml);
                        }
                    }
                }
                game.removeAll();
            }
            round.removeAll();
        }

        championPanel.removeAll();
        bracket.removeAll();
        contentContainer.removeAll();
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
        resetTeamBG();
        headerContainer.removeAll();
        revalidate();
        repaint();
    }

    // Boolean headerActive = false;
    // public void toggleHeaderGUI() {
    //     headerActive = (headerActive) ? false : true;
    // }

    // public void toggleHeaderGUI(boolean b) {
    //     headerActive = b;
    // }

    public void makeHeaderGUI(Team team) {
        Game game = (Game) team.getParent();
        BracketRound round = (BracketRound) game.getParent();
        resetTeamBG();
        team.setBackground(new Color(255,255,200));
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
        revalidate();
        repaint();
    }

    // public void toggleDisabled(String method) {
    //     for (BracketRound round : bracket.rounds) {
    //         for (Game game : round.games) {
    //             for (Team team : game.teams) {
    //                 if (method.equals("toggle")) team.setEnabled((team.isEnabled()) ? false : true);
    //                 if (method.equals("disable")) team.setEnabled(false);
    //                 if (method.equals("enable")) team.setEnabled(true);
    //                 if (team.isEnabled()) team.setCursor(new Cursor(Cursor.HAND_CURSOR));
    //             }
    //         }
    //     }
    // }

    class TeamMouseListener implements MouseListener {
        Team team;
        Game game;
        BracketRound round;

        public TeamMouseListener(Team t) {
            team = (Team) t;
            game = (Game) team.getParent();
            round = (BracketRound) team.getParent();
        }

        public void mousePressed(MouseEvent e) {
            if ((e.getButton() == MouseEvent.BUTTON3) && team.isEnabled()) {
                declareWinner(round, game, team);
                clearHeader();
                resetBracketGUI();
            }

            if ((e.getButton() == MouseEvent.BUTTON1) && team.isEnabled()) {

                headerContainer.removeAll();
                makeHeaderGUI(team);
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

    class TeamEditListener implements ActionListener {
        Team team;
        public TeamEditListener(Team team) {
            this.team = team;
        }
        public void actionPerformed(ActionEvent e) {
            JTextField newName = new JTextField(5);
            newName.setText(team.getName());
            // JTextField yField = new JTextField(5);
            
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(2,0));
            myPanel.add(new JLabel("Team Name: "));
            myPanel.add(newName);
            // myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            // myPanel.add(new JLabel("y:"));
            // myPanel.add(yField);

            int result = JOptionPane.showConfirmDialog(
                    Tournament.this, 
                    myPanel, 
                    "Team Information", 
                    JOptionPane.OK_CANCEL_OPTION
                );
            System.out.println("Editing id: " + team.id);
            if (result == JOptionPane.OK_OPTION) {
                // team.setName(newName.getText());
                for (int i = 0; i < bracket.teams.size(); i++) {
                    if (team.id == bracket.teams.get(i).id) {
                        System.out.println("new id = old id");
                        bracket.teams.get(i).setName(newName.getText());
                        team = new Team(bracket.teams.get(i));
                    }
                }
                for (int i = 0; i < bracket.rounds.size(); i++) {
                    BracketRound r = bracket.rounds.get(i);
                    for (int j = 0; j < r.games.size(); j++) {
                        Game g = r.games.get(j);
                        for (int k = 0; k < g.teams.size(); k++) {
                            Team t = g.teams.get(k);
                            if (t.id == this.team.id) {
                                t.setName(newName.getText());
                            }
                        
                        }
                    }
                }
                resetBracketGUI();
            }
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

            bracket.addRound();
            resetBracketGUI();

        }
    }

    class RemoveRoundListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // System.out.println("Size before: " + bracket.rounds.size());
            bracket.removeRound();
            // System.out.println("Size after: " + bracket.rounds.size());
            resetBracketGUI();
        }
    }




}

class TournamentTest {
    public static void main (String[] args) {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
            UIManager.getCrossPlatformLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException e) {
        // handle exception
        }
        catch (ClassNotFoundException e) {
        // handle exception
        }
        catch (InstantiationException e) {
        // handle exception
        }
        catch (IllegalAccessException e) {
        // handle exception
        }
        int numRds = Integer.parseInt(args[1]);
        String name = args[0];
        Tournament t = new Tournament(name, numRds);      

    }
}