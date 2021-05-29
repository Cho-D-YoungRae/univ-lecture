import java.awt.*;
import java.util.ArrayList;

public class TriMode extends DrawModeImp {

    public TriMode(SimplePainterModel simplePainterModel){
        super(simplePainterModel);
    }

    @Override
    public void draw(Graphics page) {
        int x, y, width, height;
        Point pt1, pt2;
        pt1 = simplePainterModel.ptOne;
        pt2 = simplePainterModel.ptTwo;

        // 마우스의 위치에 따라 좌표 및 너비, 높이 조정
        x = coordinateX(pt1, pt2);
        y = coordinateY(pt1, pt2);
        width = Math.abs(pt1.x - pt2.x);
        height = Math.abs(pt1.y - pt2.y);

        // bReg는 높이와 너비가 같은 도형을 그릴지에 대한 변수이다.
        // 이에 따른 너비와 높이 조정
        if(simplePainterModel.bReg){
            if(width < height)
                height = width;
            else
                width = height;
        }

        // 삼각형을 그리기 위한 3개의 꼭짓점 좌표
        int xArr[] = {x, x + width/2, x + width};
        int yArr[] = {y + height, y, y + height};

        page.setColor(simplePainterModel.selectedColor);
        // 색이 채워져 있는 도형
        if(simplePainterModel.bFill)
            page.fillPolygon(xArr, yArr, 3);
        // 테두리만 있는 도형
        else{
            Graphics2D page2 = (Graphics2D) page;
            page2.setStroke(new BasicStroke(simplePainterModel.nSize));
            page.drawPolygon(xArr, yArr, 3);
        }
    }

}
