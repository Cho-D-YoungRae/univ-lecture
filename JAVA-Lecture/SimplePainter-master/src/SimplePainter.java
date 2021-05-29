import javax.swing.*;

public class SimplePainter {

    public static void main(String[] args) {
        // 전체 프레임을 생성
        JFrame frame = new JFrame("SIMPLE PAINTER");
        // 창을 닫으면 프로그램이 종료 되도록
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 창 사이즈를 변경하지 못하도록
        frame.setResizable(false);

        // 화면에 나타낼 부분을 생성 후 프레임에 추가
        SimplePainterView view = new SimplePainterView();
        frame.getContentPane().add(view);

        frame.pack();
        frame.setVisible(true);
    }
}
