package Models.Type;

import Models.Value.IntValue;
import Models.Value.Value;

public class IntType implements Type {
    String type = "Int";

    public IntType() {

    }

    @Override
    public String toString() {
        return "Int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntType intType) {
            return intType.type.equals(type);
        }
        return false;
    }

    @Override
    public String getType() {
        return "Int";
    }
}
