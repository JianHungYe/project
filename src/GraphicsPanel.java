import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;




public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener {

    private JTextField mdelay;
    private BufferedImage background;

    private boolean[] pressedKeys;

    private boolean showPSU;

    private boolean showML;

    private Timer timer;
    private Timer aniTimer;
    private AniPanels startani;
    private AniPanels pdcani1;
    private AniPanels pdcani2;
    private AniPanels missileani1;
    private AniPanels missileani2;
    private AniPanels winani;
    private int time;
    private int count;
    private int clickcount;
    private int bgStage;

    private int paneltype;

    private int delaytime;

    private int current = 0;

    private boolean first = false;

    private boolean winc1 = false;
    private boolean winc2 = false;

    private boolean losec1 = false;

    private boolean stop = false;

    private String dialogue;
    private String dialogue2;

    private String torpTubeStatus;
    private PDC seedPDC1;
    private PDC seedPDC2;

    private ImageIcon AATicon;

    private ImageIcon pdctabicon;
    private ImageIcon missiletabicon;

    private ImageIcon launchMicon;

    private JButton activateAuto;
    private JButton pdcTab;
    private JButton missileTab;
    private JButton launchM;







    private int timerreduction1 = 0;

    private int timerreduction2 = 0;








    public GraphicsPanel(String name) {
        try {
            background = ImageIO.read(new File("images/background1.png"));
            AATicon = new ImageIcon(("images/activebutton.png"));
            pdctabicon = new ImageIcon("images/pdctab2.png");
            missiletabicon = new ImageIcon("images/missiletab.png");
            launchMicon = new ImageIcon("images/LMbutton.png");
        } catch (IOException e) {
            System.out.println(e.getMessage() + "graphics panel");
        }

        pressedKeys = new boolean[128];
        torpTubeStatus = "standby";
        time = 0;
        count = 0;
        clickcount = 3;
        timer = new Timer(1000, this); // this Timer will call the actionPerformed interface method every 1000ms = 1 second
        timer.start();
        aniTimer = new Timer(40, this);
        aniTimer.start();
        bgStage = 1;
        paneltype = 1;
        dialogue = Messages.getMessage(1,0);
        dialogue2 = Messages.getMessage(2, 0);
        seedPDC1 = new PDC(1, "Online", 5000);
        seedPDC2 = new PDC(1, "Online", 5000);
        mdelay = new JTextField(1);
        mdelay.setVisible(false);
        add(mdelay);

        activateAuto = new JButton(AATicon);
        activateAuto.setBorderPainted(false);
        activateAuto.setContentAreaFilled(false);
        activateAuto.setFocusPainted(false);
        activateAuto.setOpaque(false);
        activateAuto.addActionListener(this);
        activateAuto.setFocusable(false);
        add(activateAuto);
        activateAuto.setVisible(false);

        launchM = new JButton(launchMicon);
        launchM.setBorderPainted(false);
        launchM.setContentAreaFilled(false);
        launchM.setFocusPainted(false);
        launchM.setOpaque(false);
        launchM.addActionListener(this);
        launchM.setFocusable(false);
        add(launchM);
        launchM.setVisible(false);

        pdcTab = new JButton(pdctabicon);
        pdcTab.setBorderPainted(false);
        pdcTab.setContentAreaFilled(false);
        pdcTab.setFocusPainted(false);
        pdcTab.setOpaque(false);
        pdcTab.addActionListener(this);
        pdcTab.setFocusable(false);
        add(pdcTab);
        pdcTab.setVisible(false);

        missileTab = new JButton(missiletabicon);
        missileTab.setBorderPainted(false);
        missileTab.setContentAreaFilled(false);
        missileTab.setFocusPainted(false);
        missileTab.setOpaque(false);
        missileTab.addActionListener(this);
        missileTab.setFocusable(false);
        add(missileTab);
        missileTab.setVisible(false);


        startani = new AniPanels("start", 1);
        pdcani1 = new AniPanels("pdcstartup", 1);
        pdcani2 = new AniPanels("pdcrotating", 2);
        missileani1 = new AniPanels("missilelaunch", 1);
        missileani2 = new AniPanels("missilerotating", 2);
        winani = new AniPanels("win", 1);

        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }




    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // just do this


        if (!(startani.isAniEnd())) {
//            startani.setAniEnd(true);
            g.drawImage(startani.getAniScreen(), 0, 0, null);
        } else if (bgStage == 1) {


            g.drawImage(background, 0, 0, null);

            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.setColor(Color.white);
            g.drawString(dialogue, 100, 900);


        }else if (bgStage == 2 || torpTubeStatus.equals("firing")){

            g.drawImage(background, 0, 0, null);
            pdcTab.setVisible(true);
            pdcTab.setLocation(272, 200);
            missileTab.setVisible(true);
            missileTab.setLocation(272, 300);
            g.setFont(new Font("Courier New", Font.BOLD, 40));
            g.drawString("Weapons Control Panel", 500, 200);

            if (paneltype == 1) {
                launchM.setVisible(false);
                activateAuto.setVisible(true);
                mdelay.setVisible(false);
                activateAuto.setLocation(500, 250);
                g.drawRect(1050, 200, 575, 400);
                g.drawImage(pdcani2.getAniScreen(), 1050, 200, null);
                g.setFont(new Font("Courier New", Font.BOLD, 20));
                g.drawString("PDCs Operational", 1060, 215);
                g.setFont(new Font("Courier New", Font.BOLD, 40));
                g.drawString("PDC1 Ammo: " + seedPDC1.getAmmo(), 1060, 640);
                g.drawString("PDC2 Ammo: " + seedPDC2.getAmmo(), 1060, 680);

                if (showPSU) {
                    g.drawRect(400, 580, 340, 340);
                    g.setFont(new Font("Courier New", Font.BOLD, 20));
                    g.drawString("PDC Cam", 415, 595);
                    g.drawImage(pdcani1.getAniScreen(), 420, 600, null);
                }
            } else if (paneltype == 2) {
                activateAuto.setVisible(false);
                mdelay.setVisible(true);
                launchM.setVisible(true);
                launchM.setLocation(500, 250);
                g.setFont(new Font("Courier New", Font.BOLD, 20));
                g.drawString("Premature Detonation delay:", 500, 400);
                mdelay.setLocation(840, 380);
                g.drawRect(1050, 200, 575, 400);
                g.drawImage(missileani2.getAniScreen(), 1150, 350, null);
                g.setFont(new Font("Courier New", Font.BOLD, 20));
                g.drawString("Missiles Operational", 1060, 215);
                g.setFont(new Font("Courier New", Font.BOLD, 40));
                g.drawString("Missiles Remaining: " + "x", 1060, 640);

                if (first && !stop) {
                    g.setFont(new Font("Courier New", Font.BOLD, 25));
                    g.setColor(Color.red);
                    g.drawString(dialogue2, 100, 1020);
                }


                if (showML) {
                    g.setColor(Color.black);
                    g.drawRect(400, 580, 340, 340);
                    g.setFont(new Font("Courier New", Font.BOLD, 20));
                    g.drawString("Missile Hatch Cam", 415, 595);
                    g.drawImage(missileani1.getAniScreen(), 420, 600, null);
                }
            }
        }else if (bgStage == 3 && torpTubeStatus.equals("standby")) {
            launchM.setVisible(false);
            missileTab.setVisible(false);
            pdcTab.setVisible(false);
            mdelay.setVisible(false);
            g.drawImage(winani.getAniScreen(), 0, 0, null);
            if (winani.isAniEnd()) {
                g.setFont(new Font("Courier New", Font.BOLD, 50));
                g.drawString("VICTORY", 800, 500);

            }
        }
        if (bgStage == 4){
            launchM.setVisible(false);
            missileTab.setVisible(false);
            pdcTab.setVisible(false);
            mdelay.setVisible(false);
            g.drawImage(background, 0, 0, null);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.setColor(Color.red);
            g.drawString("DEFEAT", 800, 500);
        }



        // this loop does two things:  it draws each Coin that gets placed with mouse clicks,
        // and it also checks if the player has "intersected" (collided with) the Coin, and if so,
        // the score goes up and the Coin is removed from the arraylist

    }





    // ----- KeyListener interface methods -----
    public void keyTyped(KeyEvent e) { } // unimplemented




    public void keyPressed(KeyEvent e) {
        // see this for all keycodes: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
        // A = 65, D = 68, S = 83, W = 87, left = 37, up = 38, right = 39, down = 40, space = 32, enter = 10
        int key = e.getKeyCode();
        pressedKeys[key] = true;
    }




    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
    }




    // ----- MouseListener interface methods -----
    public void mouseClicked(MouseEvent e) { }  // unimplemented; if you move your mouse while clicking,
    // this method isn't called, so mouseReleased is best




    public void mousePressed(MouseEvent e) { } // unimplemented




    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {  // left mouse click

            if (bgStage ==1 && startani.isAniEnd()){

                clickcount++;
                if (clickcount < Messages.getlength(1)){
                    dialogue = Messages.getMessage(1, clickcount);
                }
                if (clickcount == Messages.getlength(1)){
                    try{
                        background = ImageIO.read(new File("images/background2.png"));
                        bgStage = 2;
                        clickcount = 0;
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            }
            if (first && !stop){

                clickcount++;
                if (clickcount < Messages.getlength(2)){
                    dialogue2 = Messages.getMessage(2, clickcount);
                }
                if (clickcount == Messages.getlength(2)){
                    clickcount = 0;
                    stop = true;


                }
            }
        }
    }




    public void mouseEntered(MouseEvent e) { } // unimplemented




    public void mouseExited(MouseEvent e) { } // unimplemented




    // ACTIONLISTENER INTERFACE METHODS: used for buttons AND timers!
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            Timer timersource = (Timer) e.getSource();
            if (timersource == aniTimer){
                if (!(startani.isAniEnd())){
                    startani.nextframe();
                }
                if (showPSU && !(pdcani1.isAniEnd())){
                    timerreduction1++;
                    if (timerreduction1 == 2){
                        pdcani1.nextframe();
                        if (pdcani1.isAniEnd()){
                            showPSU = false;
                        }
                        timerreduction1 = 0;
                    }

                }else if (showML && !(missileani1.isAniEnd())){
                    timerreduction1++;
                    torpTubeStatus = "firing";
                    if (timerreduction1 == 2){
                        missileani1.nextframe();

                        if (missileani1.isAniEnd()){
                            launchM.setIcon(launchMicon);
                            missileani1.setFrame(0);
                            showML = false;
                            torpTubeStatus = "standby";
                            missileani1.setAniEnd(false);
                        }
                        timerreduction1 = 0;
                    }

                }
                if (bgStage == 2) {
                    timerreduction2++;
                    if (timerreduction2 == 3){
                        if (paneltype == 1){
                            pdcani2.nextframe();
                        }
                        if (paneltype == 2){
                            missileani2.nextframe();
                        }


                        timerreduction2 = 0;
                    }
                }
                if (winc2 && !winani.isAniEnd() && torpTubeStatus.equals("standby")){
                    bgStage = 3;
                    winani.nextframe();

                }
            }

            if (timersource == timer){
                time++;
                System.out.println(time);
                if (first && time - current == 60 && !winc2){
                    losec1 = true;
                    bgStage = 4;
                    try {
                        background = ImageIO.read(new File("images/lose.png"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("u lose");
                }
            }
        }
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button == activateAuto ) {
                showPSU = true;
                activateAuto.setIcon(new ImageIcon("images/activebutton2.png"));
                System.out.println("ACTIVATED");
            }

            else if (button == launchM && launchM.getIcon() == launchMicon) {
                showML = true;
                launchM.setIcon(new ImageIcon("images/LMbutton2.png"));
                if (!(mdelay.getText().equals(""))){
                    try {
                        delaytime = Integer.parseInt(mdelay.getText());
                    } catch (NumberFormatException ex) {
                        System.out.println(ex.getMessage());
                    }
                    if (winc1){
                        winc1 = false;
                    }
                    if (delaytime == 2){
                        winc1 = true;
                        mdelay.setText("");
                    }
                } else{
                    if (winc1){
                        winc2 = true;
                        System.out.println("u win");
                    }
                }

                System.out.println("ACTIVATED");
            }
            else if (button == pdcTab ) {
                paneltype = 1;
                pdcTab.setIcon(new ImageIcon("images/pdctab2.png"));
                missileTab.setIcon(new ImageIcon("images/missiletab.png"));
                System.out.println("PDC TAB");
            }
            else if (button == missileTab ) {
                if (!first){
                    first = true;
                    current = time;
                }
                paneltype = 2;
                pdcTab.setIcon(new ImageIcon("images/pdctab.png"));
                missileTab.setIcon(new ImageIcon("images/missiletab2.png"));
                System.out.println("MISSILES TAB ");
            }
        }

    }

}