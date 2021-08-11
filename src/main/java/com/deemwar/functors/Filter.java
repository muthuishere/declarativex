package com.deemwar.functors;

import com.deemwar.functors.interfaces.SupplierWithException;
import com.deemwar.functors.path.False;
import com.deemwar.functors.path.True;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class Filter<T> {

    protected Try<T> function;
    private boolean value;


    public Filter(boolean value, Try<T> function) {
        this.value = value;
        this.function = function;
    }




    public static Filter If(Supplier<Boolean> method) {
            if(method.get())
                return new True();
            else
                return new False();

    }

    public Try<T> toTryWithException(){
        return function;
    }

    public T get(){
        return function.getValue();
    }


    public Optional<T> toOptional(){
        return function.toOptional();
    }


    public <L, E extends Throwable> Filter<T> then(SupplierWithException<T, E> method) {
        return this;

    }

    public  <L, E extends Throwable> Filter<T> elseThen(Try<T> tryBlock){
        return this;
    }

    public Filter elseIf(Supplier<Boolean> method) {
        return this;
    }

    public <L, E extends Throwable> Filter<T> elseThen(SupplierWithException<T, E> method) {
        return this;

    }


    public  <L, E extends Throwable> Filter<T> then(Try<T> tryBlock){
        return this;
    };
}
