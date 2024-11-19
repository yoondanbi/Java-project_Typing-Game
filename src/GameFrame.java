//통합적인 게임 프레임을 이루는 클래스
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class GameFrame extends JFrame {	
	private CardLayout layout=new CardLayout(); //카드레이아웃 객체 생성
	private Container c; 
	
	private Clip clip; //오디오 클립 객체
	private int audioOnOff=0; //오디오의 전원 ON=1, OFF=0
	
	private JMenuItem home=new JMenuItem("HOME"); //home메뉴아이템 생성(->EX메뉴에 해당)
	private JMenuItem exit=new JMenuItem("EXIT"); //exit메뉴아이템 생성(->EX메뉴에 해당)
	private JMenuItem soundOn=new JMenuItem("ON");//메뉴아이템 ON생성(->SOUND메뉴에 해당)
	private JMenuItem soundOff=new JMenuItem("OFF"); //메뉴아이템 OFF생성(->SOUND메뉴에 해당)
	
	//생성자
	public GameFrame() {
		setTitle("슈의 초콜릿 공장"); //프레임 타이틀 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //윈도우 창을 닫으면 프로그램이 종료되도록 설정
		
		makeMenu(); //메뉴바와 메뉴, 메뉴 아이템을 생성하는 함수 호출
		
		c=getContentPane(); //컨텐트팬 구하기
		c.setLayout(layout); //배치관리자를 카드 레이아웃으로 설정
		
		c.add("StartPanel",new StartPanel(this)); //시작화면을 구성하는 패널을 "StartPanel"이라는 이름으로 배치.
		c.add("GamePanel",new GamePanel(this)); //게임 화면 패널을 "GameStartPanel"이라는 이름으로 배치
		c.add("RankingPanel",new RankingPanel()); //순위 확인 패널을 "RankingPanel"이라는 이름으로 배치
		c.add("EditPanel",new EditPanel()); //단어 편집 패널을 "EditPanel"이라는 이름으로 배치
		
		layout.show(c, "startPanel"); //"StartPanel" 이라는 이름을 가진 패널을 보이도록 설정
		
		setSize(800,600); //프레임 크기를 800x600으로 설정
		setResizable(false); //프레임 크기 변경 불가능하도록 설정
		setVisible(true); //프레임이 보이도록 설정
		
		loadAudio("sound.wav"); //오디오 로딩
		clip.loop(Clip.LOOP_CONTINUOUSLY); //오디오 반복 재생 시작
		clip.start();
		audioOnOff=1; //오디오 재생 여부를 ON으로 변경
	}
	//메뉴바를 만드는 함수
	private void makeMenu() {
		JMenuBar mBar=new JMenuBar(); //메뉴바 생성
		setJMenuBar(mBar);  //프레임에 메뉴바 붙이기
		
		JMenu exMenu=new JMenu("EX"); //메뉴 exMenu 생성
		mBar.add(exMenu); //메뉴바에 exMenu 달기
		exit.addActionListener(new MenuActionListener()); //home메뉴아이템에 액션 리스너 달기
		home.addActionListener(new MenuActionListener()); //exit메뉴아이템에 액션 리스너 달기
		exMenu.add(home); //EX메뉴에 home메뉴아이템 붙이기
		exMenu.addSeparator(); //메뉴 아이템 사이의 분리선 넣기
		exMenu.add(exit); //EX메뉴에 exit메뉴아이템 붙이기
		
		JMenu soundMenu=new JMenu("SOUND"); //sound메뉴 생성
		mBar.add(soundMenu); //메뉴바에 sound메뉴 붙이기
		soundOn.addActionListener(new MenuActionListener()); //액션리스너 달기
		soundOff.addActionListener(new MenuActionListener()); //액션리스너 달기
		soundMenu.add(soundOn); //sound메뉴에 ON 메뉴아이템 붙이기
		soundMenu.addSeparator(); //메뉴 아이템 사이의 분리선 넣기
		soundMenu.add(soundOff); //sound메뉴에 OFF 메뉴아이템 붙이기 
	}
	//오디오를 로딩하여 재생하는 함수
	private void loadAudio(String pathName) { 
		//예외처리
		try {
			clip=AudioSystem.getClip(); //비어있는 오디오 클립 생성
			File audioFile=new File(pathName); //오디오 파일의 경로명
			AudioInputStream audioStream=AudioSystem.getAudioInputStream(audioFile);//오디오 파일로부터 오디오 데이터를 읽을 오디오 스트림 객체 생성
			clip.open(audioStream); //재생할 오디오 스트림 열기
		}
		catch(LineUnavailableException e){e.printStackTrace();}
		catch(UnsupportedAudioFileException e){e.printStackTrace();}
		catch(IOException e) {e.printStackTrace();}
	}
	//메뉴에 등록될 액션 리스너
	public class MenuActionListener implements ActionListener{ 
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd=e.getActionCommand(); //사용자가 선택한 메뉴 아이템의 문자열을 리턴
			
			switch(cmd) { //메뉴 아이템 종류에 따른 구분
			case "HOME": //선택된 메뉴의 문자열이 HOME이라면,
				moveFirstPanel();
				break;
			case "EXIT": //선택된 메뉴의 문자열이 EXIT라면,
				System.exit(0); //시스템 종료
				break;
			case "ON":
				if(audioOnOff==0) { //오디오 전원이 꺼져있다면
					clip.start(); //오디오 재생 시작
					audioOnOff=1; //오디오 재생 여부를 ON으로 변경
				}
				break;
			case "OFF":
				if(audioOnOff==1) { //오디오 전원이 켜져있다면
					clip.stop(); //오디오 재생 중단
					audioOnOff=0; //오디오 재생 여부를 OFF로 변경
				}
				break;
			}
		}
	}
	//시작 화면을 구성하는 패널로 변경하는 함수
	public void moveFirstPanel() { 
		layout.first(c); //첫 번째 화면을 구성하던 패널로 이동
	}
	//GameGround()패널로 변경하는 함수
	public void moveGameStart() { 
		layout.show(c, "GamePanel");
	}
	//RankingPanel()패널로 변경하는 함수
	public void moveCheckRanking() {  
		layout.show(c, "RankingPanel" );
	}
	//EditPanel()패널로 변경하는 함수
	public void moveEditWord() { 
		layout.show(c, "EditPanel");
	}
}
