package com.deemwar.functors.delivery.trycatch;

import com.deemwar.functors.Try;
import com.deemwar.functors.delivery.AppLog;
import com.deemwar.functors.testdata.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.deemwar.functors.delivery.Assertions.assertResults;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class BasedOnErrorClassTest2 {
    @Spy
    NewsService newsService;

    private static final AppLog log = new AppLog(BasedOnErrorClassTest2.class);

    //Based On Error
    @Test
    public void testTryImpIllegalArg() throws Exception {

        String topic="search";
       doThrow(new IllegalArgumentException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);

        List<String> results= startImp(topic);
        assertResults(results, "Herald Xsearch,Herald Ysearch");

    }
    @Test
    public void testTryImpIllegalAccess() throws Exception {

        String topic="search";
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);

        List<String> results= startImp(topic);

        assertResults(results, "Sun Xsearch,Sun Ysearch");
    }


    @Test
    public void testTryDec1() throws Exception {

        String topic="search";
         doThrow(new IllegalArgumentException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        //doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);

        List<String> results = tryDeclarative(topic);

        System.out.println(results.toString());
        assertResults(results, "Herald Xsearch,Herald Ysearch");

    }
    @Test
    public void testTryDec2() throws Exception {

        String topic="search";

        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);

        List<String> results = tryDeclarative(topic);

        System.out.println(results.toString());

        assertResults(results, "Sun Xsearch,Sun Ysearch");
    }

    public List<String> startImp(String topic) throws Exception {
        List<String> results = null;

        try {
            results =newsService.downloadFromNyTimes(topic);
        }catch (IllegalArgumentException e){

            try {
                results =newsService.downloadFromHerald(topic);
            }catch (Exception e1){

            }
        }catch (IllegalAccessException e){

            try {
                results =newsService.downloadFromSun(topic);
            }catch (Exception e1){

            }
        }
        System.out.println(results.toString());
        return  results;

    }


    public List<String> tryDeclarative(String topic) {
        List<String> results = null;

        results= Try.from(()->newsService.downloadFromNyTimes(topic))
                        .onError(IllegalArgumentException.class,()->newsService.downloadFromHerald(topic))
                        .onError(IllegalAccessException.class,()->newsService.downloadFromSun(topic))
                        .get();
        return results;
    }


}
