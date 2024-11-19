//게임 시작화면을 구성하는 패널
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;

public class StartPanel extends JPanel {
	private GameFrame frame;
	
	private ImageIcon icon=new ImageIcon("chocolate.png");//파일로부터 초콜릿 (배경)이미지를 로딩
	private Image chocolateImage=icon.getImage(); //초콜릿 이미지 정보 추출하여 이미지 객체 생성
	
	private JButton[]b= {new JButton("게임 시작"),new JButton("순위 확인"),new JButton("단어 편집")}; //첫 화면을 구성하는 버튼 배열
	
	//생성자
	public StartPanel(GameFrame frame) { 
		this.frame=frame; //매개변수로 전달받은 프레임을 frame변수에 저장
		this.setLayout(null);//배치관리자 없애기
		
		JLabel gameName=new JLabel("슈의 초콜릿 공장",SwingConstants.CENTER); //게임 이름 출력하는 레이블, 텍스트 중앙정렬
		gameName.setFont(new Font("Gothic",Font.BOLD,70)); //레이블을 고딕체, 70크기 폰트로 설정
		gameName.setBounds(120,50,600,65); //레이블을 (120,50)위치에 (600,50)크기로 출력
		gameName.setForeground(new Color(86,41,29)); //레이블 글자 색을 밀크브라운 색으로 설정
		add(gameName); //패널에 레이블 붙이기
		
		settingButton(b,500,200); //버튼 세팅 함수 호출
		
		BounceSueThread bounceThread=new BounceSueThread(this); //스레드 객체 생성
		bounceThread.start(); //JVM에게 bounceThread스레드 실행을 시작하도록 요청
	}
	//초콜릿 배경 이미지를 그려주는 함수
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		g.drawImage(chocolateImage,0,0,this.getWidth(),this.getHeight(),this); //배경 초콜릿 이미지를 패널에 꽉차게 그리기
		//g.drawImage(sueImg,150,130,300,350,this); //슈 이미지를 (150,130)위치에 300x350크기로 그리기
	}
	//버튼을 세팅해주고 패널에 다는 함수
	private void settingButton(JButton[]b,int width,int height) { 
		for(int i=0;i<b.length;i++) {
			b[i].setLocation(width,height); //처음 버튼의 위치를 매개변수 값에 달리도록 설정
			height+=100; //다음 버튼이 달리는 위치를 변경
			
			b[i].setSize(200,50); //버튼 사이즈를 200,50으로 설정
			b[i].setBackground(new Color(255,204,204)); //버튼 색을 따뜻한 분홍색으로 설정
			b[i].setFont(new Font("Gothic",Font.BOLD,30)); //고딕체, 굵게, 30크기 폰트로 설정
			b[i].setForeground(new Color(139,69,19)); //글자 색을 브라운 색으로 설정
			b[i].setBorderPainted(false); //버튼 테두리 없애기
			
			b[i].addActionListener(new MyActionListener()); //버튼에 ActionEvent리스너 달기
			add(b[i]); //패널에 버튼 달기
		}
	}
	//버튼들에 등록될 액션리스너 클래스
	private class MyActionListener implements ActionListener{ 
		@Override
		public void actionPerformed(ActionEvent e) { 
			JButton eventButton=(JButton)e.getSource(); //이벤트 소스 버튼 구하기
			
			if(eventButton==b[0]) { //만약 눌린 버튼이 첫 번째 버튼인 "게임 시작" 버튼이라면
				frame.moveGameStart(); //게임 시작 패널로 이동
			}
			else if(eventButton==b[1]) { //눌린 버튼이 두 번째 버튼인 "순위 확인" 버튼이라면
				frame.moveCheckRanking(); //순위 확인 패널로 이동
			}
			else if(eventButton==b[2]) { //눌린 버튼이 세 번째 버튼인 "단어 편집" 버튼이라면
				frame.moveEditWord(); //단어 편집 패널로 이동
			}
		}
	}
}
