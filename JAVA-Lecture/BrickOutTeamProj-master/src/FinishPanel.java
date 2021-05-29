import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FinishPanel extends JPanel { // 게임 종료화면의 공통적인 속성들을 가지고 있는 클래스. FailPanel(실패화면)과 ClearPanel(성공화면)이 상속받는다.
    GameFrame gameFrame;

    public FinishPanel(){
        setLayout(null);
        setPreferredSize(GameFrame.GAME_DIMENSION); // 프레임과 같은 크기로 설정
        setBackground(GameFrame.GAME_BGCOLOR); // 프레임과 같은 색으로 배경색 설정

        gameFrame = GameFrame.getGameFrame(); // 프레임 정보 얻어옴

        addMouseListener(new MouseAdapter() { // 마우스 클릭시 게임 종료하고 스테이지 선택 패널로 화면 전환
            @Override
            public void mouseClicked(MouseEvent e) {
                gameFrame.toStageSelectPanel();
            }
        });
    }
}
