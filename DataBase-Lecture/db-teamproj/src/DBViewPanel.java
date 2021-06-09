import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DBViewPanel extends JPanel {
	
	final Dimension initDim = new Dimension(1000, 800);
	ContentPanel contentPanel;
	String type, id, name;
	
	public DBViewPanel(String typeIdName) {
		setPreferredSize(initDim);
		setLayout(new BorderLayout());
		
		JPanel statePanel = new JPanel();
		JLabel typeIdNameLabel = new JLabel(typeIdName);
		statePanel.add(typeIdNameLabel);
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getInstance().toLoginPanel();
			}
			
		});
		statePanel.add(logoutBtn);
		
		add(statePanel, "North");
		
		String[] typeIdNameSplit = typeIdName.split(" ");
		type = typeIdNameSplit[0];
		if (type == "admin") {
			id = null;
			name = null;
		} else {
			id = typeIdNameSplit[1];
			name = typeIdNameSplit[2];
		}
		
		contentPanel = new ContentPanel();
		add(contentPanel, "Center");
	}

	
	class ContentPanel extends JPanel {
		String[] funcNames;
		String funcName;
		JPanel funcBtnPanel;
		JButton[] funcBtns;
		ViewPanel viewPanel;
		ArrayList<String> tblNames = new ArrayList<>(Arrays.asList(
				"Department", "Professor", "Student", "Lecture", 
				"Club", "Tuition", "Professor_has_student", "Lecture_has_student",
				"Professor_has_department", "Student_has_club"
		));
		public ContentPanel() {
			setLayout(new BorderLayout());
			
			String[] adminFuncs = {
					"초기화", "Department", "Professor", "Student",
					"Lecture", "Club", "Tuition", "Professor_has_student",
					"Lecture_has_student", "Professor_has_department", "Student_has_club"
			};
			String[] professorFuncs = {
					"강의과목", "지도학생", "학과정보", "교수시간표"
			};
			String[] studentFuncs = {
					"수강과목", "학생시간표", "성적표", "소속동아리", "동아리소속학생"
			};
			
			if (type.equals("admin"))
				funcNames = adminFuncs;
			else if (type.equals("professor"))
				funcNames = professorFuncs;
			else if (type.equals("student"))
				funcNames = studentFuncs;
			
			
			funcBtnPanel = new JPanel();
			int numFunc = funcNames.length;
			funcBtns = new JButton[numFunc];
			FuncBtnListener funcBtnListener = new FuncBtnListener();
			for (int i = 0; i < numFunc; i++) {
				funcBtns[i] = new JButton(funcNames[i]);
				funcBtns[i].addActionListener(funcBtnListener);
				funcBtnPanel.add(funcBtns[i]);
			}
			add(new JScrollPane(funcBtnPanel), "North");
			
			viewPanel = createAlertPanel("첫 페이지", "원하는 기능을 실행하세요.");
			add(viewPanel, "Center");
		}
		
		ViewPanel createAlertPanel(String alertTitle, String alertContent) {
			String[] alertContentArg = {alertContent};
			ViewPanel alertPanel = new ViewPanel(alertTitle, alertContentArg);
			
			return alertPanel;
		}
		
		void switchViewPanelTo(ViewPanel targetPanel) {
			remove(viewPanel);
			viewPanel = targetPanel;
			add(viewPanel, "Center");
			revalidate();
			repaint();
		}
		
		class ViewPanel extends JPanel {
			JLabel headerLabel;
			JList<String> dataList;
			JTable dataTbl;
			JPanel inputPanel;
			
			public ViewPanel(String header, String[] data) {
				setLayout(new BorderLayout());
				
				headerLabel = new JLabel(header);
				add(headerLabel, "North");
				
				dataList = new JList<>(data);
				add(new JScrollPane(dataList), "Center");
			}
			public ViewPanel(String header, JTable tbl) {
				setLayout(new BorderLayout());
				
				headerLabel = new JLabel(header);
				add(headerLabel, "North");
				
				dataTbl = tbl;
				add(new JScrollPane(dataTbl), "Center");
			}
			public void addListListener(ListSelectionListener listener) {
				dataList.addListSelectionListener(listener);
			}
		}
		
		class FuncBtnListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				funcName = btn.getText();
				Database DB = MainFrame.getDB();
				
				if (funcName.equals("초기화")) {
					String title = "초기화";
					String content = "초기화가 완료되었습니다.";
					ViewPanel funcPanel = createAlertPanel(title, content);
					MainFrame.getDB().initDB();
					switchViewPanelTo(funcPanel);
				}
				else if (tblNames.contains(funcName)) {
					String header = DB.getColNamesOfTbl(funcName);
					String[] content = DB.selectAllFrom(funcName);
					
					ViewPanel funcPanel = new ViewPanel(header, content);
					InputPanel inputPanel = new InputPanel(funcName);
					funcPanel.add(new JScrollPane(inputPanel), "South");
					switchViewPanelTo(funcPanel);
				}
				else if (funcName.equals("강의과목")) {
					String title = "입력된 연도/학기에 본인이 강의했던 과목에 대한 "
							+ "모든 정보를 보여주는 기능";
					String content = "연도와 학기를 입력하세요.";
					ViewPanel funcPanel = createAlertPanel(title, content);
					InputPanel inputPanel = new InputPanel(funcName);
					funcPanel.add(inputPanel, "South");
					switchViewPanelTo(funcPanel);
				}
				else if (funcName.equals("지도학생")) {
					String sql = String.format("select * from student where studentID in "
							+ "(select studentId from Professor_has_student "
							+ "where professorId=%s);", id);
					try {
						String cols = DB.getColNamesOfSql(sql);
						String[] rows = DB.executeQuery(sql);
						ViewPanel dataPanel = new ViewPanel(cols, rows);
						dataPanel.addListListener(new DataListListener());
						switchViewPanelTo(dataPanel);
					}
					catch (SQLException err) {
						String title = "오류발생";
						String content = "입력을 확인해주세요";
						ViewPanel alertPanel = createAlertPanel(title, content);
						switchViewPanelTo(alertPanel);
					}
				}
				else if (funcName.equals("학과정보")) {
					String sql = String.format("select * from department "
							+ "where departmentId in "
							+ "( select departmentId from professor "
							+ "where professorId=%s);", id);
					try {
						String cols = DB.getColNamesOfSql(sql);
						String[] rows = DB.executeQuery(sql);
						ViewPanel dataPanel = new ViewPanel(cols, rows);
						switchViewPanelTo(dataPanel);
					}
					catch (SQLException err) {
						String title = "오류발생";
						String content = "입력을 확인해주세요";
						ViewPanel alertPanel = createAlertPanel(title, content);
						switchViewPanelTo(alertPanel);
					}
				}
				else if (funcName.equals("교수시간표")) {
					String header = funcName;
					TimeTable timeTable = new TimeTable(type, id);
					ViewPanel funcPanel = new ViewPanel(header, timeTable);
					switchViewPanelTo(funcPanel);
				}
				else if (funcName.equals("수강과목")) {
					String title = "입력된 연도/학기에 본인이 수강했던 과목에 대한 "
									+ "모든 정보를 보여주는 기능";
					String content = "연도와 학기를 입력하세요.";
					ViewPanel funcPanel = createAlertPanel(title, content);
					InputPanel inputPanel = new InputPanel(funcName);
					funcPanel.add(inputPanel, "South");
					switchViewPanelTo(funcPanel);
				}
				else if (funcName.equals("학생시간표")) {
					String header = funcName;
					TimeTable timeTable = new TimeTable(type, id);
					ViewPanel funcPanel = new ViewPanel(header, timeTable);
					switchViewPanelTo(funcPanel);
				}
				else if (funcName.equals("성적표")) {
					String sql = String.format("select l.lectureId, l.name, l.credit,"
							+ " ls.grade, ls.gpa,ls.year, ls.semester "
							+ "from lecture l, lecture_has_student ls "
							+ "where l.lectureId=ls.lectureId "
							+ "and ls.studentId=%s;", id);
					try {
						String cols = DB.getColNamesOfSql(sql);
						String[] rows = DB.executeQuery(sql);
						ViewPanel dataPanel = new ViewPanel(cols, rows);
						switchViewPanelTo(dataPanel);
					}
					catch (SQLException err) {
						String title = "오류발생";
						String content = "입력을 확인해주세요";
						ViewPanel alertPanel = createAlertPanel(title, content);
						switchViewPanelTo(alertPanel);
					}
				}
				else if (funcName.equals("소속동아리")) {
					String sql = String.format("select * from club c ,"
							+ "student_has_club sc "
							+ "where c.clubid = sc.clubId "
							+ "and sc.studentId=%s;", id);
					try {
						String cols = DB.getColNamesOfSql(sql);
						String[] rows = DB.executeQuery(sql);
						ViewPanel dataPanel = new ViewPanel(cols, rows);
						switchViewPanelTo(dataPanel);
					}
					catch (SQLException err) {
						String title = "오류발생";
						String content = "입력을 확인해주세요";
						ViewPanel alertPanel = createAlertPanel(title, content);
						switchViewPanelTo(alertPanel);
					}
				}
				else if (funcName.equals("동아리소속학생")) {
					try {
						String myClubSql = String.format(
								"select c.clubid from club c ,student_has_club sc "
								+ "where c.clubid = sc.clubId "
								+ "and sc.studentId=%s;", id);
						String[] myClubIdArr = DB.executeQuery(myClubSql);
						if (myClubIdArr.length == 0) {
							String title = "소속 동아리 없음";
							String content = "소속인 동아리가 없습니다.";
							ViewPanel alertPanel = createAlertPanel(title, content);
							switchViewPanelTo(alertPanel);
							return;
						}
						ArrayList<String> imPresClubId = new ArrayList<>();
						for(int i=0; i<myClubIdArr.length; i++) {
							String myClubId = myClubIdArr[i].split(" ")[1];
							String clubPresIdSql = String.format(
									"select president_studentId "
									+ "from club where clubid=%s;", myClubId);
							String myClubPresId = DB.executeQuery(clubPresIdSql)[0]
																	.split(" ")[1];
							if (id.equals(myClubPresId)) {
								imPresClubId.add(myClubId);
							}
						}
						if(imPresClubId.size() == 0) {
							String title = "회장인 동아리가 없음";
							String content = "이 학생이 회장인 동아리가 없습니다.";
							ViewPanel alertPanel = createAlertPanel(title, content);
							switchViewPanelTo(alertPanel);
							return;
						}
						ArrayList<String> clubStudentList = new ArrayList<>();
						String cols = "소속 동아리 학생";
						for (int i=0; i<imPresClubId.size(); i++) {
							String sql = String.format(
									"select * from student "
									+ "where studentId in "
									+ "(select studentId "
									+ "from student_has_club "
									+ "where clubId=%s);", imPresClubId.get(i));
							cols = DB.getColNamesOfSql(sql);
							String[] data = DB.executeQuery(sql);
							for (int j=0; j<data.length; j++)
								clubStudentList.add(data[j]);
						}
						String[] rows  = Arrays.copyOf(clubStudentList.toArray(),
								clubStudentList.size(), String[].class);
						ViewPanel dataPanel = new ViewPanel(cols, rows);
						switchViewPanelTo(dataPanel);
					}
					catch (SQLException err) {
						String title = "오류발생";
						String content = "입력을 확인해주세요";
						ViewPanel alertPanel = createAlertPanel(title, content);
						switchViewPanelTo(alertPanel);
					}
					
				}
			}
		}
		class DataListListener implements ListSelectionListener {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					Database DB = MainFrame.getDB();
					if (funcName.equals("지도학생")) {
						JList<String> dataList = (JList<String>) e.getSource();
						String studentId = dataList.getSelectedValue().split(" ")[1];
						String sql = String.format(
								"select  l.name, ls.grade, ls.year, ls.semester "
								+ "from lecture_has_student ls, lecture l "
								+ "where ls.studentId=%s "
								+ "and ls.lectureId = l.lectureId ;", studentId);
						try {
							String cols = DB.getColNamesOfSql(sql);
							String[] rows = DB.executeQuery(sql);
							ViewPanel dataPanel = new ViewPanel(cols, rows);
							switchViewPanelTo(dataPanel);
						}
						catch (SQLException err) {
							String title = "오류발생";
							String content = "입력을 확인해주세요";
							ViewPanel alertPanel = createAlertPanel(title, content);
							switchViewPanelTo(alertPanel);
						}
					}
					else if(funcName.equals("강의과목")) {
						JList<String> dataList = (JList<String>) e.getSource();
						String lectureId = dataList.getSelectedValue().split(" ")[1];
						System.out.println(lectureId);
						String sql = String.format(
								"select  * from student s where s.studentId "
								+ "in (select ls.studentId from lecture_has_student ls "
								+ "where ls.lectureId=%s);", lectureId);
						try {
							String cols = DB.getColNamesOfSql(sql);
							String[] rows = DB.executeQuery(sql);
							ViewPanel dataPanel = new ViewPanel(cols, rows);
							switchViewPanelTo(dataPanel);
						}
						catch (SQLException err) {
							String title = "오류발생";
							String content = "입력을 확인해주세요";
							ViewPanel alertPanel = createAlertPanel(title, content);
							switchViewPanelTo(alertPanel);
						}
					}
				}				
			}
			
		}
		class InputPanel extends JPanel {
			JTextField[] inputAttrs;
			JButton inputBtn, cancelBtn, deleteBtn, updateBtn;
			
			public InputPanel(String funcName) {
				ContentPanel.this.funcName = funcName;
				int inputAttsCols = 5;
				int inputAttrsCols4Admin = 40;
				
				inputBtn = new JButton("입력");
				inputBtn.addActionListener(new InputBtnListener());
				cancelBtn = new JButton("취소");
				cancelBtn.addActionListener(new CancelBtnListener());
				
				if(tblNames.contains(funcName)) {
					inputAttrs = new JTextField[1];
					inputAttrs[0] = new JTextField(inputAttrsCols4Admin);
					add(inputAttrs[0]);
					add(inputBtn);
					add(cancelBtn);
				}
				else if(funcName.equals("강의과목")) {
					String[] inputAttrNames = {"연도", "학기"};
					int numAttrs = inputAttrNames.length;
					inputAttrs = new JTextField[numAttrs];
					for (int i=0; i<numAttrs; i++) {
						inputAttrs[i] = new JTextField(inputAttsCols);
						add(inputAttrs[i]);
					}
					add(inputBtn);
					add(cancelBtn);
				}
				else if(funcName.equals("수강과목")) {
					String[] inputAttrNames = {"연도", "학기"};
					int numAttrs = inputAttrNames.length;
					inputAttrs = new JTextField[numAttrs];
					for (int i=0; i<numAttrs; i++) {
						inputAttrs[i] = new JTextField(inputAttsCols);
						add(inputAttrs[i]);
					}
					add(inputBtn);
					add(cancelBtn);
				}
			}
			class InputBtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					Database DB = MainFrame.getDB();
					
					if(funcName.equals("강의과목")) {
						String year = inputAttrs[0].getText();
						String semester = inputAttrs[1].getText();
						for(int i=0; i<inputAttrs.length; i++) {
							inputAttrs[i].setText("");
						}

						String sql = String.format("select * from lecture"
								+ " where professorId IN "
								+ "(select professorId from lecture_has_student "
								+ "where year = %s "
								+ "&& semester= %s && professorId=%s);",
								year, semester, id);
						try {
							String cols = DB.getColNamesOfSql(sql);
							String[] rows = DB.executeQuery(sql);
							ViewPanel dataPanel = new ViewPanel(cols, rows);
							dataPanel.addListListener(new DataListListener());
							switchViewPanelTo(dataPanel);
						}
						catch (SQLException err) {
							String title = "오류발생";
							String content = "입력을 확인해주세요";
							ViewPanel alertPanel = createAlertPanel(title, content);
							switchViewPanelTo(alertPanel);
						}
					}
					else if (funcName.equals("수강과목")) {
						String year = inputAttrs[0].getText();
						String semester = inputAttrs[1].getText();
						for(int i=0; i<inputAttrs.length; i++) {
							inputAttrs[i].setText("");
						}
						
						String sql = String.format("select * from lecture "
								+ "where lectureId IN "
								+ "(select lectureId from lecture_has_student "
								+ "where year = %s "
								+ "&& semester=%s && studentId=%s);",
								year, semester, id);
						try {
							String cols = DB.getColNamesOfSql(sql);
							String[] rows = DB.executeQuery(sql);
							ViewPanel dataPanel = new ViewPanel(cols, rows);
							switchViewPanelTo(dataPanel);
						}
						catch (SQLException err) {
							String title = "오류발생";
							String content = "입력을 확인해주세요";
							ViewPanel alertPanel = createAlertPanel(title, content);
							switchViewPanelTo(alertPanel);
						}
					}
					else if (tblNames.contains(funcName)) {
						JTextField inputTextField = inputAttrs[0];
		                String sql = inputTextField.getText();
		                try {
		                	  	DB.execute(sql);
		                }
		                catch (Exception e2){
		                   if (e2.toString().contains("PRIMARY")) {
		                      System.out.println("\n입력값이 PRIMARY KEY 제약조건을 위배했습니다.\n");
		                   } else if (e2.toString().contains("FOREIGN")) {
		                      System.out.println("\n입력값이 FOREIGN KEY 제약조건을 위배했습니다.\n");
		                   } else if (e2.toString().contains("NumberFormatException")) {
		                     System.out.println("\n------Int 항목에 잘못된 값을 입력하셨습니다.------\n");
		                   } else {
		                      System.out.println("\nPRIMARY KEY, FOREIGH KEY, NumberFormat 외에 에러가 발생했습니다.\n");
		                   }
		                   System.out.println("상세 설명 : ");

		                   String str = e2.toString();
		                   System.out.println(str);

		                     e2.printStackTrace();
		                }
		                
		                inputTextField.setText("");
	               }
				}
			}
			class CancelBtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					for (int i=0; i<inputAttrs.length; i++) {
						inputAttrs[i].setText("");
					}
				}
				
			}
		}
	}
}

