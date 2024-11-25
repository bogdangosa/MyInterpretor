package Models.Value;

import Models.Type.StringType;
import Models.Type.Type;

public class StringValue implements Value{
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringValue other) {
            return this.value.equals(other.value);
        }
        return false;
    }

    @Override
    public boolean sameTypeAs(Type type) {
        return type.equals(this.getType());
    }

}
