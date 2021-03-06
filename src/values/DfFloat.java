public class DfFloat extends Value {
    private float number;

    public DfFloat(float number){
        this.number = number;
    }

    public DfFloat(String string){
        try {
            this.number = Float.parseFloat(string);
        } catch (Exception NumberFormatException) {
            throw new IllegalArgumentException("Invalid Float format");
        }
    }


    @Override
    public String toString() {
        return String.valueOf(this.number);
    }

    @Override
    public Value add(Value value) {
        if (value instanceof DfInteger){
            return new DfFloat(this.number + ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return new DfDouble((double)this.number + ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfFloat(this.number + ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats and Doubles can be addends");
        }
    }

    @Override
    public Value sub(Value value) {
        if (value instanceof DfInteger){
            return new DfFloat(this.number - ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return new DfDouble((double)this.number - ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfFloat(this.number - ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats and Doubles can be subtrahends");
        }
    }

    @Override
    public Value mul(Value value) {
        if (value instanceof DfInteger){
            return new DfInteger((int)this.number * ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return new DfDouble((double)this.number * ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfFloat(this.number * ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats or Doubles can be multipliers");
        }
    }

    @Override
    public Value div(Value value) {
        if (value instanceof DfInteger){
            return new DfDouble(this.number  / ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return new DfDouble(this.number / ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfDouble(this.number / ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats or Doubles can be divisors");
        }
    }

    @Override
    public Value pow(Value value) {
        if (value instanceof DfInteger){
            return new DfDouble(java.lang.Math.pow(this.number, ((DfInteger)value).getNumber()));
        }
        else if(value instanceof DfDouble){
            return new DfDouble(java.lang.Math.pow(this.number, ((DfDouble)value).getNumber()));
        }
        else if(value instanceof DfFloat){
            return new DfDouble(java.lang.Math.pow(this.number, ((DfFloat)value).getNumber()));
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
        if (other instanceof DfInteger){
            return (this.number == ((DfInteger)other).getNumber());
        }
        else if(other instanceof DfDouble){
            return (this.number == ((DfDouble)other).getNumber());
        }
        else if (other instanceof DfFloat){
            return (this.number == ((DfFloat)other).getNumber());
        }
        else{
            return false;
        }
    }
    @Override
    public int hashCode() {
        return java.lang.Float.valueOf(number).hashCode();
    }

    @Override
    public Value create(String s) {
        return new DfFloat(java.lang.Float.parseFloat(s));
    }

    public float getNumber() {
        return number;
    }

    @Override
    public Value clone(Value value) {
        return new DfFloat(((DfFloat)value).getNumber());
    }
}
