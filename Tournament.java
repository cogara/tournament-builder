import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   
import javax.swing.border.*;
import javax.swing.UIManager;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
 
public class Tournament extends JFrame {
    Bracket bracket;
    JPanel headerContainer = new JPanel();
    JPanel footerContainer = new JPanel();
    JPanel contentContainer = new JPanel();
    JPanel championPanel = new JPanel();
    JLabel championLabel = new JLabel("Champion:");
    JLabel champion = new JLabel(" ----- ");
    ArrayList<JPanel> roundPanels = new ArrayList<JPanel>();
    ArrayList<JPanel> gamePanels = new ArrayList<JPanel>();
    ArrayList<TeamPanel> teamPanels = new ArrayList<TeamPanel>();

    JButton addRoundButton = new JButton("Add Round");
    AddRoundListener addRoundL = new AddRoundListener();
    
    JButton removeRoundButton = new JButton("Remove Round");
    RemoveRoundListener removeRoundL = new RemoveRoundListener();

    JButton addTeamButton = new JButton("Add Team");
    AddTeamListener addTeamL = new AddTeamListener();

    

    public Tournament(String name, int numRds) {

        bracket = new Bracket(name, numRds);
        
        String[] teams = {"Twins","White Sox","Tigers", "Indians", "Mariners", "Yankees", "Astros", "Giants"};
        for (String teamName : teams) {
            Team team = new Team();
            team.setName(teamName);
            bracket.addTeam(team);
        }

        int a = 0;
        for(int i = 0; i < 1; i++) {
            for (int j = 0; j < bracket.rounds.get(i).games.size(); j++) {
                for (int k = 0; k < bracket.rounds.get(i).games.get(j).teams.size(); k++) {
                    System.out.println(bracket.teams.get(a));
                    bracket.rounds.get(i).games.get(j).teams.set(k, bracket.teams.get(a));
                    a++;
                    if (a > 7) { a = 0; }
                } 
            }
        }

        makeAndDrawFrame();
        // saveBracket(bracket);
    }

    public Color randomColor() {
        int r = (int) (Math.random() * 250);
        int g = (int) (Math.random() * 250);
        int b = (int) (Math.random() * 250);
        
        Color color = new Color(r, g, b);
        return color;
    }

    public void makeAndDrawFrame() {
        setTitle("Tournament Builder - " + bracket.name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        headerContainer.setBackground(Color.WHITE);
        footerContainer.setBackground(Color.WHITE);
        contentContainer.setLayout(new GridLayout(1,1));
        headerContainer.setPreferredSize(new Dimension(100, 40));
        footerContainer.setPreferredSize(new Dimension(100, 40));
        headerContainer.setBackground(Color.BLACK);
        footerContainer.setBackground(Color.BLACK);

        addRoundButton.addActionListener(addRoundL);
        removeRoundButton.addActionListener(removeRoundL);
        addTeamButton.addActionListener(addTeamL);
        footerContainer.add(addRoundButton);
        footerContainer.add(removeRoundButton);
        footerContainer.add(addTeamButton);
        JScrollPane scroll = new JScrollPane(contentContainer,
                            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(headerContainer, BorderLayout.NORTH);
        getContentPane().add(footerContainer, BorderLayout.SOUTH);
        getContentPane().add(scroll, BorderLayout.CENTER);

        JButton bracketJsonButton = new JButton("Bracket to JSON");
        footerContainer.add(bracketJsonButton);
        bracketJsonButton.addActionListener(new B2J());

        JButton jsonBracketButton = new JButton("JSON to Bracket");
        footerContainer.add(jsonBracketButton);
        jsonBracketButton.addActionListener(new J2B());

        makeAndDrawBracket();
    
        pack();

        int h = getHeight();
        if (h > 800) { h = 800; }
        setSize(800,h);
        setVisible(true);

    }

   
    public void clearPanelList() {
        roundPanels = new ArrayList<JPanel>();
        gamePanels = new ArrayList<JPanel>();
        teamPanels = new ArrayList<TeamPanel>();
    }

    
    public void makeAndDrawBracket() {
        clearPanelList();
        contentContainer.removeAll();

        GridLayout gridLayout = new GridLayout(1,0);
        GridBagConstraints gbc = new GridBagConstraints();
        BracketPanel bracketPanel = new BracketPanel();
        bracketPanel.setLayout(new GridBagLayout());
        // bracketPanels.add(bracketPanel);
        for (int roundNumber = 0; roundNumber < bracket.rounds.size(); roundNumber++) {
            BracketRound round = (BracketRound) bracket.rounds.get(roundNumber);
            JPanel roundPanel = new JPanel();
            roundPanel.setLayout(new GridBagLayout());
            roundPanels.add(roundPanel);

            for (int gameNumber = 0; gameNumber < round.games.size(); gameNumber++) {
                Game game = (Game) round.games.get(gameNumber);
                JPanel gamePanel = new JPanel();
                gamePanel.setLayout(new GridBagLayout());
                gamePanels.add(gamePanel);

                for (int teamNumber = 0; teamNumber < game.teams.size(); teamNumber++) {
                    Team team = (Team) game.teams.get(teamNumber);
                    TeamPanel teamPanel = new TeamPanel();
                    teamPanels.add(teamPanel);
                    if (game.gameWinner == team) teamPanel.setAsWinner();
                    if (game.gameLoser == team) teamPanel.setAsLoser();
                    JLabel teamLabel = new JLabel();
                    if (!team.isSet) teamLabel.setText(" --- ");
                    if (team.isSet) teamLabel.setText(team.getName());
                    // teamPanel.setBackground(getBgColor(team, game));
                    teamPanel.addMouseListener(new TeamMouseListener(roundNumber, gameNumber, teamNumber)); 
                    GridBagConstraints tc = new GridBagConstraints(); 
                    tc.weightx = .5;
                    tc.weighty = 1;
                    // tc.ipady = 5;
                    tc.gridx = 0;
                    tc.gridy = GridBagConstraints.RELATIVE;
                    tc.fill = GridBagConstraints.HORIZONTAL;
                    teamPanel.add(teamLabel); // add team name to JPanel
                    gamePanel.add(teamPanel, tc); // add team to current game
                    if (teamNumber == 0) {
                        GridBagConstraints ic = new GridBagConstraints();
                        ic.weightx = .5;
                        ic.weighty = .5;
                        // ic.ipady = 5;
                        ic.gridx = 0;
                        ic.gridy = GridBagConstraints.RELATIVE;
                        ic.fill = GridBagConstraints.HORIZONTAL;
                        JPanel gameInfo = new JPanel();
                        gameInfo.setBackground(Color.white);
                        gameInfo.add(new JLabel("01/01 5:00 PM, Rink A"));
                        // gameInfo.add(new JLabel("Location: Rink A"));
                        gamePanel.add(gameInfo, ic);
                    }
                }

                GridBagConstraints gc = new GridBagConstraints();
                gc.fill = GridBagConstraints.HORIZONTAL;
                gc.weightx = 1;
                gc.weighty = 1;
                // gc.ipady = 30;
                gc.gridy = GridBagConstraints.RELATIVE;
                gc.gridx = 0;
                gc.insets = new Insets(2, 0, 2, 0);
                roundPanel.add(gamePanel, gc); //add game to current round

            }
            roundPanel.setBackground(new Color(255,255,255,0));
        
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = GridBagConstraints.RELATIVE;
            gbc.insets = new Insets(10,10,10,10);
            bracketPanel.add(roundPanel, gbc);            
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
        bracketPanel.add(championPanel, champc);
        contentContainer.add(bracketPanel);
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
        Team newTeam = (Team) team;
        game.setWinner(team);

        if ((bracket.rounds.indexOf(round) + 1) == bracket.rounds.size()) {
            bracket.champion = new Team(team);
            champion.setText(bracket.champion.getName());
        } else {
            bracket.rounds.get(newRoundNumber).games.get(newGameNumber).setTeam(newTeamNumber, newTeam);
        }
    }

    public void declareWinner(int rn, int gn, int tn) {
        Team team = (Team) bracket.rounds.get(rn).games.get(gn).teams.get(tn);
        int newRound = rn + 1;
        int newGame = (int) (Math.floor(gn / 2));
        int newTeam = ((gn % 2) == 0) ? 0 : 1;
        
        bracket.rounds.get(rn).games.get(gn).setWinner(team);
        bracket.rounds.get(newRound).games.get(newGame).clearWinner();
        bracket.rounds.get(newRound).games.get(newGame).setTeam(newTeam, team);
        clearHeader();
        makeAndDrawBracket();

    }

    public void clearHeader() {
        headerContainer.removeAll();
        revalidate();
        repaint();
    }

    public void makeHeader(int rn, int gn, int tn) {
        clearHeader();
        BracketRound round = (BracketRound) bracket.rounds.get(rn);
        Game game = (Game) round.games.get(gn);
        Team team = (Team) game.teams.get(tn);

        JLabel teamName = new JLabel(team.getName() + ": ");
        teamName.setForeground(Color.WHITE);
        JButton teamWinButton = new JButton("Winner");
        JButton teamEditButton = new JButton("Edit Team");
        String changeText = "Select Team";
        if (team.isSet) changeText = "Change Team";
        JButton teamChangeButton = new JButton(changeText);
        JButton teamRemoveButton = new JButton("Remove Team");
        JButton teamCancelButton = new JButton("Cancel");
        TeamWinListener winListener = new TeamWinListener(rn, gn, tn);
        TeamEditListener editListener = new TeamEditListener(rn, gn, tn);
        TeamChangeListener changeListener = new TeamChangeListener(rn, gn, tn);
        TeamRemoveListener removeListener = new TeamRemoveListener(rn, gn, tn);
        HeaderCancelListener cancelListener = new HeaderCancelListener();
        
        teamWinButton.addActionListener(winListener);
        teamEditButton.addActionListener(editListener);
        teamChangeButton.addActionListener(changeListener);
        teamRemoveButton.addActionListener(removeListener);
        teamCancelButton.addActionListener(cancelListener);
        headerContainer.add(Box.createRigidArea(new Dimension(0,10)));
        if (team.isSet) headerContainer.add(teamName, BorderLayout.NORTH);
        if (team.isSet) headerContainer.add(teamWinButton, BorderLayout.NORTH);
        if (team.isSet) headerContainer.add(teamEditButton, BorderLayout.NORTH);
        headerContainer.add(teamChangeButton, BorderLayout.NORTH);
        if (team.isSet) headerContainer.add(teamRemoveButton, BorderLayout.NORTH);
        headerContainer.add(new JLabel(" | "), BorderLayout.NORTH);
        headerContainer.add(teamCancelButton, BorderLayout.NORTH);

        revalidate();
        repaint();
    }

    public void saveBracket(Bracket b) {
        System.out.println("INSERT INTO brackets (name) VALUES (" + b.getName() + ")");
        int numRounds = b.rounds.size();
        int roundIter = 1;
        int teamOneId = 1;
        int teamTwoId = 2;
        int roundId = 1;
        for(BracketRound round : b.rounds) {
            System.out.println("INSERT INTO rounds (bracket_id, round_number) VALUES (1, " + roundIter++ + ")");
            for(Game game : round.games) {
                System.out.println(
                    "INSERT INTO games (round_id, team_one_id, team_two_id, winner_id, loser_id) " +
                    "VALUES (" + roundId + ", " + teamOneId + ", " + teamTwoId + ", null, null)");
                for(Team team : game.teams) {
                    String name = team.getName();
                    System.out.println("INSERT INTO teams (name) VALUES (" + name + ")");
                }
                teamOneId += 2;
                teamTwoId += 2;
            }
        }
    }

    class TeamMouseListener implements MouseListener {
        Team team;
        Game game;
        BracketRound round;
        int curRound;
        int curGame;
        int curTeam;

        public TeamMouseListener(int rn, int gn, int tn) {
            round = (BracketRound) bracket.rounds.get(rn);
            game = (Game) round.games.get(gn);
            team = (Team) game.teams.get(tn);
            curRound = rn;
            curGame = gn;
            curTeam = tn;
        }

        public void mousePressed(MouseEvent e) {
            TeamPanel teamPanel = (TeamPanel) e.getSource();
            JPanel gamePanel = (JPanel) teamPanel.getParent();
            
            // Left Click
            if ((e.getButton() == MouseEvent.BUTTON1)) {
                for (TeamPanel tp : teamPanels) {
                    if (teamPanel == tp) {
                        tp.select();
                        if (tp.isSelected()) {
                            makeHeader(curRound, curGame, curTeam);
                        } else {
                            clearHeader();
                        }
                    } else {
                        tp.select(false);
                    }
                }
            }

            // Right Click
            if ((e.getButton() == MouseEvent.BUTTON3)) {
                declareWinner(curRound, curGame, curTeam);
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

    class TeamEditListener implements ActionListener {
        int rn;
        int gn;
        int tn;
        public TeamEditListener(int rn, int gn, int tn) {
            this.rn = rn;
            this.gn = gn;
            this.tn = tn;
        }
        public void actionPerformed(ActionEvent e) {
            Team team = (Team) bracket.rounds.get(rn).games.get(gn).teams.get(tn);
            JTextField newName = new JTextField(5);
            newName.setText(team.getName());
            
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(2,0));
            myPanel.add(new JLabel("Team Name: "));
            myPanel.add(newName);

            int result = JOptionPane.showConfirmDialog(
                Tournament.this, 
                myPanel, 
                "Team Information", 
                JOptionPane.OK_CANCEL_OPTION
            );
            if (result == JOptionPane.OK_OPTION) {
                team.setName(newName.getText());
                makeAndDrawBracket();
            }
        }
    }

    class TeamWinListener implements ActionListener {
        Team team;
        int rn;
        int gn;
        int tn;
        public TeamWinListener(Team team) {
            this.team = team;
        }

        public TeamWinListener(int rn, int gn, int tn) {
            this.rn = rn;
            this.gn = gn;
            this.tn = tn;
            team = (Team) bracket.rounds.get(rn).games.get(gn).teams.get(tn);
        }
        public void actionPerformed(ActionEvent e) {
            declareWinner(rn, gn, tn);
            
        }
    }

    class TeamChangeListener implements ActionListener {        
        int rn;
        int gn;
        int tn;
        public TeamChangeListener(int rn, int gn, int tn) {
            this.rn = rn;
            this.gn = gn;
            this.tn = tn;
        }
        public void actionPerformed(ActionEvent e) {
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(0,1));
            JList<Team> teamList = new JList<Team>();
            DefaultListModel<Team> model = new DefaultListModel<Team>();
            for(Team team : bracket.teams) {
                model.addElement(team);
            }
            teamList.setModel(model);

            teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            teamList.setLayoutOrientation(JList.VERTICAL);
            teamList.setVisibleRowCount(-1);


            JScrollPane listScroller = new JScrollPane(teamList);
            // listScroller.pack();
            listScroller.setPreferredSize(new Dimension(250, 200));

            myPanel.add(listScroller);
            // myPanel.pack();
            int result = JOptionPane.showConfirmDialog(
                Tournament.this,
                myPanel,
                "Select Team",
                JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                int index = teamList.getSelectedIndex();
                Team setTeam = (Team) model.get(index);
                bracket.rounds.get(rn).games.get(gn).teams.set(tn, setTeam);
                clearHeader();
                makeAndDrawBracket();
            }
        }
    }
    
    class AddTeamListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JTextField newName = new JTextField(15);

            JPanel myPanel = new JPanel();
            myPanel.setLayout(new GridLayout(2,0));
            myPanel.add(new JLabel("Team Name: "));
            myPanel.add(newName);

            int result = JOptionPane.showConfirmDialog(
                    Tournament.this, 
                    myPanel, 
                    "Team Information", 
                    JOptionPane.OK_CANCEL_OPTION
                );
            if (result == JOptionPane.OK_OPTION) {
                Team newTeam = new Team();
                newTeam.setName(newName.getText());
                bracket.teams.add(new Team(newTeam));
            }
            
        }
    }

    class TeamRemoveListener implements ActionListener {
        int rn;
        int gn;
        int tn;
        // Team team;
        public TeamRemoveListener(int rn, int gn, int tn) {
            this.rn = rn;
            this.gn = gn;
            this.tn = tn;
        }
        public void actionPerformed(ActionEvent e) {
            bracket.rounds.get(rn).games.get(gn).teams.set(tn, new Team());
            bracket.rounds.get(rn).games.get(gn).clearWinner();
            clearHeader();
            makeAndDrawBracket();
        }
    }
    
    class HeaderCancelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (TeamPanel tp : teamPanels) {
                tp.select(false);
            }
            clearHeader();
        }
    }

    class AddRoundListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            bracket.addRound();
            makeAndDrawBracket();

        }
    }

    class RemoveRoundListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            bracket.removeRound();
            makeAndDrawBracket();
        }
    }

    class J2B implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                sendGet();
            } catch(Exception ex) {}
        }
    }

    class B2J implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for(Team team : bracket.teams) {
                System.out.println(team.getName());
            }
            bracketToJson(bracket);
        }
    }


    private void sendGet() throws Exception {

		String url = "http://api.dev/?action=get_app&id=1";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Tournament-App");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
        jsonToBracket(response.toString());

	}
    public void jsonToBracket(String json) {
        Gson gson = new Gson();
        Bracket newBracket = gson.fromJson(json, Bracket.class);
        Team newTeam = (Team) newBracket.rounds.get(0).games.get(0).teams.get(0);
        printRounds(newBracket);
        bracket = newBracket;
        makeAndDrawBracket();
        // System.out.println(newBracket.userAgent);
    }

    public void bracketToJson(Bracket b) {
        Gson gson = new Gson();
        for (BracketRound round : b.rounds) {
            int rn = b.rounds.indexOf(round);
            round.roundNumber = rn;
            for(Game game : round.games) {
                int gn = round.games.indexOf(game);
                game.gameNumber = gn;
                for (Team team : game.teams) {
                    int tn = game.teams.indexOf(team);
                    team.teamNumber = tn;
                }
            }
        }
        String json = gson.toJson(b);
        System.out.println(json);
    }
    public void printRounds(Bracket b) {
        for(BracketRound round : b.rounds) {
            System.out.println("Round " + round.roundNumber);
            for(Game game : round.games) {
                System.out.println("  Game " + game.gameNumber);
                for(Team team : game.teams) {
                    System.out.println("    Team " + team.teamNumber);
                    System.out.println("      " + team.getName());
                }
            }
        }
    }

    public void checkData() {
        int rd = 1;
        int gm = 1;
        int tm = 1;
        for(BracketRound round : bracket.rounds) {
            System.out.println("Round: " + rd++);
            for(Game game : round.games) {
                System.out.println("  Game: " + gm++);
                for(Team team : game.teams) {
                    System.out.println("    Team: " + tm++);
                }
                tm = 1;
            }
            gm = 1;
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
        // t.checkData();   

    }
}
