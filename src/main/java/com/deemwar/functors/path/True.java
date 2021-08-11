package com.deemwar.functors.path;

import com.deemwar.functors.Filter;
import com.deemwar.functors.Try;
import com.deemwar.functors.interfaces.SupplierWithException;

public class True<T> extends Filter<T> {
    public True(Try<T> function) {
        super(true, function);
    }

    public True() {
        super(true, null);
    }
    @Override
    public <L, E extends Throwable> Filter<T> then(SupplierWithException<T, E> method) {
        this.function = Try.from(method);
        return this;
    }

    @Override
    public <L, E extends Throwable> Filter<T> then(Try<T> tryBlock) {
        this.function = tryBlock;
        return this;
    }
}



