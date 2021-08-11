package com.deemwar.functors.path;

import com.deemwar.functors.Try;

import java.util.function.Consumer;

public class Success<T> extends Try<T> {

    public Success(T value) {
        super(value);
    }

    @Override
    public Try<T> peek(Consumer<T> s) {
        s.accept(this.getValue());
        return this;
    }

}
