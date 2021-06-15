import java.sql.SQLException;

public interface DataBase {
	public void initDB();
	public String[] executeQuery(String sql) throws SQLException;
	public boolean execute(String sql) throws SQLException;
	public String getColNamesOfSql(String sql) throws SQLException;
	public String getColNamesOfTbl(String tblName) throws SQLException;
}

