import java.io.*;
import java.util.ArrayList;

public class SparseDataFrame extends DataFrame {
    private ArrayList<SparseColumn> columns = new ArrayList<SparseColumn>(0);
    private boolean frozen = false;
    private String hide = "";

    SparseDataFrame(){}

    SparseDataFrame(DataFrame df,String hide){
        this.frozen = df.isFrozen();
        this.hide = hide;
        for (int i = 0; i < df.getColumns().size(); i++) {
            this.addColumn(new SparseColumn(df.getColumns().get(i).getColname(),df.getColumns().get(i).getType(),hide));
        }
        for (int i = 0; i < df.getColumns().size(); i++) {
            for (int j = 0; j < df.getColumns().get(i).getSize(); j++) {
                this.columns.get(i).addData(df.getColumns().get(i).getData().get(j));
            }
        }
    }

    SparseDataFrame(String[] colnames, Class<? extends Value>[] types, String hide){
        if (colnames.length != types.length){
            throw new IllegalArgumentException("Colnames array length does not match types array length");
        }
        for (int i = 1; i < types.length; i++) {
            if (types[i]!=types[i-1]){
                throw new IllegalArgumentException("SparseDataFrame has to have all columns of the same type");
            }
        }
        for (int i = 0; i < colnames.length; i++) {
            this.addColumn(new SparseColumn(colnames[i], types[i], hide));
        }
    }

    SparseDataFrame(String file, Class<? extends Value>[] types, String hide) {
        this.hide = hide;
        try {
            FileInputStream fstream = null;
            fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            int colNr = 0;
            if ((strLine = br.readLine()) != null) {
                for (String string : strLine.split(",")) {
                    this.columns.add(new SparseColumn(string, types[colNr], hide));
                    colNr++;
                }
            }
            while ((strLine = br.readLine()) != null) {
                colNr = 0;
                for (String string : strLine.split(",")) {
                    this.columns.get(colNr).addData(string);
                    colNr++;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid filename - file not found");
        } catch (IOException e) {
            throw new RuntimeException("I/O error");
        }

    }

    DataFrame toDense(){
        DataFrame result = new DataFrame();

        for (int i = 0; i < columns.size(); i++) {
            result.addColumn(new Column(columns.get(i).getColname(),columns.get(i).getType()));
        }
        for (int colNr = 0; colNr < columns.size(); colNr++) {
            for (int dfRowIndex = 0; dfRowIndex < columns.get(colNr).getSize(); dfRowIndex++) {
                for (int sdfRowIndex = 0; sdfRowIndex < columns.get(colNr).getData().size(); sdfRowIndex++) {
                    try {
                        if (((DfPair)columns.get(colNr).getData().get(sdfRowIndex)).getIndex() == dfRowIndex){
                            result.getColumns().get(colNr).addData(((DfPair)columns.get(colNr).getData().get(sdfRowIndex)).getData());
                            break;
                        }
                        else if (((DfPair)columns.get(colNr).getData().get(sdfRowIndex)).getIndex() > dfRowIndex){
                            result.getColumns().get(colNr).addData(hide);
                            break;
                        }
                        else {
                            continue;
                        }
                    } catch (Exception IndexOutOfBoundsException) {
                        System.out.println("\n\nindeks kolumny [colNr]: " + colNr + "\nindeks danych w zwyklej kolumnie [dfRowIndex]: " +
                                dfRowIndex + "\nindeks w kolumnie sparse [sdfRowIndex]: " + sdfRowIndex + "\nWielkosc kolumny zwyklej: " +
                                result.getColumns().get(colNr).getSize() + "\nWielkosc danych kolumny sparse: " + this.columns.get(colNr).getData().size());
                    }
                }
            }
        }
        for (int i = 0; i < columns.size(); i++) {
            while (result.getColumns().get(i).getSize() < columns.get(0).getSize()){
                result.getColumns().get(i).addData(hide);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (SparseColumn column: columns){
            result.append(column.toString());
            result.append("\n");
        }
        return result.toString();
    }

    @SuppressWarnings("Duplicates")
    private boolean addColumn(SparseColumn column){
        try{
            if (!this.columns.get(0).getSize().equals(column.getSize())){
                throw new IllegalArgumentException("Column size is not compatible with Column sizes in this DataFrame");
            }
            else{
                this.columns.add(column);
                return true;
            }
        }
        catch (Exception IndexOutOfBoundsException){
            this.columns.add(column);
            return true;
        }
    }

    public Integer size(){
        try{
            return this.columns.get(0).getSize();
        }
        catch (Exception IndexOutOfBoundsException){
            return 0;
        }
    }

    public SparseColumn getColumn(String colname){
        for (SparseColumn column: columns){
            if (column.getColname().equals(colname)){
                return column;
            }
        }
        throw new IllegalArgumentException("No column with such name in this DataFrame");
    }

    private SparseDataFrame get(String [] cols, boolean copy){
        SparseDataFrame result = new SparseDataFrame();
        if (copy) {
            for (int i = 0; i < cols.length; i++) {
                for (int j = 0; j < this.columns.size(); j++) {
                    if (cols[i].equals(this.columns.get(j).getColname())){
                        result.addColumn(this.columns.get(j));
                    }
                }
            }
            result.setFrozen(true);
        }
        else{
            for (int i = 0; i < cols.length; i++) {
                for (int j = 0; j < this.columns.size(); j++) {
                    if (cols[i].equals(this.columns.get(j).getColname())) {
                        result.addColumn(new SparseColumn(this.columns.get(j)));
                    }
                }
            }
        }
        return result;
    }

    private SparseDataFrame iloc(int i) {
        SparseDataFrame result = new SparseDataFrame();
        for (int j = 0; j < columns.size(); j++) {
            for (int k = 0; k < columns.get(j).getData().size(); k++) {
                if (((DfPair) columns.get(j).getData().get(k)).getIndex() == i) {
                    result.addColumn(new SparseColumn(
                            new DfPair(0, ((DfPair) columns.get(j).getData().get(k)).getData()),
                            columns.get(j).getColname(),
                            this.hide));
                } else if (((DfPair) columns.get(j).getData().get(k)).getIndex() >= i) {
                    result.addColumn(new SparseColumn());
                    result.getColumns().get(j).setSize(1);
                }
            }
        }
        return result;
    }

    public SparseDataFrame iloc(int from, int to) {
        SparseDataFrame result = new SparseDataFrame();
        result.hide = this.hide;
        for (SparseColumn column : this.columns) {
            result.addColumn(new SparseColumn(column));
        }
        int indexCount = 0;
        boolean copied = false;
        for (int columnNr = 0; columnNr < this.columns.size(); columnNr++) {
            for (int i = from; i < to+1; i++) {
                for (int pairNr = 0; pairNr < this.columns.get(columnNr).getData().size(); pairNr++) {
                    if (((DfPair) columns.get(columnNr).getData().get(pairNr)).getIndex() == i){
                        result.columns.get(columnNr).addData(new DfPair(indexCount,(((DfPair) columns.get(columnNr).getData().get(pairNr)).getData())));
                        indexCount ++;
                        copied = true;
                        break;
                    }
                }
                if (!copied){
                    indexCount++;
                }
                copied = false;
            }
            result.columns.get(columnNr).setSize(to-from+1);
            indexCount = 0;
        }


        return result;
    }

}