import java.awt.event.*;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;

public class JC17011520M1 extends JFrame implements ActionListener {
	final int NUM_SEARCH = 6;
	JButton[] btnSearchs;
	JButton btnInit, btnInput;
	JTextArea txtResult, txtStatus;
	JPanel btnPanel;
	JPanel inputPanel;
 	JTextField orderidTF, custidTF, bookidTF, salepriceTF, orderdateTF;

	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang"; // 내 DB비밀번호임 -> 제출 전 바꾸기

	public JC17011520M1() {
		super("17011520");
		layInit();
		conDB(); // JDBC
		setVisible(true);
		setBounds(200, 200, 600, 600); // 가로위치,세로위치,가로길이,세로길이
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void layInit() {
		btnPanel = new JPanel();
		
		btnSearchs = new JButton[NUM_SEARCH];
		for (int i=0; i < NUM_SEARCH; i++) {
			btnSearchs[i] = new JButton("검색" + (i + 1));
			btnSearchs[i].addActionListener(this);
			btnPanel.add(btnSearchs[i]);
		}
		
		btnInit = new JButton("초기화");
		btnInit.addActionListener(this);		
		btnPanel.add(btnInit);		
	
		inputPanel = new JPanel();
		orderidTF = new JTextField(5);
		custidTF = new JTextField(5);
		bookidTF = new JTextField(5);
		salepriceTF = new JTextField(5);
		orderdateTF = new JTextField(10);
		btnInput = new JButton("입력1");
		btnInput.addActionListener(this);
		
		inputPanel.add(orderidTF);
		inputPanel.add(custidTF);
		inputPanel.add(bookidTF);
		inputPanel.add(salepriceTF);
		inputPanel.add(orderdateTF);
		inputPanel.add(btnInput);
		
		
		// 결과에 대한 내용
		txtResult = new JTextArea();
		txtStatus = new JTextArea(5, 30);

		txtResult.setEditable(false);
		txtStatus.setEditable(false);
		
		// scroll 넣어준다
		JScrollPane scrollPane = new JScrollPane(txtResult);
		JScrollPane scrollPane2 = new JScrollPane(txtStatus);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add("North", inputPanel);
		centerPanel.add("Center", scrollPane);
		add("North", btnPanel);
		add("Center", centerPanel);
		add("South", scrollPane2);
	}
	

	// 그대로 사용하면 된다.
	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// System.out.println("드라이버 로드 성공");
			txtStatus.append("드라이버 로드 성공...\n");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try { /* 데이터베이스를 연결하는 과정 */
			// System.out.println("데이터베이스 연결 준비...");
			txtStatus.append("데이터베이스 연결 준비...\n");
			con = DriverManager.getConnection(url, userid, pwd);
			// System.out.println("데이터베이스 연결 성공");
			txtStatus.append("데이터베이스 연결 성공...\n");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			stmt = con.createStatement();
			if (e.getSource() == btnInput) {
				String query = "INSERT INTO Orders VALUES ("
						+ orderidTF.getText() + ", "
						+ custidTF.getText() + ", "
						+ bookidTF.getText() + ", "
						+ salepriceTF.getText() + ", "
						+ "STR_TO_DATE(\'" + orderdateTF.getText() + "\',\'%Y-%m-%d\'));";
				stmt.execute(query);
				txtResult.setText("orderid\t" + "custid\t" + "bookid\t" +
						"saleprice\t" + "orderdate\n");
				txtResult.append(orderidTF.getText() + "\t" +
						custidTF.getText() + "\t" +
						bookidTF.getText() + "\t" +
						salepriceTF.getText() + "\t" +
						orderdateTF.getText() + "\n");
				orderidTF.setText("");
				custidTF.setText("");
				bookidTF.setText("");
				salepriceTF.setText("");
				orderdateTF.setText("");
				txtStatus.setText("입력1\n");
			}
			else if (e.getSource() == btnSearchs[0]) {
				String query = "SELECT * FROM book";
				String head = "bookid\t" + "bookname\t" + "publisher\t" + "price\n";
				txtResult.setText(head);
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					String row = rs.getInt(1) + "\t" + rs.getString(2) +
							"\t" + rs.getString(3) + "\t" + rs.getInt(4) + "\n";
					txtResult.append(row);
				}
				txtStatus.setText("검색1\n");
			}
			else if (e.getSource() == btnSearchs[1]) {
				String query = "SELECT * FROM orders";
				String head = "orderid\t" + "custid\t" + "bookid\t" +
						"saleprice\t" + "orderdate\n";
				txtResult.setText(head);
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					String row = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" 
							+ rs.getInt(3) + "\t" + rs.getInt(4) + "\t" 
							+ rs.getString(5) + "\n";
					txtResult.append(row);
				}
				txtStatus.setText("검색2\n");
			}
			else if (e.getSource() == btnSearchs[2]) {
				String query = "SELECT * FROM customer";
				String head = "cust\t" + "name\t" + "address\t" + "phone\n";
				txtResult.setText(head);
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					String row = rs.getInt(1) + "\t" + rs.getString(2) +
							"\t" + rs.getString(3) + "\t" + rs.getString(4) + "\n";
					txtResult.append(row);
				}
				txtStatus.setText("검색3\n");
			}
			else if (e.getSource() == btnSearchs[3]) {
				String query = "select bookname from book \n"
						+ "where bookname not in (select b.bookname \n"
						+ "						from book b,orders o,customer c \n"
						+ "                        where o.custid = c.custid \n"
						+ "							and b.bookid = o.bookid \n"
						+ "                            and c.name ='박지성');\n";
				String head = "박지성이 구매하지 않은 도서의 이름\n";
				txtResult.setText(head);
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					String row = rs.getString(1) + "\n";
					txtResult.append(row);
				}
				txtStatus.setText("검색4\n");
			}
			else if (e.getSource() == btnSearchs[4]) {
				String query = "select count(distinct publisher) from book b, orders o\n"
						+ "where b.bookid = o.bookid \n"
						+ "and custid = (select custid from customer where name = '박지성');";
				String head = "박지성이 구매한 도서의 출판사 수\n";
				txtResult.setText(head);
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					String row = rs.getInt(1) + "\n";
					txtResult.append(row);
				}
				txtStatus.setText("검색5\n");
			}
			else if (e.getSource() == btnSearchs[5]) {
				String query = "select count(bookid) \n"
						+ "from orders \n"
						+ "where custid = (select custid \n"
						+ "				from customer \n"
						+ "                where name = '박지성');\n";
				String head = "박지성이 구매한 도서 수\n";
				txtResult.setText(head);
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					String row = rs.getInt(1) + "\n";
					txtResult.append(row);
				}
				txtStatus.setText("검색6\n");
			}
			else if (e.getSource() == btnInit) {
				stmt.execute("delete from orders;");
				stmt.execute("delete from book;");
				stmt.execute("delete from customer;");
				for (int i = 0; i < insertQueries.length; i++)
					stmt.execute(insertQueries[i]);
				
				txtResult.setText("");
				txtStatus.setText("초기화\n");
			}

		} catch (Exception e2) {
			System.out.println("쿼리 읽기 실패 :" + e2);
			txtStatus.setText("orderid custid bookid saleprice orderdate(yyyy-mm-dd)\n"
					+ "적절한 데이터를 입력해주세요.");
		}
	}

	public static void main(String[] args) {
		JC17011520M1 BLS = new JC17011520M1();

		BLS.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					con.close();
				} catch (Exception e4) {
				}
				System.out.println("프로그램 완전 종료!");
				System.exit(0);
			}
		});
	}
	
	String[] insertQueries = {
			"INSERT INTO Book VALUES(1, '축구의 역사', '굿스포츠', 7000);",
			"INSERT INTO Book VALUES(2, '축구아는 여자', '나무수', 13000);",
			"INSERT INTO Book VALUES(3, '축구의 이해', '대한미디어', 22000);",
			"INSERT INTO Book VALUES(4, '골프 바이블', '대한미디어', 35000);",
			"INSERT INTO Book VALUES(5, '피겨 교본', '굿스포츠', 8000);",
			"INSERT INTO Book VALUES(6, '역도 단계별기술', '굿스포츠', 6000);",
			"INSERT INTO Book VALUES(7, '야구의 추억', '이상미디어', 20000);",
			"INSERT INTO Book VALUES(8, '야구를 부탁해', '이상미디어', 13000);",
			"INSERT INTO Book VALUES(9, '올림픽 이야기', '삼성당', 7500);",
			"INSERT INTO Book VALUES(10, 'Olympic Champions', 'Pearson', 13000);",
			"INSERT INTO Customer VALUES (1, '박지성', '영국 맨체스타', '000-5000-0001');",
			"INSERT INTO Customer VALUES (2, '김연아', '대한민국 서울', '000-6000-0001');",
			"INSERT INTO Customer VALUES (3, '장미란', '대한민국 강원도', '000-7000-0001');",
			"INSERT INTO Customer VALUES (4, '추신수', '미국 클리블랜드', '000-8000-0001');",
			"INSERT INTO Customer VALUES (5, '박세리', '대한민국 대전',  NULL);",
			"INSERT INTO Orders VALUES (1, 1, 1, 6000, STR_TO_DATE('2014-07-01','%Y-%m-%d'));",
			"INSERT INTO Orders VALUES (2, 1, 3, 21000, STR_TO_DATE('2014-07-03','%Y-%m-%d'));",
			"INSERT INTO Orders VALUES (3, 2, 5, 8000, STR_TO_DATE('2014-07-03','%Y-%m-%d'));",
			"INSERT INTO Orders VALUES (4, 3, 6, 6000, STR_TO_DATE('2014-07-04','%Y-%m-%d'));",
			"INSERT INTO Orders VALUES (5, 4, 7, 20000, STR_TO_DATE('2014-07-05','%Y-%m-%d'));",
			"INSERT INTO Orders VALUES (6, 1, 2, 12000, STR_TO_DATE('2014-07-07','%Y-%m-%d'));",
			"INSERT INTO Orders VALUES (7, 4, 8, 13000, STR_TO_DATE( '2014-07-07','%Y-%m-%d'));",
			"INSERT INTO Orders VALUES (8, 3, 10, 12000, STR_TO_DATE('2014-07-08','%Y-%m-%d'));",
			"INSERT INTO Orders VALUES (9, 2, 10, 7000, STR_TO_DATE('2014-07-09','%Y-%m-%d'));",
			"INSERT INTO Orders VALUES (10, 3, 8, 13000, STR_TO_DATE('2014-07-10','%Y-%m-%d'));",
	};
}
