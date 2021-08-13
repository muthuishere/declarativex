package declarativex.lazy.models;

public class LazyRequest {
    Operation operation;
    Object object;

    public LazyRequest(Operation operation, Object object) {
        this.operation = operation;
        this.object = object;
    }

    public Operation getOperation() {
        return operation;
    }

    public Object getValue() {
        return object;
    }
}
