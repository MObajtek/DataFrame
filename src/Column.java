import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Column {
    private String colname;
    private ArrayList<Value> data;
    private java.lang.Integer size;
    private Class<? extends Value> type;

    public Column(){
        this.colname = "";
        this.data = new ArrayList<>(0);
        this.size = 0;
        this.type = null;
    }

    public Column(Column column){
        this.colname = column.getColname();
        this.size = column.getSize();
        this.data = new ArrayList<>(this.size);
        this.type = column.getType();
        for (int i = 0; i < this.size; i++) {
            this.data.add((column.getData().get(i)).clone(column.getData().get(i)));
        }
    }

    public Column(Value data, String colname){
        this.colname = colname;
        this.type = data.getClass();
        this.data = new ArrayList<Value>(0);
        this.data.add(data);
        this.size = this.data.size();
    }

    public Column(ArrayList<Value> data, String colname) {
        try {
            this.colname = colname;
            this.type = data.get(0).getClass();
            this.data = data;
            this.size = this.data.size();
        } catch (Exception IndexOutOfBoundsException) {
            throw new IllegalArgumentException("Cannot make a Column object out of empty data Arraylist\n" +
                    "Try using no-arguments Column constructor");
            }
    }

    public Column(String colname, Class<? extends Value> type){
        this.colname = colname;
        this.type = type;
        this.size = 0;
        this.data = new ArrayList<>(0);
    }

    public ArrayList<Value> getData() {
        return data;
    }

    public void setData(ArrayList<Value> data) {
        this.data = data;
    }

    public void addData(Value data){
        if(this.type == data.getClass()) {
            this.data.add(data);
            this.size += 1;
        }
        else{
            throw new IllegalDataClass(data,this);
        }
    }

    public void addData(String s){
        try {
            this.data.add(this.type.getConstructor(String.class).newInstance(s));
            this.size += 1;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public Class<? extends Value> getType() {
        return type;
    }

    public void setType(Class<? extends Value> type) {
        if (!this.data.isEmpty()){
            throw new IllegalStateException("Dataframe has to be empty while setting it's type");
        }
        else {
            this.type = type;
        }
    }

    public Value max(){
        Value max = this.data.get(0);
        for (int i = 1; i < this.size; i++) {
            if (this.data.get(i).gt(max)) {
                max = this.data.get(i);
            }
        }
        return max;
    }

    public Value min(){
        Value min = this.data.get(0);
        for (int i = 1; i < this.size; i++) {
            if (this.data.get(i).lt(min)) {
                min = this.data.get(i);
            }
        }
        return min;
    }

    public Value mean(){
        Value sum = this.data.get(0);
        for (int i = 1; i < size; i++) {
            sum = sum.add(this.data.get(i));
        }
        return sum.div(new DfInteger(size));
    }

    public Value std(){
        Value mean = this.mean();
        Value sum = new DfDouble(0);
        for (int i = 0; i < size; i++) {
            sum = sum.add(data.get(i).sub(mean).pow(new DfInteger(2)));
        }
        return sum.div(new DfInteger(size)).pow(new DfDouble(0.5));
    }

    public Value var(){
        Value mean = this.mean();
        Value sum = new DfDouble(0);
        for (int i = 0; i < size; i++) {
            sum = sum.add(data.get(i).sub(mean).pow(new DfInteger(2)));
        }
        return sum.div(new DfInteger(size));
    }

    public Value sum(){
        Value sum = new DfDouble(0);
        for (int i = 0; i < size; i++) {
            sum = sum.add(data.get(i));
        }
        return sum;
    }

    // Dodawanie dwóch kolumn:
    // dodajemy i-te miejsce z this.data do i-tego miejsca z column.getData.get(i)
    // zwracamy kolumnę wynikową
    public Column add(Column column){
        if(!this.size.equals(column.getSize())){
            throw new IllegalArgumentException("Columns have to be the same size");
        }
        if (this.data.get(0).add(column.getData().get(0)) == null){
            throw new IllegalArgumentException("Invalid column types; Addition not possible");
        }
        Column result = new Column(this.getColname(),this.type);
        for (int i = 0; i < this.size; i++) {
            result.addData(this.data.get(i).add(column.getData().get(i)));
        }
        return result;
    }

    public Column sub(Column column){
        if(!this.size.equals(column.getSize())){
            throw new IllegalArgumentException("Columns have to be the same size");
        }
        if (this.data.get(0).sub(column.getData().get(0)) == null){
            throw new IllegalArgumentException("Invalid column types; Subtraction not possible");
        }
        Column result = new Column(this.getColname(),this.type);
        for (int i = 0; i < this.size; i++) {
            result.addData(this.data.get(i).sub(column.getData().get(i)));
        }
        return result;
    }

    public Column mul(Column column){
        if(!this.size.equals(column.getSize())){
            throw new IllegalArgumentException("Columns have to be the same size");
        }
        if (this.data.get(0).mul(column.getData().get(0)) == null){
            throw new IllegalArgumentException("Invalid column types; Multiplication not possible");
        }
        Column result = new Column(this.getColname(),this.type);
        for (int i = 0; i < this.size; i++) {
            result.addData(this.data.get(i).mul(column.getData().get(i)));
        }
        return result;
    }

    public Column div(Column column){
        if(!this.size.equals(column.getSize())){
            throw new IllegalArgumentException("Columns have to be the same size");
        }
        if (this.data.get(0).div(column.getData().get(0)) == null){
            throw new IllegalArgumentException("Invalid column types; Division not possible");
        }
        Column result = new Column(this.getColname(),this.type);
        for (int i = 0; i < this.size; i++) {
            result.addData(this.data.get(i).div(column.getData().get(i)));
        }
        return result;
    }

    public Column pow(Column column){
        if(!this.size.equals(column.getSize())){
            throw new IllegalArgumentException("Columns have to be the same size");
        }
        if (this.data.get(0).pow(column.getData().get(0)) == null){
            throw new IllegalArgumentException("Invalid column types; Exponentiation not possible");
        }
        Column result = new Column(this.getColname(),this.type);
        for (int i = 0; i < this.size; i++) {
            result.addData(this.data.get(i).pow(column.getData().get(i)));
        }
        return result;
    }

    public Column add(Value value){
        if (this.data.get(0).add(value) == null){
            throw new IllegalArgumentException("Invalid column types; Addition not possible");
        }
        Column result = new Column(this.getColname(),this.type);
        for (int i = 0; i < this.size; i++) {
            result.addData(this.data.get(i).add(value));
        }
        return result;
    }

    public Column sub(Value value){
        if (this.data.get(0).sub(value) == null){
            throw new IllegalArgumentException("Invalid column types; Subtraction not possible");
        }
        Column result = new Column(this.getColname(),this.type);
        for (int i = 0; i < this.size; i++) {
            result.addData(this.data.get(i).sub(value));
        }
        return result;
    }

    public Column mul(Value value){
        if (this.data.get(0).mul(value) == null){
            throw new IllegalArgumentException("Invalid column types; Multiplication not possible");
        }
        Column result = new Column(this.getColname(),this.type);
        for (int i = 0; i < this.size; i++) {
            result.addData(this.data.get(i).mul(value));
        }
        return result;
    }

    public Column div(Value value){
        if (this.data.get(0).div(value) == null){
            throw new IllegalArgumentException("Invalid column types; Division not possible");
        }
        Column result = new Column(this.getColname(),this.type);
        for (int i = 0; i < this.size; i++) {
            result.addData(this.data.get(i).div(value));
        }
        return result;
    }

    public Column pow(Value value){
        if (this.data.get(0).pow(value) == null){
            throw new IllegalArgumentException("Invalid column types; Exponentiation not possible");
        }
        Column result = new Column(this.getColname(),this.type);
        for (int i = 0; i < this.size; i++) {
            result.addData(this.data.get(i).pow(value));
        }
        return result;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getColname() {
        return colname;
    }

    public void setColname(String colname) {
        this.colname = colname;
    }
}
