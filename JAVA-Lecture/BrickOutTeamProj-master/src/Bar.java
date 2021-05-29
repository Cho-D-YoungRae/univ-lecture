import javax.swing.*;
import java.awt.*;


public class Bar extends JLabel {
    private Point barPos;
    private int barWidth = 80, barHeight = 20;
    private int dWidth = 30;

    public Bar() {
        barPos = new Point(GameFrame.GAME_WIDTH/2 - barWidth/2,
                            GameFrame.GAME_HEIGHT - 100);


        setSize(barWidth, barHeight);
        setBackground(Color.white);
        setLocation(barPos);
        setOpaque(true);
    }

    // Bar를 움직이는 메소드
    // 매개변수로 받은 x의 위치로 Bar를 이동시킨다.
    // Bar의 y좌표는 움직이지 않는다.
    public void move(int x){
        barPos.x = x - barWidth/2;
        setLocation(barPos);
    }

    // Bar의 사이즈를 dWidth만큼 증가시킨다.
    public void extendSize() {
        barWidth += dWidth;
        setSize(barWidth, barHeight);
    }
    // Bar의 사이즈를 dWidth만큼 감소시킨다.
    public void reduceSize() {
        barWidth -= dWidth;
        setSize(barWidth, barHeight);
    }
    
    public Point getPt() { return barPos; }
}
