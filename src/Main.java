import twitter4j.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите тематику: ");
        String theme = sc.nextLine();
        System.out.print("Введите ключевые слова через пробел: ");
        String line = sc.nextLine();
        System.out.print("Введите количество запросов: ");
        int query_number = sc.nextInt();
        String[] voc = line.split(" ");
        Data start_parsing = new Data(theme, voc, query_number);
        start_parsing.getMessages();
    }
}
