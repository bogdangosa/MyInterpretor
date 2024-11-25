package Models.Value;

import Models.Type.IntType;
import Models.Type.Type;

public class IntValue implements Value {
    int value;

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean sameTypeAs(Type type) {
        return type.equals(this.getType());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntValue other) {
            return this.value == other.value;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
