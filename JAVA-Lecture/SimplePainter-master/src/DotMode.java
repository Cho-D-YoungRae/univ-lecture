import java.awt.*;

// 점 그리기 모드
public class DotMode extends DrawModeImp {
    // 모델의 정보를 이용해서 그려야되므로 현재 모델의 정보를 받는다.
    public DotMode(SimplePainterModel simplePainterModel) {
        super(simplePainterModel);
    }

    @Override
    public void draw(Graphics page) {
        // 모델의 정보를 통해 점을 그린다
        page.setColor(simplePainterModel.selectedColor);    // 색 설정
        page.fillOval(simplePainterModel.ptOne.x - simplePainterModel.nSize / 2,
                simplePainterModel.ptOne.y - simplePainterModel.nSize / 2,
                simplePainterModel.nSize, simplePainterModel.nSize);
    }
}
