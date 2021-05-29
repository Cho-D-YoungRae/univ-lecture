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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �������� ����
		setResizable(false);// frame ������ ���� �Ұ�

		contentPane = getContentPane();

		stageSelectPanel = new StageSelectPanel();

		setVisible(true);

		gameFrame = this;
	}

	public void toStageSelectPanel() {// stageSelectPanel�� �г� ����
		changeGamePanel(stageSelectPanel);
	}

	public void toClearPanel(int stageNum) {// ClearPanel�� �г� ����
		ClearPanel clearPanel = new ClearPanel(stageNum);
		clearPanel.start();
		changeGamePanel(clearPanel);
	}

	public void toFailPanel(int brickNum) {// FailPanel�� �г� ����
		FailPanel failPanel = new FailPanel(brickNum);
		failPanel.start();
		changeGamePanel(failPanel);
	}

	public void changeGamePanel(JPanel gamePanel) {// ���ڷ� ���� Panel�� Frame�� ǥ�õ� Panel�� �ٲ��ִ� �޼ҵ�
		contentPane.removeAll();// �� �����
		contentPane.add(gamePanel);// Container�� ���� Frame�� ���ڷ� ���� Panel �߰�

		pack();

		revalidate();// �����̳ʸ� �ٽ� ���̾ƿ�
		repaint();// �ٽ� ǥ��
	}
}
