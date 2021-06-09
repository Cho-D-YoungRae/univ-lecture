import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;

public class LoginPanel extends JPanel {
	final Dimension initDim = new Dimension(300, 300);
	final String[] TYPENAMES = {
			"admin", "professor", "student"
	};
	int typeIdx;
	LoginStatePanel loginStatePanel;
	LoginTypePanel loginTypePanel;
	
	public LoginPanel() {
		setLayout(new BorderLayout());
		typeIdx = 0;
		
		loginStatePanel = new LoginStatePanel();
		loginTypePanel = new LoginTypePanel();
		
		add(loginStatePanel, "North");
		add(loginTypePanel, "Center");
		
		setPreferredSize(initDim);
	}
	void setType(int typeIdx) {
		this.typeIdx = typeIdx;
		loginStatePanel.setType(typeIdx);
		loginTypePanel.setType(typeIdx);
		if(typeIdx > 0) {
			loginStatePanel.loginBtn.setEnabled(false);
		} else {
			loginStatePanel.loginBtn.setEnabled(true);
		}
	}
	void setIdName(String idName) {
		String loginType = loginStatePanel
							.loginState.getText().split(" ")[0];
		String loginStateTxt = String.format("%s %s", loginType, idName);
		loginStatePanel.loginState.setText(loginStateTxt);
		loginStatePanel.loginBtn.setEnabled(true);		
	}
	
	class LoginStatePanel extends JPanel {
		JLabel loginState;
		JButton loginBtn;
		
		public LoginStatePanel() {
			setLayout(new BorderLayout());
			
			loginState = new JLabel(TYPENAMES[typeIdx]);
			loginBtn = new JButton("Login");
			loginBtn.addActionListener(new LoginBtnListener());
			
			add(loginState, "Center");
			add(loginBtn, "East");
		}
		
		public void setType(int typeIdx) {
			loginState.setText(TYPENAMES[typeIdx]);
		}
		
		class LoginBtnListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				String typeIdName = loginState.getText();
				MainFrame.getInstance()
							.changePanel(new DBViewPanel(typeIdName));
			}
		}
	}
	
	class LoginTypePanel extends JPanel {
		JPanel typePanel;
		JList<String> idNameByType;
		JButton[] typeBtns;
		
		public LoginTypePanel() {
			setLayout(new GridLayout(1, 2));
			
			initTypePanel();
			initIdNameByType();
		}
		
		void initTypePanel() {
			typePanel = new JPanel();
			typePanel.setLayout(new GridLayout(3, 1));
			
			typeBtns = new JButton[TYPENAMES.length];
			TypeBtnListener typeBtnListener = new TypeBtnListener();
			for (int i=0; i<TYPENAMES.length; i++) {
				JButton typeBtn = new JButton(TYPENAMES[i]);
				typeBtn.addActionListener(typeBtnListener);
				typeBtns[i] = typeBtn;
				typePanel.add(typeBtns[i]);
			}
			add(typePanel);
		}
		
		void initIdNameByType() {
			idNameByType = new JList<>();
			idNameByType.setEnabled(false);
			IdNameByTypeListener idNameByTypeListener = new IdNameByTypeListener();
			idNameByType.addListSelectionListener(idNameByTypeListener);
			add(new JScrollPane(idNameByType));
		}
		class IdNameByTypeListener implements ListSelectionListener {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					String selectedIdName = idNameByType
											.getSelectedValue()
											.toString();
					setIdName(selectedIdName);
				}
			}
			
		}
		
		void setType(int typeIdx) {
			if(typeIdx == 0) {
				idNameByType.setEnabled(false);
			}
			else {
				Database DB = MainFrame.getDB();
				String[][] sqlResults = DB.selectIdName(TYPENAMES[typeIdx]);
				String[] idArr = sqlResults[0], nameArr = sqlResults[1];
				int numData = idArr.length;
				String[] idNameArr = new String[numData];
				
				for (int i = 0; i < numData; i++) {
					idNameArr[i] = String.format("%s %s", idArr[i], nameArr[i]);
				}
				
				idNameByType.setEnabled(true);
				idNameByType.setListData(idNameArr);
			}
		}
		
		class TypeBtnListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				LoginPanel loginPanel = LoginPanel.this;
				JButton typeBtn = (JButton)e.getSource();
				JButton[] typeBtns = loginTypePanel.typeBtns;
				
				for(int i = 0; i < loginTypePanel.typeBtns.length; i++) {
					if (loginPanel.typeIdx != i && typeBtn == typeBtns[i]) {
						loginPanel.setType(i);
					}
				}
			}
		}
	}

}
