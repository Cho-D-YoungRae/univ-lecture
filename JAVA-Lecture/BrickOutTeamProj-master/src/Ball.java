import javax.swing.*;
import java.awt.*;

public class Ball extends JLabel implements Runnable { // 스레드 구동을 위해 Runnable 인터페이스 구현
    public static final int ballSize = 20; // 공의 크기 상수

    private static int ballSpeed = 10; // 공 속도를 나타내는 변수
    private int angle; // 바에 튕기는 각도
    private int _xDir; // x좌표 방향
    private int _yDir; // y좌표 방향
    private int _xPos; // 공의 중앙 x좌표
    private int _yPos; // 공의 중앙 y좌표
    private double _dx; // x좌표의 변화량
    private double _dy; // y좌표의 변화량


    private Thread ballThread; // 스레드 구현을 위한 인스턴스

    private Stage stage; // 매개변수를 통해 클래스간 연결
    private Bar bar; // 클래스간 연결을 위한 인스턴스
    private Brick[][] bricks; // 벽돌 객체 배열
    private GameFrame gameFrame; // 클래스간 연결을 위한 인스턴스

    public Ball(Stage stage) {
        _xDir = 1;
        _yDir = -1;
        _xPos = 400; 
        _yPos = 690; 
        angle = 90; // 공의 정보 초기화

        this.stage = stage; // 스테이지 객체 연결
        bar = stage.getBar();
        bricks = stage.getBricks();
        gameFrame = GameFrame.getGameFrame(); // 클래스간 연결

        ballThread = null; // 스레드 객체를 null로 초기화

        setOpaque(false); // 배경을 투명하게 하여 패널에 원모양으로 보일 수 있게 함
    } // Ball Constructor

    public void paintComponent(Graphics g) // 패널에 원 그리기 메소드
    {
        super.paintComponent(g);

        g.setColor(Color.white);
        g.fillOval(0, 0, ballSize, ballSize); // 패널 안에 공의 사이즈만큼 흰색 공을 그린다.
    }

    public void start() { // 스레드 시작을 위한 메소드. 다른클래스에서 호출되면 run()메소드로 이동
        if (ballThread == null)
            ballThread = new Thread(this); // 스레드 생성

        ballThread.start(); // 스레드 구동을 위해 run() 메소드로 이동
    }

    @Override
    public void run() // 실질적인 스레드 작업을 하는 run() 메소드
    { 
        while (_yPos + (ballSize / 2) < 820) // 공이 바닥에 떨어지면 스레드는 종료된다
        { 
            if (stage.getBrickCount() == 0) { // 스레드 구동중 스테이지에 남은 벽돌 개수가 0이면
                gameFrame.toClearPanel(stage.getStageLevel()); // 성공했다는 메세지를 출력하는 clearPanel로 화면을 전환한 뒤 게임을 끝낸다.
                break;
            }

            if (checkWallHit() || checkBrickHit() || checkBarHit()) // 벽, 벽돌, 바와의 충돌체크
                System.out.println("Hit");

            _dx = Math.cos(Math.toRadians(angle)) * ballSpeed * _xDir; // 바에 맞은 각도, 공 속도, 뱡향을 종합하여 좌표의 증가량을 계산한다. 각도는 다시 바에 맞기 전까지 유지됨
            _dy = Math.sin(Math.toRadians(angle)) * ballSpeed * _yDir;

            _dx = (int) Math.round(_dx); // setBounds() 메소드는 정수값을 인자로 갖기때문에 정수로 캐스팅
            _dy = (int) Math.round(_dy);

            _xPos += _dx; // 각각의 좌표에 증가량을 더함
            _yPos += _dy;

            setBounds(getLeftXPos(), getUpperYPos(), ballSize, ballSize); // 변경된 좌표를 이용하여 공의 위치를 다시 정해줌. 이때 공의 왼쪽 위 x, y좌표를 사용한다.

            try {
                Thread.sleep(30); // 스레드간 딜레이를 위한 try-catch문
            } catch (Exception e) {}
        }

        if(stage.getBrickCount() > 0)  // 스레드가 종료되었을경우(공이 바닥에 떨어졌을 경우) 
        {
			stage.reduceBallCount(); // 스테이지에 현재 존재하는 공의 개수를 1 줄인다.(아에팀 효과중 공의 개수를 늘리는 아이템이 있음)
			
			if (stage.getBallCount() == 0) // 만약 공의 개수가 줄어들어 0이 되었다면
				gameFrame.toFailPanel(stage.getBrickCount()); // 실패했다는 메세지를 출력하는 failPanel로 화면을 전환한 뒤 게임을 끝낸다.
		}

        stage.removeBall(this); // 공이 바닥에 떨어졌기때문에 필요없으므로 스테이지 패널에서 지운다.
    }

    private boolean checkWallHit() // 공과 벽돌과의 충돌을 체크하는 메소드
    {
        boolean isHit = false; // 맞았는지 여부를 나타내는 변수. false로 초기화
     
        if ((_xDir > 0) && (getRightXPos() > 800)) // 공이 오른쪽으로 진행하다가 x좌표가 프레임 오른쪽 끝의 x좌표보다 커지면 오른쪽 벽과 충돌이 일어난것임
        {
            _xDir = _xDir * -1; // x좌표의 진행방향을 반대로 바꿈
            isHit = true;
        }

        if ((_xDir < 0) && (getLeftXPos() < 0))  // 왼쪽 벽과의 충돌 체크
        {
            _xDir = _xDir * -1;
            isHit = true;
        }

        if ((_yDir < 0) && (getUpperYPos() < 0))  // 위쪽 벽과의 충돌 체크
        {
            _yDir = _yDir * -1; //y좌표의 진행방향을 반대로 바꿈
            isHit = true;
        }

        return isHit; // 맞았는지 여부 반환
    }



    private boolean checkBrickHit() { // 공과 벽돌 간의 충돌을 체크하는 메소드
        int brkDur; // 맞은 벽돌의 내구도 변경을 위한 변수
        int i, j;

        for (i = 0; i < bricks.length; i++) { // 2차원 객체배열의 모든 벽돌을 확인한다
            for (j = 0; j < bricks[i].length; j++) {
                Brick brk = bricks[i][j];
                brkDur = brk.getDurability(); // 각 벽돌의 내구도를 저장

                if(brkDur > 0 && isInBrickBoundary(brk)){ // 벽돌의 내구도가 0 이상이고 공이 벽돌과 충돌이 일어났을때
                    if(isUpToBrick(brk) || isBelowToBrick(brk)) // 아래에서 맞거나 위에서 맞으면
                        _yDir *= -1; // y좌표의 진행방향을 반대로 바꿈
                    else if(isLeftToBrick(brk) || isRightToBrick(brk)) // 왼쪽이나 오른쪽에서 맞으면
                        _xDir *= -1; // x좌표의 진행방향을 반대로 바꿈

                    brkDur--; 
                    brk.setDurability(brkDur); // 충돌후 벽돌의 내구도를 1 감소
                    if(brkDur == 0){ // 내구도가 감소하여 0이 되면
                        brk.clearBrick(); // 스테이지패널에서 안보이게 지운다.
                        stage.reduceBrickCount(); // 게임 성공 여부 판단을 위해 stage클래스의 현재 벽돌개수를 1 감소
                    }
                    return true; // 충돌이 일어나면 참값 반환, 메소드 종료
                }
            }
        }
        return false; // 아무런 벽돌과 충돌이 일어나지 않았으므로 false 반환
    }

    private boolean isInBrickBoundary(Brick brick) { // 공이 벽돌의 범위 안에 있는지(벽돌과 충돌이 일어났는지) 판단하는 메소드
        if (xIsInBrickBoundary(brick) && yIsBrickBoundary(brick)) // 공의 x좌표가 벽돌의 범위 안에 있고 y좌표가 벽돌의 범위 안에 있으면
            return true; // true 반환
        return false;
    }

    private boolean xIsInBrickBoundary(Brick brick) { // 공의 x좌표가 벽돌의 범위 안에 있는지 확인
        if (_xPos + ballSize/2 >= brick.getBrickLeftX() &&
                _xPos - ballSize/2 <= brick.getBrickRightX())
            return true;
        return false;
    }

    private boolean yIsBrickBoundary(Brick brick) { // 공의 y좌표가 벽돌의 범위 안에 있는지 확인
        if (_yPos + ballSize/2 >= brick.getBrickUpperY() &&
                _yPos - ballSize/2 <= brick.getBrickBottomY())
            return true;
        return false;
    }

    private boolean isBelowToBrick(Brick brick) { // 공이 벽돌 아래에 있는지 확인
        if(_yPos > brick.getBrickBottomY() && _yDir == -1)
            return true;
        return false;
    }
    private boolean isUpToBrick(Brick brick) { // 공이 벽돌 위에 있는지 확인
        if(_yPos < brick.getBrickUpperY() && _yDir == 1)
            return true;
        return false;
    }
    private boolean isRightToBrick(Brick brick){ // 공이 벽돌 오른쪽에 있는지 확인
        if(_xPos > brick.getBrickRightX() && _xDir == -1)
            return true;
        return false;
    }
    private boolean isLeftToBrick(Brick brick) { // 공이 벽돌 왼쪽에 있는지 확인
        if(_xPos < brick.getBrickLeftX() && _xDir == 1)
            return true;
        return false;
    }

    private boolean checkBarHit() { // 공과 바 간의 충돌을 체크하기 위한 메소드
        int barX = bar.getPt().x; // 바의 왼쪽 위 x좌표
        int barY = bar.getPt().y; // 바의 왼쪽 위 y좌표
        int width = bar.getWidth(); // 바의 너비
        int height = bar.getHeight(); // 바의 높이
        int barCenterX = barX + width / 2; // 바의 중앙 x좌표

        boolean isHit = false; // 맞았는지 여부 반환하기 위한 변수 

        if (_yDir > 0 && _xPos > barX && _xPos < barX + width && getBottomYPos() > barY && getBottomYPos() < barY + height) { // 공 좌표와 바 좌표를 비교해서 충돌이 일어났는지 비교
            if (_xPos < barCenterX) { // 공이 바의 왼쪽에 맞을 경우
                if (_xDir > 0) // 공이 오른쪽으로 진행하고 있었다면 왼쪽으로 진행방향을 바꿈
                    _xDir *= -1;

                angle = 30 + (60 * (_xPos - barX) / (width / 2)); // 공이 바에 맞은 좌표와 바 너비의 비율을 계산하여 각도에 저장
            }

            if (_xPos > barCenterX) { // 공이 바의 오른쪽으로 맞을 경우
                if (_xDir < 0) // 공이 왼쪽으로 진행하고 있었다면 오른쪽으로 진행방향을 바꿈
                    _xDir *= -1;

                angle = 90 - (60 * (_xPos - barCenterX) / (width / 2)); // 공이 바에 맞은 좌표와 바 너비의 비율을 계산하여 각도에 저장
            }

            _yDir *= -1; // 진행방향을 위로 바꿈
            isHit = true;
        }

        return isHit;
    }

    public static void speedUp() { // 공 속도 증가 아이템에서 적용하는 메소드
        ballSpeed += 2;
    }
    public static void speedDown() { // 공 속도 감소 아이템에서 적용하는 메소드
        ballSpeed -= 2;
    }
    
    public int getBallSpeed() { // 공의 속도 반환
        return ballSpeed;
    }

    private int getLeftXPos() { // 공의 왼쪽 x좌표 반환
        return _xPos - (ballSize / 2);
    }

    private int getRightXPos() { // 공의 오른쪽 x좌표 반환
        return _xPos + (ballSize / 2);
    }

    private int getUpperYPos() { // 공의 위쪽 y좌표 반환
        return _yPos - (ballSize / 2);
    }

    private int getBottomYPos() { // 공의 아래쪽 y좌표 반환
        return _yPos + (ballSize / 2);
    }

}
