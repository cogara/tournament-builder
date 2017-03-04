import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Tournament extends JFrame {
    // String name;
    // Bracket bracket;    
    // JFrame frame;
    Bracket bracket;
    public static int idCounter;
    int id;
    JLabel contentContainer = new JLabel(new ImageIcon("lol.jpg"));
    JPanel headerContainer = new JPanel();
    JPanel footerContainer = new JPanel();
    // GridBagConstraints gbc;
    JPanel championPanel = new JPanel();
    ArrayList<JPanel> roundList = new ArrayList<JPanel>();

    public Color randomColor() {
        int r = (int) (Math.random() * 250);
        int g = (int) (Math.random() * 250);
        int b = (int) (Math.random() * 250);
        
        Color color = new Color(r, g, b);
        return color;
    }
    // public void makeAndDrawChampion() {
    //     // championPanel.add(new JLabel("Champion:"));
    //     // JLabel champion = new JLabel("Team Champion");
    //     // champion.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
    //     // championPanel.add(champion);
    //     // contentContainer.add(championPanel);
    // }

    public void makeAndDrawFrame() {
        JButton addRoundButton = new JButton("Add Container");
        addRoundButton.addActionListener(new AddRoundListener());
        headerContainer.add(addRoundButton);
        footerContainer.add(new JButton("Testing3"));
        setTitle("Tournament Builder - " + bracket.name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        headerContainer.setBackground(Color.WHITE);
        footerContainer.setBackground(Color.WHITE);
        getContentPane().add(headerContainer, BorderLayout.NORTH);
        getContentPane().add(footerContainer, BorderLayout.SOUTH);

        makeAndDrawBracket();    
    
        pack();
        // setResizable(false);
        setVisible(true);

    }
    
    public void makeAndDrawBracket() {
        GridLayout gridLayout = new GridLayout(1,0);
        GridBagConstraints gbc = new GridBagConstraints();
    
        contentContainer.setLayout(new GridBagLayout());
        contentContainer.setBackground(new Color(220,200,255));
        
        for (int i = 0; i < bracket.rounds.size(); i++) {
            // loops through each round of bracket
            JPanel round = new JPanel(); // creates container element for each round
            round.setLayout(new GridBagLayout());

            
            for (int j = 0; j < bracket.rounds.get(i).games.size(); j++) { 
                // loops through each game in the current round
                Team teamOne = bracket.rounds.get(i).games.get(j).teams.get(0); // gets team 1's name
                Team teamTwo = bracket.rounds.get(i).games.get(j).teams.get(1); // gets team 2's name
                // sets team names or sets to default of not defined
                String teamOneName = (teamOne.getName().length() > 0) ? teamOne.getName() : "Team 1";
                String teamTwoName = ( teamTwo.getName().length() > 0) ? teamTwo.getName() : "Team 2";
                
                // create new container for current game in current round
                JPanel game = new JPanel();
                game.setLayout(new GridLayout(0,1));
                GridBagConstraints gc = new GridBagConstraints();
                JButton teamOneButton = new JButton(teamOneName); // add button for team action - future use
                JButton teamTwoButton = new JButton(teamTwoName); // add button for team action - future use
                
                // action listeners defined for future use - declaring winner, setting team, etc.
                teamOneButton.addActionListener(new TeamButtonListener()); 
                teamTwoButton.addActionListener(new TeamButtonListener());
                // add buttons to the game for display in GUI
                game.setBorder(BorderFactory.createLineBorder(Color.RED));
                game.add(teamOneButton);
                game.add(teamTwoButton);
        
                gc.fill = GridBagConstraints.HORIZONTAL;
                gc.weightx = 1;
                gc.weighty = 1;
                gc.gridy = GridBagConstraints.RELATIVE;
                gc.gridx = 0;
                
                // game.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                //add game to current round, spacing out with 
                round.add(game, gc);
            }
            // round.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            round.setBackground(new Color(255,255,255,0));
        
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = GridBagConstraints.RELATIVE;
            gbc.insets = new Insets(10,10,10,10);
            // contentContainer.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
            contentContainer.add(round, gbc);
            
            



            // adds round to JPanel ArrayList for future repaints
            roundList.add(round);
        }

        JLabel championLabel = new JLabel("Champion:");
        JLabel champion = new JLabel("Team Champion");
        championPanel.setLayout(new GridLayout(0,1));
        championPanel.add(championLabel);
        championPanel.add(champion);
        champion.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
        contentContainer.add(championPanel);
        getContentPane().add(contentContainer, BorderLayout.CENTER);
    
        revalidate();
        repaint();
        

    }

    public Tournament(String name, int numRds) {
        id = ++idCounter;
        bracket = new Bracket(name, numRds);
        // bgImage = new JLabel(new ImageIcon("lol.jpg"));
        makeAndDrawFrame();
        // makeAndDrawBracket();
        
    }
    // JFrame Tournament
    // | JLabel contentContainer
    // | | JPanel round
    // | | | JPanel game
    // | | | | JButton Team 1
    // | | | | JButton Team 2

    class TeamButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton teamButton = (JButton) e.getSource();
            JPanel gamePanel = (JPanel) teamButton.getParent(); 
            JPanel roundPanel = (JPanel) gamePanel.getParent(); 
            int roundNumber = roundList.indexOf(roundPanel);
            System.out.println(roundNumber);
            System.out.println(bracket.rounds.get(roundNumber).games.size());

            // JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, container);

            // container.remove(panel);
            // container.revalidate();
            // container.repaint();
            // frame.revalidate();
            // frame.repaint();
        }
    }

    class AddRoundListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int prevNumGames = bracket.rounds.get(0).games.size();

            // JPanel round = new JPanel();
            BracketRound bracketRound = new BracketRound(prevNumGames * 2);
            bracket.addRound(bracketRound);
            championPanel.removeAll();
            contentContainer.removeAll();

            makeAndDrawBracket();

            JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JButton) e.getSource());

            frame.revalidate();
            frame.repaint();
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