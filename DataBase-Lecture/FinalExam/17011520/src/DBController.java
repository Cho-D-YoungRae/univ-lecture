import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DBController extends JPanel {
	final Dimension initDim = new Dimension(1000, 800);
	JPanel funcBtnPanel;
	JButton[] funcBtns;
	ContentPanel contentPanel;
	
	public DBController() {
		contentPanel = null;
		
		setPreferredSize(initDim);
		setLayout(new BorderLayout());
		
		String[] funcNames = {
				"초기화", "삽입", "삭제", "변경", "검색1", "검색2", "검색3"
		};
		funcBtnPanel = new JPanel();
		funcBtns = new JButton[funcNames.length];
		FuncBtnListener funcBtnListener = new FuncBtnListener();
		for (int i = 0; i < funcNames.length; i++) {
			JButton funcBtn = new JButton(funcNames[i]);
			funcBtn.addActionListener(funcBtnListener);
			funcBtns[i] = funcBtn;
			funcBtnPanel.add(funcBtn);
		}
		add(funcBtnPanel, BorderLayout.NORTH);	
	}
	class FuncBtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton funcBtn = (JButton) e.getSource();
			String funcName = funcBtn.getText();
			
			System.out.println("Function: " + funcName);
			ContentPanel targetPanel = new ContentPanel(funcName);
			switchContentPanelTo(targetPanel);
		}
		
	}
	void switchContentPanelTo(ContentPanel targetPanel) {
		if (contentPanel != null) {
			remove(contentPanel);
		}
		contentPanel = targetPanel;
		add(contentPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
