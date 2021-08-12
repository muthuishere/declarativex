package com.deemwar.functors.delivery.trycatch;

import com.deemwar.functors.Try;
import com.deemwar.functors.delivery.AppLog;
import com.deemwar.functors.testdata.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.deemwar.functors.delivery.Assertions.assertResults;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ExceptionLoggingTest3 {
    @Spy
    NewsService newsService;

    AppLog actualLog = new AppLog(ExceptionLoggingTest3.class);

    AppLog log=null;

    @BeforeEach
    public void before(){
        log= Mockito.spy(actualLog);
    }
    //private static final AppLog log = new AppLog(NewsServiceTestPart2.class);

    @Test
    public void testTryImp() throws Exception {

        String topic="search";
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);

        List<String> results = null;

        try {
            results =newsService.downloadFromNyTimes(topic);
        }catch (Exception e){
            log.error(e);
            try {

                results =newsService.downloadFromHerald(topic);
            }catch (Exception e1){
                log.error(e);
                try {
                    results =newsService.downloadFromSun(topic);
                }catch (Exception e2){
                    log.error(e);


                }
            }
        }
        System.out.println(results.toString());
        assertResults(results, "Sun Xsearch,Sun Ysearch");
        Mockito.verify(log,times(2)).error(any());

    }
    @Test
    public void testTryDec1() throws Exception {

        String topic="search";
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);

        List<String> results = null;

        results= Try.from(()->newsService.downloadFromNyTimes(topic))
                        .peekError(log::error)
                        .or(()->newsService.downloadFromHerald(topic))
                        .peekError(log::error)
                        .or(()->newsService.downloadFromSun(topic))
                        .peekError(log::error)
                        .get();
        System.out.println(results.toString());
        assertResults(results, "Sun Xsearch,Sun Ysearch");
        Mockito.verify(log,times(2)).error(any());
    }


}
