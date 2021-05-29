import java.awt.*;

import javax.swing.*;

// backgrounnd  19  27  50
// blue         67  138 254
// yellow       255 250 100
// pink         255 76  176
// green        83  205 94

public class GameFrame extends JFrame {
	private static GameFrame gameFrame;

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 800;
	public static final Dimension GAME_DIMENSION = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	public static final Color GAME_BGCOLOR = new Color(19, 27, 50);

	private Container contentPane;
	private StageSelectPanel stageSelectPanel;

	public static GameFrame getGameFrame() {
		if (gameFrame == null)
			gameFrame = new GameFrame();
		return gameFrame;
	}

	public GameFrame() {
		setTitle("MSP BrickOut");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 정상적인 종료
		setResizable(false);// frame 사이즈 변경 불가

		contentPane = getContentPane();

		stageSelectPanel = new StageSelectPanel();

		setVisible(true);

		gameFrame = this;
	}

	public void toStageSelectPanel() {// stageSelectPanel로 패널 변경
		changeGamePanel(stageSelectPanel);
	}

	public void toClearPanel(int stageNum) {// ClearPanel로 패널 변경
		ClearPanel clearPanel = new ClearPanel(stageNum);
		clearPanel.start();
		changeGamePanel(clearPanel);
	}

	public void toFailPanel(int brickNum) {// FailPanel로 패널 변경
		FailPanel failPanel = new FailPanel(brickNum);
		failPanel.start();
		changeGamePanel(failPanel);
	}

	public void changeGamePanel(JPanel gamePanel) {// 인자로 받은 Panel로 Frame에 표시될 Panel을 바꿔주는 메소드
		contentPane.removeAll();// 다 지우기
		contentPane.add(gamePanel);// Container를 통해 Frame에 인자로 받은 Panel 추가

		pack();

		revalidate();// 컨테이너를 다시 레이아웃
		repaint();// 다시 표시
	}
}
