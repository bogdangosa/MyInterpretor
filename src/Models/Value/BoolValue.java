package Models.Value;

import Models.Type.BoolType;
import Models.Type.Type;

public class BoolValue implements Value {
    boolean value;

    public BoolValue(){
        value = false;
    }

    public BoolValue(boolean value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(value);
    }

    public boolean getValue() {
        return value;
    }


    @Override
    public boolean sameTypeAs(Type type) {
        return type.equals(this.getType());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoolValue other) {
            return this.value == other.value;
        }
        return false;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}
