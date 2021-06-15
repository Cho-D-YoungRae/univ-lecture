import java.util.ArrayList;
import java.util.Arrays;

import java.sql.*;
import com.ibatis.common.jdbc.ScriptRunner;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;



public class MadangDB implements DataBase {
	Connection conn;
	ScriptRunner scriptRunner;
	Path sqlDir;
	
	public MadangDB() {
		sqlDir = Paths.get("MySQL");
		String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
		String userid = "madang";
		String pwd = "madang";	// madang으로 변경해야함 
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException err) {
			err.printStackTrace();
		}

		try { /* 데이터베이스를 연결하는 과정 */
			System.out.println("데이터베이스 연결 준비...");
			conn = DriverManager.getConnection(url, userid, pwd);
			System.out.println("데이터베이스 연결 성공");
		} catch (SQLException err) {
			err.printStackTrace();
		}
		
		scriptRunner = new ScriptRunner(conn, false, false);
	}

	@Override
	public void initDB() {
		System.out.println("initDB...");
		try (InputStreamReader createTblSql = new InputStreamReader(
				new FileInputStream(sqlDir.resolve("init_tbl.sql").toString()));
			 InputStreamReader insertDataSql = new InputStreamReader(
				new FileInputStream(sqlDir.resolve("init_data.sql").toString()))){
			
			scriptRunner.runScript(createTblSql);
			scriptRunner.runScript(insertDataSql);
		} catch (IOException err) {
			err.printStackTrace();
		} catch (SQLException err) {
			err.printStackTrace();
		}
		System.out.println("finish initDB");
	}

	@Override
	public String[] executeQuery(String sql) throws SQLException{
		ArrayList<String> rowList = new ArrayList<>();
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			int numCols = rs.getMetaData().getColumnCount() + 1;
			while(rs.next()) {
				String row = "";
				for (int i=1; i<numCols; i++) {
					row = row + " " + rs.getString(i);
				}
				rowList.add(row);
			}
		} 
		
		int numData = rowList.size();
		String[] rowArr = Arrays.copyOf(rowList.toArray(), numData, String[].class);
		return rowArr;
	}
	
	@Override
	public boolean execute(String sql) throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			return stmt.execute(sql);
		}
	}
	
	@Override
	public String getColNamesOfSql(String sql) throws SQLException {
		String colNames = "";
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numCols = rsmd.getColumnCount() + 1;
			
			for (int i=1; i<numCols; i++) {
				colNames += rsmd.getColumnName(i) + " ";
			}
		} 
		return colNames;
	}
	
	@Override
	public String getColNamesOfTbl(String tblName) throws SQLException {
		String sql = String.format("select * from %s", tblName);
		String colNames = "";
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numCols = rsmd.getColumnCount() + 1;
			
			for (int i=1; i<numCols; i++) {
				colNames += rsmd.getColumnName(i) + " ";
			}
		}
		
		return colNames;
	}
}
