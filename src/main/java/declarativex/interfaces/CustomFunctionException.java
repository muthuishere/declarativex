package declarativex.interfaces;

@FunctionalInterface
public
interface CustomFunctionException<I extends Throwable, O, E extends Throwable> {
    O apply(I value) throws E;
}
