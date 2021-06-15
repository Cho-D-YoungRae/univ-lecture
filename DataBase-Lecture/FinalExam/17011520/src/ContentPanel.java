import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ContentPanel extends JPanel {
	final DataBase DB;
	final String funcName;
	String tblName;
	JPanel statePanel;
	JRadioButton[] tblNameRadios;
	ViewPanel viewPanel;
	InputPanel inputPanel;
	
	public ContentPanel(String funcName) {
		DB = MainFrame.getDB();
		this.funcName = funcName;
		viewPanel = null;
		
		setLayout(new BorderLayout());
		
		statePanel = new JPanel();
		statePanel.add(new JLabel(funcName));		
		add(statePanel, BorderLayout.NORTH);
		
		if (isNeedTblNameRadio(funcName)) {
			initTblNameRadio();
		}
		
		if (isNeedInputPanel(funcName)) {
			System.out.println(funcName + " needs InputPanel");
			inputPanel = new InputPanel();
			add(inputPanel, BorderLayout.SOUTH);
		}
		
		if (funcName.equals("초기화")) {
			DB.initDB();
			String title = "초기화 기능";
			String content = "초기화가 완료되었습니다";
			ViewPanel targetPanel = createAlertPanel(title, content);
			switchViewPanelTo(targetPanel);
		}
		else if (funcName.equals("삽입")) {
			String title = "삽입 기능";
			String content = "테이블을 선택하세요.";
			ViewPanel targetPanel = createAlertPanel(title, content);
			switchViewPanelTo(targetPanel);
		}
		else if (funcName.equals("삭제")) {
			String title = "삭제 기능";
			String content = "테이블을 선택하세요.";
			ViewPanel targetPanel = createAlertPanel(title, content);
			switchViewPanelTo(targetPanel);
		}
		else if (funcName.equals("변경")) {
			String title = "변경 기능";
			String content = "테이블을 선택하세요.";
			ViewPanel targetPanel = createAlertPanel(title, content);
			switchViewPanelTo(targetPanel);
		}
		else if (funcName.equals("검색1")) {
			String title = "SELECT * FROM table_name;";
			String content = "테이블 이름을 선택하세요.";
			ViewPanel targetPanel = createAlertPanel(title, content);
			switchViewPanelTo(targetPanel);
		}
		else if (funcName.equals("검색2")) {
			String sql = "SELECT pat_job as `환자 직업`, count(*) as `환자 직업별 주임 간호사에게 배정된 수`\n"
					+ "	FROM Patients P\n"
					+ "    WHERE P.nur_id IN (SELECT N.nur_id FROM Nurses N WHERE N.nur_position = \"주임\")\n"
					+ "    GROUP BY pat_job;";
			try {
				String colNames = DB.getColNamesOfSql(sql);
				String[] data = DB.executeQuery(sql);
				String[] contents = new String[data.length + 1];
				for (int i = 0; i < data.length; i ++) {
					contents[i] = data[i];
				}
				contents[data.length] = sql;
				ViewPanel targetPanel = new ViewPanel(colNames, contents);
				switchViewPanelTo(targetPanel);
			} 
			catch (SQLException err) {
				err.printStackTrace();
			}
		}
		else if (funcName.equals("검색3")) {
			String sql = "SELECT pat_job as `환자 직업`, count(*) as `환자 직업 별 남자 의사, 남자 간호사 에게 배정된 수`\n"
					+ "	FROM Patients P\n"
					+ "    WHERE P.nur_id IN (SELECT N.nur_id from Nurses N WHERE N.nur_gen = \"M\")\n"
					+ "		AND P.doc_id IN (SELECT D.doc_id from Doctors D WHERE D.doc_gen = \"M\")\n"
					+ "	GROUP BY P.pat_job;";
			try {
				String colNames = DB.getColNamesOfSql(sql);
				String[] data = DB.executeQuery(sql);
				String[] contents = new String[data.length + 1];
				for (int i = 0; i < data.length; i ++) {
					contents[i] = data[i];
				}
				contents[data.length] = sql;
				ViewPanel targetPanel = new ViewPanel(colNames, contents);
				switchViewPanelTo(targetPanel);
			} 
			catch (SQLException err) {
				err.printStackTrace();
			}
		}
	}
	
	boolean isNeedTblNameRadio(String funcName) {
		ArrayList<String> funcNamesNeedTblRadio = new ArrayList<>(Arrays.asList(
				"삽입", "삭제", "변경", "검색1"));
		
		return funcNamesNeedTblRadio.contains(funcName);
	}
	
	void initTblNameRadio() {
		String[] tblNames = {
				"Doctors", "Nurses", "Patients", "Treatments", "Charts"
		};
		ButtonGroup tblNameRadioGroup = new ButtonGroup();
		tblNameRadios = new JRadioButton[tblNames.length];
		TblNameRadioListener tblNameRadioListener = new TblNameRadioListener();
		for (int i = 0; i < tblNames.length; i++) {
			JRadioButton tblNameRadio = new JRadioButton(tblNames[i]);
			tblNameRadio.addActionListener(tblNameRadioListener);
			tblNameRadios[i] = tblNameRadio;
			tblNameRadioGroup.add(tblNameRadio);
			statePanel.add(tblNameRadio);
		}
	}
	
	boolean isNeedInputPanel(String funcName) {
		ArrayList<String> funcNeedInputPanel = new ArrayList<>(Arrays.asList(
				"삽입", "삭제", "변경"));
		
		return funcNeedInputPanel.contains(funcName);
	}
	
	void switchViewPanelTo(ViewPanel targetPanel) {
		if (viewPanel != null) {
			remove(viewPanel);
		}
		viewPanel = targetPanel;
		add(viewPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	ViewPanel createAlertPanel(String alertTitle, String alertContent) {
		String[] alertContentArg = {alertContent};
		ViewPanel alertPanel = new ViewPanel(alertTitle, alertContentArg);
		
		return alertPanel;
	}
	
	class TblNameRadioListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton tblNameRadio = (JRadioButton) e.getSource();
			tblName = tblNameRadio.getText();
			System.out.println("Table: " + tblName);
			
			if (funcName.equals("검색1")) {
				String sql = String.format("SELECT * FROM %s", tblName);
				try {
					String colNames = DB.getColNamesOfSql(sql);
					String[] data = DB.executeQuery(sql);
					ViewPanel targetPanel = new ViewPanel(colNames, data);
					switchViewPanelTo(targetPanel);
				} 
				catch (SQLException err) {
					err.printStackTrace();
				}
			}
			else if (funcName.equals("삽입")) {
				try {
					String colNames = DB.getColNamesOfTbl(tblName);
					String[] content = {
							String.format("INSERT INTO %s VALUES "
									+ "(괄호 안에 들어갈 데이터)", tblName),
							"데이터 별로 , 로 나누어 입력하세요."};
					ViewPanel targetPanel = new ViewPanel(colNames, content);
					switchViewPanelTo(targetPanel);
				} 
				catch (SQLException err) {
					err.printStackTrace();
				}
			}
			else if (funcName.equals("삭제")) {
				try {
					String colNames = DB.getColNamesOfTbl(tblName);
					String content = String.format(
							"DELETE FROM %s -> 이후에 들어갈 WHERE 절을 입력하세요.", tblName);
					ViewPanel targetPanel = createAlertPanel(colNames, content);
					switchViewPanelTo(targetPanel);
				} 
				catch (SQLException err) {
					err.printStackTrace();
				}
			}
			else if (funcName.equals("변경")) {
				try {
					String colNames = DB.getColNamesOfTbl(tblName);
					String content = String.format(
							"UPDATE %s -> 이후 들어갈 SET/WHERE 절을 입력하세요.", tblName);
					ViewPanel targetPanel = createAlertPanel(colNames, content);
					switchViewPanelTo(targetPanel);
				} 
				catch (SQLException err) {
					err.printStackTrace();
				}
			}
		}
	}
	
	class ViewPanel extends JPanel {
		JLabel headerLabel;
		JList<String> dataList;
		
		public ViewPanel(String header, String[] data) {
			setLayout(new BorderLayout());
			
			headerLabel = new JLabel(header);
			add(headerLabel, BorderLayout.NORTH);
			
			dataList = new JList<>(data);
			add(new JScrollPane(dataList), BorderLayout.CENTER);
		}
	}
	
	class InputPanel extends JPanel {
		JTextField sqlTextField;
		JButton inputBtn, cancelBtn;
		
		public InputPanel() {
			int inputAttrCols = 60;
			
			sqlTextField = new JTextField(inputAttrCols);
			add(sqlTextField);
			
			inputBtn = new JButton("입력");
			inputBtn.addActionListener(new InputBtnListener());
			add(inputBtn);
			
			cancelBtn = new JButton("취소");
			cancelBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("cancel input");
					sqlTextField.setText("");
				}
			});
			add(cancelBtn);
		}
		
		class InputBtnListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				String sqlCond = sqlTextField.getText();
				sqlTextField.setText("");
				
				if (funcName.equals("삽입")) {
					String sql = String.format(
							"INSERT INTO %s VALUES (%s);", tblName, sqlCond);
					System.out.println(sql);
					try {
						DB.execute(sql);
						String title = "삽입이 완료되었습니다.";
						ViewPanel targetPanel = createAlertPanel(title, sql);
						switchViewPanelTo(targetPanel);
					}
					catch (SQLException err) {
						String title = "오류발생";
						String content = "입력을 확인해주세요.";
						ViewPanel alertPanel = createAlertPanel(title, content);
						switchViewPanelTo(alertPanel);
					}
					
				}
				else if (funcName.equals("삭제")) {
					String sql = String.format(
							"DELETE FROM %s %s;", tblName, sqlCond);
					System.out.println(sql);
					try {
						DB.execute(sql);
						String title = "삭제가 완료되었습니다.";
						ViewPanel targetPanel = createAlertPanel(title, sql);
						switchViewPanelTo(targetPanel);
					}
					catch (SQLException err) {
						String title = "오류발생";
						String content = "입력을 확인해주세요.";
						ViewPanel alertPanel = createAlertPanel(title, content);
						switchViewPanelTo(alertPanel);
					}
				}
				else if (funcName.equals("변경")) {
					String sql = String.format(
							"UPDATE %s %s;", tblName, sqlCond);
					System.out.println(sql);
					try {
						DB.execute(sql);
						String title = "변경이 완료되었습니다.";
						ViewPanel targetPanel = createAlertPanel(title, sql);
						switchViewPanelTo(targetPanel);
					}
					catch (SQLException err) {
						String title = "오류발생";
						String content = "입력을 확인해주세요.";
						ViewPanel alertPanel = createAlertPanel(title, content);
						switchViewPanelTo(alertPanel);
					}
				}
			}
		}
	}
}
