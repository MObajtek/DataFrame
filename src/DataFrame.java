import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DataFrame {
    private ArrayList<Column> columns = new ArrayList<Column>(0);
    private boolean frozen = false;

    DataFrame() {

    }

    DataFrame(String[] colnames, Class<? extends Value>[] types){
        for (int i = 0; i < colnames.length; i++) {
            this.addColumn(new Column(colnames[i], types[i]));
        }
    }

    DataFrame(String file, Class<? extends Value>[] types) {
        try {
            FileInputStream fstream = null;
            fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            int colNr = 0;
            if ((strLine = br.readLine()) != null) {
                for (String string : strLine.split(",")) {
                    this.columns.add(new Column(string, types[colNr]));
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

    DataFrame(String file, Class<? extends Value>[] types, boolean header){
        try {
            FileInputStream fstream = null;
            fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            int colNr = 0;
            if (header) {
                if ((strLine = br.readLine()) != null) {
                    for (String string : strLine.split(",")) {
                        this.columns.add(new Column(string, types[colNr]));
                        colNr++;
                    }
                }
            }
            else {
                for (int i = 0; i < types.length; i++) {
                    this.columns.add(new Column("Kol" + i,types[i]));
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

    public Integer size(){
        try{
            return this.columns.get(0).getSize();
        }
        catch (Exception IndexOutOfBoundsException){
            return 0;
        }
    }

    private DataFrame iloc(int i){
        DataFrame result = new DataFrame();
        for (int j = 0; j < columns.size(); j++) {
            try {
                result.addColumn(new Column(this.columns.get(j).getData().get(i), this.columns.get(j).getColname()));
            }
            catch (Exception IndexOutOfBoundsException) {
                throw new IllegalArgumentException("Invalid row index");
            }
        }
        return result;
    }

    private DataFrame iloc(int from, int to) {
        DataFrame result = new DataFrame();
        for (int i = 0; i< columns.size(); i++) {
            try {
                result.addColumn(new Column(this.columns.get(i).getColname(),
                        this.columns.get(i).getType()));
            }
            catch (Exception IndexOutOfBoundsExeption) {
                throw new IllegalStateException("DataFrame's Columns are currently empty");
            }
            for (int j = from; j < to+1; j++) {
                try {
                    result.getColumn(i).addData(this.columns.get(i).getData().get(j));
                }
                catch (Exception IndexOutOfBoundsException) {
                    throw new IllegalArgumentException("Invalid row indices");
                }
            }
        }
        return result;
    }

    // DLA COPY == TRUE KOPIA PLYTKA
    private DataFrame get(String [] cols, boolean copy){
        DataFrame result = new DataFrame();
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
                    result.addColumn(new Column(this.columns.get(j)));
                    }
                }
            }
        }
        return result;
    }

    public boolean addColumn(Column column){
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

    public Column getColumn(int i){
        try {
            return this.columns.get(i);
        }
        catch (Exception IndexOutOfBoundsException){
            throw new IllegalArgumentException("Invalid column index");
        }
    }

    public Column getColumn(String colname){
        for (Column column: columns){
            if (column.getColname().equals(colname)){
                return column;
            }
        }
        throw new IllegalArgumentException("No column with such name in this DataFrame");
    }

    private void join(DataFrame dataFrame){
        if (frozen){
            throw new IllegalStateException("Cannot join/add data to frozen DataFrame");
        }
        if (this.columns.size() != dataFrame.columns.size()){
            throw new IllegalArgumentException("Cannot join DataFrames of different sizes");
        }
        if (this.columns.size() == 0){
            throw new IllegalStateException("Cannot join empty DataFrames");
        }
        for (int i = 0; i < this.columns.size(); i++) {
            if (this.columns.get(i).getType() != dataFrame.getColumn(i).getType()){
                throw new IllegalArgumentException("Cannot join DataFrames with different column types");
            }
        }
        for (int i = 0; i < dataFrame.columns.get(0).getSize(); i++) {
            for (int j = 0; j < this.columns.size(); j++) {
                this.columns.get(j).addData(dataFrame.columns.get(j).getData().get(i));
            }
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        try {
            for (int i = 0; i < columns.size(); i++) {
//                result.append(columns.get(i).getColname());
                result.append(StringUtils.center(columns.get(i).getColname(),25));
                if (i < columns.size()-1){
                    result.append("|");
                }
            }
            result.append("\n");
            result.append(StringUtils.repeat("-",26*columns.size()-1));
//            result.append("------------------------------------------------------------------");
            result.append("\n");
            for (int i = 0; i < columns.get(0).getSize(); i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < columns.size(); j++) {
//                    result.append(this.columns.get(j).getData().get(i).toString());
                    result.append(StringUtils.center(this.columns.get(j).getData().get(i).toString(),25));
                    if (j < columns.size()-1){
                        result.append("|");
                    }
                }
                result.append("\n");
            }
            return result.toString();
        } catch (Exception IndexOutOfBoundsException) {
            throw new IllegalStateException("DataFrame is empty");
        }
    }

// Drukuje n pierwszych rekordów
    public String toString(int n) {
        StringBuilder result = new StringBuilder();
        try {
            for (int i = 0; i < columns.size(); i++) {
                result.append(columns.get(i).getColname());
//                result.append(StringUtils.center(columns.get(i).getColname(),21));
                if (i < columns.size()-1){
                    result.append("|");
                }
            }
            result.append("\n");
//            result.append(StringUtils.repeat("-",22*columns.size()-1));
            result.append("------------------------------------------------------------------");
            result.append("\n");
            for (int i = 0; i < n; i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < columns.size(); j++) {
                    result.append(this.columns.get(j).getData().get(i).toString());
//                    result.append(StringUtils.center(this.columns.get(j).getData().get(i).toString(),21));
                    if (j < columns.size()-1){
                        result.append("|");
                    }
                }
                result.append("\n");
            }
            return result.toString();
        } catch (Exception IndexOutOfBoundsException) {
            throw new IllegalStateException("DataFrame is empty");
        }
    }

    public DfMap groupby(String[] colnames){
        DfMap result = new DfMap();
        try {
            for (int i = 0; i < columns.get(0).getData().size(); i++) {
                ArrayList<Value> tmp = new ArrayList<>();
                for (int j = 0; j < colnames.length; j++    ) {
                    tmp.add(this.getColumn(colnames[j]).getData().get(i));
                }
                if (result.map.containsKey(tmp)){
                    result.map.get(tmp).join(this.iloc(i));
                }
                else {
                    result.map.put(tmp,iloc(i));
                }
            }
            return result;
        } catch (Exception IndexOutOfBoundsException) {
            throw new IllegalStateException("Cannot execute groupby on empty Dataframe");
        }
    }

    public class DfMap implements Groupby{
        private HashMap<ArrayList<Value>,DataFrame> map;

        DfMap(){
            map = new HashMap<>();
        }

        @Override
        public DataFrame max() {
            DataFrame result = new DataFrame();
            for (int i = 0; i < columns.size(); i++) {
                result.addColumn(new Column(columns.get(i).getColname(),columns.get(i).getType()));
            }
            for(Map.Entry<ArrayList<Value>, DataFrame> entry : map.entrySet()) {
                for (Column column: entry.getValue().columns){
                    boolean exists = false;
                    for (int i = 0; i < entry.getKey().size(); i++) {
                        if (column.getData().get(0).equals(entry.getKey().get(i))){
                            result.getColumn(column.getColname()).addData(column.getData().get(0));
                            exists = true;
                        }
                    }
                    if (exists){
                        continue;
                    }
                    else {
                        result.getColumn(column.getColname()).addData(column.max());
                    }
                }
            }
            return result;
        }

        @Override
        public DataFrame min() {
            DataFrame result = new DataFrame();
            for (int i = 0; i < columns.size(); i++) {
                result.addColumn(new Column(columns.get(i).getColname(),columns.get(i).getType()));
            }
            for(Map.Entry<ArrayList<Value>, DataFrame> entry : map.entrySet()) {
                for (Column column: entry.getValue().columns){
                    boolean exists = false;
                    for (int i = 0; i < entry.getKey().size(); i++) {
                        if (column.getData().get(0).equals(entry.getKey().get(i))){
                            result.getColumn(column.getColname()).addData(column.getData().get(0));
                            exists = true;
                        }
                    }
                    if (exists){
                        continue;
                    }
                    else {
                        result.getColumn(column.getColname()).addData(column.min());
                    }
                }
            }
            return result;
        }

        @Override
        public DataFrame mean() {
            DataFrame result = new DataFrame();
            for (int i = 0; i < columns.size(); i++) {
                result.addColumn(new Column(columns.get(i).getColname(),columns.get(i).getType()));
            }
            for(Map.Entry<ArrayList<Value>, DataFrame> entry : map.entrySet()) {
                for (Column column : entry.getValue().columns) {
                    boolean exists = false;
                    for (int i = 0; i < entry.getKey().size(); i++) {
                        if (column.getData().get(0).equals(entry.getKey().get(i))){
                            result.getColumn(column.getColname()).addData(column.getData().get(0));
                            exists = true;
                        }
                    }
                    if (exists){
                        continue;
                    }
                    else if(column.getData().get(0).div(new DfInteger(0)) == null){
                        if (result.getColumn(column.getColname()).getData().isEmpty()) {
                            result.getColumn(column.getColname()).setType(DfString.class);
                        }
                        result.getColumn(column.getColname()).addData("null");
                    }
                    else {
                        Value sum = column.getData().get(0);
                        for (int i = 1; i < column.getSize(); i++) {
                            sum = sum.add(column.getData().get(i));
                        }
                        sum = sum.div(new DfInteger(column.getSize()));
                        if (result.getColumn(column.getColname()).getData().isEmpty()) {
                            result.getColumn(column.getColname()).setType(DfDouble.class);
                        }
                        result.getColumn(column.getColname()).addData(sum);
                    }
                }
            }
            return result;


        }

        @Override
        public DataFrame std() {
            DataFrame result = new DataFrame();
            for (int i = 0; i < columns.size(); i++) {
                result.addColumn(new Column(columns.get(i).getColname(),columns.get(i).getType()));
            }
            for(Map.Entry<ArrayList<Value>, DataFrame> entry : map.entrySet()) {
                for (Column column : entry.getValue().columns) {
                    boolean exists = false;
                    for (int i = 0; i < entry.getKey().size(); i++) {
                        if (column.getData().get(0).equals(entry.getKey().get(i))){
                            result.getColumn(column.getColname()).addData(column.getData().get(0));
                            exists = true;
                        }
                    }
                    if (exists){
                        continue;
                    }
                    else if(column.getData().get(0).div(new DfInteger(0)) == null){
                        if (result.getColumn(column.getColname()).getData().isEmpty()) {
                            result.getColumn(column.getColname()).setType(DfString.class);
                        }
                        result.getColumn(column.getColname()).addData("null");
                    }
                    else {
                        if (result.getColumn(column.getColname()).getData().isEmpty()) {
                            result.getColumn(column.getColname()).setType(DfDouble.class);
                        }
                        result.getColumn(column.getColname()).addData(column.std());
                    }
                }
            }
            return result;
        }

        @Override
        public DataFrame sum() {
            DataFrame result = new DataFrame();
            for (int i = 0; i < columns.size(); i++) {
                result.addColumn(new Column(columns.get(i).getColname(),columns.get(i).getType()));
            }
            for(Map.Entry<ArrayList<Value>, DataFrame> entry : map.entrySet()) {
                for (Column column : entry.getValue().columns) {
                    boolean exists = false;
                    for (int i = 0; i < entry.getKey().size(); i++) {
                        if (column.getData().get(0).equals(entry.getKey().get(i))){
                            result.getColumn(column.getColname()).addData(column.getData().get(0));
                            exists = true;
                        }
                    }
                    if (exists){
                        continue;
                    }
                    else if(column.getData().get(0).add(new DfInteger(0)) == null){
                        if (result.getColumn(column.getColname()).getData().isEmpty()) {
                            result.getColumn(column.getColname()).setType(DfString.class);
                        }
                        result.getColumn(column.getColname()).addData("null");
                    }
                    else {
                        Value sum = column.getData().get(0);
                        for (int i = 1; i < column.getSize(); i++) {
                            sum = sum.add(column.getData().get(i));
                        }
                        if (result.getColumn(column.getColname()).getData().isEmpty()) {
                            result.getColumn(column.getColname()).setType(sum.getClass());
                        }
                        result.getColumn(column.getColname()).addData(sum);
                    }
                }
            }
            return result;
        }

        @Override
        public DataFrame var() {
            DataFrame result = new DataFrame();
            for (int i = 0; i < columns.size(); i++) {
                result.addColumn(new Column(columns.get(i).getColname(),columns.get(i).getType()));
            }
            for(Map.Entry<ArrayList<Value>, DataFrame> entry : map.entrySet()) {
                for (Column column : entry.getValue().columns) {
                    boolean exists = false;
                    for (int i = 0; i < entry.getKey().size(); i++) {
                        if (column.getData().get(0).equals(entry.getKey().get(i))){
                            result.getColumn(column.getColname()).addData(column.getData().get(0));
                            exists = true;
                        }
                    }
                    if (exists){
                        continue;
                    }
                    else if(column.getData().get(0).div(new DfInteger(0)) == null){
                        if (result.getColumn(column.getColname()).getData().isEmpty()) {
                            result.getColumn(column.getColname()).setType(DfString.class);
                        }
                        result.getColumn(column.getColname()).addData("null");
                    }
                    else {
                        if (result.getColumn(column.getColname()).getData().isEmpty()) {
                            result.getColumn(column.getColname()).setType(DfDouble.class);
                        }
                        result.getColumn(column.getColname()).addData(column.var());
                    }
                }
            }
            return result;
        }

        @Override
        public DataFrame apply(Applyable applyable) {
            DataFrame result = new DataFrame();
            for (int i = 0; i < columns.size(); i++) {
                result.addColumn(new Column(columns.get(i).getColname(),columns.get(i).getType()));
            }
            for(Map.Entry<ArrayList<Value>, DataFrame> entry : map.entrySet()) {
                result.join(applyable.apply(entry.getValue()));
            }
            return result;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            for(Map.Entry<ArrayList<Value>, DataFrame> entry : map.entrySet()) {
                result.append(entry.getKey());
                result.append(":\n");
                result.append(entry.getValue().toString(5));
                result.append("\n-------------------------\n");
            }
            return result.toString();
        }
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<Column> columns) {
        this.columns = columns;
    }
}
