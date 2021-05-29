import java.awt.*;

// 그리기에 대한 데이터를 가지고 있다.
public class SimplePainterModel {

    public DrawMode drawMode;   // 그리기 모드. 이를 통해 하나의 메소드로 모든 그리기를 통일
    public Point ptOne, ptTwo;
    public int nSize;
    public boolean bFill, bReg;
    public Color selectedColor;

    // 처음 아무 설정도 없을 때에 대한 생성자
    public SimplePainterModel() {
        setDrawMode(Constants.NONE);
        ptOne = new Point();
        ptTwo = new Point();
        nSize = 1;
        bFill = false;
        selectedColor = Color.black;
    }

    // 인자로 받은 데이터를 통해 생성하는 생성자
    public SimplePainterModel(SimplePainterModel data) {
        setDrawMode(data.getDrawModeConst());
        ptOne = data.ptOne;
        ptTwo = data.ptTwo;
        nSize = data.nSize;
        bFill = data.bFill;
        bReg = data.bReg;
        selectedColor = data.selectedColor;
    }

    // 인자로 받은 정수에 따라 그에 맞는 그리기 모드를 설정
    public void setDrawMode(int nDrawMode){
        switch(nDrawMode){
            case Constants.DOT:
                this.drawMode = new DotMode(this);
                break;
            case Constants.LINE:
                this.drawMode = new LineMode(this);
                break;
            case Constants.RECT:
                this.drawMode = new RectMode(this);
                break;
            case Constants.OVAL:
                this.drawMode = new OvalMode(this);
                break;
            case Constants.TRI:
                this.drawMode = new TriMode(this);
                break;
            case Constants.HEART:
                this.drawMode = new HeartMode(this);
                break;
            default:
                this.drawMode = new NoneMode(this);
        }
    }
    // 지금 이 모델의 그리기 모드가 무엇인지 상수로 반환
    public int getDrawModeConst() {
        if(drawMode instanceof DotMode)
            return Constants.DOT;
        else if(drawMode instanceof LineMode)
            return Constants.LINE;
        else if (drawMode instanceof RectMode)
            return Constants.RECT;
        else if(drawMode instanceof OvalMode)
            return Constants.OVAL;
        else if(drawMode instanceof TriMode)
            return Constants.TRI;
        else if(drawMode instanceof HeartMode)
            return Constants.HEART;
        else
            return Constants.NONE;
    }

    // 상속을 통해 구현되어 있는 그리기 모드의 draw를 통해 어떤 그리기 모드이든
    // 하나의 draw의 메소드 호출로 가능하다
    // state 패턴
    public void draw(Graphics page){
        drawMode.draw(page);
    }

    // 이 모델의 좌표를 반환한다.
    // 점은 하나의 좌표만 나타내고 나머지는 왼쪽 맨위와 오른쪽 맨 아래 좌표를 나타낸다
    public String getPointStr() {
        String pointStr = "(" + ptOne.x + ", " + ptOne.y + ")";
        if(getDrawModeConst() != Constants.DOT)
            pointStr += "(" + ptTwo.x + ", " + ptTwo.y + ")";

        return pointStr;
    }
}
