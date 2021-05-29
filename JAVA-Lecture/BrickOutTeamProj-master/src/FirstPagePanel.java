import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class FirstPagePanel extends JPanel {
	private final int UPPER_WIDTH = GameFrame.GAME_WIDTH, UPPER_HEIGHT = GameFrame.GAME_HEIGHT / 5 * 3; // ����� �����
																										// GameFrame��
																										// ���̿� ���̸� ���� ����

	public FirstPagePanel() {

		setLayout(null);
		setPreferredSize(GameFrame.GAME_DIMENSION);// GameFrame�� ������� �Ȱ��� ������� ����
		setBackground(Color.orange); // for debugging

		add(new UpperPanel());// FirstPagePanel�� ���Ʒ��� ������ �г��� �߰�
		add(new LowerPanel());
	}

	class UpperPanel extends JPanel {// ����Ÿ��Ʋ�� ǥ�õ� ���� �г�
		public UpperPanel() {
			ImageIcon image1 = new ImageIcon("images/brickout.PNG");// ���� ù ȭ�� �ΰ� �̹����� image1���� ����
			setLayout(null);
			setSize(new Dimension(UPPER_WIDTH, UPPER_HEIGHT));
			setBackground(GameFrame.GAME_BGCOLOR);
			setLocation(0, 0);
			JLabel lbltitle = new JLabel(image1);// image1�� JLabel�� ������ �� ���ڷ� �־���
			lbltitle.setBounds(80, 100, 643, 330);

			add(lbltitle);// UpperPanel�� ����Ÿ��Ʋ �߰�
		}
	}

	class LowerPanel extends JPanel {// ���ӽ��� ��ư�� ���� �Ʒ��� �г�
		private final int LP_HEIGHT = GameFrame.GAME_HEIGHT - UPPER_HEIGHT;// GameFrame�� ���̿��� UpperPanel�� ���̸� ����ŭ
		private FirstPageLabel gameStart = new FirstPageLabel("Game Start");

		public LowerPanel() {
			setLayout(null);// null���̾ƿ� ���
			setSize(GameFrame.GAME_WIDTH, LP_HEIGHT);// ������ ����
			setBackground(GameFrame.GAME_BGCOLOR);// ���� ����
			setLocation(0, GameFrame.GAME_HEIGHT - LP_HEIGHT);// ��ġ ����
			gameStart.setBounds(300, 50, 350, 40);// ���ӽ��� ��ư ��ġ, ũ�� ����
			gameStart.setForeground(Color.white);// ���ӽ��� ��ư �۾� ��
			MenuMouseListener listener = new MenuMouseListener();
			gameStart.addMouseListener(listener);// ���ӽ��۹�ư�� ���콺 ������ linstener �߰�
			add(gameStart);// ���ӽ��۹�ư �гο� �߰�
		}

		class MenuMouseListener extends MouseAdapter {// ���콺 ������Ŭ���� ����
			@Override
			public void mouseEntered(MouseEvent e) {// ���콺�� ��ư���� �ö��� ��
				JLabel menu = (JLabel) e.getSource();
				menu.setFont(new Font("Verdana", Font.BOLD, 40));// ��Ʈ ����
				menu.setBounds(270, 50, 350, 40);// ũ�⸸ �����ϸ� ���ϴ´�� ȿ���� �ȳ�Ÿ���� ��¦ ���������� ���ڰ� �и��� Ŀ���� �����̾ ��ġ ����
			}

			@Override
			public void mouseExited(MouseEvent e) {// ���콺�� ��ư������ ������ ��
				JLabel menu = (JLabel) e.getSource();
				menu.setFont(new Font("Verdana", Font.BOLD, 30));// ������� ��Ʈ����
				menu.setBounds(300, 50, 350, 40);// ���� ��ġ�� ���ƿ�
			}

			@Override
			public void mouseClicked(MouseEvent e) {// ��ư�� ������ ��
				JComponent source = (JComponent) e.getSource();
				GameFrame gameFrame = (GameFrame) source.getTopLevelAncestor();
				gameFrame.toStageSelectPanel();// �������� ���� �гη� �г� ����
			}
		}
	}

	class FirstPageLabel extends JLabel {// ���ӽ��� ��ư ���鶧 ����� Ŭ����
		Font fnt = new Font("Verdana", Font.BOLD, 30);// ��Ʈ����

		public FirstPageLabel(String txt) {// ������ ����
			setText(txt);
			setFont(fnt);
		}
	}

}
