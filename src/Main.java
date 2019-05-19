import twitter4j.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws TwitterException{

        Data start_parsing = new Data("Україна");
        start_parsing.getMessages();
    }
}
