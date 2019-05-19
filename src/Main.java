import twitter4j.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws TwitterException{
        String[] voc = {"Corolla", "Camry", "Venza"};
        Data start_parsing = new Data("Toyota", voc);
        start_parsing.getMessages();
    }
}
