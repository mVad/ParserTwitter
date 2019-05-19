import java.io.*;
import java.net.URL;
import java.net.URLConnection;

class telegramBot{

    static void send(String message) throws IOException {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

        String apiToken = "826190480:AAE3OgvUSl_3jJJW5vnl2qEIDb0VzMOFQqY";
        String chatId = "@twitter_notification";

        urlString = String.format(urlString, apiToken, chatId, message);
        System.out.println();

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        conn.getInputStream();

    }
}