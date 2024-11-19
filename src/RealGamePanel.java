//실질적으로 게임이 이루어지는 패널

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RealGamePanel extends JPanel { //실질적으로 게임이 이루어지는 패널
	private ImageIcon sueIcon=null; //행복한 슈 이미지 아이콘
	private Image sueImg=null; //행복한 슈 이미지 객체
	private ImageIcon sadSueIcon=null; //슬픈 슈 이미지 아이콘
	private Image sadSueImg=null;  //슬픈 슈 이미지 객체
	
	private JLabel happySueLabel=null;  //행복한 슈 이미지 레이블
	private JLabel sadSueLabel=null; //슬픈 슈 이미지 레이블
	
	//게임 시작을 안내하는 레이블 생성, GamePanel에서 접근하기 위해 접근 지정자를 protected로 설정
	protected JLabel guideLabel=new JLabel("재생 버튼을 누르면 게임이 시작됩니다.");
	private JLabel textLabel=new JLabel(); //단어를 띄울 레이블 생성
	
	private CreateLabelThread createLabelThread=null; //스레드 객체 생성
	private MovingThread movingThread=null; //스레드 객체 생성
	
	private UserData userData=null; //사용자의 이름과 점수를 가지는 클래스
	
	private int countSecond=0; //게임이 진행되는 초를 세는 변수
	
	private CurrentLevelPanel levelPanel=null; //현재 게임 레벨을 나타내는 패널
	private ScorePanel scorePanel=null; //현재 점수들을 출력하는 패널
	private RankingPanel rankingPanel=null; //랭킹을 출력하는 패널
	
	private Vector<JLabel>labelVector=null; //생성된 초콜릿 레이블을 저장할 벡터
	
	private JLabel conveyer=new JLabel(); //컨베이어벨트의 베이스가 될 레이블객체 생성
	private JLabel []conveyerBelt=new JLabel[10]; //컨베이어벨트의 선을 나타낼 레이블 객체 배열 생성
	
	//생성자
	public RealGamePanel(MovingThread movingThread,CreateLabelThread createLabelThread,ScorePanel scorePanel,
			Vector<JLabel>labelVector,int countSecond,CurrentLevelPanel levelPanel,UserData userData,RankingPanel rankingPanel) {
		
		this.movingThread=movingThread;
		this.createLabelThread=createLabelThread;
		this.scorePanel=scorePanel;
		this.levelPanel=levelPanel;
		this.labelVector=labelVector;
		this.countSecond=countSecond;
		this.userData=userData;
		this.rankingPanel=rankingPanel;
		
		this.happySueLabel=createHappySueLabel(); //행복한 슈 이미지 레이블 생성
		this.sadSueLabel=createSadSueLabel(); //슬픈 슈 이미지 레이블 생성
		
		this.setBackground(new Color(245,177,206)); //패널의 배경색을 분홍색으로 설정
		
		setLayout(null); //배치관리자 없애기

		guideLabel.setBounds(220,10,215,20); //레이블을 (220,10)위치에 (215x20)크기로 설정
		guideLabel.setBackground(Color.WHITE); //레이블의 배경색을 하얀색으로 설정
		guideLabel.setOpaque(true); //레이블의 배경색이 보이도록 설정
		add(guideLabel); //안내 레이블 붙이기
		
		textLabel.setLocation(0,320); //레이블의 위치를 (0,320)로 설정
		textLabel.setSize(100,20); //레이블의 크기를 100x20으로 설정
		add(textLabel); //레이블 붙이기
		
		attachHappySueLabel(); //초기 화면은 행복한 슈 레이블을 붙인 상태
		
		drawConveyerBelt(); //컨베이어벨트 그리는 함수 호출하여 그리기
		
		this.createLabelThread=new CreateLabelThread(this,labelVector,countSecond,levelPanel,scorePanel,userData,rankingPanel); //스레드 객체 생성
		this.createLabelThread.start(); //JVM에게 createLabelThread스레드 실행을 시작하도록 요청
		this.movingThread=new MovingThread(labelVector,scorePanel,this,countSecond); //스레드 객체 생성
		this.movingThread.start(); //JVM에게 movingThread스레드 실행을 시작하도록 요청
	}
	//컨베이어 벨트를 그리는 함수
	public void drawConveyerBelt() { 
		conveyer.setBounds(0,350,600,70); //컨베이어벨트 베이스가 되는 레이블을 (0,350)위치에 600x70사이즈로 출력하도록 지정
		conveyer.setBackground(Color.LIGHT_GRAY); //레이블의 배경색을 연한 회색으로 설정
		conveyer.setOpaque(true); //배경색이 보이도록 설정
		add(conveyer); //레이블을 패널에 부착
		
		int x=27; //컨베이어 벨트의 선을 나타내는 레이블 객체가 처음 부착될 (conveyer객체의)x좌표
		for(int i=0;i<conveyerBelt.length;i++) {
			conveyerBelt[i]=new JLabel(); //객체 배열 요소로 레이블 객체 생성
			conveyerBelt[i].setSize(5,conveyer.getHeight()); //크기를 (5,100)으로 설정
			conveyerBelt[i].setLocation(x,0); //위치 설정
			x+=60; //x좌표를 60씩 더하기
			conveyerBelt[i].setBackground(Color.BLACK); //선을 표현하는 레이블의 색을 검정색으로 설정
			conveyerBelt[i].setOpaque(true); //레이블의 배경색이 보이도록 설정
			conveyer.add(conveyerBelt[i]); //컨베이어 벨트 베이스를 표현하는 conveyer객체에 레이블 붙이기
		}
	}
	//슈게임을 시작하도록 하는 함수
	public void startGame() { 
		createLabelThread.startMovingText();
		movingThread.startMovingText();
	}
	//행복한 슈 캐릭터 이미지 레이블을 생성하는 함수
	public JLabel createHappySueLabel() { 
		ImageIcon sueIcon=new ImageIcon("sue.png"); //슈 캐릭터 이미지를 로딩하여 아이콘 객체 생성
		Image sueImg=sueIcon.getImage(); //캐릭터 이미지 객체 생성
		sueImg=sueImg.getScaledInstance(180,220,Image.SCALE_SMOOTH); //이미지 객체를 180x220크기로 리뉴얼
		ImageIcon newHappySueIcon=new ImageIcon(sueImg); //이미지 객체로부터 크기가 수정된 이미지 아이콘 구하기
		
		this.happySueLabel=new JLabel(newHappySueIcon); //행복한 슈 캐릭터 이미지 레이블 객체 생성
		happySueLabel.setLocation(230,50); //이미지 레이블의 위치를(230,0=50)으로 생성
		happySueLabel.setSize(180,220); //이미지 레이블의 크기를 180x220으로 설정
		
		return happySueLabel; //행복한 슈 캐릭터 이미지 레이블 리턴
	}
	//슬픈 슈 캐릭터 이미지 레이블을 생성하는 함수
	public JLabel createSadSueLabel() { 
		ImageIcon sadSueIcon=new ImageIcon("sadSue.png"); //슈 캐릭터 이미지를 로딩하여 아이콘 객체 생성
		Image sadSueImg=sadSueIcon.getImage(); //캐릭터 이미지 객체 생성
		sadSueImg= sadSueImg.getScaledInstance(180,220,Image.SCALE_SMOOTH); //이미지 객체를 180x220크기로 리뉴얼
		ImageIcon newsadSueIcon=new ImageIcon(sadSueImg); //이미지 객체로부터 크기가 수정된 이미지 아이콘 구하기
		
		this.sadSueLabel=new JLabel(newsadSueIcon); //슬픈 슈 캐릭터 이미지 레이블 객체 생성
		sadSueLabel.setLocation(230,50); //이미지 레이블의 위치를(230,0=50)으로 생성
		sadSueLabel.setSize(180,220); //이미지 레이블의 크기를 180x220으로 설정
		
		return sadSueLabel; //슬픈 슈 캐릭터 이미지레이블 리턴
	}
	//패널에서 행복한 슈 레이블을 붙이는 함수
	public void attachHappySueLabel() { 
		RealGamePanel.this.add(happySueLabel); //RealGamePanel패널에 레이블 붙이기
		RealGamePanel.this.repaint(); //패널 다시 그리기
	}
	//패널에서 슬픈 슈 레이블을 붙이는 함수
	public void attachSadSueLabel() { 
		RealGamePanel.this.add(sadSueLabel); //RealGamePanel패널에 레이블 붙이기
		RealGamePanel.this.repaint(); //패널 다시 그리기
	}
	//패널에서 행복한 슈 레이블을 삭제하는 함수
	public void deleteHappySueLabel() { 
		RealGamePanel.this.remove(this.happySueLabel); //패널에서 행복한 슈 레이블 삭제
		RealGamePanel.this.repaint(); //패널 다시 그리기
	}
	//패널에서 슬픈 슈 레이블을 삭제하는 함수
	public void deleteSadSueLabel() {
		RealGamePanel.this.remove(this.sadSueLabel); //패널에서 슬픈 슈 레이블 삭제
		RealGamePanel.this.repaint(); //패널 다시 그리기
	}
	//게임을 일시정지시키기 위한 함수
	public void temporaryStopGame() {
		createLabelThread.finish(); //스레드 종료
		movingThread.finish(); // 스레드 종료
	}
	//게임 화면 리셋을 위한 함수
	public void resetPanels() {
		createLabelThread.finish(); //스레드 종료
		movingThread.finish(); // 스레드 종료
		scorePanel.initScorePanel(); // 점수 패널 초기화
		levelPanel.initLevelPanel(); //현재 레벨을 나타내는 패널 초기화
		createLabelThread.initValue(); //createLabelThread의 스레드 변수 초기화
		movingThread.initValue(); //movingThread의 스레드 변수 초기화
		for(int i=0;i<labelVector.size();i++) { //레이블 벡터 크기만큼 돌면서
			this.remove(labelVector.get(i)); //초콜릿 레이블 모두 지우기
		}
		repaint(); //다시 그리기
	}
}

