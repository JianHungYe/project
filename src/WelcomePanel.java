import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WelcomePanel extends JPanel implements ActionListener {

    private JButton submitButton;
    private JFrame enclosingFrame;
    private BufferedImage logo;

    public WelcomePanel(JFrame frame) {

        enclosingFrame = frame;
        try {
            logo = ImageIO.read(new File("images/LElogo.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage() + "welcome");
        }

        submitButton = new JButton("Enter");
        // textField doesn't need a listener since nothing needs to happen when we type in text
        add(submitButton);

        submitButton.addActionListener(this);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString("Welcome Captain of the Empire", 30, 110);
        g.drawImage(logo, 90, 0, 100,100, null);


        submitButton.setLocation(110, 125);
    }

    // ACTIONLISTENER INTERFACE METHODS
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton button) {
            if (button == submitButton) {
                MainFrame f = new MainFrame();
                enclosingFrame.setVisible(false);
            }
        }
    }
}