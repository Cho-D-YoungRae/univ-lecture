import java.util.ArrayList;
import java.util.Arrays;

import java.sql.*;
import com.ibatis.common.jdbc.ScriptRunner;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;



public class MadangDB implements Database {
	Connection conn;
	ScriptRunner scriptRunner;
	Path sqlDir;
	
	public MadangDB() {
		sqlDir = Paths.get("mySQL");
		String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
		String userid = "madang";
		String pwd = "madang";
		
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
				new FileInputStream(sqlDir.resolve("madang_sql.sql").toString()));
			 InputStreamReader insertDataSql = new InputStreamReader(
				new FileInputStream(sqlDir.resolve("madang_insert_data.sql").toString()))){
			
			scriptRunner.runScript(createTblSql);
			scriptRunner.runScript(insertDataSql);
		} catch (IOException err) {
			err.printStackTrace();
		} catch (SQLException err) {
			err.printStackTrace();
		}
		System.out.println("finish initDB");
	}
	
	// loginType 으로는 "professor" or "student"가 들어오게 된다.
	// 입력에 따라 해당 테이블에서 쿼리를 수행한다.
	@Override
	public String[][] selectIdName(String loginType) {
		String sqlName = String.format("select_id_name_%s.sql", loginType);
		String query = getSql(sqlName);
		String[][] results = null;
		ArrayList<String> idList = new ArrayList<>(), nameList = new ArrayList<>();
		
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
//				int id = rs.getInt(1);
				String id = rs.getString(1);
				String name = rs.getString(2);
//				idList.add(Integer.toString(id));
				idList.add(id);
				nameList.add(name);
			}
			
			int numData = idList.size();
			results = new String[2][];
			results[0] = Arrays.copyOf(idList.toArray(), numData, String[].class);
			results[1] = Arrays.copyOf(nameList.toArray(), numData, String[].class);
		} 
		catch (SQLException err) {
			err.printStackTrace();
		}
		
		return results;
	}

	String getSql(String sqlName) {
		String sqlPath = sqlDir.resolve(sqlName).toString();
		char[] buffer = new char[1024 * 64];
		String sql = null;
		try(InputStreamReader sqlFile = new InputStreamReader(
				new FileInputStream(sqlPath))) {
			sqlFile.read(buffer);
			sql = new String(buffer);
		} catch (IOException err) {
			System.out.println(err);
		}
		return sql;
	}
	
	public Statement createStatement() throws SQLException {
		return conn.createStatement();
	}
	
	public String[] selectAllFrom(String tblName) {
		ArrayList<String> rowList = new ArrayList<>();
		String sql = String.format("select * from %s", tblName);
		
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
		catch (SQLException err) {
			err.printStackTrace();
		}
		
		int numData = rowList.size();
		String[] rowArr = Arrays.copyOf(rowList.toArray(), numData, String[].class);
		return rowArr;
	}
	

	@Override
	public String getColNamesOfTbl(String tblName) {
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
		catch (SQLException err) {
			err.printStackTrace();
		}
		
		return colNames;
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
	
}
