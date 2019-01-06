public class DfPair extends Value{
    private int index;
    private Value data;

    public DfPair(int index, Value data){
        this.index = index;
        this.data = data;
    }

    private DfPair(DfPair pair){
        this.index = pair.index;
        this.data = pair.data;
    }

    boolean equalsIndex(int obj){
        return index == obj;
    }

    boolean equalsData(Value obj){
        return data == obj;
    }

    int getIndex() {
        return index;
    }

    Value getData() {
        return data;
    }

    public DfPair copy(){
        return new DfPair(this);
    }

    @Override
    public String toString() {
        return "DfPair(" +
                index +
                "," +
                data +
                ')';
    }

    @Override
    public Value add(Value value) {
        return new DfPair(this.index,this.data.add(value));
    }

    @Override
    public Value sub(Value value) {
        return new DfPair(this.index,this.data.sub(value));
    }

    @Override
    public Value mul(Value value) {
        return new DfPair(this.index,this.data.mul(value));
    }

    @Override
    public Value div(Value value) {
        return null;
    }

    @Override
    public Value pow(Value value) {
        return new DfPair(this.index,this.data.pow(value));
    }

    @Override
    public boolean eq(Value value) {
        if(value instanceof DfPair){
            if (((DfPair) value).getData() == this.data){
                return ((DfPair) value).getIndex() == this.index;
            }
        }
        return false;
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
        return java.lang.Integer.valueOf(index + data.hashCode()).hashCode();
    }

    @Override
    public Value create(String s) {
        String sub = s.substring(1,s.length()-1);
        return new DfPair(java.lang.Integer.parseInt(sub.split(",")[0]), data.create(sub.split(",")[1]));
    }

    @Override
    public Value clone(Value value) {
        return new DfPair(((DfPair)value).getIndex(),((DfPair)value).getData());
    }
}

