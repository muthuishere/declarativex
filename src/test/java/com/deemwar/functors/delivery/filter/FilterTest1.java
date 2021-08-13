package com.deemwar.functors.delivery.filter;

import com.deemwar.functors.Filter;
import com.deemwar.functors.delivery.AppLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
public class FilterTest1 {


    AppLog actualLog = new AppLog(FilterTest1.class);

    AppLog log = null;

    @BeforeEach
    public void before() {
        log = Mockito.spy(actualLog);
    }


    @ParameterizedTest
    @CsvSource({"65,Can Retire",
            "41,Can Go World Tour",
            "34,Can Go for Job",
            "19,Can Go To College",
            "8,Can Play"})
    public void ageandActivity(int age, String expected) {


        String result = null;


        if (age > 60) {
            result = "Can Retire";
        } else if (age > 40) {
            result = "Can Go World Tour";
        } else if (age > 32) {
            result = "Can Go for Job";
        } else if (age > 18) {
            result = "Can Go To College";
        } else {
            result = "Can Play";
        }

        assertThat(result, equalTo(expected));
    }

    @ParameterizedTest
    @CsvSource({"65,Can Retire",
            "41,Can Go World Tour",
            "34,Can Go for Job",
            "19,Can Go To College",
            "8,Can Play"})
    public void ageandActivityOpt1(int age, String expected) {


        String result = Filter.If(() -> age > 60)
                .then(() -> "Can Retire")
                .elseIf(() -> age > 40)
                .then(() -> "Can Go World Tour")
                .elseIf(() -> age > 32)
                .then(() -> "Can Go for Job")
                .elseIf(() -> age > 18)
                .then(() -> "Can Go To College")
                .elseThen(() -> "Can Play")
                .get();


        assertThat(result, equalTo(expected));
    }

    @ParameterizedTest
    @CsvSource({"65,Can Retire",
            "41,Can Go World Tour",
            "34,Can Go for Job",
            "19,Can Go To College",
            "8,Can Play"})
    public void ageandActivityOpt2(int age, String expected) {

        String result = Filter.If(() -> age > 60)
                .then("Can Retire")
                .elseIf(() -> age > 40)
                .then("Can Go World Tour")
                .elseIf(() -> age > 32)
                .then("Can Go for Job")
                .elseIf(() -> age > 18)
                .then("Can Go To College")
                .elseThen("Can Play")
                .get();


        assertThat(result, equalTo(expected));
    }

    @ParameterizedTest
    @CsvSource({"65,Can Retire",
            "41,Can Go World Tour",
            "34,Can Go for Job",
            "19,Can Go To College",
            "8,Can Play"})
    public void ageandActivityDo(int age, String expected) {

        System.out.println(age + "-" + expected);
        Filter.If(() -> age > 60)
                .thenDo(() -> log.info("Can Retire"))
                .elseIf(() -> age > 40)
                .thenDo(() -> log.info("Can Go World Tour"))
                .elseIf(() -> age > 32)
                .thenDo(() -> log.info("Can Go for Job"))
                .elseIf(() -> age > 18)
                .thenDo(() -> log.info("Can Go To College"))
                .elseDo(() -> log.info("Can Play"))
                .execute();

        Mockito.verify(log).info(expected);


    }
}
