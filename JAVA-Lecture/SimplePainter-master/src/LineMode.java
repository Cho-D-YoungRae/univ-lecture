import java.awt.*;

public class LineMode extends DrawModeImp {

    public LineMode(SimplePainterModel simplePainterModel) {
        super(simplePainterModel);
    }

    @Override
    public void draw(Graphics page) {
        // 색 설정
        page.setColor(simplePainterModel.selectedColor);
        Graphics2D page2 = (Graphics2D) page;
        // 선 굵기 설정
        page2.setStroke(new BasicStroke(simplePainterModel.nSize));
        page.drawLine(simplePainterModel.ptOne.x, simplePainterModel.ptOne.y,
                simplePainterModel.ptTwo.x, simplePainterModel.ptTwo.y);
    }
}
