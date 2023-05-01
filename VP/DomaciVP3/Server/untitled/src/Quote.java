public class Quote {
    private String author;
    private String quote;

    public Quote(String author, String quote) {
        this.author = author;
        this.quote = quote;
    }

    //VRACA CITAT U OBLIKU ZA PRIKAZ
    public String getQuote() {
        return this.author + ": " + '"' + this.quote + '"';
    }


}
