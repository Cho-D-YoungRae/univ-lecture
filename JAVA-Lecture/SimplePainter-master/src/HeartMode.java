import java.awt.*;

// 하트 모양을 그린다.
public class HeartMode extends DrawModeImp{
    public HeartMode(SimplePainterModel simplePainterModel) {
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

        // 하트는 작은 반원 두 개와 큰 반원 하나로 그리고 이를 위해
        // 전체 너비를 특정 크기로 나누어 사용하였다.
        int dividedWidth, dividedHeight;

        dividedWidth = (int)Math.round(width/4.0);
        dividedHeight = (int)Math.ceil(height/3.0);

        // 색 설정
        page.setColor(simplePainterModel.selectedColor);
        // 색이 채워져 있는 도형
        if(simplePainterModel.bFill){
            page.fillArc(x, y,
                    2*dividedWidth, 2*dividedHeight,
                    0, 180);
            page.fillArc(x + dividedWidth*2, y,
                    2*dividedWidth, 2*dividedHeight,
                    0, 180);
            page.fillArc(x, y - dividedHeight,
                    width, height + dividedHeight,
                    0, -180);
        }
        // 테두리만 있는 도형
        else{
            Graphics2D page2 = (Graphics2D) page;
            page2.setStroke(new BasicStroke(simplePainterModel.nSize));
            page.drawArc(x, y,
                    2*dividedWidth, 2*dividedHeight,
                    0, 180);
            page.drawArc(x + dividedWidth*2, y,
                    2*dividedWidth, 2*dividedHeight,
                    0, 180);
            page.drawArc(x, y - dividedHeight,
                    width, height + dividedHeight,
                    0, -180);
        }
    }

}
