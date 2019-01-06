public class DfString extends Value {
    private String string;

    public DfString(String string){
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public Value add(Value value) {
        if (value instanceof DfString){
            return new DfString(this.string + ((DfString) value).getString());
        }
        else{
            return new DfString("null");
        }
    }

    @Override
    public Value sub(Value value) {
        return new DfString("null");
    }

    @Override
    public Value mul(Value value) {
        if (value instanceof DfInteger){
            DfString result = new DfString(this.string);
            for (int i = 0; i < ((DfInteger) value).getNumber()-1; i++) {
                result.add(new DfString(this.string));
            }
            return result;
        }
        else{
            return new DfString("null");
        }
    }

    @Override
    public Value div(Value value) {
        return new DfString("null");
    }

    @Override
    public Value pow(Value value) {
        return new DfString("null");
    }

    @Override
    public boolean eq(Value value) {
        if (value instanceof DfString) {
            return this.string.equals(((DfString) value).getString());
        }
        else {
            return false;
        }
    }

    @Override
    public boolean lte(Value value) {
        return false;
    }

    @Override
    public boolean gte(Value value) {
        return false;
    }

    @Override
    public boolean lt(Value value) {
        return false;
    }

    @Override
    public boolean gt(Value value) {
        return false;
    }

    @Override
    public boolean neq(Value value) {
        return !this.eq(value);
    }

    @Override
    public boolean equals(Object other) {
        return this.eq((Value)other);
    }

    @Override
    public int hashCode() {
        return String.valueOf(string).hashCode();
    }


    @Override
    public Value create(String s) {
        return new DfString(s);
    }

    public String getString() {
        return string;
    }

    @Override
    public Value clone(Value value) {
        return new DfString(((DfString)value).getString());
    }

}
