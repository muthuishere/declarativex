package declarativex.conditions;

import declarativex.Filter;
import declarativex.core.TryBlock;
import declarativex.interfaces.SupplierWithException;


public class CompletedTrue<T> extends True<T> {
    public CompletedTrue(TryBlock<T> function) {
        super(function);
    }


    public <L, E extends Throwable> Filter<L> then(SupplierWithException<L, E> method) {

        return (Filter<L>) this;

    }

    public <L, E extends Throwable> Filter<T> then(TryBlock<T> tryBlock) {
        return (Filter<T>) this;
    }

    ;


}



