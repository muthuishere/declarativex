package com.deemwar.functors.lazy;


import com.deemwar.functors.Try;
import com.deemwar.functors.core.TryBlock;
import com.deemwar.functors.interfaces.SupplierWithException;
import com.deemwar.functors.testdata.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class TryBlockLazyTest {
    private static final Logger log = Logger.getLogger(TryBlock.class.getName());
    final String topic = "somex";

    @Spy
    NewsService newsService;


    @Test
    public void tryWithExceptionShouldWorkEffectivelyForNoException() {


        List<String> results = Try.lazy.from(this::downloadCacheData)
                .get();
        assertThat(results, containsInAnyOrder("Cache X" + topic, "Cache Y" + topic));
    }



    private List<String> downloadCacheData() throws Exception {
        return newsService.downloadCacheData(topic);
    }

    @Test
    public void tryWithExceptionShouldWorkForPeekIfNoException() {


        AtomicReference<String> res = new AtomicReference<>();
        AtomicReference<Throwable> resException = new AtomicReference<>();
        Try.lazy.from((SupplierWithException<List<String>, Exception>) () -> newsService.downloadCacheData(topic))
                .peekError(exception -> {
                    resException.set(exception);
                })
                .peek(strings -> {
                    res.set(strings.toString());
                }).get();
        assertNotNull(res.get());
        assertNull(resException.get());
    }

    @Test
    public void tryWithExceptionShouldWorkEffectivelyForSingleException() throws Exception {
        throwExceptionOnDownloadFromHerald();
        List<String> results = executeWithTry(this::downloadFromHindu);
        assertNull(results);

    }

    @Test
    public void tryWithExceptionShouldNotUpdatePeekIfException() throws Exception {


        AtomicReference<String> res = new AtomicReference<>();
        throwExceptionOnDownloadFromHerald();
        Try.lazy.from(this::downloadFromHindu)
                .peek(strings -> {
                    res.set(strings.toString());
                }).get();


        assertNull(res.get());
    }

    private void throwExceptionOnDownloadFromHerald() throws Exception {
        doThrow(new Exception("Herald Service down")).when(newsService).downloadFromHerald(topic);
    }

    @Test
    public void tryWithExceptionShouldUpdatePeekExceptionIfException() throws Exception {


        AtomicReference<Throwable> resException = new AtomicReference<>();
        AtomicReference<String> res = new AtomicReference<>();
        throwExceptionOnDownloadFromHerald();
        Try.lazy.from(this::downloadFromHindu)
                .peek(items -> {
                    res.set(items.toString());
                })
                .peekError(exception -> {
                    resException.set(exception);
                }).get();


        assertNotNull(resException.get());
        assertNull(res.get());

    }

    private List<String> downloadFromHindu() throws Exception {
        return newsService.downloadFromHerald(topic);
    }


    @Test
    public void tryWithExceptionShouldReturnDefaultValueOnException() throws Exception {
        throwExceptionOnDownloadFromHerald();
        final List<String> defaultValue = Arrays.asList("X", "Y");
        List<String> results = Try.lazy.from(this::downloadFromHindu)
                .orElseGet(defaultValue);
        log.info("results " + results.toString());
        log.info(defaultValue.toString());
        assertThat(results, equalTo(defaultValue));

    }

    private <T> T executeWithTry(SupplierWithException<T, Exception> parameter) {
        return Try.lazy.from(parameter)
                .get();
    }


}