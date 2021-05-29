import java.awt.*;

// 아무런 그리기 모드도 설정되어 있지 않은 상태이다.
public class NoneMode extends DrawModeImp {
    public NoneMode(SimplePainterModel simplePainterModel) {
        super(simplePainterModel);
    }

    @Override
    public void draw(Graphics page) { }
}
