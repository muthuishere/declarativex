package com.deemwar.functors;

import com.deemwar.functors.interfaces.SupplierWithException;
import com.deemwar.functors.testdata.NewsService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)

class TryMultipleTest {
    final String topic = "somex";

    @Spy
    NewsService newsService;



    private List<String> downloadFromHerald() throws Exception {
        return newsService.downloadFromHerald(topic);
    }

    private List<String> downloadCacheData() throws Exception {
        return newsService.downloadCacheData(topic);
    }
    private void throwExceptionOnDownloadFromHindu() throws Exception {
        doThrow(new Exception("Hindu")).when(newsService).downloadFromHerald(topic);
    }



    @Test
    public void tryWithExceptionShouldWorkEffectivelyForOrCase() throws Exception {
        throwExceptionOnDownloadFromHindu();

        List<String> results = Try.from(this::downloadFromHerald)
                                        .or(this::downloadCacheData)
                                            .get();
        assertThat(results, containsInAnyOrder("Cache X" + topic, "Cache Y" + topic));
    }

    @Test
    public void tryWithExceptionShouldWorkEffectivelyForAnyCase() throws Exception {
        throwExceptionOnDownloadFromHindu();

        List<String> results = Try.any(newsService::downloadFromHerald,newsService::downloadCacheData)
                                        .with(topic)
                                            .get();
        assertThat(results, containsInAnyOrder("Cache X" + topic, "Cache Y" + topic));
    }

    @Test
    public void basedOnIllegalArgumentExceptionTheDownloadShouldVary() throws Exception {
        doThrow(new IllegalArgumentException("Invalid Topic")).when(newsService).downloadFromHerald(topic);


        List<String> results = Try.from(this::downloadFromHerald)
                                        .onError(IllegalArgumentException.class,()->newsService.downloadFromNyTimes(topic))
                                        .onError(IllegalAccessException.class,()->newsService.downloadCacheData(topic))
                                        .get();
        assertThat(results, containsInAnyOrder("NY X" + topic, "NY Y" + topic));
    }


    @Test
    public void basedOnIllegalAccessExceptionTheDownloadShouldVary() throws Exception {
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);

        List<String> results = Try.from(this::downloadFromHerald)
                                        .onError(IllegalArgumentException.class,()->newsService.downloadFromNyTimes(topic))
                                        .onError(IllegalAccessException.class,()->newsService.downloadCacheData(topic))
                                        .get();
        assertThat(results, containsInAnyOrder("Cache X" + topic, "Cache Y" + topic));
    }

   private <T> T executeWithTry(SupplierWithException<T, Exception> parameter) {
        return Try.from(parameter)
                .get();
    }


}