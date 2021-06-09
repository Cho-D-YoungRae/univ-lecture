import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Container;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.InputStreamReader;
// for test
import java.nio.file.Path;
import java.nio.file.Paths;


public class MainFrame extends JFrame {
	static MainFrame mainFrame;
	static Database DB;
	public static MainFrame getInstance() {
		return mainFrame;
	}
	public static Database getDB() {
		return DB;
	}
	
	final Point INIT_POS = new Point(200, 200);
	final Container contentPane;
	
	LoginPanel loginPanel;
	
	public MainFrame() {
		super("17011520/조영래 18011399/강예림");
		contentPane = getContentPane();
		mainFrame = this;
		DB = new MadangDB();
		
		initLoginLayout();
		
		setLocation(INIT_POS);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}
	
	void initLoginLayout() {
		loginPanel = new LoginPanel();
		contentPane.add(loginPanel);
	}
	
	void toLoginPanel() {
		changePanel(loginPanel);
	}
	
	public void changePanel(JPanel panel) {
		contentPane.removeAll();
		contentPane.add(panel);
		
		pack();
		revalidate();
		repaint();
	}
	
	public static void main(String[] args) {
		MainFrame program = new MainFrame();
	}
}
