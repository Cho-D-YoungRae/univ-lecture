

import java.awt.event.*;
//import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;


public class BookListSwing extends JFrame implements ActionListener {
   JButton btnOk, btnReset;
   JTextArea txtResult, txtStatus;
   JPanel pn1;

   static Connection con;
   Statement stmt;
   ResultSet rs;
   String Driver = "";
   String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
   String userid = "madang";
   String pwd = "madang";

   public BookListSwing() {
      super("Swing Database");
      layInit();
      conDB();
      setVisible(true);
      setBounds(200, 200, 400, 400); //가로위치,세로위치,가로길이,세로길이
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public void layInit() {
      btnOk = new JButton("select * from book");
      btnReset = new JButton("화면초기화");
      txtResult = new JTextArea();
      txtStatus = new JTextArea(5,30);
      
      pn1 = new JPanel();
      pn1.add(btnOk);
      pn1.add(btnReset);
      
      txtResult.setEditable(false);
      txtStatus.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(txtResult);
      JScrollPane scrollPane2 = new JScrollPane(txtStatus);
      
      add("North", pn1);
      add("Center", scrollPane);
      add("South", scrollPane2);
      
      btnOk.addActionListener(this);
      btnReset.addActionListener(this);
   }

   public void conDB() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         //System.out.println("드라이버 로드 성공");
         txtStatus.append("드라이버 로드 성공...\n");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      
      try { /* 데이터베이스를 연결하는 과정 */
          //System.out.println("데이터베이스 연결 준비...");
    	  txtStatus.append("데이터베이스 연결 준비...\n");
          con = DriverManager.getConnection(url, userid, pwd);
          //System.out.println("데이터베이스 연결 성공");
          txtStatus.append("데이터베이스 연결 성공...\n");
       } catch (SQLException e1) {
          e1.printStackTrace();
       }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
    
      try {
         stmt = con.createStatement();
         String query = "SELECT * FROM Book ";
         if (e.getSource() == btnOk) {
            txtResult.setText("");
            txtResult.setText("BOOKNO           BOOK NAME       PUBLISHER      PRICE\n");
            rs = stmt.executeQuery(query);
            while (rs.next()) {
               String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4)
                     + "\n";
               txtResult.append(str);
            }
         } else if (e.getSource() == btnReset) {
            txtResult.setText("");
         }
      } catch (Exception e2) {
         System.out.println("쿼리 읽기 실패 :" + e2);
/*      } finally {
         try {
            if (rs != null)
               rs.close();
            if (stmt != null)
               stmt.close();
            if (con != null)
               con.close();
         } catch (Exception e3) {
            // TODO: handle exception
         }
  */
      }

   }

   public static void main(String[] args) {
      BookListSwing BLS = new BookListSwing();
      
      //BLS.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      //BLS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      BLS.addWindowListener(new WindowAdapter() {
    	  public void windowClosing(WindowEvent we) {
    		try {
    			con.close();
    		} catch (Exception e4) { 	}
    		System.out.println("프로그램 완전 종료!");
    		System.exit(0);
    	  }
    	});
   }
}
