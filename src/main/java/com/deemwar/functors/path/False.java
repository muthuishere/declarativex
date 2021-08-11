package com.deemwar.functors.path;

import com.deemwar.functors.Filter;
import com.deemwar.functors.Try;
import com.deemwar.functors.interfaces.SupplierWithException;

import java.util.function.Supplier;

public class False<T> extends Filter<T> {
    public False( Try function) {
        super(false, function);
    }


    public False() {
        super(false,null);
    }


    public <L, E extends Throwable> Filter<T> elseThen(SupplierWithException<T, E> method) {
        this.function = Try.from(method);
        return this;

    }

    @Override
    public <L, E extends Throwable> Filter<T> elseThen(Try<T> tryBlock) {
        this.function = tryBlock;
        return this;

    }

    public Filter elseIf(Supplier<Boolean> method) {
            return Filter.If(method);
    }
}
