import twitter4j.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by vadim on 26.02.2019.
 */

class Data {
    private static final int TWEETS_PER_QUERY		= 100;
    private int MAX_QUERIES;
    private Twitter twitter;
    private String[] vocabulary;
    private Query query;

    Data(String message, String[] vocab, int queries){
        twitter = Connection.getConnection();
        query = new Query(message);
        vocabulary = vocab;
        MAX_QUERIES = queries;
    }
    private static void write_to_file(Map<String, ArrayList<Status>> result){
        JSONArray listMain = new JSONArray();
        for (String key : result.keySet()) {
            JSONObject obj = new JSONObject();
            obj.put("Word", key);
            JSONArray messages = new JSONArray();
            for (Status s : result.get(key)) {
                JSONObject obj1 = new JSONObject();
                obj1.put("author", s.getUser().getScreenName());
                obj1.put("date", s.getCreatedAt());
                obj1.put("text", s.getText());
                messages.put(obj1);
            }
            obj.put("messages", messages);
            listMain.put(obj);
        }

        try(FileWriter file = new FileWriter("result.json")) {
                file.write(listMain.toString());
                file.flush();
            }

        catch (IOException e) {
            e.printStackTrace();
        }

    }

    void getMessages() {
        try {
            long maxID = -1;
            Map<String, ArrayList<Status>> map = new HashMap<>();
            RateLimitStatus searchTweetsRateLimit = twitter.getRateLimitStatus("search").get("/search/tweets");
            for (int queryNumber = 0; queryNumber < MAX_QUERIES; queryNumber++)
            {
                if (searchTweetsRateLimit.getRemaining() == 0) {
                    Thread.sleep((searchTweetsRateLimit.getSecondsUntilReset() + 2) * 1000);
                }

                this.query.setCount(TWEETS_PER_QUERY);
                this.query.resultType(Query.ResultType.valueOf("recent"));
                if (maxID != -1) { this.query.setMaxId(maxID - 1); }

                QueryResult r = twitter.search(this.query);
                if (r.getTweets().size() != 0) {
                    for (Status s : r.getTweets()) {
                        if (maxID == -1 || s.getId() < maxID) {
                            maxID = s.getId();
                        }
                        for(String i : this.vocabulary){
                            if (s.getText().contains(i)){
                                if (!map.containsKey(i)) {
                                    map.put(i, new ArrayList<>());
                                }
                                map.get(i).add(s);
                            }
                        }
                    }
                }
                searchTweetsRateLimit = r.getRateLimitStatus();
            }

            if (map.isEmpty()){telegramBot.send("Ничего не найдено!%0A");}
            else {
                String message = "";
                for (String key : map.keySet()) {
                    message = message.concat("Найдено по слову " + key + " " + map.get(key).size() + " сообщений.%0A");
                }
                write_to_file(map);
                telegramBot.send("Результат: %0A" + message + "%0A%0A" + "Отчёт сохранён в result.json");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
