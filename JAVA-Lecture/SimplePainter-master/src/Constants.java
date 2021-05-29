import java.awt.*;

// 사용되는 상수들
public class Constants {
    // 그리기 도구의 이름
    static final public String MENU[] = {
            "DOT", "LINE", "RECT", "OVAL", "TRI", "HEART", "NONE"
    };
    // 지우기 메뉴의 이름
    static final public String ERASE_MENU[] = {
            "UNDO", "REDO", "CLEAR"
    };
    // 호버링을 위한 색 상수
    static final public Color HOVERING[] = {
            Color.white, Color.black, Color.yellow, Color.red
    };
    // 그리기 도구에 대한 상수
    static final public int DOT = 0;
    static final public int LINE = 1;
    static final public int RECT = 2;
    static final public int OVAL = 3;
    static final public int TRI = 4;
    static final public int HEART = 5;
    static final public int NONE = 6;
}
