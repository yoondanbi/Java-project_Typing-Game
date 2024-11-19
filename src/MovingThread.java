//초콜릿 이미지+텍스트를 포함하는 레이블을 움직이게 하는 스레드
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MovingThread extends Thread{ //레이블을 움직이게 하는 스레드
	private boolean stopFlag=true; //true=Stop, false=Go 
	private int criteriaLose=0;
	private int countSecond=0; //게임 지속 시간
	private int delay=100; //초콜릿 이동 지연 시간, 초기값은 0.1초
	
	private Vector<JLabel>labelVector=null; //레이블 벡터 객체변수 만들기
	private ScorePanel scorePanel=null;
	private RealGamePanel realGamePanel=null;
	
	//생성자
	public MovingThread(Vector<JLabel>labelVector,ScorePanel scorePanel,RealGamePanel realGamePanel,int countSecond) { //생성자
		this.labelVector=labelVector;
		this.scorePanel=scorePanel;
		this.realGamePanel=realGamePanel;
		this.countSecond=countSecond;
	} 
	@Override
	public void run() {
		while(true) {
			checkWait(); //stopFlag가 true일 때, wait()함수 호출
			for(int i=0;i<labelVector.size();i++) { //labelVector의 사이즈만큼 반복
				//옆으로만 움직이도록 
				int x=labelVector.get(i).getX()+3; //x좌표만 3픽셀씩 이동
				int y=labelVector.get(i).getY(); //y좌표 그대로
			
				if(x>realGamePanel.getWidth()) { //realGamePanel의 가로 크기를 넘어서면
					scorePanel.losingChocolateCount(); //흘려보낸 초콜릿 카운팅하여 레이블에 출력하는 함수 호출
					criteriaLose=scorePanel.getCriteriaCount();//하트 지우는 기준이되는 흘린 초콜릿 수 구하기
					labelVector.remove(i); //벡터에서 해당 값 삭제
					
					//흘려보낸 초콜릿이 15개를 이상이면 생명(=하트) 하나 깎임
					if(criteriaLose>=15) { //흘려보낸 초콜릿 수가 15개 이상이면,
						scorePanel.setcriteriaCount();  //하트 잃는지 판단하는 기준 수를 0개로 초기화, 다시 카운트
						scorePanel.decreaseHeart(); //score패널의 생명(하트) 깎는 함수 호출
					}
				} 
				else  //realGamePanel의 가로 크기를 넘어 서지 않는다면
					labelVector.get(i).setLocation(x,y); //위치 재설정
					
				try {
					realGamePanel.repaint(); //다시 그리기
				}
				catch(NullPointerException e) {
					return;
				}
			}//end of for
			try {
				Thread.sleep(delay); //0.1초 간격으로 time sleep
				countSecond+=delay; //게임 진행시간을 지연시간을 더하여 구함
				//지연 시간을 줄게 하여 난이도를 높이는 코드
				if(countSecond==16000||countSecond==34000) { //게임 진행 시간이 16초 or 34초가 되면
					delay/=2; //지연 시간을 기존의 반으로 줄이기
				}
				//총 1분이 되면 게임 종료하는 코드
				if(countSecond==60000) { //게임 진행 시간이 60초, 즉 1분이 되면
					finish(); //스레드 종료
				}
			}
			catch (InterruptedException e) {
				return;
			}
		}
	}
	//스레드에서의 변수들을 초기화하는 함수
	public void initValue() {
		this.delay=100; //지연 시간
		this.countSecond=0; //게임 진행 시간
		this.criteriaLose=0; //기준이 되는 흘린 초콜릿 수
		scorePanel.setcriteriaCount(); //기준이 되는 흘린 초콜릿 수
	}
	//스레드를 종료시키는 함수
	public void finish() { 
		stopFlag=true;
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
}