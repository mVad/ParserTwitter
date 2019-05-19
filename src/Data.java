import twitter4j.*;

/**
 * Created by vadim on 26.02.2019.
 */

class Data {
    private Twitter twitter;
    private long start_id;

    Data(long id){
        twitter = Connection.getConnection();
        start_id = id;
    }
}
