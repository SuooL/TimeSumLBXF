package suool.net.timesumlbxf.db;

/**
 * Created by SuooL on 2014/10/6.
 */
public class TimeSumDBInfo {
    private static String TableNames[] = {
            "TBL_EXPENDITURE_CATEGORY",        // 时间花费类别数据库表
            "TBL_EXPENDITURE_SUB_CATEGORY",    // 时间花费子类别数据库表
            "TBL_EXPENDITURE"                  // 时间花费数据库表
    };//表名


    private static String FieldNames[][] = {
            {"ID","NAME"},
            {"ID","NAME","PARENT_CATEGORY_ID"},
            {"ID", "AMOUNT", "EXPENDITURE_CATEGORY_ID",
                    "EXPENDITURE_SUB_CATEGORY_ID","DATE", "TIME","MEMO"}
    };//字段名

    private static String FieldTypes[][] = {
            {"INTEGER PRIMARY KEY AUTOINCREMENT","text"},
            {"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT","INTEGER"},
            {"INTEGER PRIMARY KEY AUTOINCREMENT","DOUBLE",
                    "INTEGER","INTEGER","TEXT","TEXT","TEXT"}
    };//字段类型

    public TimeSumDBInfo() {
        // TODO Auto-generated constructor stub
    }

    public static String[] getTableNames() {
        return TableNames;
    }

    public static String[][] getFieldNames() {
        return FieldNames;
    }

    public static String[][] getFieldTypes() {
        return FieldTypes;
    }
}
