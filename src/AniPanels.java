import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AniPanels {
    private BufferedImage aniScreen;
    private String anifolder;
    private int frame;
    private boolean aniEnd;
    private String prefix;
    private int type;
    int count;

    public AniPanels(String folder, int type){
        anifolder = folder;
        aniEnd = false;
        try {
            aniScreen = ImageIO.read(new File("animations/" + folder + "/0001.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage()+ "ani " + folder);
        }
        count = 0;
        File f = new File("animations/" + anifolder);
        for (File file: f.listFiles()){
            count++;
        }
        System.out.println(count);
        frame = 0;

        this.type = type;
    }

    public AniPanels (String folder, int type, int start){
        anifolder = folder;
        aniEnd = false;
        try {
            aniScreen = ImageIO.read(new File("animations/" + folder + "/0001.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage()+ "ani " + folder);
        }
        count = 0;
        File f = new File("animations/" + anifolder);
        for (File file: f.listFiles()){
            count++;
        }
        System.out.println(count);
        frame = start;
        count += start;
        this.type = type;
    }


//what went wrong?

    public BufferedImage nextframe(){
        frame++;
        if (frame < 10){
            prefix = "000";
        }else if (frame > 9 && frame < 100){
            prefix = "00";
        }else if (frame > 99 && frame < 1000){
            prefix = "0";
        }else{
            prefix = "0";
        }
        try {
            aniScreen = ImageIO.read(new File("animations/" + anifolder +"/"+ prefix + frame +".png"));
        } catch (IOException e) {
            System.out.println(e.getMessage() + " aniPanels");
        }

        if (frame == count){
            if (type == 1){
                aniEnd = true;
            } else if (type ==2) {
                frame = 0;
            }
        }
        return aniScreen;
    }

    public boolean isAniEnd() {
        return aniEnd;
    }

    public void setAniEnd(boolean aniEnd) {
        this.aniEnd = aniEnd;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public BufferedImage getAniScreen() {
        return aniScreen;
    }
}