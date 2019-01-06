import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        DataFrame df1 = new DataFrame("groupby.csv",new Class[]{DfString.class, DfDate.class, DfDouble.class, DfDouble.class});
//        System.out.println(df1.groupby(new String[]{"id"}).max().toString());
//        System.out.println(df1.groupby(new String[]{"id"}).min().toString());

        DataFrame mean = df1.groupby(new String[]{"id"}).mean();

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mean.getColumns().size(); i++) {
            result.append(StringUtils.center(mean.getColumns().get(i).getColname(),21));
            if (i < mean.getColumns().size()-1){
                result.append("|");
            }
        }
        result.append("\n");
        result.append(StringUtils.repeat("-",22*mean.getColumns().size()-1));
        result.append("\n");
        for (int i = 0; i < mean.getColumns().get(0).getSize(); i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < mean.getColumns().size(); j++) {
                result.append(StringUtils.center(mean.getColumns().get(j).getData().get(i).toString(),21));
                if (j < mean.getColumns().size()-1){
                    result.append("|");
                }
            }
            result.append("\n");
        }


        System.out.println(result);

    }
}
