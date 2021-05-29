import java.awt.*;

// 그리기 모드에 대한 인터페이스
// 어떤 모드이던지 draw로 그리기에 대한 메소드 통일시키기 위해
// state 패턴 적용을 위해
public interface DrawMode {
    public void draw(Graphics page);
}
