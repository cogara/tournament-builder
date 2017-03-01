import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   

public class Tournament {
    String name;
    Bracket bracket;    
    
    public Tournament(String n) {
        name = n;
        bracket = new Bracket();
        // bracket.drawRounds();
        JFrame frame = new JFrame("Tournament Builder - " + name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentContainer = new JPanel();
        JPanel contentFooter = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        contentContainer.setLayout(springLayout);
        contentContainer.setBackground(Color.white);
        contentFooter.setBackground(Color.white);

        JButton button = new JButton("Add Round");

        // ArrayList<JPanel> panels = new ArrayList<JPanel>();

        // int numTeams = 8;
        // for (int i = numTeams/2; i >= 1; i /= 2) {
        //     JPanel newPanel = new JPanel();
        //     newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.PAGE_AXIS));
        //     newPanel.setBackground(new Color(255-(i*6),235-(i*5),225-(i*4)));

        //     Dimension min = new Dimension(150,0);
        //     Dimension pref = new Dimension(150,0);
        //     Dimension max = new Dimension(150, 0);
        //     newPanel.add(new Box.Filler(min, pref, max));
           
        //     newPanel.add(Box.createVerticalGlue());
        //     for (int j = i; j > 0; j--) {
        //         JLabel newLabel = new JLabel("New Label: " + (j+1));
        //         JButton newButton = new JButton("New button: " + (j+1));
        //         JButton newButtonTwo = new JButton("New button: " + (j));
        //         newLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //         newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //         newButtonTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        //         newPanel.add(newLabel);
        //         newPanel.add(newButton);
        //         newPanel.add(newButtonTwo);
        //         if (j > 1) {
        //             newPanel.add(Box.createVerticalGlue());
        //         }
                    
        //         // newPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                
        //     }
        //     newPanel.add(Box.createVerticalGlue());
        //     // newPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        //     // JButton addButton = new JButton("Add Round");
        //     // addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //     // newPanel.add(addButton);
        //     //  if (i == 0) {
        //     //     System.out.println(newPanel);
        //     // }

            
        //     panels.add(newPanel);
        // }
        // System.out.println(bracket.panels.get(0));
        for (int i = 0; i < bracket.panels.size(); i++) {
            if (i == 0) {
                springLayout.putConstraint(SpringLayout.WEST, bracket.panels.get(i), 5, SpringLayout.WEST, contentContainer);
                springLayout.putConstraint(SpringLayout.SOUTH, contentContainer, 5, SpringLayout.SOUTH, bracket.panels.get(i));
            } else {
                springLayout.putConstraint(SpringLayout.WEST, bracket.panels.get(i), 5, SpringLayout.EAST, bracket.panels.get(i-1));
                if (i == (bracket.panels.size()-1)) {
                    springLayout.putConstraint(SpringLayout.EAST, contentContainer, 5, SpringLayout.EAST, bracket.panels.get(i));
                    
                }
                springLayout.putConstraint(SpringLayout.NORTH, bracket.panels.get(i), -5, SpringLayout.NORTH, contentContainer);
                springLayout.putConstraint(SpringLayout.SOUTH, bracket.panels.get(i), -5, SpringLayout.SOUTH, contentContainer); 
            }
            contentContainer.add(bracket.panels.get(i));
        }
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentFooter.add(button);
        frame.getContentPane().setBackground(new Color(0,0,0));
        frame.getContentPane().add(BorderLayout.CENTER, contentContainer);
        frame.getContentPane().add(BorderLayout.SOUTH, contentFooter);

        frame.pack();
        frame.setVisible(true);

    }

    public void go() {
        System.out.println(name + " ");
    }

    public String getName() {
        return name;
    }
}

class TournamentTest {
    public static void main (String[] args) {
        Tournament t = new Tournament(args[0]);
        t.go();
    }
}