import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;





public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener {

    private JTextField mdelay;
    private BufferedImage background;
    private BufferedImage wcpanel;



    private Timer timer;
    private Timer aniTimer;
    private AniPanels startani;
    private AniPanels pdcani1;
    private AniPanels pdcani2;
    private AniPanels missileani1;
    private AniPanels missileani2;
    private AniPanels winani;

    private AniPanels loseani;

    private AniPanels idleani;

    private AniPanels missileanig;

    private AniPanels pap1;
    private AniPanels pap2;
    private AniPanels pap3;
    private int time;

    private int clickcount;
    private int bgStage;

    private int paneltype;

    private int delaytime;

    private int current = 0;

    private boolean showPSU;

    private boolean showML;
    private boolean guidev;

    private boolean idle = false;

    private boolean first = false;

    private boolean winc1 = false;
    private boolean winc2 = false;

    private boolean stop = false;

    private boolean lost = false;

    private boolean mdetected = false;

    private String dialogue;
    private String dialogue2;

    private int torpTubeStatus;
    private PDC seedPDC1;
    private PDC seedPDC2;

    private ImageIcon AATicon;

    private ImageIcon pdctabicon;
    private ImageIcon missiletabicon;
    private BufferedImage logo;
    private ImageIcon launchMicon;

    private JButton activateAuto;
    private JButton pdcTab;
    private JButton missileTab;
    private JButton launchM;

    private int timerreduction1 = 0;

    private int papstutus = 0;

    private int timerreduction2 = 0;

    public GraphicsPanel() {
        try {
            background = ImageIO.read(new File("images/background1.png"));
            AATicon = new ImageIcon(("images/activebutton.png"));
            pdctabicon = new ImageIcon("images/pdctab2.png");
            missiletabicon = new ImageIcon("images/mtab1.png");
            launchMicon = new ImageIcon("images/LMbutton.png");
            wcpanel = ImageIO.read(new File("images/wcPanel.png"));
            logo = ImageIO.read(new File("images/LElogo.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage() + "graphics panel");
        }

        torpTubeStatus = 1;
        time = 0;
        clickcount = 3;
        timer = new Timer(1000, this); // this Timer will call the actionPerformed interface method every 1000ms = 1 second
        timer.start();
        aniTimer = new Timer(15, this);
        aniTimer.start();
        bgStage = 1;
        paneltype = 1;
        dialogue = Messages.getMessage(1,0);
        dialogue2 = Messages.getMessage(2, 0);
        seedPDC1 = new PDC(1, "Online", 5000);
        seedPDC2 = new PDC(1, "Online", 5000);
        guidev = false;


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
        missileanig = new AniPanels("missilelaunchglobal", 1);
        loseani = new AniPanels("loseani",1);
        winani = new AniPanels("win", 1,70);
        idleani = new AniPanels("idle", 1);
        try{
            pap1 = new AniPanels("pdcactivepath1", 1);
            pap2 = new AniPanels("pdcactivepath2", 1);
            pap3 = new AniPanels("pdcactivepath3", 1);
        } catch (Exception e) {
            System.out.println("animations not made yet");
        }


        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }




    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // just do this


        if (bgStage == 0) {

            g.drawImage(startani.getAniScreen(), 0, 0, 1366, 768, null);
            if (startani.isAniEnd()){
                bgStage = 1;
            }
        } else if (bgStage == 1) {


            g.drawImage(background, 0, 0, 1366, 768, null);
            g.drawImage(logo, 550, 175, 300,300, null);

            g.setFont(new Font("Courier New", Font.BOLD, 18));
            g.setColor(Color.white);
            g.drawString(dialogue, 100, 700);


        }else if (bgStage == 2 || torpTubeStatus == 2){

            if (lost) {
                g.drawImage(loseani.getAniScreen(), 0, 0, null);
            }else if (papstutus > 0){
                if (papstutus ==1){
                    g.drawImage(pap1.getAniScreen(), 0, 0, null);
                }else if(papstutus ==2){
                    g.drawImage(pap2.getAniScreen(), 0, 0, null);
                }else{
                    g.drawImage(pap3.getAniScreen(), 0, 0, null);
                }
            }else if (showML){
                g.drawImage(missileanig.getAniScreen(),0, 0, null);
            }else if (idle){
                g.drawImage(idleani.getAniScreen(), 0, 0, null);
            }else{
                g.drawImage(background, 0, 0, 900, 768,null);
            }
            g.drawImage(wcpanel, 900, 0, null);
            pdcTab.setVisible(true);
            pdcTab.setLocation(910, 3);
            missileTab.setVisible(true);
            missileTab.setLocation(1120, 3);
            g.setFont(new Font("Courier New", Font.BOLD, 25));
            g.drawString("Weapons Control Panel", 920, 100);

            if (paneltype == 1) {
                launchM.setVisible(false);
                activateAuto.setVisible(true);
                mdelay.setVisible(false);
                activateAuto.setLocation(920, 120);
                g.drawRect(980, 250, 325, 200);
                g.drawImage(pdcani2.getAniScreen(), 980, 250, 300,200,null);
                g.setFont(new Font("Courier New", Font.BOLD, 15));
                g.drawString("PDCs Operational", 985, 265);


                if (showPSU) {
                    g.drawImage(pdcani1.getAniScreen(), 0, 0, null);
                }
            } else if (paneltype == 2) {
                activateAuto.setVisible(false);
                mdelay.setVisible(true);
                launchM.setVisible(true);
                launchM.setLocation(920, 120);
                g.setFont(new Font("Courier New", Font.BOLD, 15));
                g.drawString("Premature Detonation delay:", 920, 230);
                mdelay.setLocation(1170, 213);
                g.drawRect(980, 280, 300, 85);
                g.drawImage(missileani2.getAniScreen(), 980, 285, 300,75, null);
                g.setFont(new Font("Courier New", Font.BOLD, 15));
                g.drawString("Missiles Operational", 985, 280);
                g.setFont(new Font("Courier New", Font.BOLD, 20));
                g.drawString("Missiles Remaining: " + "x", 920, 670);

                if (first && !stop) {
                    g.setFont(new Font("Courier New", Font.BOLD, 20));
                    g.setColor(Color.red);
                    g.drawString(dialogue2, 100, 700);
                }


                if (showML) {
                    g.setColor(Color.black);
                    g.drawRect(980, 390, 240, 240);
                    g.setFont(new Font("Courier New", Font.BOLD, 15));
                    g.drawString("Missile Hatch Cam", 990, 400);
                    g.drawImage(missileani1.getAniScreen(), 1000, 410,200,200, null);
                }
            }

        }else if (bgStage == 3 && torpTubeStatus == 1) {
            launchM.setVisible(false);
            missileTab.setVisible(false);
            pdcTab.setVisible(false);
            mdelay.setVisible(false);
            g.drawImage(winani.getAniScreen(), 0, 0,1366, 768, null);
            if (winani.isAniEnd()) {
                g.setFont(new Font("Courier New", Font.BOLD, 50));
                g.drawString("VICTORY", 560, 350);

            }
        }else if (bgStage == 4){
            activateAuto.setVisible(false);
            launchM.setVisible(false);
            missileTab.setVisible(false);
            pdcTab.setVisible(false);
            mdelay.setVisible(false);
            g.drawImage(background, 0, 0, null);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.setColor(Color.red);
            g.drawString("DEFEAT", 580, 350);
        }

        if (guidev){
            try {
                g.drawString("x: " + getMousePosition().x + " y: " + getMousePosition().y, getMousePosition().x, getMousePosition().y);
            } catch (HeadlessException e) {
                System.out.println(e.getMessage());
            }
        }
    }





    // ----- KeyListener interface methods -----
    public void keyTyped(KeyEvent e) { } // unimplemented




    public void keyPressed(KeyEvent e) {
    }




    public void keyReleased(KeyEvent e) {
    }




    // ----- MouseListener interface methods -----
    public void mouseClicked(MouseEvent e) { }  // unimplemented; if you move your mouse while clicking,
    // this method isn't called, so mouseReleased is best




    public void mousePressed(MouseEvent e) { } // unimplemented




    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {  // left mouse click

            if (bgStage ==1){

                clickcount++;
                if (clickcount < Messages.getlength(1)){
                    dialogue = Messages.getMessage(1, clickcount);
                }
                if (clickcount == Messages.getlength(1)){
                    try{
                        background = ImageIO.read(new File("images/background2.png"));
                        bgStage = 2;
                        idle = true;
                        clickcount = 0;
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage() + "graphic");
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
        if (e.getSource() instanceof Timer timersource) {
            if (timersource == aniTimer){
                if (bgStage == 0){
                    startani.nextframe();
                }else if (showPSU && !(pdcani1.isAniEnd())) {
                    timerreduction1++;
                    if (timerreduction1 == 2) {
                        pdcani1.nextframe();
                        if (pdcani1.isAniEnd()) {
                            showPSU = false;
                        }
                        timerreduction1 = 0;
                    }
                }else if (pdcani1.isAniEnd() && papstutus > 0 && idle){
                    if (papstutus == 1){
                        pap1.nextframe();
                        if (pap1.isAniEnd()){
                            pap1.setFrame(0);
                            mdetected = false;
                            pap1.setAniEnd(false);
                            papstutus = 0;
                        }
                    }else if (papstutus == 2){
                        pap2.nextframe();
                        if (pap2.isAniEnd()){
                            pap2.setFrame(0);
                            mdetected = false;
                            pap2.setAniEnd(false);
                            papstutus = 0;
                        }
                    }else if (papstutus == 3){
                        pap3.nextframe();
                        if (pap3.isAniEnd()){
                            pap3.setFrame(0);
                            mdetected = false;
                            pap3.setAniEnd(false);
                            papstutus = 0;
                        }
                    }
                }else if (showML && !(missileani1.isAniEnd())){
                    timerreduction1++;
                    torpTubeStatus = 2;
                    if (timerreduction1 == 2){
                        missileani1.nextframe();
                        missileanig.nextframe();
                        if (missileani1.isAniEnd()){
                            launchM.setIcon(launchMicon);
                            missileani1.setFrame(0);
                            missileanig.setFrame(0);
                            showML = false;
                            torpTubeStatus = 1;
                            missileani1.setAniEnd(false);
                            missileanig.setAniEnd(false);
                        }
                        timerreduction1 = 0;
                    }
                }else if(idle && !winc2){
                    idleani.nextframe();
                    if (idleani.isAniEnd()){
                        idleani.setFrame(0);
                        idleani.setAniEnd(false);
                    }
                }
                if (bgStage == 2) {
                    timerreduction2++;
                    if (timerreduction2 == 2){
                        if (paneltype == 1){
                            pdcani2.nextframe();
                        }
                        if (paneltype == 2){
                            missileani2.nextframe();
                        }


                        timerreduction2 = 0;
                    }
                }else if (winc2 && !winani.isAniEnd() && torpTubeStatus == 1){

                    winani.nextframe();

                }
                if (lost && !loseani.isAniEnd()){
                    loseani.nextframe();
                    if (loseani.isAniEnd()){
                        bgStage = 4;
                        try {
                            background = ImageIO.read(new File("images/lose.png"));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }

            if (timersource == timer){
                time++;
                if (papstutus == 0 && pdcani1.isAniEnd() &&!showML &&!winc2){
                    int r = (int) (Math.random()*6)+1;
                    System.out.println("r:" + r);
                    if (r == 2){
                        int c = (int) (Math.random()*3) +1;
                        papstutus = c;
                        System.out.println("MISSILES DETECTED: " + papstutus);
                    }
                }
                System.out.println(time);
                if (first && time - current == 30 && !winc2){
                    lost = true;

                    System.out.println("u lose");
                }
            }
        }
        if (e.getSource() instanceof JButton button) {
            if (button == activateAuto && !lost && !pdcani1.isAniEnd()) {
                showPSU = true;
                activateAuto.setIcon(new ImageIcon("images/activebutton2.png"));
                System.out.println("ACTIVATED");
            }

            else if (button == launchM && launchM.getIcon() == launchMicon &&!lost) {
                showML = true;
                launchM.setIcon(new ImageIcon("images/LMbutton2.png"));
                if (!(mdelay.getText().isEmpty())){
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
                        bgStage = 3;
                        System.out.println("u win");
                    }
                }

                System.out.println("ACTIVATED");
            }
            else if (button == pdcTab ) {
                paneltype = 1;
                pdcTab.setIcon(new ImageIcon("images/pdctab2.png"));
                missileTab.setIcon(new ImageIcon("images/mtab1.png"));
                System.out.println("PDC TAB");
            }
            else if (button == missileTab ) {
                if (!first){
                    first = true;
                    current = time;
                }
                paneltype = 2;
                pdcTab.setIcon(new ImageIcon("images/pdctab1.png"));
                missileTab.setIcon(new ImageIcon("images/mtab2.png"));
                System.out.println("MISSILES TAB ");
            }
        }

    }

}
