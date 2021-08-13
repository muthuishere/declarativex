package declarativex.delivery.trycatch;

import declarativex.Try;
import declarativex.delivery.AppLog;
import declarativex.testdata.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static declarativex.delivery.Assertions.assertResults;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTestPart4 {
    @Spy
    NewsService newsService;

    private static final AppLog log = new AppLog(NewsServiceTestPart4.class);

    @Test
    public void testAllExceptionsThownDefaultValueIsNull() throws Exception {

        String topic="search";
         doThrow(new IllegalArgumentException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromSun(topic);

        List<String> results = null;

        results= Try.from(()->newsService.downloadFromNyTimes(topic))
                        .or(()->newsService.downloadFromHerald(topic))
                        .or(()->newsService.downloadFromSun(topic))
                        .get();

        
        assertNull(results);


    }
    @Test
    public void testAllExceptionsThownCanSetDefaultValue() throws Exception {

        String topic="search";
         doThrow(new IllegalArgumentException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromSun(topic);

        List<String> results = null;

        results= Try.from(()->newsService.downloadFromNyTimes(topic))
                        .or(()->newsService.downloadFromHerald(topic))
                        .or(()->newsService.downloadFromSun(topic))
                        .orElseGet(Arrays.asList("x,y"));



        assertResults(results, "x,y");

    }


}
