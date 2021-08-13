package declarativex.lazy;

import declarativex.interfaces.BiFunctionWithException;
import declarativex.interfaces.FunctionWithException;
import declarativex.interfaces.SupplierWithException;
import declarativex.interfaces.TriFunctionWithException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LazyBuilder<I1, I2, I3, O, E extends Throwable> {
    List<Object> methods;
    public LazyBuilder() {

    }
    public  <L, U extends Throwable> TryLazyBlock<L> from(SupplierWithException<L, U> method) {
        Objects.requireNonNull(method);
        TryLazyBlock lazyTry = new TryLazyBlock();
        lazyTry.or(method);

        return lazyTry;
    }

    public TryLazyBlock<O> with(I1 arg1, I2 arg2, I3 arg3) {
        return methods.stream()
                .peek(Objects::requireNonNull)
                .map(obj -> (TriFunctionWithException<I1, I2, I3, O, E>) obj)
                .map(ioeFunctionWithException -> getSupplierWithException(ioeFunctionWithException, arg1, arg2, arg3))
                .reduce(new TryLazyBlock(), (tryLazyBlock, oeSupplierWithException) -> tryLazyBlock.or(oeSupplierWithException),null);
    }
    public TryLazyBlock<O> with(I1 arg1, I2 arg2) {
        return methods.stream()
                .peek(Objects::requireNonNull)
                .map(obj -> (BiFunctionWithException<I1, I2,  O, E>) obj)
                .map(ioeFunctionWithException -> getSupplierWithException(ioeFunctionWithException, arg1, arg2))
                .reduce(new TryLazyBlock(), (tryLazyBlock, oeSupplierWithException) -> tryLazyBlock.or(oeSupplierWithException),null);
    }

    public TryLazyBlock<O> with(I1 arg1) {
        return methods.stream()
                .peek(Objects::requireNonNull)
                .map(obj -> (FunctionWithException<I1, O, E>) obj)
                .map(ioeFunctionWithException -> getSupplierWithException(ioeFunctionWithException, arg1))
                .reduce(new TryLazyBlock(), (tryLazyBlock, oeSupplierWithException) -> tryLazyBlock.or(oeSupplierWithException),((tryLazyBlock, tryLazyBlock2) -> tryLazyBlock));

    }


    protected SupplierWithException<O, E> getSupplierWithException(TriFunctionWithException<I1, I2, I3, O, E> ioeFunctionWithException, I1 arg1, I2 arg2, I3 arg3) {
        return () -> ioeFunctionWithException.apply(arg1, arg2, arg3);
    }

    protected SupplierWithException<O, E> getSupplierWithException(BiFunctionWithException<I1, I2, O, E> ioeFunctionWithException, I1 arg1, I2 arg2) {
        return () -> ioeFunctionWithException.apply(arg1, arg2);
    }

    protected SupplierWithException<O, E> getSupplierWithException(FunctionWithException<I1,  O, E> ioeFunctionWithException, I1 arg1) {
        return () -> ioeFunctionWithException.apply(arg1);
    }

    public  <I1, I2, I3, O, E extends Throwable> LazyBuilder<I1, I2, I3, O, E> any(FunctionWithException<I1, O, E>... methods) {

        this.methods=Arrays.asList(methods);
        return (LazyBuilder<I1, I2, I3, O, E>) this;

    }


}
