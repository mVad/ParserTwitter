import twitter4j.*;

import java.util.*;

/**
 * Created by vadim on 26.02.2019.
 */

class Data {
    private static final int TWEETS_PER_QUERY		= 100;
    private static final int MAX_QUERIES            = 5;
    private Twitter twitter;
    private Query query;

    Data(String message, String[] vocab){
        twitter = Connection.getConnection();
        query = new Query(message);
        String[] vocabulary = vocab;
    }

    void getMessages() {
        long maxID = -1;
        try {
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
                        System.out.println("@" + s.getUser().getScreenName() + " : " + s.getText());
                    }
                }
                searchTweetsRateLimit = r.getRateLimitStatus();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
