import java.awt.*;

// DrawMode 인터페이스를 구현한 것이다.
// 그리기 모드들은 이 클래스를 상속한다.
public class DrawModeImp implements DrawMode {
    SimplePainterModel simplePainterModel;

    // 생성자는 매개변수로 받은 모델(이 그리기 모드가 그리기 위해 사용할 데이터)을 받는다
    public DrawModeImp(SimplePainterModel simplePainterModel){
        this.simplePainterModel = simplePainterModel;
    }

    // 이는 아직 상속한 그리기 모드들이 구현해야 한다.
    @Override
    public void draw(Graphics page) {
        return;
    }

    // 사각형, 원 등을 그릴 때 마우스의 위치에 따라 좌표를 다르게 설정해줘야 한다.
    // 이를 위해 좌표를 조정해줄 메소드를 전역으로 정의해 다른 그리기 모드들에서도
    // 사용할 수 있게 하였다.
    // x좌표 조정
    public static int coordinateX(Point ptOne, Point ptTwo){
        if(ptOne.x < ptTwo.x)
            return ptOne.x;
        else
            return ptTwo.x;
    }
    // y좌표 조정
    public static int coordinateY(Point ptOne, Point ptTwo){
        if(ptOne.y < ptTwo.y)
            return ptOne.y;
        else
            return ptTwo.y;
    }


}
