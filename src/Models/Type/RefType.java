package Models.Type;

import Models.Value.Value;

public class RefType implements Type {
    @Override
    public Value defaultValue() {
        return null;
    }

    @Override
    public Type deepCopy() {
        return null;
    }

    @Override
    public String getType() {
        return "";
    }
}
