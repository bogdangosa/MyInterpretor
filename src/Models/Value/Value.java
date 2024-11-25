package Models.Value;

import Models.Type.Type;

public interface Value {
    Type getType();
    Value deepCopy();
    boolean sameTypeAs(Type type);
}
