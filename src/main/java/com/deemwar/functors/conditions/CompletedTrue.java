package com.deemwar.functors.conditions;

import com.deemwar.functors.Filter;
import com.deemwar.functors.core.TryBlock;
import com.deemwar.functors.interfaces.SupplierWithException;


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



