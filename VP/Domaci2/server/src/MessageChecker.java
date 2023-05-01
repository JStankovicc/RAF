import java.util.Arrays;
import java.util.List;

public class MessageChecker {

    //LISTA RECI KOJE NISU DOZVOLJENE
    private List<String> forbidden = Arrays.asList("ZABRANJENO","ZABRANJENA","ZABRANJENI","ZABRANJEN");
    public MessageChecker(){}

    //FUNKCIJIA VRACA FILTEROVANU RECENICU
    public String filterMessage(String message){

        if(checkMessage(message))return message;

        //AKO RECENICU TREBA FILTEROVATI

        return replaceForbidden(message);
    }

    //PROVERAVA DA LI STRING SADRZI NEKU OD RECI KOJE NISU DOZVOLJENE
    private boolean checkMessage(String message){

        for(String s : forbidden){
            if (message.toUpperCase().contains(s))return false;
        }

        return true;
    }

    //MENJA ZABRANJENU REC SA NJENIM DOZVOLJENIM OBLIKOM
    private String replaceForbidden(String message){

        String[] words = message.split(" ");
        String newMessage = "";

        for(String s : words){

            for(String forb : forbidden){

                if(s.toUpperCase().contains(forb)){

                    int length = s.length();
                    char first = s.charAt(0);
                    char last = s.charAt(length-1);

                    s = "" + first;

                    for(int i = 0; i<(length-2);i++){
                        s+="*";
                    }

                    s+= last;
                }
            }

            newMessage+=s;

            newMessage+=" ";

        }

        return newMessage;
    }
}
