package declarativex.delivery.trycatch;


import declarativex.Try;
import declarativex.interfaces.SupplierWithException;
import declarativex.testdata.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static declarativex.delivery.Assertions.assertResults;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class MonadRulesTest {

    @Spy
    NewsService newsService;

    @Test
    public void try_should_obey_monad_laws_left_case() throws Exception {

        String topic="search";
        List<String> bindResults = null;
        bindResults= Try.from(getListExceptionSupplierWithException(topic))
                .get();
        System.out.println(bindResults.toString());

        List<String> applyingToValue = getListExceptionSupplierWithException(topic).get();

        assertResults(bindResults, "NY Xsearch,NY Ysearch");
        assertResults(applyingToValue, "NY Xsearch,NY Ysearch");


    }
    @Test
    public void try_should_obey_monad_laws_right_case() throws Exception {

        String topic="search";
        List<String> bindResults = null;
        bindResults= Try.from(getListExceptionSupplierWithException(topic))
                .get();
        System.out.println(bindResults.toString());

        List<String> applyingToValue = getListExceptionSupplierWithException(topic).get();

        assertResults(bindResults, "NY Xsearch,NY Ysearch");
        assertResults(applyingToValue, "NY Xsearch,NY Ysearch");


    }


    public SupplierWithException<List<String>, Exception> getListExceptionSupplierWithException(String topic) {
        return () -> newsService.downloadFromNyTimes(topic);
    }


}
