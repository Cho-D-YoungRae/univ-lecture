import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

// MVC 패턴의 Controller 부분으로
// Model에 명령을 보내 데이터의 상태를 바꾸고
// View에 명령을 보내 어떤 화면을 보내줄지 결정
public class DrawController extends JPanel {

    private SimplePainterModel nowData;
    private ArrayList<SimplePainterModel> savedList;    // 지금까지 그려진 데이터를 저장
    private ArrayList<SimplePainterModel> undoList; //  undo(되돌리기)를 한 데이터를 저장 -> redo(되돌리기를 되돌리기) 하기위해
    private DrawListener drawL;
    private SimplePainterView view;
    private boolean bDrag;

    public DrawController() {
        // Singleton 패턴으로써 프로그램내에 하나만 생성되는 view를 어디서든 접근할 수 있도록 되어있다.
        this.view = SimplePainterView.getInstance();

        setBackground(Color.white);

        drawL = new DrawListener();
        addMouseListener(drawL);
        addMouseMotionListener(drawL);

        nowData = new SimplePainterModel();
        savedList = new ArrayList<>();
        undoList = new ArrayList<>();

        nowData.setDrawMode(Constants.NONE);

        bDrag = false;
    }

    // 그리기 모드를 설정한다
    public void setDrawMode(int mode) {
        // 매개변수로 주어진 정수에 따라 그리기 모드를 설정한다.
        nowData.setDrawMode(mode);
        // 점을 그릴때는 그리기 크기를 10으로 해주고 나머지 경우에는 1로 해준다.
        if(nowData.getDrawModeConst() == Constants.DOT){
            view.setDrawSize(10);
        } else
            view.setDrawSize(1);
    }

    // 현재 그리려는 model의 색을 설정해준다.
    public void setSelectedColor(Color color){
        nowData.selectedColor = color;
    }

    // 그린 것을 하나씩 되돌린다.
    public void undo(){
        // 그려져 있는 model이 있을 때 작동
        if(savedList.size() > 0){
            int topIdx = savedList.size() - 1;  // 가장 마지막에 그려진 것부터
            undoList.add(savedList.get(topIdx));    // redo를 위한 리스트에 저장 후
            savedList.remove(topIdx);   // 제거
            repaint();  // 데이터를 제거한 후 화면에 적용하기위해 repaint
        }
    }
    // undo 한 것을 되돌린다.
    public void redo() {
        if(undoList.size() > 0){    // undo를 한 적이 있을 때 작동
            int topIdx = undoList.size() - 1;   // 가장 마지막에 undo 한 것부터
            savedList.add(undoList.get(topIdx));    // 다시 화면에 그려지는 데이터 리스트에 저장
            undoList.remove(topIdx);    // 제거
            repaint();  // 데이터를 제거한 후 화면에 적용하기위해 repaint
        }
    }
    // 화면에 그려진 것을 모두 지운다.
    public void clear(){
        // 그려진 것을 redo를 위한 리스트에 저장 후
        for(int i = savedList.size() - 1; i >= 0 ; i --)
            undoList.add(savedList.get(i));
        // 리스트를 아예 비운다.
        savedList.clear();
        repaint();  // 데이터를 제거한 후 화면에 적용하기위해 repaint
    }

    // redo를 위한 undo리스트를 제거
    // redo 한 후 다시 다른 그리기 등을 했을 때
    // 이 리스트를 지워야 한다.
    public void clearUndoList() {
        if(undoList.size() > 0)
            undoList.clear();
    }

    // 지금까지 저장된 model 및 현재 그리고 있는 model 을 화면에 표시
    public void paintComponent(Graphics page){
        super.paintComponent(page);

        for(SimplePainterModel data: savedList)
            data.draw(page);

        // 현재 그리고 있는 그림을 가장 위에 나타나게 하기위해
        // 이 조건문을 가장 아래에 둔다.
        if (bDrag)
            nowData.draw(page);
    }

    // 그림을 그리기 위한 이벤트 리스너
    private class DrawListener extends MouseAdapter {
        // 클릭했을 때 점을 그린다
        @Override
        public void mouseClicked(MouseEvent e) {
            // 그리기 모드가 점일때
            if(nowData.getDrawModeConst() == Constants.DOT) {
                // 현재 그리는 데이터에 클릭된 곳 좌표 받는다.
                nowData.ptOne = e.getPoint();
                // 현재 설정되어있는 그리기 크기를 받는다.
                nowData.nSize = view.getDrawSize();
                // 이 정보를 바탕으로 그리기 데이터를 생성하여 저장한다.
                savedList.add(new SimplePainterModel(nowData));

                // 메세지 패널에 클릭된 좌표를 보여준다.
                view.setPointLabelText(nowData.getPointStr());
                // 그리기 전에 undo를 했었으면 이 리스트 데이터를 이제 지운다
                clearUndoList();
                repaint();
            }

        }

        // 마우스가 눌러진 상태일 때
        @Override
        public void mousePressed(MouseEvent e) {
            int nDrawMode = nowData.getDrawModeConst();
            // 점 이외에 다른 것을 그린다.
            if(nDrawMode > Constants.DOT && nDrawMode < Constants.NONE) {
                bDrag = true;   // 현재 드레그 중이라는 상태

                nowData.ptOne = e.getPoint();   // 처음 누를 때는 첫 좌표를 받는다
                nowData.nSize = view.getDrawSize(); // 그리기 크기
                nowData.bFill = view.getChkFill();  // 색이 있는 도형을 그릴지 확인
                nowData.bReg = view.getChkReg();    // 가로 세로 같은 크기의 도형을 그릴지 확인
            }

        }

        // 마우스 버튼이 놓아졌을 때
        @Override
        public void mouseReleased(MouseEvent e) {
            int nDrawMode = nowData.getDrawModeConst();
            // 점 이외 다른 것에 대하여
            if(nDrawMode > Constants.DOT && nDrawMode < Constants.NONE) {
                bDrag = false;  // 이제 드래그가 끝남

                nowData.ptTwo = e.getPoint();   // 두번재 점의 좌표 받는다
                savedList.add(new SimplePainterModel(nowData)); // 현재 데이터로 모델을 새로 생성
                view.setPointLabelText(nowData.getPointStr());  // 그림을 그리는 데이터 모델 좌표 보여주기
                clearUndoList();    // 새로 그림을 그렸으므로 undo를 위한 리스트 지우기
                repaint();  // 새로 그린 데이터를 화면에 보여주기 위해 repaint
            }
        }

        // 드래그 중일 때
        @Override
        public void mouseDragged(MouseEvent e) {
            // 점 이외에 다른 것에 대하여
            if(nowData.getDrawModeConst() != Constants.DOT) {
                nowData.ptTwo = e.getPoint();   // 드래그 중인 곳에 대해 두번째 좌표 변경
                view.setPointLabelText(nowData.getPointStr());  // 그리는 중인 데이터 좌표 보여주기
                repaint();  // 현재 그리는 중인 그림을 드래그에 따라 계속 변경시켜 보여주기 위해
            }
        }
    }
}
