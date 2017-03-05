import java.util.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

public class ButtonTest extends JButton {
    public static void main(String[] args){ 
        JFrame frame = new JFrame("Testings");
        JPanel panel = new JPanel();
        ButtonTest button = new ButtonTest();
        button.setText("Button!");
        panel.add(button);
        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
        
    }
}