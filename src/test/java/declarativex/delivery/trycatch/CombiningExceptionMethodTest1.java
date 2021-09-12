package declarativex.delivery.trycatch;

import declarativex.Try;
import declarativex.testdata.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static declarativex.delivery.Assertions.assertResults;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class CombiningExceptionMethodTest1 {
    @Spy
    NewsService newsService;

    @Test
    public void testTryImp() throws Exception {

        String topic="search";
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);

        List<String> results = null;

        try {
            results =newsService.downloadFromNyTimes(topic);
        }catch (Exception e){
            try {
                results =newsService.downloadFromHerald(topic);
            }catch (Exception e1){
                try {
                    results =newsService.downloadFromSun(topic);
                }catch (Exception ignored){
                }
            }
        }

        System.out.println(results.toString());
        assertResults(results, "Sun Xsearch,Sun Ysearch");


    }



    @Test
    public void testTryDec1() throws Exception {

        String topic="search";
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);

        List<String> results = null;

        results= Try.from(()->newsService.downloadFromNyTimes(topic))
                        .or(()->newsService.downloadFromHerald(topic))
                        .or(()->newsService.downloadFromSun(topic))
                        .get();

        // since the for the above case it will be null

        // if you would like to get default value
        results= Try.from(()->newsService.downloadFromNyTimes(topic))
                .or(()->newsService.downloadFromHerald(topic))
                .or(()->newsService.downloadFromSun(topic))
                .orElseGet(Arrays.asList("Topic x","Topic Y"));

        System.out.println(results.toString());
        assertResults(results, "Sun Xsearch,Sun Ysearch");

    }
    @Test
    public void testTryDec3() throws Exception {

        String topic="search";
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);

        List<String> results = null;

        results= Try.any(newsService::downloadFromNyTimes,
                         newsService::downloadFromHerald,
                         newsService::downloadFromSun)
                        .with(topic)
                        .get();




        System.out.println(results.toString());
        assertResults(results, "Sun Xsearch,Sun Ysearch");

    }

}
