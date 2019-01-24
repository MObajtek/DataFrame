public class IllegalDataClass extends IllegalArgumentException {
    public IllegalDataClass(Value data, Column column) {
        super("Data type: " + data.getClass() + "\nThis column type: "+ column.getType() +
                "\nData type is not compatible with data types in this Column");

    }
}
