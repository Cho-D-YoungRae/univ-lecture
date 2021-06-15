import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Container;
import java.awt.Point;



public class MainFrame extends JFrame {
	static MainFrame mainFrame;
	static DataBase DB;
	public static MainFrame getInstance() {
		return mainFrame;
	}
	public static DataBase getDB() {
		return DB;
	}
	
	final Point INIT_POS = new Point(200, 200);
	final Container contentPane;
	
	
	public MainFrame() {
		super("17011520/조영래");
		contentPane = getContentPane();
		mainFrame = this;
		DB = new MadangDB();
		
		contentPane.add(new DBController());
		
		setLocation(INIT_POS);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
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
