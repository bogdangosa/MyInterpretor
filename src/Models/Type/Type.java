package Models.Type;

import Models.Value.Value;

public interface Type {
    Value defaultValue();
    Type deepCopy();
    String getType();
}
