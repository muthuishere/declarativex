package com.deemwar.functors;

import com.deemwar.functors.core.TryBlock;
import com.deemwar.functors.interfaces.SupplierWithException;
import com.deemwar.functors.conditions.False;
import com.deemwar.functors.conditions.True;
import com.deemwar.functors.interfaces.VoidConsumer;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public  class Filter<T> {

    protected TryBlock<T> function;
    private boolean result;


    public Filter(boolean result, TryBlock<T> function) {
        this.result = result;
        this.function = function;
    }

    public Filter() {

    }


    public static Filter<?> If(Supplier<Boolean> method) {
            if(method.get())
                return new True<>();
            else
                return new False<>();

    }

    public TryBlock<T> toTryWithException(){
        return function;

    }

    public T get(){
        return orElseGet(null);
    }

    public void execute(){
        orElseGet(null);
    }
    public T orElseGet(T defaultValue){
        return toOptional()
                        .orElse(defaultValue);
    }


    public Optional<T> toOptional(){
        return Optional.ofNullable(function)
                .map(fn->fn.getValue());
    }


    public <L, E extends Throwable> Filter<L> then(SupplierWithException<L, E> method) {

        return (Filter<L>) this;

    }

    public <L, E extends Throwable> Filter<L> thenDo(VoidConsumer val) {


        return then(()->{
            val.accept();
            return null;
        });

    }
    public <L, E extends Throwable> Filter<L> elseDo(VoidConsumer val) {


        return (Filter<L>) this;

    }
    public <L, E extends Throwable> Filter<L> then(L val) {

        return then(()->val);

    }



    public void displayFinalValue(){

    }

    public  <L, E extends Throwable> Filter<T> elseThen(TryBlock<T> tryBlock){
        return this;
    }

    public  <L, E extends Throwable> Filter<T> elseThen(T val){
        return elseThen(()->val);
    }



    public Filter<?> elseIf(Supplier<Boolean> method) {
        return this;
    }

    public <L, E extends Throwable> Filter<T> elseThen(SupplierWithException<T, E> method) {
        return this;

    }


    public  <L, E extends Throwable> Filter<T> then(TryBlock<T> tryBlock){
        return this;
    };
}
