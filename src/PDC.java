public class PDC {
    private int type;
    private String status;
    private int ammo;

    private int accuracy;

    public PDC (int t, String s, int a){
        type = t;
        status = s;
        ammo = a;
        if (type == 1){ // Spacial Environment Electronic Detection
            accuracy = 15;
        }
        if (type == 2){ //Rotating Offensive Armament
            accuracy = 20;
        }
    }

    public String getStatus() {
        return status;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setStatus(String s){
        status = s;
    }

    public int getType(){
        return type;
    }

    public void useAmmo(){
        ammo -= (int) (Math.random()*(100)) + accuracy;
        if (ammo <= 0){
            status = "Offline";
        }
    }
}