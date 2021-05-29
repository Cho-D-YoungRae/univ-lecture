import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimplePainterView extends JPanel {
    // Singleton 패턴으로 이 프로그램 내에서 하나만 생성되는 것이 보장된 이 SimplePainterView 인스턴스를
    // 어디에서나 접근할 수 있도록 한다.
    private static SimplePainterView view;
    public static SimplePainterView getInstance() {
        return view;
    }


    private DrawController drawController;
    private JPanel menuPanel, eraseMenuPanel, optionPanel, messagePanel, chkBoxPanel;
    private JLabel drawModeLabel, colorLabel, sizeLabel, pointLabel;
    private JButton[] btnMenuArray, eraseBtnMenuArray;
    private JSlider drawSize;
    private JButton btnColorChooser;
    private JCheckBox chkFill, chkReg;
    private HoveringListener hoveringListener;
    private MenuListener menuListener;
    private EraseMenuListener eraseMenuListener;

    public SimplePainterView() {
        // Singleton 패턴을 위해 변수 초기화
        view = this;

        setBackground(Color.white);
        setPreferredSize(new Dimension(820, 830));
        setLayout(null);

        // 그림을 그리는 MVC 중 Controller 부분
        drawController = new DrawController();
        drawController.setBounds(10, 10, 800, 600);
        drawController.setBackground(Color.white);
        drawController.setBorder(BorderFactory.createTitledBorder("DRAWING"));
        add(drawController);

        // 그리기 메뉴를 가진 패널
        menuPanel = new JPanel();
        menuPanel.setBounds(10, 610, 300, 200);
        menuPanel.setBackground(Color.white);
        menuPanel.setBorder(BorderFactory.createTitledBorder("DRAW MENU"));
        menuPanel.setLayout(new GridLayout(2, 3));
        add(menuPanel);

        // 지우기 메뉴를 가진 패널
        eraseMenuPanel = new JPanel();
        eraseMenuPanel.setBounds(310, 610, 100, 200);
        eraseMenuPanel.setBackground(Color.white);
        eraseMenuPanel.setBorder(BorderFactory.createTitledBorder("ERASE"));
        eraseMenuPanel.setLayout(new GridLayout(3, 1));
        add(eraseMenuPanel);

        // 색, 크기, 색을 채운 도형을 그릴지, 높이 너비가 같은 도형을 그릴지 의 옵션을 선택하는 패널
        optionPanel = new JPanel();
        optionPanel.setBounds(410, 610, 200, 200);
        optionPanel.setBackground(Color.white);
        optionPanel.setBorder(BorderFactory.createTitledBorder("OPTION"));
        optionPanel.setLayout(new GridLayout(3, 1));
        add(optionPanel);

        // 현재 그리는 중인 model에 대한 정보를 나타내는 패널
        messagePanel = new JPanel();
        messagePanel.setBounds(610, 610, 200, 200);
        messagePanel.setBackground(Color.white);
        messagePanel.setBorder(BorderFactory.createTitledBorder("MESSAGE"));
        messagePanel.setLayout(null);
        add(messagePanel);

        // 그리기 모드에 대한 버튼
        btnMenuArray = new JButton[6];
        hoveringListener = new HoveringListener();// 호버링을 위한 이벤트 리스너
        menuListener = new MenuListener();// 그리기 모드 선택을 위한 이벤트 리스너
        for(int i=0; i<6; i++){
            btnMenuArray[i] = new JButton(Constants.MENU[i]);
            btnMenuArray[i].setBackground(Constants.HOVERING[0]);
            btnMenuArray[i].setForeground(Constants.HOVERING[1]);
            btnMenuArray[i].addMouseListener(hoveringListener);
            btnMenuArray[i].addActionListener(menuListener);
            menuPanel.add(btnMenuArray[i]);
        }

        // 지우기 버튼
        eraseBtnMenuArray = new JButton[3];
        eraseMenuListener = new EraseMenuListener();    // 지우기 버튼에 대한 이벤트 리스너
        for(int i=0; i<3; i++){
            eraseBtnMenuArray[i] = new JButton(Constants.ERASE_MENU[i]);
            eraseBtnMenuArray[i].setBackground(Constants.HOVERING[0]);
            eraseBtnMenuArray[i].setForeground(Constants.HOVERING[1]);
            eraseBtnMenuArray[i].addMouseListener(hoveringListener);
            eraseBtnMenuArray[i].addActionListener(eraseMenuListener);
            eraseMenuPanel.add(eraseBtnMenuArray[i]);
        }

        // 색 선택을 위한 버튼
        btnColorChooser = new JButton("COLOR CHOOSER");
        btnColorChooser.setBackground(Constants.HOVERING[0]);
        btnColorChooser.setForeground(Constants.HOVERING[1]);
        btnColorChooser.addMouseListener(new HoveringListener());
        btnColorChooser.addActionListener(new MenuListener());
        optionPanel.add(btnColorChooser);   // 옵션 패널에 추가

        // 그리기 크기를 결정한다.
        // 기존 텍스트 필드에서 슬라이더로 변경하여
        // 숫자외에 정보가 입력되지 않게하고 직관적으로 보기 쉽게 하였다.
        drawSize = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
        drawSize.setPaintLabels(true);
        drawSize.setPaintTicks(true);
        drawSize.setPaintTrack(true);
        drawSize.setMajorTickSpacing(10);
        drawSize.setMinorTickSpacing(1);
        drawSize.setVisible(false);
        drawSize.addMouseListener(new SliderMouseListener());
        optionPanel.add(drawSize);  // 옵션 패널에 추가

        // 색을 채운 도형을 그릴지, 높이 너비가 같은 도형을 그릴지 설정하는 체크박스 두개를
        // 이 패널에 담아서 함께 옵션 패널에 추가한다.
        chkBoxPanel = new JPanel();
        chkBoxPanel.setBackground(Color.white);
        optionPanel.add(chkBoxPanel);

        // 체크박스의 폰트가 같으므로 역시 한번 생성해 적용한다.
        Font checkBoxFont = new Font("Verdana", Font.BOLD, 16);

        // 색을 채운 도형을 그릴지에 대한 체크박스
        chkFill = new JCheckBox("FILL");
        chkFill.setBackground(Color.white);
        chkFill.setFont(checkBoxFont);
        chkFill.setVisible(false);
        chkBoxPanel.add(chkFill);

        // 높이 너비가 같은 도형을 그릴지에 대한 체크박스
        chkReg = new JCheckBox("REG");
        chkReg.setBackground(Color.white);
        chkReg.setFont(checkBoxFont);
        chkReg.setVisible(false);
        chkBoxPanel.add(chkReg);

        // 메세지패널에 들어가는 라벨들의 폰트
        Font messageFont = new Font("SanSerif", Font.BOLD, 20);

        // 어떤 그리기 모드로 그리고 있는지에 대한 정보를 나타내는 라벨
        drawModeLabel = new JLabel(Constants.MENU[6]);  // 처음에는 NONE 모드로 초기화
        drawModeLabel.setBounds(0, 10, 200, 60);
        drawModeLabel.setFont(messageFont);
        drawModeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        drawModeLabel.setVerticalAlignment(SwingConstants.CENTER);
        messagePanel.add(drawModeLabel);

        // 무슨 색으로 그리고 있는지에 대한 정보를 나타낼 라벨
        colorLabel = new JLabel();
        colorLabel.setBounds(10, 80, 40, 40);
        colorLabel.setBackground(Color.black);
        colorLabel.setOpaque(true);
        messagePanel.add(colorLabel);

        // 현재 어떤 크기로 그림을 그리고 있는지에 대해 나타내는 라벨
        sizeLabel = new JLabel();
        sizeLabel.setBounds(60, 70, 140, 60);
        sizeLabel.setFont(messageFont);
        sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sizeLabel.setVerticalAlignment(SwingConstants.CENTER);
        messagePanel.add(sizeLabel);

        // 현재 그림을 그리고 있는 model의 좌표를 나타내는 라벨
        pointLabel = new JLabel();
        pointLabel.setBounds(0, 130, 200, 60);
        pointLabel.setFont(messageFont);
        pointLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pointLabel.setVerticalAlignment(SwingConstants.CENTER);
        messagePanel.add(pointLabel);
    }

    // 특정 그리기 모드를 선택하였을 때 크기를 초기화 시켜주기 위한 메소드
    public void setDrawSize(int size) {
        drawSize.setValue(size);
        sizeLabel.setText(Integer.toString(size));
    }
    // 현재 그리기 크기의 값을 반환한다.
    public int getDrawSize() {
        return drawSize.getValue();
    }

    public void setPointLabelText(String text){
        pointLabel.setText(text);
    }
    public boolean getChkFill() {
        return chkFill.isSelected();
    }
    public boolean getChkReg() { return chkReg.isSelected(); }

    // 호버링을 위한 이벤트 리스터
    private class HoveringListener extends MouseAdapter {
        // 마우스를 버튼에 올렸을 때
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton btnMenu = (JButton)e.getSource();
            btnMenu.setBackground(Constants.HOVERING[2]);
            btnMenu.setForeground(Constants.HOVERING[3]);
        }
        // 마우스가 버튼 밖으로 나갔을 때
        @Override
        public void mouseExited(MouseEvent e) {
            JButton btnMenu = (JButton)e.getSource();
            btnMenu.setBackground(Constants.HOVERING[0]);
            btnMenu.setForeground(Constants.HOVERING[1]);
        }
    }

    // 그리기 모드 선택을 위한 이벤트 리스너
    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();

            drawSize.setVisible(true);
            chkFill.setVisible(false);
            chkReg.setVisible(false);

            // 색 설정
            if(obj == btnColorChooser) {
                Color c = JColorChooser.showDialog(btnColorChooser, "COLOR CHOOSER", Color .black);
                drawController.setSelectedColor(c);
                colorLabel.setBackground(c);
            }

            // 누른 버튼에 따른 그리기 모드 설정
            for(int i=0; i<6; i++) {
                if(obj == btnMenuArray[i]) {
                    drawController.setDrawMode(i);
                    // 선 점 이외의 버튼은 색 채워져 있는 도형을 그릴지
                    // 높이 너비가 같은 도형을 그릴지에 대한 체크박스 나타나게 한다.
                    if(i > Constants.LINE){
                        chkFill.setVisible(true);
                        chkReg.setVisible(true);
                    }
                    // 선택한 그리기 모드에 대한 정보를 메세지 패널에 표시한다.
                    drawModeLabel.setText(Constants.MENU[i]);
                    break;
                }
            }
        }
    }

    //  지우기 버튼 선택을 위한 이벤트 리스너
    private class EraseMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton obj = (JButton) e.getSource();

            if(obj.getText().equals(Constants.ERASE_MENU[0]))
                drawController.undo();
            else if(obj.getText().equals(Constants.ERASE_MENU[1]))
                drawController.redo();
            else if(obj.getText().equals(Constants.ERASE_MENU[2]))
                drawController.clear();
        }
    }
    // 슬라이더로 그리기 크기를 선택했을 때 이 정보를 메세지 패널에 띄워주기 위한 이벤트 리스너
    private class SliderMouseListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            sizeLabel.setText(Integer.toString(drawSize.getValue()));
        }
    }

}
