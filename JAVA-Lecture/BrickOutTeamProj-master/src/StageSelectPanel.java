import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class StageSelectPanel extends JPanel {

	public ImageIcon image1OnMouse = new ImageIcon("images/1m.PNG");
	public ImageIcon image2OnMouse = new ImageIcon("images/2m.PNG");
	public ImageIcon image3OnMouse = new ImageIcon("images/3m.PNG");
	public ImageIcon exiton = new ImageIcon("images/exiton.PNG");
	public ImageIcon image1 = new ImageIcon("images/1.PNG");
	public ImageIcon image2 = new ImageIcon("images/2.PNG");
	public ImageIcon image3 = new ImageIcon("images/3.PNG");
	public ImageIcon exit = new ImageIcon("images/exit.PNG");
	private StageMouseListener listener;

	public StageSelectPanel() {
		setLayout(null);// null���̾ƿ� ���
		setPreferredSize(GameFrame.GAME_DIMENSION);// GameFrame�� ���� ũ��� ����
		setBackground(GameFrame.GAME_BGCOLOR);// GameFrame���� ������ ������ ���� ����

		listener = new StageMouseListener();

		StageLabel testStageLabel1 = new StageLabel("1", image1, 11, 270);// 1�������� ��
		StageLabel testStageLabel2 = new StageLabel("2", image2, 292, 270);// 2�������� ��
		StageLabel testStageLabel3 = new StageLabel("3", image3, 572, 270);// 3�������� ��
		StageLabel exitLabel = new StageLabel("4", exit, 720, 0);// �������� ��
		// �� �󺧿� ���콺������ �߰�
		testStageLabel1.addMouseListener(listener);
		testStageLabel2.addMouseListener(listener);
		testStageLabel3.addMouseListener(listener);
		exitLabel.addMouseListener(listener);
		// �гο� ���߰�
		add(testStageLabel1);
		add(testStageLabel2);
		add(testStageLabel3);
		add(exitLabel);
	}

	private class StageLabel extends JLabel {
		Font fnt, bigFnt;
		int _stageLevel;

		public StageLabel(String Level, ImageIcon icon, int x, int y) {
			super(icon);// ���� ������ ó�� �̹��� �󺧿� ����
			setOpaque(true);// ���� ����
			setBackground(GameFrame.GAME_BGCOLOR);// GameFrame���� ������ ������ ���� ����
			_stageLevel = Integer.parseInt(Level);
			// �������� �󺧸� ������ �ٸ��� ����
			if (_stageLevel == 4)
				setSize(70, 70);
			else
				setSize(220, 220);
			setLocation(x, y);// x,y�� ��ġ ����

			fnt = new Font("Verdana", Font.BOLD, 60);
			bigFnt = new Font("Verdana", Font.BOLD, 80);
			setFont(fnt);

			// ���� ����
			setHorizontalAlignment(SwingConstants.CENTER);
			setVerticalAlignment(SwingConstants.CENTER);
		}
	}

	private class StageMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			StageLabel source = (StageLabel) e.getSource();
			int stageLevel = source._stageLevel;// Ŭ���� ���� ��ȣ ������

			// x������ ��������
			if (stageLevel == 4)
				System.exit(0);
			else// �ٸ� �������� �󺧴����� ���������� �гκ���
				GameFrame.getGameFrame().changeGamePanel(new Stage(stageLevel));

			if (stageLevel == 1)
				source.setIcon(image1);
			else if (stageLevel == 2)
				source.setIcon(image2);
			else if (stageLevel == 3)
				source.setIcon(image3);
			else if (stageLevel == 4)
				source.setIcon(exit);
		}

		@Override
		public void mouseEntered(MouseEvent e) {// ���콺�� ������ �ö����� ���̹��� ����
			StageLabel source = (StageLabel) e.getSource();
			if (source._stageLevel == 1)
				source.setIcon(image1OnMouse);
			else if (source._stageLevel == 2)
				source.setIcon(image2OnMouse);
			else if (source._stageLevel == 3)
				source.setIcon(image3OnMouse);
			else if (source._stageLevel == 4)
				source.setIcon(exiton);
		}

		@Override
		public void mouseExited(MouseEvent e) {// ���콺�� �������� �����̹����� ����
			StageLabel source = (StageLabel) e.getSource();
			if (source._stageLevel == 1)
				source.setIcon(image1);
			else if (source._stageLevel == 2)
				source.setIcon(image2);
			else if (source._stageLevel == 3)
				source.setIcon(image3);
			else if (source._stageLevel == 4)
				source.setIcon(exit);

		}
	}
}
