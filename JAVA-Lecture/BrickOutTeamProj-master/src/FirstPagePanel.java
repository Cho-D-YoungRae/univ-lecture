import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class FirstPagePanel extends JPanel {
	private final int UPPER_WIDTH = GameFrame.GAME_WIDTH, UPPER_HEIGHT = GameFrame.GAME_HEIGHT / 5 * 3; // 사용할 사이즈를
																										// GameFrame의
																										// 넓이와 높이를 통해 설정

	public FirstPagePanel() {

		setLayout(null);
		setPreferredSize(GameFrame.GAME_DIMENSION);// GameFrame의 사이즈와 똑같은 사이즈로 설정
		setBackground(Color.orange); // for debugging

		add(new UpperPanel());// FirstPagePanel의 위아래로 나눠서 패널을 추가
		add(new LowerPanel());
	}

	class UpperPanel extends JPanel {// 게임타이틀이 표시될 위쪽 패널
		public UpperPanel() {
			ImageIcon image1 = new ImageIcon("images/brickout.PNG");// 게임 첫 화면 로고 이미지를 image1으로 설정
			setLayout(null);
			setSize(new Dimension(UPPER_WIDTH, UPPER_HEIGHT));
			setBackground(GameFrame.GAME_BGCOLOR);
			setLocation(0, 0);
			JLabel lbltitle = new JLabel(image1);// image1을 JLabel을 생성할 때 인자로 넣어줌
			lbltitle.setBounds(80, 100, 643, 330);

			add(lbltitle);// UpperPanel에 게임타이틀 추가
		}
	}

	class LowerPanel extends JPanel {// 게임시작 버튼이 있을 아래쪽 패널
		private final int LP_HEIGHT = GameFrame.GAME_HEIGHT - UPPER_HEIGHT;// GameFrame의 높이에서 UpperPanel의 높이를 뺀만큼
		private FirstPageLabel gameStart = new FirstPageLabel("Game Start");

		public LowerPanel() {
			setLayout(null);// null레이아웃 사용
			setSize(GameFrame.GAME_WIDTH, LP_HEIGHT);// 사이즈 지정
			setBackground(GameFrame.GAME_BGCOLOR);// 배경색 지정
			setLocation(0, GameFrame.GAME_HEIGHT - LP_HEIGHT);// 위치 지정
			gameStart.setBounds(300, 50, 350, 40);// 게임시작 버튼 위치, 크기 지정
			gameStart.setForeground(Color.white);// 게임시작 버튼 글씨 색
			MenuMouseListener listener = new MenuMouseListener();
			gameStart.addMouseListener(listener);// 게임시작버튼에 마우스 리스너 linstener 추가
			add(gameStart);// 게임시작버튼 패널에 추가
		}

		class MenuMouseListener extends MouseAdapter {// 마우스 리스너클래스 생성
			@Override
			public void mouseEntered(MouseEvent e) {// 마우스가 버튼위에 올라갔을 때
				JLabel menu = (JLabel) e.getSource();
				menu.setFont(new Font("Verdana", Font.BOLD, 40));// 폰트 변경
				menu.setBounds(270, 50, 350, 40);// 크기만 변경하면 원하는대로 효과가 안나타나고 살짝 오른쪽으로 글자가 밀리며 커지는 느낌이어서 위치 조정
			}

			@Override
			public void mouseExited(MouseEvent e) {// 마우스가 버튼위에서 나갔을 때
				JLabel menu = (JLabel) e.getSource();
				menu.setFont(new Font("Verdana", Font.BOLD, 30));// 원래대로 폰트변경
				menu.setBounds(300, 50, 350, 40);// 원래 위치로 돌아옴
			}

			@Override
			public void mouseClicked(MouseEvent e) {// 버튼을 눌렀을 때
				JComponent source = (JComponent) e.getSource();
				GameFrame gameFrame = (GameFrame) source.getTopLevelAncestor();
				gameFrame.toStageSelectPanel();// 스테이지 선택 패널로 패널 변경
			}
		}
	}

	class FirstPageLabel extends JLabel {// 게임시작 버튼 만들때 사용할 클래스
		Font fnt = new Font("Verdana", Font.BOLD, 30);// 폰트설정

		public FirstPageLabel(String txt) {// 생성자 설정
			setText(txt);
			setFont(fnt);
		}
	}

}
