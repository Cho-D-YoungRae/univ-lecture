import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ClearPanel extends FinishPanel implements Runnable{ // 게임 종료 화면 중 성공화면. FinishPanel을 상속받으며 스레드 구동을 위해 Runnable 인터페이스 구현
	private JLabel gameClear; // 성공 문구 출력을 위한 JLabel
	private JLabel score; // 벽돌을 다 부쉈다는 문구 출력을 위한 JLabel
	private JLabel restart; // 아무데나 클릭하라는 문구 출력을 위한 JLabel
	private Thread labelThread; // 스레드 구동을 위한 인스턴스
	private Font fnt;
	
	public ClearPanel(int stageNum){
        super(); // 슈퍼클래스의 생성을 위한 명령어
       
        gameClear = new JLabel("Stage" + stageNum + " Clear");
		gameClear.setFont(new Font("Verdana", Font.BOLD, 60));
		gameClear.setForeground(Color.cyan);
		gameClear.setBounds(0, 100, 800, 80); // 문자열, 폰트, 색, 크기 지정
		gameClear.setHorizontalAlignment(SwingConstants.CENTER); // 문자열 가운데 정렬
		add(gameClear);
		
		score = new JLabel("You Crushed All Bricks!!");
		score.setFont(new Font("Verdana", Font.BOLD, 40));
		score.setForeground(Color.pink);
		score.setBounds(0, 250, 800, 40); // 문자열, 폰트, 색, 크기 지정
		score.setHorizontalAlignment(SwingConstants.CENTER); // 문자열 가운데 정렬
		add(score);
		
		restart = new JLabel("Click Anywhere To Restart");
		restart.setFont(new Font("Verdana", Font.BOLD, 30));
		restart.setForeground(Color.white);
		restart.setBounds(0, 600, 800, 50); // 문자열, 폰트, 색, 크기 지정
		restart.setHorizontalAlignment(SwingConstants.CENTER); // 문자열 가운데 정렬
		add(restart);
	
		labelThread = null; // 스레드 변수 null로 초기화
    }
	 public void start() // 스레드 시작을 위한 메소드. 다른클래스에서 호출되면 run()메소드로 이동
	{
			if(labelThread == null)
				labelThread = new Thread(this); // 스레드 생성
			
			labelThread.start(); // 스레드 구동을 위해 run() 메소드로 이동
	}
		@Override
	public void run() { // 스레드 시작
		while(true) {
			for(int i = 1; i <= 10; i++) // restart 라벨의 문자 크기가 커졌다 작아졌다를 반복함.
			{
				fnt = new Font("Verdana", Font.BOLD, 30 + i);
				restart.setFont(fnt);
				try{
					Thread.sleep(50);
				}catch(Exception e) {}
			}
			
			for(int i = 1; i <= 10; i++)
			{
				fnt = new Font("Verdana", Font.BOLD, 40 - i);
				restart.setFont(fnt);
				try{
					Thread.sleep(50);
				}catch(Exception e) {}
			}
		}	
	}
}
