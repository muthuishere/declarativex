package declarativex.conditions;

import declarativex.Filter;
import declarativex.Try;
import declarativex.core.TryBlock;
import declarativex.interfaces.SupplierWithException;
import declarativex.interfaces.VoidConsumer;

import java.util.function.Supplier;

public class False<T> extends Filter<T> {
    public False( TryBlock function) {
        super(false, function);
    }


    public False() {
        super(false,null);
    }
    //Start





    public Filter elseIf(Supplier<Boolean> method) {
        return Filter.If(method);
    }

    public <L, E extends Throwable> Filter<T> elseThen(SupplierWithException<T, E> method) {
        TryBlock<T> currentfunction = Try.from(method);
        return new False<T>(currentfunction);

    }
    public  <L, E extends Throwable> Filter<T> elseThen(TryBlock<T> tryBlock){
        return new False<T>(tryBlock);
    }


    public <L, E extends Throwable> Filter<L> then(SupplierWithException<L, E> method) {

        return (Filter<L>) this;

    }
    public <L, E extends Throwable> Filter<L> elseDo(VoidConsumer val) {

        return (Filter<L>) elseThen(()->{
            val.accept();
            return null;
        });

    }
    public  <L, E extends Throwable> Filter<T> then(TryBlock<T> tryBlock){
        return this;
    };
}
