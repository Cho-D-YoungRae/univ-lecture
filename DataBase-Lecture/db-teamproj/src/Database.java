import java.sql.SQLException;
import java.sql.Statement;

public interface Database {
	public String[][] selectIdName(String loginType);
	public void initDB();
	public Statement createStatement() throws SQLException;
	public String[] selectAllFrom(String tblName);
	public String getColNamesOfTbl(String tblName);
	public String[] executeQuery(String sql) throws SQLException;
	public boolean execute(String sql) throws SQLException;
	public String getColNamesOfSql(String sql) throws SQLException;
}

