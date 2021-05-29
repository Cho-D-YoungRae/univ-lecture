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
		setLayout(null);// null레이아웃 사용
		setPreferredSize(GameFrame.GAME_DIMENSION);// GameFrame과 같은 크기로 설정
		setBackground(GameFrame.GAME_BGCOLOR);// GameFrame에서 지정한 색으로 배경색 설정

		listener = new StageMouseListener();

		StageLabel testStageLabel1 = new StageLabel("1", image1, 11, 270);// 1스테이지 라벨
		StageLabel testStageLabel2 = new StageLabel("2", image2, 292, 270);// 2스테이지 라벨
		StageLabel testStageLabel3 = new StageLabel("3", image3, 572, 270);// 3스테이지 라벨
		StageLabel exitLabel = new StageLabel("4", exit, 720, 0);// 게임종료 라벨
		// 각 라벨에 마우스리스너 추가
		testStageLabel1.addMouseListener(listener);
		testStageLabel2.addMouseListener(listener);
		testStageLabel3.addMouseListener(listener);
		exitLabel.addMouseListener(listener);
		// 패널에 라벨추가
		add(testStageLabel1);
		add(testStageLabel2);
		add(testStageLabel3);
		add(exitLabel);
	}

	private class StageLabel extends JLabel {
		Font fnt, bigFnt;
		int _stageLevel;

		public StageLabel(String Level, ImageIcon icon, int x, int y) {
			super(icon);// 원래 생성자 처럼 이미지 라벨에 적용
			setOpaque(true);// 배경색 적용
			setBackground(GameFrame.GAME_BGCOLOR);// GameFrame에서 지정한 색으로 배경색 설정
			_stageLevel = Integer.parseInt(Level);
			// 게임종료 라벨만 사이즈 다르게 설정
			if (_stageLevel == 4)
				setSize(70, 70);
			else
				setSize(220, 220);
			setLocation(x, y);// x,y로 위치 설정

			fnt = new Font("Verdana", Font.BOLD, 60);
			bigFnt = new Font("Verdana", Font.BOLD, 80);
			setFont(fnt);

			// 글자 정렬
			setHorizontalAlignment(SwingConstants.CENTER);
			setVerticalAlignment(SwingConstants.CENTER);
		}
	}

	private class StageMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			StageLabel source = (StageLabel) e.getSource();
			int stageLevel = source._stageLevel;// 클릭한 라벨의 번호 가져옴

			// x누르면 게임종료
			if (stageLevel == 4)
				System.exit(0);
			else// 다른 스테이지 라벨누르면 스테이지로 패널변경
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
		public void mouseEntered(MouseEvent e) {// 마우스가 라벨위에 올라갔을때 라벨이미지 변경
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
		public void mouseExited(MouseEvent e) {// 마우스가 나왔을대 원래이미지로 변경
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
