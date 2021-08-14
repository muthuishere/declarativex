package declarativex.testdata;


import declarativex.Try;
import declarativex.core.TryBlock;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/*

    Either(fn)
        .apply
            .right()
                .apply

            .left()
                .apply
 */


//class Left<U> extends Either<U>{
//
//    public Left(U value) {
//        super(value);
//    }
//    public Left() {
//        super(null);
//    }
//
//}

/*

class Either
    or abstract


 from
 or

  from


 failure
    or
    from

    Transducer{
        Set of Functions
    }

    Transducer  onSuccess = transducers.compose
                                    map()
    Transducer  onError = transducers.compose()
                                    map()
                                    .filter
                                    .tap()



    Try.from()
            .connect()

            .or
            from()
             .or
             .from()

       Either.filterIf

              .orFilterIf



    Either.from(Function).andThen(
    ()=>
    ()=>
    )
            .right()
            .left
 */


public class NewsService {
    private static final Logger log = Logger.getLogger(TryBlock.class.getName());

    public List<String> getNewsRelatedTo(String topic) {


        return Try.any(this::downloadFromNyTimes, this::downloadFromHerald, this::downloadCacheData)
                .with(topic)
                .get();


    }


    public List<String> getNewsRelatedToOld(String topic) {
        List<String> contents = null;


        try {
            contents = downloadFromNyTimes(topic);
        } catch (Exception e) {

            log.info("Exception while Downloading from Ny Times");
            try {
                contents = downloadFromHerald(topic);
            } catch (Exception exception) {
                log.info("Exception while Downloading from Hindu");

                contents = downloadCacheData(topic);
            }

        }
        return contents;
    }

    public List<String> downloadCacheData(String topic) {
        List<String> news = new ArrayList<>();
        news.add("Cache X" + topic);
        news.add("Cache Y" + topic);
        return news;
    }

    public  List<String> downloadFromHerald(String topic) throws Exception {
        List<String> news = new ArrayList<>();
        news.add("Herald X" + topic);
        news.add("Herald Y" + topic);
        return news;
    }

    public  List<String> downloadFromSun(String topic) throws Exception {
        List<String> news = new ArrayList<>();
        news.add("Sun X" + topic);
        news.add("Sun Y" + topic);
        return news;
    }

    public  List<String> downloadFromIndianTimes(String topic, Integer serverLocation) throws Exception {
        List<String> news = new ArrayList<>();
        news.add("IndianTimes X" + topic);
        news.add("IndianTimes Y" + topic);
        return news;
    }

    public   List<String> downloadFromNyTimes(String topic) throws Exception {
        List<String> news = new ArrayList<>();
        news.add("NY X" + topic);
        news.add("NY Y" + topic);
        return news;
    }

    @Test
    void name() {

    }
}
