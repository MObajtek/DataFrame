public class Main {

    public static void main(String[] args) {
        DataFrame df1 = new DataFrame("groupby.csv",new Class[]{DfString.class, DfDate.class, DfDouble.class, DfDouble.class});
        System.out.println(df1.groupby(new String[]{"id"}).max().toString());

        Applyable ap = new Applyable() {
            @Override
            public DataFrame apply(DataFrame df) {
                // Bierze drugie rekordy z kolumn
                DataFrame result = new DataFrame();
                for (int i = 0; i < df.getColumns().size(); i++) {
                    result.addColumn(new Column(df.getColumns().get(i).getColname(),
                            df.getColumns().get(i).getType()));
                }
                for (Column column: df.getColumns()) {
                    result.getColumn(column.getColname()).addData(column.getData().get(1));
                }
                return result;
            }
        };

        System.out.println(df1.groupby(new String[]{"id"}).apply(ap).toString());


    }
}
