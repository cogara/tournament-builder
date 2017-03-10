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
    // public static int idCounter;
    // int id;
    JPanel headerContainer = new JPanel();
    JPanel footerContainer = new JPanel();
    JPanel contentContainer = new JPanel();
    JPanel championPanel = new JPanel();
    JLabel championLabel = new JLabel("Champion:");
    JLabel champion = new JLabel(" ----- ");
    // ArrayList<Team> teams = new ArrayList<Team>();
    ArrayList<Bracket> bracketHistory = new ArrayList<Bracket>();
    ArrayList<Bracket> savedBrackets = new ArrayList<Bracket>();
    // TeamButtonListener teamButtonL = new TeamButtonListener();
    int count = 1;

    // Bracket b1 = new Bracket("B1", 2);
    // Bracket b2 = new Bracket("B2", 3);
    // Bracket b3 = new Bracket("B3", 4);

    JButton addRoundButton = new JButton("Add Round");
    JButton undoButton = new JButton("Undo");
    AddRoundListener addRoundL = new AddRoundListener();
    UndoButtonListener undoButtonL = new UndoButtonListener();
    

    public Tournament(String name, int numRds) {
        Bracket b1 = new Bracket("B1", 2);
        Bracket b2 = new Bracket("B2", 3);
        // Bracket b3 = new Bracket("B3", 4);
        // savedBrackets.add(b1);
        // savedBrackets.add(b2);
        
        // id = ++idCounter;
        bracket = new Bracket(name, numRds);
        addRoundButton.addActionListener(addRoundL);
        undoButton.addActionListener(undoButtonL);
        
        

        String[] teams = {"CLG","C9","EF", "TSM", "TL", "FQ", "IMT", "DIG"};
        for (String teamName : teams) {
            Team team = new Team();
            team.setName(teamName);
            bracket.addTeam(team);
        }
        String[] teams2 = {"Navi","NiP","CS", "Liquid", "AB", "AC", "ZD", "DE"};
        for (String teamName : teams2) {
            Team team = new Team();
            team.setName(teamName);
            b1.addTeam(team);
        }
        String[] teams3 = {"ab","cd","ef", "gh", "ij", "kl", "mn", "op"};
        for (String teamName : teams3) {
            Team team = new Team();
            team.setName(teamName);
            b2.addTeam(team);
        }
            
        savedBrackets.add(bracket);
        savedBrackets.add(b1);
        savedBrackets.add(b2);
        makeAndDrawFrame();
        
    }

    public void loadBracket(int index) {
        bracketHistory = new ArrayList<Bracket>();
        bracket = savedBrackets.get(index);
        headerContainer.removeAll();
        footerContainer.removeAll();
        contentContainer.removeAll();
        makeAndDrawFrame();
    }
    
    public Color randomColor() {
        int r = (int) (Math.random() * 250);
        int g = (int) (Math.random() * 250);
        int b = (int) (Math.random() * 250);
        
        Color color = new Color(r, g, b);
        return color;
    }


    class LoadBracketListener implements ActionListener {
        int i;
        public LoadBracketListener(int index) {
            i = index;
        }
        public void actionPerformed(ActionEvent e) {
            loadBracket(i);
        }
    }

    public void makeAndDrawFrame() {
        JButton loadOne = new JButton("Load 1");
        loadOne.addActionListener(new LoadBracketListener(0));
        footerContainer.add(loadOne);
        JButton loadTwo = new JButton("Load 2");
        loadTwo.addActionListener(new LoadBracketListener(1));
        footerContainer.add(loadTwo);
        JButton loadThree = new JButton("Load 3");
        loadThree.addActionListener(new LoadBracketListener(2));
        footerContainer.add(loadThree);
        JButton editBracketButton = new JButton("Edit Bracket");
        editBracketButton.addActionListener(new EditBracketListener());
        footerContainer.add(editBracketButton);
        JButton addTeamButton = new JButton("Modal Button");
        addTeamButton.addActionListener(new AddTeamListener());
        footerContainer.add(addTeamButton);
        // JButton addRoundButton = new JButton("Add Round");
        // addRoundButton.addActionListener(new AddRoundListener());
        // footerContainer.add(addRoundButton);
        // JButton undoButton = new JButton("Undo");
        // undoButton.addActionListener(new UndoButtonListener());
        // footerContainer.add(undoButton);
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
        toggleDisabled("disable");  
    
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
    public void showDialog() {
        // JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
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
        if (bracket.saveHistory) bracketHistory.add(new Bracket(bracket));
        if (bracketHistory.size() < 2) { 
            undoButton.setEnabled(false);
        } else {
            undoButton.setEnabled(true);
        }
        GridLayout gridLayout = new GridLayout(1,0);
        GridBagConstraints gbc = new GridBagConstraints();
    
        bracket.setLayout(new GridBagLayout());
        for (BracketRound round : bracket.rounds) {
            // loops through each round of bracket
            round.setLayout(new GridBagLayout());

            for (Game game : round.games) {
                // loops through each game in the current round
                game.setLayout(new GridBagLayout());
                
                for (Team team : game.teams) {
                    // Loops through each team in the current game
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
        contentContainer.add(bracket);
        // getContentPane().add(contentContainer, BorderLayout.CENTER);
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
                    // team.removeActionListener(teamButtonL);
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

        // bracket.removeAll();
        championPanel.removeAll();
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

    public void toggleDisabled(String method) {
        for (BracketRound round : bracket.rounds) {
            for (Game game : round.games) {
                for (Team team : game.teams) {
                    if (method.equals("toggle")) team.setEnabled((team.isEnabled()) ? false : true);
                    if (method.equals("disable")) team.setEnabled(false);
                    if (method.equals("enable")) team.setEnabled(true);
                    if (team.isEnabled()) team.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        }
    }
    
    class EditBracketListener implements ActionListener {
        Boolean isEditMode = false;
        JButton button;
        public void actionPerformed(ActionEvent e) {           
            button = (JButton) e.getSource();     
            if (!isEditMode) {
                button.setBackground(Color.GREEN);
                button.setText("Done Editing");

                
                // addRoundButton.addActionListener(addRoundL);
                footerContainer.add(addRoundButton);
                // undoButton.addActionListener(undoButtonL);
                footerContainer.add(undoButton);
            } else {
                button.setBackground((Color) new JButton().getBackground());
                button.setText("Edit Bracket");
                // addRoundButton.removeActionListener(addRoundL);
                footerContainer.remove(addRoundButton);
                // undoButton.removeActionListener(undoButtonL);
                footerContainer.remove(undoButton);
            }
            isEditMode = (isEditMode) ? false : true;
            toggleDisabled("toggle");
            clearHeader();
        }
    }

    

    class UndoButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (bracketHistory.size() < 2) {
                System.out.println("No history to be undone");
            } else {
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

                int i = bracketHistory.size()-2; // gets the 2nd to last history, which is last bracket state
                // bracket = null;
                // bracket = new Bracket(bracketHistory.get(i));
                bracket.rounds = null;
                bracket.rounds = bracketHistory.get(i).rounds;


                bracketHistory.remove(bracketHistory.size()-1); // remove the last bracket state from the history

                champion.setText("-----");
                bracket.saveHistory = false; // reloading a previous state will not save it to history again
                clearHeader();
                resetBracketGUI();
                bracket.saveHistory = true; // turn back on saving history
            }
        }
    }

    class TeamMouseListener implements MouseListener {
        Team team;
        public TeamMouseListener(Team team) {
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
            if ((e.getButton() == MouseEvent.BUTTON1) && team.isEnabled()) {
                headerContainer.removeAll();
                // Team team = (Team) e.getSource();
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