import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class SparseColumn extends Column {
    private String colname;
    private ArrayList<Value> data;
    private java.lang.Integer size;
    private Class<? extends Value> type;
    private String hide;

    public SparseColumn(){
        this.colname = "";
        this.data = new ArrayList<>(0);
        this.size = 0;
        this.type = null;
        this.hide = "";
    }

    public SparseColumn(String colname, Class<? extends Value> type, String hide){
        this.colname = colname;
        this.type = type;
        this.size = 0;
        this.data = new ArrayList<>(0);
        this.hide = hide;
    }

    public SparseColumn(SparseColumn column){
        this.colname = column.getColname();
        this.size = column.getSize();
        this.data = new ArrayList<>(this.size);
        this.type = column.getType();
        this.hide = column.getHide();
        for (int i = 0; i < this.size; i++) {
            this.data.add((DfPair) (column.getData().get(i)).clone(column.getData().get(i)));
        }
    }

    public SparseColumn(DfPair data, String colname, String hide){
        this.colname = colname;
        this.type = data.getType();
        this.data = new ArrayList<Value>(0);
        if (hide.equals(data.toString(false))){
            this.size = 1;
        }
        else {
            this.data.add(data);
            this.size = 1;
        }
    }

    public SparseColumn(int size, Class<? extends Value> type, String colname, String hide){
        this.colname = colname;
        this.type = type;
        this.data = new ArrayList<Value>(0);
        this.size = size;
        this.hide = hide;
    }

    public void addData(String s){
        try {
            if (!s.equals(hide)){
                this.data.add(new DfPair(this.size,this.type.getConstructor(String.class).newInstance(s)));
            }
            this.size += 1;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void addData(Value value){
        if (!value.toString().equals(hide)){
            this.data.add(new DfPair(this.size,value));
        }
        this.size += 1;

    }


    public Integer getSize() {
        return size;
    }

    public String getColname() {
        return colname;
    }

    public Class<? extends Value> getType() {
        return type;
    }

    public ArrayList<Value> getData() {
        return data;
    }

    public String getHide() {
        return hide;
    }

    @Override
    public void setColname(String colname) {
        this.colname = colname;
    }

    @Override
    public void setData(ArrayList<Value> data) {
        this.data = data;
    }

    @Override
    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public void setType(Class<? extends Value> type) {
        this.type = type;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }

    @Override
    public String toString() {
        return "SparseColumn{" +
                "colname='" + colname + '\'' +
                ", data=" + data +
                '}';
    }
}
