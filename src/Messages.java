public class Messages {

    private static String[] startMessages = {"Pilot Vlassis: Captain! Wake up... Wake up!", "Captain: Ughh. How long was I out?", "Specialist Aenor: A minute or so, but we are under attack. The Strelitzia is gone. Rail gun shot ignited it's reactor.", "Captain: God damn it. What are we up against?", "Specialist Aenor: Our latest IFF ping returned the CCS Shimmering Bolt. It's a Confederate Heavy Frigate sir.", "Captain: Stats? Armaments?", "Technician Ceri: 1 spinal mounted Hybrid rail-gun, 2 ROA PDCs, and 3 torpedo bays.", "Captain: Vlassis, stay out of the rail gun's effective radius. Everyone else, remain strapped in. We are outclassed here.", "Captain: Aenor, I want weapons control transferred to my screen. Let kill this ship."};
    private static String[] problemMessages = {"Pilot Vlassis: Captain. We have a problem. Shimmering Bolt is on a hard burn towards us.", "Captain: How much time do we have left?", "Pilot Vlassis: We have less than 60 seconds before we enter their rail gun's ER"};

    public static String getMessage(int type, int index){
        String message = "";
        if (type == 1){
            message = startMessages[index];
        }else if (type == 2){
            message = problemMessages[index];
        }
        return message;
    }

    public static int getlength(int type){
        if (type == 1){
            return startMessages.length;
        }else if (type == 2){
            return problemMessages.length;
        }
        return -1;
    }
}