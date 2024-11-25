package Models.Type;

import Models.Value.BoolValue;
import Models.Value.Value;

public class BoolType implements Type {
    String type = "Bool";

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return "Bool";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoolType boolType) {
            return boolType.type.equals(type);
        }
        return false;
    }

    @Override
    public String getType() {
        return "Bool";
    }
}
