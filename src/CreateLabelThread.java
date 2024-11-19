//초콜릿 이미지+랜덤 텍스트를 포함하는 레이블들을 생성하는 스레드
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class CreateLabelThread extends Thread{ //벡터의 크기만큼 JLabel을 생성하는 스레드
	private Vector<JLabel>labelVector=null;//JLabel타입의 벡터 변수 생성
	
	private boolean stopFlag=true; //true=Stop, false=Go 
	
	private JFrame parent; //JFrame객체
	private TextSource textSource=new TextSource(parent); //단어 벡터 생성
	private UserData userData=null; //사용자의 이름과 점수를 가지는 클래스
	
	private RealGamePanel realGamePanel=null;
	private CurrentLevelPanel levelPanel=null;
	private ScorePanel scorePanel=null;
	private RankingPanel rankingPanel=null;
	
	private String userName=null;
	private int score=0;
	
	private int countSecond=0; //게임 진행 시간
	private int delay=4000; //초기 지연시간 4초로 설정
	//생성자
	public CreateLabelThread(RealGamePanel realGamePanel,Vector<JLabel>labelVector,
					int countSecond,CurrentLevelPanel levelPanel,ScorePanel scorePanel,UserData userData,RankingPanel rankingPanel) {//생성자
		this.realGamePanel=realGamePanel;
		this.labelVector=labelVector;
		this.countSecond=countSecond;
		this.levelPanel=levelPanel;
		this.scorePanel=scorePanel;
		this.userData=userData;
		this.rankingPanel=rankingPanel;
	} 
	
	@Override
	public void run(){
		textSource.renewalWordVector(); //단어가 들어있는 벡터를 다시 세팅하는 함수 호출
		levelPanel.addLv1(); ////레벨 1임을 알리는 레이블을 levelPanel에 붙이기
		while(true) {
			checkWait(); //stopFlag가 true일 때, wait()함수 호출
			JLabel imageLabel=makeImageLabel(); //랜덤한 단어를 가진 이미지 레이블 만드는 함수 호출
			labelVector.add(imageLabel);//그 레이블을 벡터에 저장
			try {
				sleep(delay); //초기 4초 timeSleep, 랜덤한 이미지 레이블이 지연시간마다 생성됨
				countSecond+=delay; //게임 지속시간은 지연시간이 더해진 시간으로 리셋 
				
				if(countSecond==20000||countSecond==40000) { //게임 지속시간이 20초 or 40초가 되면
					delay=delay/2; //지연시간을 기존의 반으로 줄여 난이도 높임
					//현재 레벨 출력하는 코드
					if(countSecond==20000) { //게임 지속시간이 20초가 되면
						levelPanel.removeLV1(); //레벨 1임을 나타내는 레이블을 지우고
						levelPanel.addLv2(); //레벨 2임을 알리는 레이블을 levelPanel에 붙이기
					}
					else if(countSecond==40000) { //게임 지속시간이 40초가 되면
						levelPanel.removeLV2(); //레벨 2임을 나타내는 레이블을 지우고
						levelPanel.addLv3(); //레벨 3임을 알리는 레이블을 levelPanel에 붙이기
					}
				}
				//총 1분이 되면 게임 종료하는 코드
				else if(countSecond==60000) { //게임 진행 시간이 60초, 즉 1분이 되면
					finish(); //스레드 종료
					JOptionPane.showMessageDialog(parent,"게임을 종료했습니다."); //게임이 종료됐음을 알리는 메시지창 띄우기
					userName=JOptionPane.showInputDialog("저장할 이름을 입력하세요."); ///사용자 이름 입력받기
					score=scorePanel.getChocolateCount(); //현재 초콜릿 개수 가져오기
					
					new UserData(userName,score,rankingPanel); //사용자의 이름과 점수를 가지는 UserData객체 생성
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//스레드를 다시 실행시키는 함수
	synchronized public void startMovingText() { 
		stopFlag=false;
		notify();
	}
	//현재의 stopFlag의 값을 확인하는 함수
	synchronized private void checkWait() { 
		if(stopFlag==true)
			try{
				wait();
			}
		catch(InterruptedException e) {
			return;
		}
	}
	//스레드를 종료시키는 함수
	public void finish() { 
		stopFlag=true;
	}
	//스레드에서의 변수들을 초기화하는 함수
	public void initValue() {
		this.delay=4000; //초기 지연시간 4초로 설정
		this.countSecond=0; //게임 진행 시간
	}
	//랜덤한 단어와 초콜릿 이미지가 함께하는 레이블을 만드는 함수
	public JLabel makeImageLabel() {
		String word=textSource.get(); //랜덤한 단어 하나 읽어오기
		
		ImageIcon basicChocolateIcon=new ImageIcon("BasicChocolate.png"); //초콜릿 이미지 읽어서 이미지 아이콘으로 만들기
		Image basicChocolateImage=basicChocolateIcon.getImage(); //이미지 아이콘으로부터 이미지객체 구하기
		basicChocolateImage=basicChocolateImage.getScaledInstance(80,80,Image.SCALE_SMOOTH); //이미지 객체를 60x60크기로 리뉴얼
		ImageIcon newbasicImageIcon=new ImageIcon(basicChocolateImage); //이미지 객체로부터 크기가 수정된 이미지 아이콘 구하기
			
		JLabel textLabel=new JLabel(word,newbasicImageIcon,SwingConstants.CENTER);//그 단어의 텍스트가 이미지와 함께하는 레이블로 만들기
		textLabel.setSize(newbasicImageIcon.getIconWidth()+50,newbasicImageIcon.getIconHeight()); //레이블 크기를 (이미지아이콘 가로너비+50,이미지 아이콘 세로너비)크기로 설정
		textLabel.setLocation(0,270); //레이블의 초기 위치를 (0,270)으로 설정
		textLabel.setForeground(Color.WHITE); //레이블의 글자색을 하얀색으로 설정
		textLabel.setFont(new Font("Gothic",Font.BOLD,15)); //폰트를 고딕체의 BOLD스타일로 크기는 15로 설정 
		textLabel.setHorizontalTextPosition(JLabel.CENTER); //레이블의 텍스트를 수평 중앙에 정렬
		textLabel.setVerticalTextPosition(JLabel.CENTER); //레이블의 텍스트를 수직 중앙에 정렬
		realGamePanel.add(textLabel); //텍스트레이블 붙이기
		
		return textLabel;
	}
}