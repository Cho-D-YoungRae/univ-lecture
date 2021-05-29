import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;


public class Stage extends JPanel {

	public static int Intervalbr = 4;	// 스테이지 내 블럭 배치 시 블럭 사이 간격
	public static int BrWidth = Brick.BRICK_WIDTH + 3;
	public static int BrHeight = Brick.BRICK_HEIGHT + 3; // Brick Height + brick interval

	private Vector<Ball> balls;
	private Bar bar;
	private BarMover barMover;
	private Brick[][] bricks;
	private int ballCount;
	private int brickCount;
	private int stageLevel;



	public Stage(int stageLevel) {
		setLayout(null);
		setPreferredSize(GameFrame.GAME_DIMENSION);
		setBackground(GameFrame.GAME_BGCOLOR);

		this.stageLevel = stageLevel;

		// Stage 클래스 생성시 매개변수로 받은 스테이지 레벨에 따른 블랙 배치
		switch (stageLevel) {
		case 1:
			initStage1();
			break;
		case 2:
			initStage2();
			break;
		case 3:
			initStage3();
			break;
		}

		// 공을 튀기는 Bar 생성
		bar = new Bar();
		add(bar);

		// 공 생성.
		// ballCount는 일단 0으로 초기화 -> addBall 메소드에서 공이 추가되며 함께 증가
		ballCount = 0;
		balls = new Vector<>();
		addBall();

		// Bar를 움직이게하는 이벤트 리스너
		barMover = new BarMover();
		addMouseListener(barMover);
		addMouseMotionListener(barMover);
		setFocusable(true);
		requestFocus();
	}

	// 매개변수로 받은 Brick의 위치에 Item을 생성한다.
	public void addItem(Point brickPos) {
		Item item = new Item(this, new Point(brickPos));
		add(item);
		item.start();
	}
	// Stage에 Ball을 추가한다.
	public void addBall(){
		Ball ball = new Ball(this);
		ballCount++;
		add(ball);
		ball.start();
		balls.add(ball);
	}

	// Stage에 있는 공 중 매개변수로 받은 공을 제거한다.
	public void removeBall(Ball ball) {
		balls.remove(ball);
		remove(ball);
	}

	public void reduceBallCount() {
		ballCount--;
	}
	public int getBallCount() { return ballCount; }

	// 모든 공들의 속도를 증가 시킨다.
	public void speedUpBalls() {
		Ball.speedUp();	// static 변수로 되어있는 공 속도를 static메소드로 값 변경 하여 전체 공 속도 조절
	}

	public void speedDownBalls() {
		Ball.speedDown();	// static 변수로 되어있는 공 속도를 static메소드로 값 변경 하여 전체 공 속도 조절
	}
	// 모든 공의 속도가 같으(static변수로 되어있기 때문)므로
	// balls(ball 이 저장되어있는 Vector)에 저장되어있는 공들 중
	// 가장 첫 ball(가장 첫 공은 게임이 끝나지 않는 이상 꼭 존재) 속도만 반환해주면 된다.
	public int getBallsSpeed(){
		return balls.get(0).getBallSpeed();
	}
	
	public void reduceBrickCount() { brickCount--;	}
	public int getBrickCount() { return brickCount; }
	public Bar getBar() { return bar; }
	public Brick[][] getBricks() { return bricks; }

	// 스테이지 레벨1에 대한 블럭 배치
	private void initStage1() {
		bricks = new Brick[7][15];
		int type = 1;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 15; j++) {
				bricks[i][j] = new Brick(this, (j * 53) + 4, (i * 43) + 4, type);
				add(bricks[i][j]);
			}
			type++;
			if (type > 2)
				type = 1;
		}
		
		brickCount = 7 * 15;
	}
	// 스테이지 레벨2에 대한 블럭 배치
	private void initStage2() {
		
		bricks = new Brick[12][15];
		int type = 1;
		int start = 0, finish = 14;
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 15; j++) {
				bricks[i][j] = new Brick(this, (j * BrWidth) + Intervalbr, (i * BrHeight) + Intervalbr, type);
				add(bricks[i][j]);
				if (i == 0) {
					if (j == 4 || j == 10) {
					} else
						bricks[i][j].delBrick();
				}
				if (i == 1) {
					if (j == 3 || j == 4 || j == 5 || j == 9 || j == 10 || j == 11) {
					} else
						bricks[i][j].delBrick();
				}
				if (i == 2) {
					if (j >= 2 && j <= 6 || j >= 8 && j <= 12) {
					} else
						bricks[i][j].delBrick();

				}
				if (i == 3) {
					if (j >= 1 && j <= 13) {
					} else
						bricks[i][j].delBrick();

				}
				if (i >= 4) {
					if (j >= start && j <= finish) {
					} else
						bricks[i][j].delBrick();

				}

			}
			type++;
			if (type > 3)
				type = 1;

			if (i >= 4) {
				start++;
				finish--;
			}

		}
		
		brickCount = 95;
	}

	// 스테이지 레벨3에 대한 블럭 배치
	private void initStage3() {
		bricks = new Brick[1][1];
		int type = 1;
		for(int i=0;i<1;i++){
			for(int j=0;j<1;j++) {
				bricks[i][j] = new Brick(this,(j * 53) + 400, (i * 43) + 300, type);
				add(bricks[i][j]);
			}
		}
		
		brickCount = 1 * 1;
	}

	// Bar를 움직이게하는 이벤트 리스너
	private class BarMover extends MouseAdapter {
		// 마우스의 위치에 따라 x좌표를 받아 좌우로 움직인다.
		@Override
		public void mouseMoved(MouseEvent e) {
			bar.move(e.getX());
		}
	}
	public int getStageLevel() { return stageLevel; }

}
