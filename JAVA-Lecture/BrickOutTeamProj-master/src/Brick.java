import java.awt.*;
import javax.swing.*;

import javax.swing.*;

// backgrounnd  19  27  50
// blue         67  138 254
// yellow       255 250 100
// pink         255 76  176
// green        83  205 94

public class Brick extends JLabel {
	public static final int BRICK_WIDTH = 50, BRICK_HEIGHT = 40; 
	public static final Dimension BRICK_SIZE = new Dimension(BRICK_WIDTH, BRICK_HEIGHT); //
	public static final Color BRICK_YELLOW =  new Color(255, 250, 100), 
							    BRICK_PINK = new Color(255, 76, 176),
							   BRICK_GREEN = new Color(83, 205, 94), 
							    BRICK_BLUE = new Color(67, 138, 254);

	private int dur; //벽돌의 강도
	private Point brickPt; //벽돌의 좌표
	private Color brickColor; //벽돌의 색깔
	private Stage stage; //벽돌이 속해있는 stage


	public Brick(Stage stage, int x, int y) { //Brick 생성자
		this.stage = stage; //속해있는 stage 정보 저장
		dur = 1; //강도 1로 저장
		brickPt = new Point(x, y); //좌표값 저장
		brickColor = BRICK_YELLOW; //색깔 설정
		setSize(BRICK_SIZE);	   //사이즈 설정
		setLocation(brickPt);
		setBackground(brickColor);
		setOpaque(true); //JLabel인 벽돌의 색을 칠하기위해 setOpaque  true값 적용
	}

	public Brick(Stage stage, int x, int y, int brickType) { // brickType : ywello, pink, green, blue
		this.stage = stage; ///Brick 생성자
		dur = 1;	//강도 1로 저장
		brickPt = new Point(x, y);	//좌표값 저장

		switch (brickType) { //bricktype은 int값으로 stage에서 Brick 생성할때 1=yellow,2=pink,3=green,4=blue로 설정하여 stage구성함
		case 1:
			brickColor = BRICK_YELLOW; //노랑대입
			dur = 1;//강도 1
			break;
		case 2:
			brickColor = BRICK_PINK; //핑크대입
			dur = 2; //강도 2
			break;
		case 3:
			brickColor = BRICK_GREEN; //초록대입
			dur = 3; //강도 3
			break;
		case 4:
			brickColor = BRICK_BLUE; //파랑 대입
			dur = 4; //강도 4
			break;
		}

		setSize(BRICK_SIZE);//사이즈 셋팅
		setLocation(brickPt); //위치 셋팅
		setBackground(brickColor); //배경색 적용
		setOpaque(true);
	}

	public void setDurability(int a) { //강도 적용
		dur = a; // BrickTest
	}

	public int getBrickLeftX() { return brickPt.x; } //x좌표 리턴
	public int getBrickRightX() { return brickPt.x + BRICK_WIDTH; } //x+폭 리턴 
	public int getBrickUpperY() { return brickPt.y; } //y 좌표 리턴
	public int getBrickBottomY() { return brickPt.y + BRICK_HEIGHT; } //y+높이 리턴


	public int getDurability() {//강도 리턴
		return dur;
	} // BrickTest


	public void clearBrick() { //벽돌 안보이게 하는 메소드
		this.setVisible(false); //안보이게함
		if((int)(Math.random() * (100 + 1)) < Item.ITEM_DROP_PROB)//일정확률로 stage에 아이템 추가되게함
			stage.addItem(brickPt);
	}
	public void delBrick() { //벽돌 지우는 메소드
		this.setVisible(false);
		setDurability(0); //강도 0으로 설정
	}

}
