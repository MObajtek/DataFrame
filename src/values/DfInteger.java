public class DfInteger extends Value {
    private int number;

    public DfInteger(int number){
        this.number = number;
    }

    public DfInteger(String string){
        this.number = java.lang.Integer.parseInt(string);
    }

    @Override
    public String toString() {
        return "" + number;
    }

    @Override
    public Value add(Value value) {
        if (value instanceof DfInteger){
            return new DfInteger(this.number + ((DfInteger)value).getNumber());
        }
        else if (value instanceof DfDouble){
            return new DfDouble((double)(this.number) + ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfFloat((float)this.number + ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers and Doubles can be addends");
        }
    }

    @Override
    public Value sub(Value value) {
        if (value instanceof DfInteger){
            return new DfInteger(this.number - ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return new DfDouble((double)(this.number) - ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfFloat((float)this.number - ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers and Doubles can be subtrahends");
        }
    }

    @Override
    public Value mul(Value value) {
        if (value instanceof DfInteger){
            return new DfInteger(this.number * ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return new DfDouble((double)(this.number) * ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfFloat((float)this.number * ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers or Doubles can be multipliers");
        }
    }

    @Override
    public Value div(Value value) {
        if (value instanceof DfInteger){
            return new DfInteger(this.number / ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return new DfDouble((double)(this.number) / ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfFloat((float)this.number / ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers or Doubles can be divisors");
        }
    }

    @Override
    public Value pow(Value value) {
        if (value instanceof DfInteger){
            return new DfInteger((int)java.lang.Math.pow(this.number, ((DfInteger)value).getNumber()));
        }
        else if(value instanceof DfDouble){
            return new DfDouble(java.lang.Math.pow(this.number, ((DfDouble)value).getNumber()));
        }
        else if(value instanceof DfFloat){
            return new DfFloat((float)java.lang.Math.pow(this.number, ((DfFloat)value).getNumber()));
        }
        else{
            throw new IllegalArgumentException("Only a DfInteger, DfFloat or DfDouble can be an exponent");
        }
    }

    @Override
    public boolean eq(Value value) {
        if (value instanceof DfInteger){
            return (this.number == ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return (this.number == ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return (this.number == ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats and/or Doubles can be compared");
        }
    }

    @Override
    public boolean lte(Value value) {
        if (value instanceof DfInteger){
            return (this.number <= ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return (this.number <= ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return (this.number <= ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats and/or Doubles can be compared");
        }
    }

    @Override
    public boolean gte(Value value) {
        if (value instanceof DfInteger){
            return (this.number >= ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return (this.number >= ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return (this.number >= ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats and/or Doubles can be compared");
        }
    }

    @Override
    public boolean lt(Value value) {
        if (value instanceof DfInteger){
            return (this.number < ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return (this.number < ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return (this.number < ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats and/or Doubles can be compared");
        }
    }

    @Override
    public boolean gt(Value value) {
        if (value instanceof DfInteger){
            return (this.number > ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return (this.number > ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return (this.number > ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats and/or Doubles can be compared");
        }
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
        return java.lang.Integer.valueOf(number).hashCode();
    }


    @Override
    public Value create(String s) {
        return new DfInteger(java.lang.Integer.parseInt(s));
    }

    @Override
    public Value clone(Value value) {
        return new DfInteger(((DfInteger)value).getNumber());
    }

    public int getNumber() {
        return number;
    }

}
