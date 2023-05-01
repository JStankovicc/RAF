import java.time.LocalDateTime;

public class Message {

    private static MessageChecker messageChecker = new MessageChecker();
    private LocalDateTime time;
    private String user;
    private String message;

    public Message(String user,String message){
        this.user = user;
        this.message = messageChecker.filterMessage(message);

        //VREME PORUKE JE SADASNJI TRENUTAK

        time = LocalDateTime.now();
    }

    //OBLIK PORUKE KOJI SE SALJE KADA JE POTREBNO
    public String getMessageForSending(){
        return time.getDayOfMonth() + "/"
                + time.getMonthValue() + "/"
                + time.getYear() + " "
                + time.getHour() + ":"
                + time.getMinute() + " - "
                + user + ": "
                + message;
    }

    //DODAVANJE U ISTORIJU PORUKA
    public void addToHistory() throws InterruptedException {

        //LISTA PAMTI POSLENJIH 100 PORUKA

        if(ServerMain.messages.size()>100){
            ServerMain.messages.remove(ServerMain.messages.get(0));
        }

        ServerMain.messages.add(this);

    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
