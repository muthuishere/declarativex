package com.deemwar.functors.conditions;

import com.deemwar.functors.Filter;
import com.deemwar.functors.Try;
import com.deemwar.functors.core.TryBlock;
import com.deemwar.functors.interfaces.SupplierWithException;
import com.deemwar.functors.path.Success;

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



