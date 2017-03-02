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
    JPanel contentContainer, headerContainer, footerContainer;
    ArrayList<JPanel> roundList = new ArrayList<JPanel>();

    public Color randomColor() {
        int r = (int) (Math.random() * 250);
        int g = (int) (Math.random() * 250);
        int b = (int) (Math.random() * 250);
        
        Color color = new Color(r, g, b);
        return color;
    }
    
    public Tournament(int numRds) {
        id = ++idCounter;
        bracket = new Bracket(numRds);
        contentContainer = new JPanel();
        headerContainer = new JPanel();
        footerContainer = new JPanel();
        GridLayout gridLayout = new GridLayout(1,0);
    
        contentContainer.setLayout(gridLayout);
        contentContainer.setBackground(Color.WHITE);
        headerContainer.setBackground(Color.WHITE);
        footerContainer.setBackground(Color.WHITE);

        JButton addButton = new JButton("Add Container");
        addButton.addActionListener(new AddContainerListener());
        headerContainer.add(addButton);
        footerContainer.add(new JButton("Testing3"));

        // for (int i = 0; i < bracket.rounds.size(); i++) {
        //     roundList.add(new JPanel());
        // }
        

        for (int i = 0; i < bracket.rounds.size(); i++) {
            JPanel round = new JPanel();
            round.setLayout(new BoxLayout(round, BoxLayout.Y_AXIS));
            round.add(Box.createVerticalGlue());
            for (int j = 0; j < bracket.rounds.get(i).games.size(); j++) {
                JPanel game = new JPanel();
                game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
                // round.add(Box.createRigidArea(new Dimension(0, 20)));
                // round.add(Box.createVerticalGlue());
                JButton teamOne = new JButton(bracket.rounds.get(i).games.get(j).teams.get(0).name);
                JButton teamTwo = new JButton(bracket.rounds.get(i).games.get(j).teams.get(1).name);
                // JButton btn = new JButton("Button: " + (j+1));
                teamOne.addActionListener(new ButtonListener());
                teamTwo.addActionListener(new ButtonListener());
                teamOne.setAlignmentX(Component.CENTER_ALIGNMENT);
                teamTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
                game.add(Box.createVerticalGlue());
                game.add(teamOne);
                game.add(teamTwo);
                game.add(Box.createVerticalGlue());
                game.setAlignmentX(Component.CENTER_ALIGNMENT);
                round.add(Box.createRigidArea(new Dimension(0,5)));
                round.add(game);
                round.add(Box.createRigidArea(new Dimension(0,5)));
                round.add(Box.createVerticalGlue());
            }
            
            // roundList.get(i).setBackground(new Color(200+(i*10),150+(i*15),250-(i*20)));
            // roundList.get(i).add(btn);
            round.setBackground(randomColor());
            roundList.add(round);
            contentContainer.add(round);
        }


        setTitle("Tournament Builder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        getContentPane().add(headerContainer, BorderLayout.NORTH);
        getContentPane().add(contentContainer, BorderLayout.CENTER);
        getContentPane().add(footerContainer, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            JPanel panel = (JPanel) button.getParent();
            JPanel container = (JPanel) panel.getParent();
            // JFrame frame = (JFrame) container.getParent();
            JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, container);
            // System.out.println("button clicked: " + frame);
            roundList.remove(panel);
            container.remove(panel);
            // container.revalidate();
            // container.repaint();
            frame.revalidate();
            frame.repaint();
        }
    }

    class AddContainerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int i = roundList.size();
            int prevNumGames = bracket.rounds.get(0).games.size();

            JPanel round = new JPanel();
            BracketRound bracketRound = new BracketRound(prevNumGames * 2);
            bracket.addRound(bracketRound);

            round.setLayout(new BoxLayout(round, BoxLayout.Y_AXIS));
            round.add(Box.createVerticalGlue());
            for (int j = 0; j < bracket.rounds.get(0).games.size(); j++) {
                JPanel game = new JPanel();
                game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
                // round.add(Box.createRigidArea(new Dimension(0, 20)));
                // round.add(Box.createVerticalGlue());
                JButton teamOne = new JButton(bracketRound.games.get(j).teams.get(0).name);
                JButton teamTwo = new JButton(bracketRound.games.get(j).teams.get(1).name);
                // JButton btn = new JButton("Button: " + (j+1));
                teamOne.addActionListener(new ButtonListener());
                teamTwo.addActionListener(new ButtonListener());
                teamOne.setAlignmentX(Component.CENTER_ALIGNMENT);
                teamTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
                game.add(Box.createVerticalGlue());
                game.add(teamOne);
                game.add(teamTwo);
                game.add(Box.createVerticalGlue());
                game.setAlignmentX(Component.CENTER_ALIGNMENT);
                round.add(Box.createRigidArea(new Dimension(0,5)));
                round.add(game);
                round.add(Box.createRigidArea(new Dimension(0,5)));
                round.add(Box.createVerticalGlue());
            }
            
            round.setBackground(randomColor());
            roundList.add(0, round);
            contentContainer.add(round, 0);











            // JButton btn = new JButton("Button: " + (i+1));
            JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JButton) e.getSource());
            // btn.addActionListener(new ButtonListener());
            // panel.setBackground(randomColor());
            // panel.add(btn);
            // roundList.add(panel);
            // contentContainer.add(panel);
            // container.remove(panel);
            // container.revalidate();
            // container.repaint();
            frame.revalidate();
            frame.repaint();
        }
    }


        // bracket = new Bracket();
        // bracket.drawRounds();
        
        // JFrame frame = new JFrame("Tournament Builder - " + name);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // JPanel contentContainer = new JPanel();
        // JPanel contentFooter = new JPanel();
        // JPanel contentHeader = new JPanel();
        // SpringLayout springLayout = new SpringLayout();
        // contentContainer.setLayout(springLayout);
        // contentContainer.setBackground(Color.white);
        // contentFooter.setBackground(Color.white);

        // JButton addRdButton = new JButton("Add Round");
        // JButton newTournamentButton = new JButton("New Tournament");
        // JButton loadTournamentButton = new JButton("Load Tournament");

        // for (int i = 0; i < bracket.panels.size(); i++) {
        //     if (i == 0) {
        //         springLayout.putConstraint(SpringLayout.WEST, bracket.panels.get(i), 5, SpringLayout.WEST, contentContainer);
        //         springLayout.putConstraint(SpringLayout.SOUTH, contentContainer, 5, SpringLayout.SOUTH, bracket.panels.get(i));
        //     } else {
        //         springLayout.putConstraint(SpringLayout.WEST, bracket.panels.get(i), 5, SpringLayout.EAST, bracket.panels.get(i-1));
        //         if (i == (bracket.panels.size()-1)) {
        //             springLayout.putConstraint(SpringLayout.EAST, contentContainer, 5, SpringLayout.EAST, bracket.panels.get(i));
                    
        //         }
        //         springLayout.putConstraint(SpringLayout.NORTH, bracket.panels.get(i), -5, SpringLayout.NORTH, contentContainer);
        //         springLayout.putConstraint(SpringLayout.SOUTH, bracket.panels.get(i), -5, SpringLayout.SOUTH, contentContainer); 
        //     }
        //     if (i == 0) {
        //         JButton removeButton = new JButton("Remove Round");
        //         removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //         removeButton.addActionListener(new RemoveRoundListener(bracket.rounds.get(i)));
        //         // newPanel.add(removeButton);
        //         bracket.panels.get(i).add(removeButton);
        //     }
        //     contentContainer.add(bracket.panels.get(i));
        // }
        // addRdButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // contentHeader.add(newTournamentButton);
        // contentHeader.add(loadTournamentButton);
        // contentFooter.add(addRdButton);
        // frame.getContentPane().setBackground(new Color(0,0,0));
        // frame.getContentPane().add(BorderLayout.CENTER, contentContainer);
        // frame.getContentPane().add(BorderLayout.SOUTH, contentFooter);
        // frame.getContentPane().add(BorderLayout.NORTH, contentHeader);

        // frame.pack();
        // frame.setVisible(true);

    

    // public class RemoveRoundListener implements ActionListener {
    //     private int roundIndex;

    //     public RemoveRoundListener(BracketRound br) {
    //         roundIndex = bracket.rounds.indexOf(br);
    //     }
    //     public void actionPerformed(ActionEvent event) {
    //         bracket.rounds.remove(roundIndex);
    //         // frame.repaint();

    //         // drawRounds();
    //         System.out.println(roundIndex);
    //         bracket.drawRounds();
    //         frame.revalidate();
    //         frame.repaint();
    //     }
    // }

}

class TournamentTest {
    public static void main (String[] args) {
        int numRds = Integer.parseInt(args[0]);
        Tournament t = new Tournament(numRds);        
        // t.go();
    }
}