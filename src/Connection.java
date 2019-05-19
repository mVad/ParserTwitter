import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by vadim on 26.02.2019.
 */

class Connection {

    static Twitter getConnection(){
        ConfigurationBuilder cf = new ConfigurationBuilder();

        String OAuthConsumerKey = "zprk20viKsk55G6G0zFihzP4H";
        String OAuthConsumerSecret = "jrk7lRD1TftLEGVV7ocdYo7CQICesukLqoFtpxKPzOV2nzcRIV";
        String OAuthAccessToken = "968054349195857920-7hBuobz6XJfjqCqetloCJOqLzCtvvQJ";
        String OAuthAccessTokenSecret = "me7oAxtF2lfdf8juLLm84ktOna7qtaPVz994IzUBTKMgE";

        cf.setDebugEnabled(true)
                .setOAuthConsumerKey(OAuthConsumerKey)
                .setOAuthConsumerSecret(OAuthConsumerSecret)
                .setOAuthAccessToken(OAuthAccessToken)
                .setOAuthAccessTokenSecret(OAuthAccessTokenSecret);

        return (new TwitterFactory(cf.build())).getInstance();
    }
}
