//게임 시작을 누르면 나타나는 패널(게임이 이루어지는 패널, 텍스트 입력 패널, 점수 패널, 단어 편집 패널 등으로 구성)

import java.awt.BorderLayout; //BorderLayout 배치관리자 사용을 위한 import문
import java.awt.Color; //색상 사용을 위한 import문
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

public class GamePanel extends JPanel{	
	private Vector<JLabel>labelVector=new Vector<JLabel>(30000); //JLabel타입의 벡터 생성 크기는 30000으로 초기화
	
	private MovingThread movingThread=null;
	private CreateLabelThread createLabelThread=null;
	private int countSecond=0;
	
	private RankingPanel rankingPanel=new RankingPanel(); //랭킹을 출력하는 패널
	private UserData userData=null; //사용자의 이름과 점수를 가지는 클래스
	private GameFrame frame;
	private ScorePanel scorePanel=new ScorePanel(); //ScorePanel 객체 생성
	private CurrentLevelPanel levelPanel=new CurrentLevelPanel(); //CurrentLevelPanel객체 생성
	private RealGamePanel realGamePanel=new RealGamePanel(movingThread,createLabelThread,
											scorePanel,labelVector,countSecond,levelPanel,userData,rankingPanel); //RealGamePanel객체 생성
	private InputPanel inputPanel=new InputPanel(labelVector,scorePanel,realGamePanel); //InputPanel객체 생성
	private GameGroundPanel gameGroundPanel=new GameGroundPanel(scorePanel,realGamePanel,inputPanel); //GameGroundPanel객체 생성
	
	//이미지 로딩하여 이미지 아이콘을 생성하는 코드
	private ImageIcon pressedStartIcon=new ImageIcon("pressedStart.png"); //이미지 로딩하여 이미지 아이콘 생성
	private ImageIcon startIcon=new ImageIcon("start.png"); //이미지 로딩하여 이미지 아이콘 생성
	private ImageIcon homeIcon=new ImageIcon("home.jpg"); //툴바에 부착될 홈버튼을 구성할 이미지 아이콘 객체 생성
	private ImageIcon tempStopIcon=new ImageIcon("stop.png"); //이미지 로딩하여 이미지 아이콘 생성

	//이미지 아이콘으로 버튼 생성하는 코드
	private JButton startBtn=new JButton(startIcon); //툴바에 부착될 게임 시작 버튼 생성
	private JButton homeBtn=new JButton(homeIcon); //홈이미지 버튼 생성
	private JButton temporaryStopBtn=new JButton(tempStopIcon); //일시정지 버튼 생성
	
	//생성자
	public GamePanel(GameFrame frame) { 
		this.frame=frame; //매개변수로 전달받은 frame객체를 멤버Frame객체에 저장
		
		setBackground(new Color(39,33,3)); //패널의 색을 r,b,b=39,33,3 값으로 설정
		setLayout(new BorderLayout()); //패널의 배치관리자를 BorderLayout으로 설정
		splitPanel(); //JSplitPane을 생성하여 컨텐트팬의 CENTER에 부착하는 함수 호출
		makeToolBar(); //툴바를 생성하여 컨텐트팬의 NORTH에 부착하는 함수 호출
	}
	//패널을 나누는 함수
	private void splitPanel() {
		JSplitPane hPane=new JSplitPane(); //SplitPane객체 생성
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); //수평으로 패널을 쪼갤 수 있도록 설정.
		hPane.setDividerLocation(600); //x좌표 600위치에서 나누도록 설정
		hPane.setEnabled(false); //SplitPane영역을 사용자가 임의로 움직일 수 없도록 설정
		add(hPane,BorderLayout.CENTER); //SplitPane을 컨텐트팬의 중앙에 부착
		
		JSplitPane vPane=new JSplitPane(); //SplitPane객체 생성
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT); //수직으로 패널을 쪼갤 수 있도록 설정
		vPane.setDividerLocation(300); //h좌표 300에서 나누도록 설정
		vPane.setEnabled(false); //SplitPane영역을 사용자가 임의로 움직일 수 없도록 설정
		hPane.setRightComponent(vPane); //수직 경계로부터 오른쪽에 VPane을 컴포넌트로 지정.
	
		hPane.setLeftComponent(gameGroundPanel); //수직 경계로부터 왼쪽에 게임이 실질적으로 이루어지는 GameGround패널 붙이기
		vPane.setTopComponent(scorePanel); //점수를 나타내는 scorePanel패널을 오른쪽 위에 붙이기
		vPane.setBottomComponent(levelPanel); //오른쪽 아래에 현재 레벨 출력하는 levelPanel패널 붙이기 
	}
	//툴바를 만드는 함수
	private void makeToolBar() { 
		JToolBar tBar=new JToolBar(); //툴바 객체 생성
		
		startBtn.addActionListener(new StartAction()); //시작 버튼에 액션리스너 붙이기
		homeBtn.addActionListener(new HomeAction()); //홈 이미지버튼에 액션리스너 붙이기
		startBtn.setPressedIcon(pressedStartIcon); //시작 버튼이 눌릴 때 이미지 아이콘을 pressedStartIcon으로 설정
		temporaryStopBtn.addActionListener(new stopAction()); //일시 정지 버튼에 액션리스너 붙이기
		
		tBar.add(homeBtn); //홈 이미지 버튼을 툴바에 붙이기
		tBar.add(startBtn); //시작 버튼을 툴바에 붙이기
		tBar.add(temporaryStopBtn); //일시 정지 버튼을 툴바에 붙이기
		
		tBar.setEnabled(false); //툴바 위치를 사용자가 임의로 움직일 수 없도록 설정
		tBar.setBackground(new Color(255,255,240)); //툴바의 배경색을 상아색으로 설정
		add(tBar,BorderLayout.NORTH); //컨텐트팬의 북쪽에 툴바 붙이기
	}
	//툴바의 시작 버튼에 등록될 액션리스너
	private class StartAction implements ActionListener{ 
		@Override
		public void actionPerformed(ActionEvent e) {
			realGamePanel.guideLabel.setText(null); //안내 레이블 텍스트 없애기
			realGamePanel.guideLabel.setOpaque(false); //안내 레이블의 배경색 없애기
			realGamePanel.startGame(); //게임을 시작하도록 호출
		}
	}
	//툴바의 홈 버튼에 등록될 액션리스너
	private class HomeAction implements ActionListener{ 
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.moveFirstPanel(); //현재 화면을 처음 화면을 구성하는 패널로 변경시키는 GameFrame클래스의 함수 호출
			realGamePanel.resetPanels(); //게임화면을 리셋시키기 위한 함수 호출
			levelPanel.addLv1(); ////레벨 1임을 알리는 레이블을 levelPanel에 붙이기
		}
	}
	//툴바의 일시정지 버튼에 등록될 액션리스너
	public class stopAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			realGamePanel.temporaryStopGame(); //게임을 일시 정지시키기 위한 함수 호출
		}
	}
}
