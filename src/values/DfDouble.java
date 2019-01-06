public class DfDouble extends Value {
    private double number;

    public DfDouble(double number){
        this.number = number;
    }

    public DfDouble(String string){
        this.number = Double.valueOf(string);;
    }


    @Override
    public String toString() {
//        return String.valueOf(this.number);
        return "" + this.number;
    }

    @Override
    public Value add(Value value) {
        if (value instanceof DfInteger){
            return new DfInteger((int)this.number + ((DfInteger)value).getNumber());
        }
        else if (value instanceof DfDouble){
            return new DfDouble(this.number + ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfFloat((float)this.number + ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats and Doubles can be addends");
        }
    }

    @Override
    public Value sub(Value value) {
        if (value instanceof DfInteger){
            return new DfInteger((int)this.number - ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return new DfDouble(this.number - ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfFloat((float)this.number - ((DfFloat)value).getNumber());
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
            return new DfDouble(this.number * ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfFloat((float)this.number * ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats or Doubles can be multipliers");
        }
    }

    @Override
    public Value div(Value value) {
        if (value instanceof DfInteger){
            return new DfInteger((int)this.number  / ((DfInteger)value).getNumber());
        }
        else if(value instanceof DfDouble){
            return new DfDouble(this.number / ((DfDouble)value).getNumber());
        }
        else if (value instanceof DfFloat){
            return new DfFloat((float)this.number / ((DfFloat)value).getNumber());
        }
        else{
            throw new IllegalArgumentException("Only Integers, Floats or Doubles can be divisors");
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
    // a.lt(b)  ==  a < b
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
        return java.lang.Double.valueOf(number).hashCode();
    }

    @Override
    public Value create(String s) {
        return new DfDouble(java.lang.Double.parseDouble(s));
    }

    public double getNumber() {
        return number;
    }

    @Override
    public Value clone(Value value) {
        return new DfDouble(((DfDouble)value).getNumber());
    }

}
