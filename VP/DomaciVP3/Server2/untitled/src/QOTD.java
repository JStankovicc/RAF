import java.time.LocalDate;
import java.util.concurrent.CopyOnWriteArrayList;

public class QOTD {

    public static CopyOnWriteArrayList<Quote> QOTDList = new CopyOnWriteArrayList<Quote>();
    public QOTD(){
        QOTDList.add(new Quote("Oscar Wilde","Be yourself, everyone else is already taken."));
        QOTDList.add(new Quote("Albert Einstein","Two things are infinite: the universe and human stupidity; and I'm not sure about the universe."));
        QOTDList.add(new Quote("Robert Frost","In three words I can sum up everything I've learned about life: it goes on."));
        QOTDList.add(new Quote("Mahatma Gandhi","Be the change that you wish to see in the world."));
    }

    public Quote getQOTD(){

        LocalDate today = LocalDate.now();
        int index = today.getDayOfYear() % this.QOTDList.size();

        System.out.println("Poslat QOTD: " + QOTDList.get(index).getQuote());
        return QOTDList.get(index);
    }
}