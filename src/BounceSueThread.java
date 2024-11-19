//시작을 구성하는 패널에서 슈 캐릭터가 움직일 수 있도록 하는 스레드 클래스

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BounceSueThread extends Thread {
	private boolean stopFlag=false; //true=Stop, false=Go 
	private boolean isSueReversed=false; // false면 일반적, true면 좌우 반전된 슈가 레이블에 붙어있음을 나타냄
	
	private StartPanel startPanel=null;
	
	private JLabel sueLabel=null;
	private JLabel reverseSueLabel=null;
	
	private ImageIcon sueIcon=null;
	private ImageIcon reverseSueIcon=null;
	private ImageIcon newSueIcon=null;
	private ImageIcon newReverseSueIcon=null;
	private Image sueImg=null;
	private Image reverseSueImg=null;
	
	//생성자
	public BounceSueThread(StartPanel startPanel) { 
		this.startPanel=startPanel;
		createSueLabel(); //원하는 사이즈로 리뉴얼시킨 슈 이미지 레이블 생성
		createReverseSueLabel(); //원하는 사이즈로 리뉴얼시킨 좌우반전된 슈 이미지 레이블 생성
		
		//attachSueLabel();
	}
	@Override
	public void run() {
		while(true) {
			checkWait(); //stopFlag가 true일 때, wait()함수 호출
			try {
				attachSueLabel(); //StartPanel에 슈 레이블 붙이기
				Thread.sleep(500); //0.5초 간격으로 time sleep
				deleteSueLabel(); //StartPanel에서 슈 레이블 떼기
				attachReverseSueLabel(); ////StartPanel에 좌우 반전된 슈 레이블 붙이기
				Thread.sleep(500); //0.5초 간격으로 time sleep
				deleteReverseSueLabel(); //StartPanel에서 좌우 반전된 슈 레이블 떼기
			}
			catch (InterruptedException e) {
				return;
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
		if(stopFlag==true) {
			try{
				wait();
			}
			catch(InterruptedException e) {
				return;
			}
		}
	}
	//슈 레이블을 만드는 함수
	public void createSueLabel() { 
		this.sueIcon=new ImageIcon("sue.png"); //슈의 이미지 아이콘 생성
		this.sueImg=sueIcon.getImage(); //슈의 이미지 객체 생성
		this.sueImg=sueImg.getScaledInstance(300, 350, Image.SCALE_SMOOTH); //이미지 객체를 크기 변경하여 리뉴얼
		this.newSueIcon=new ImageIcon(sueImg); //새로운 슈의 이미지 아이콘 생성
		
		this.sueLabel=new JLabel(newSueIcon); //좌우 반전된 슈 캐릭터 이미지 레이블 객체 생성
		this.sueLabel.setLocation(150,130); //이미지 레이블의 위치를(250,130)으로 생성
		this.sueLabel.setSize(newSueIcon.getIconWidth(),newSueIcon.getIconHeight()); //이미지 레이블의 크기를 이미지 객체만한 크기로 설정
	}
	//좌우 반전된 슈 레이블을 만드는 함수
	public void createReverseSueLabel() { 
		this.reverseSueIcon=new ImageIcon("reverseSue.png"); //좌우 반전된 슈의 이미지아이콘 생성
		this.reverseSueImg=reverseSueIcon.getImage(); //거꾸로 된 슈의 이미지 객체 생성
		this.reverseSueImg=reverseSueImg.getScaledInstance(300,350,Image.SCALE_SMOOTH); //이미지 객체를 크기 변경하여 리뉴얼
		this.newReverseSueIcon=new ImageIcon(reverseSueImg); //새로운 좌우 반전된 슈의 이미지 아이콘 생성
		
		this.reverseSueLabel=new JLabel(newReverseSueIcon); //좌우 반전된 슈 캐릭터 이미지 레이블 객체 생성
		this.reverseSueLabel.setLocation(150,130); //이미지 레이블의 위치를(250,130)으로 생성
		this.reverseSueLabel.setSize(newReverseSueIcon.getIconWidth(),newReverseSueIcon.getIconHeight()); //이미지 레이블의 크기를 이미지 객체만한 크기로 설정
	}
	//시작 패널에 슈 캐릭터 이미지 레이블을 붙이는 함수
	public void attachSueLabel() {
		startPanel.add(sueLabel); //레이블을 startPanel에 붙이기
		startPanel.repaint(); //시작 패널 다시 그리기
	}
	//시작 패널에 좌우 반전된 슈 캐릭터 이미지 레이블을 붙이는 함수
	public void attachReverseSueLabel() {
		startPanel.add(reverseSueLabel); //레이블을 startPanel에 붙이기
		startPanel.repaint(); //시작 패널 다시 그리기
	}
	//시작 패널에서 슈 레이블을 삭제하는 함수
	public void deleteSueLabel() { 
		startPanel.remove(this.sueLabel); //StartPanel패널에서 슈 레이블 삭제
		startPanel.repaint(); //패널 다시 그리기
	}
	//시작 패널에서 좌우 반전된 슈 레이블을 삭제하는 함수
	public void deleteReverseSueLabel() { 
		startPanel.remove(this.reverseSueLabel); //StartPanel패널에서 좌우 반전된 슈 레이블 삭제
		startPanel.repaint(); //패널 다시 그리기
	}
}