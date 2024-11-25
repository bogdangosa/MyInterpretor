package Models.Type;

import Models.Value.IntValue;
import Models.Value.StringValue;
import Models.Value.Value;

public class StringType implements Type{
    String type = "String";

    public StringType() {

    }

    @Override
    public String toString() {
        return "String";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }

    @Override
    public String getType() {
        return "String";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringType stringType) {
            return stringType.type.equals(type);
        }
        return false;
    }
}
