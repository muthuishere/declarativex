package com.deemwar.functors.delivery;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Assertions {
    public static void assertResults(List<String> results, String expected) {
        String actual= results.stream().collect(Collectors.joining(","));
        System.out.println(actual);
        assertThat(expected,equalTo(actual));
    }
}
