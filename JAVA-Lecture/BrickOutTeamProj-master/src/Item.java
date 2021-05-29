import javax.swing.*;
import java.awt.*;

public class Item extends JLabel implements Runnable {
    public static final int ITEM_DROP_PROB = 50;

    private static final int ITEM_WIDTH = 20, ITEM_HEIGHT = 20;
    private static final Dimension ITEM_SIZE = new Dimension(20, 20);
    private static final Color ITEM_COLOR = new Color(0xff7f00);
    private static final int DELAY_TIME = 5, DY_MIN = 1, DY_MAX = 5;    // 아이템이 움직이는 딜레이 시간, 딜레이마다 움직이는 거리 최소, 최대
    private static final int EFFECT_NUM = 5;    // 아이템 효과 개수

    private Stage stage;
    private Bar bar;
    private Thread itemThread;
    private Point itemPos;
    private int appliedTime, dy;    // 아이템 효과 적용 시간, 아이템 딜레이당 이동 거리


    public Item(Stage stage, Point pos) {
        itemThread = null;
        this.stage = stage; // 스테이지의 레퍼런스를 받는다.
        this.bar = stage.getBar();  // 스테이지내 Bar 레퍼런스 받는다.

        // 아이템은 깨진 Brick의 가운데에서 생성된다.
        itemPos = new Point(pos.x + Brick.BRICK_WIDTH/2 - ITEM_WIDTH/2, pos.y);

        // 아이템의 딜레이당 이동거리 즉, 속도는 랜덤하게 주어진다.
        dy = DY_MIN + (int)(Math.random() * (DY_MAX - DY_MIN));
        appliedTime = 3000; // 아이템 효과 적용 시간

        setSize(ITEM_SIZE);
        setLocation(itemPos);
        setBackground(ITEM_COLOR);
        setOpaque(true);

        System.out.println("item speed >> " + dy);
    }



    public void start() {
        if(itemThread == null)
            itemThread = new Thread(this);
        itemThread.start();
    }

    @Override
    public void run() {
        // Item이 바닥으로 떨어질 때 까지 실행
        while(itemPos.y <= GameFrame.GAME_HEIGHT){
            // Item은 아래로 이동한다
            try {
                itemThread.sleep(DELAY_TIME);
            } catch(InterruptedException e) {}
            itemPos.y += dy;
            setLocation(itemPos);

            if(isInBarBoundary()) { // Bar의 범위안에 들어갔을 때 즉, Bar에 부딪혔을 때
                try {
                    setOpaque(false);   // 화면에서 지운다
                    applyRandomEffect();    // EFFECT_NUM 개의 효과들 중 랜덤하게 적용
                } catch(InterruptedException e) {}
                break;
            }
        }
        // 아이템이 바닥으로 떨어지거나 아이템 효과가 적용되면 제거
        stage.remove(this);
    }

    // Bar의 범위에 들어갔는지 -> Bar에 부딪혔는지
    private boolean isInBarBoundary(){
        if(xIsInBarBoundary() && yIsInBarBoundary())
            return true;
        return false;
    }
    // y좌표가 Bar의 범위에 들어갔는지
    private boolean yIsInBarBoundary() {
        if(itemPos.y > bar.getY() - ITEM_HEIGHT &&
                itemPos.y < bar.getY() + bar.getHeight())
            return true;
        return false;
    }
    // x좌표가 Bar의 범위에 들어갔는지
    private boolean xIsInBarBoundary() {
        if(itemPos.x > bar.getX() - ITEM_WIDTH &&
            itemPos.x < bar.getX() + bar.getWidth())
            return true;
        return false;
    }
    // EFFECT_NUM 개의 효과들 중 랜덤하게 적용
    // throw를 통해 이 메소드 내 sleep의 예외 처리를
    // 이 메소드가 호출된 곳으로 전가가
    private void applyRandomEffect() throws InterruptedException{
        // 랜덤으로 아이템선택
        switch((int)(Math.random() * EFFECT_NUM)){
            case 0: // Bar 크기를 일정시간 증가시킨 후 감소
                System.out.println("extend item");
                if(bar.getWidth() < 400){   // 일정 크기 이상으로는 커지지 않게 하기 위함
                    bar.extendSize();
                    itemThread.sleep(appliedTime);
                    bar.reduceSize();
                }
                break;
            case 1: // Bar 크기를 일정시간 감소시킨 후 증가
                System.out.println("reduce item");
                if(bar.getWidth() > 30){    // 일정 크기 이하으로는 작아지지 않게 하기 위함
                    bar.reduceSize();
                    itemThread.sleep(appliedTime);
                    bar.extendSize();
                }
                break;
            case 2: // Ball 일정속도 감소시킨 후 증가
                System.out.println("ball speed down");
                if(stage.getBallsSpeed() > 2){  // 일정 속도 이하로 작아지지 않게 하기 위함
                    stage.speedDownBalls();
                    itemThread.sleep(appliedTime);
                    stage.speedUpBalls();
                }
                break;
            case 3: // Ball 일정속도 증가시킨 후 감소
                System.out.println("ball speed up");
                stage.speedUpBalls();
                itemThread.sleep(appliedTime);
                stage.speedDownBalls();
                break;
            case 4: // Stage에 공 추가.
                System.out.println("add ball");
                stage.addBall();
                break;
        }
    }
}
