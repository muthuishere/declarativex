package declarativex.conditions;

import declarativex.Filter;
import declarativex.Try;
import declarativex.core.TryBlock;
import declarativex.interfaces.SupplierWithException;

import java.util.function.Supplier;

public class True<T> extends Filter<T> {
    public True(TryBlock<T> function) {
        super(true, function);
    }

    public True() {
        super(true, null);
    }


    // Start


    public <L, E extends Throwable> Filter<L> then(SupplierWithException<L, E> method) {
        TryBlock<L> currentfunction = Try.from(method);
        return new CompletedTrue<L>(currentfunction);

    }

    public  <L, E extends Throwable> Filter<T> then(TryBlock<T> tryBlock){
        return new CompletedTrue<T>(tryBlock);
    };

    public Filter elseIf(Supplier<Boolean> method) {
        return this;
    }

    public <L, E extends Throwable> Filter<T> elseThen(SupplierWithException<T, E> method) {
        return this;

    }
    public  <L, E extends Throwable> Filter<T> elseThen(TryBlock<T> tryBlock){
        return this;
    }


}



