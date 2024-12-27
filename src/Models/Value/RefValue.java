package Models.Value;

import Models.Type.RefType;
import Models.Type.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public Type getType(){
        return new RefType(locationType);
    }


    public Type getInnerType(){
        return locationType;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getAddr() {return address;}

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public boolean sameTypeAs(Type type) {
        return type.equals(this.getType());
    }

    @Override
    public String toString() {
        return "("+address+", "+locationType+")";
    }
}
