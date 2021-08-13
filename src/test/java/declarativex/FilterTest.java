package declarativex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


class FilterTest {

    @ParameterizedTest
    @CsvSource({"23,Can Vote","15,Cannot Vote"})
    public void ageGreaterThan18ReturnTrue(int age,String expected){


        System.out.println("age" +age);
        System.out.println("expected" +expected);

        String result= Filter.If(()->age >18)
                .then(()->"Can Vote")
                .elseThen(()-> "Cannot Vote")
                .get();

        assertEquals(expected,result);
    }
    @ParameterizedTest
    @CsvSource({"23,Can Vote","15,Cannot Vote"})
    public void ageGreaterThan18ReturnTrueForSingle(int age,String expected){


        System.out.println("age" +age);
        System.out.println("expected" +expected);

        String result= Filter.If(()->age >18)
                .then("Can Vote")
                .elseThen("Cannot Vote")
                .get();

        assertEquals(expected,result);
    }
    @ParameterizedTest
    @CsvSource({"23,Can Drive","15,Cannot Drive Very Young","75,Cannot Drive Older"})
    public void ageandDrive(int age,String expected){


        System.out.println("age" +age);
        System.out.println("expected" +expected);

        String result= Filter.If(()->age >60)
                .then(()->"Cannot Drive Older")
                .elseIf(()->age > 18)
                .then(()->"Can Drive")
                .elseThen(()-> "Cannot Drive Very Young")
                .get();

        assertEquals(expected,result);
    }
  @ParameterizedTest
    @CsvSource({"23,Can Drive","15,Cannot Drive Very Young","75,Cannot Drive Older"})
    public void ageandDriveSingle(int age,String expected){


        System.out.println("age" +age);
        System.out.println("expected" +expected);

        String result= Filter.If(()->age >60)
                .then("Cannot Drive Older")
                .elseIf(()->age > 18)
                .then("Can Drive")
                .elseThen("Cannot Drive Very Young")
                .get();

        assertEquals(expected,result);
    }

    @ParameterizedTest
    @CsvSource({"23,Can Drive","45,Can Drive","75,Cannot Drive Older"})
    public void ageandDriveSingleWithOnlyCOnditions(int age,String expected){


        System.out.println("age" +age);
        System.out.println("expected" +expected);

        String result= Filter.If(()->age >60)
                .then("Cannot Drive Older")
                .elseIf(()->age > 18)
                .then("Can Drive")
                .get();

        assertEquals(expected,result);
    }
  @ParameterizedTest
    @CsvSource({"23,Can Drive","15,Cannot Drive Very Young","75,Cannot Drive Older"})
    public void ageandDriveSingleWithDefault(int age,String expected){


        System.out.println("age" +age);
        System.out.println("expected" +expected);

        String result= Filter.If(()->age >60)
                .then("Cannot Drive Older")
                .elseIf(()->age > 18)
                .then("Can Drive")
                .orElseGet("Cannot Drive Very Young");

        assertEquals(expected,result);
    }

  @ParameterizedTest
    @CsvSource({"23,Can Drive","15,Cannot Drive Very Young","75,Cannot Drive Older"})
    public void ageandDriveSingleWithDefaultVoid(int age,String expected){


        System.out.println("age" +age);
        System.out.println("expected" +expected);

        Filter.If(()->age >60)
                .thenDo(()-> System.out.println("Cannot Drive Older"))
                .elseIf(()->age > 18)
                .thenDo(()-> System.out.println("Can Drive Older"))
                .execute();


    }

    @Test
    public void ageGreaterThan18ReturnTrueOn(){
        int age=15;


        String result= Filter.If(()->age >18)
                .then(()->"Can Vote")
                .get();

        assertNull(result);
    }
}