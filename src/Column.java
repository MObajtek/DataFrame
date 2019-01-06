import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Column {
    private String colname;
    private ArrayList<Value> data;
    private java.lang.Integer size;
    private Class<? extends Value> type;

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
            System.out.println("Data type: " + data.getClass() + "\nThis column type: "+ this.getType());
            throw new IllegalArgumentException("Data type is not compatible with data types in this Column");
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
        this.type = type;
    }

    public java.lang.Integer getSize() {
        return size;
    }

    public void setSize(java.lang.Integer size) {
        this.size = size;
    }

    public String getColname() {
        return colname;
    }

    public void setColname(String colname) {
        this.colname = colname;
    }
}
