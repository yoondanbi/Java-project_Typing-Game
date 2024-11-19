//게임을 하는 동안 점수(초콜릿 개수)를 출력하는 패널
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private int chocolate=0; //획득한 초콜릿 개수
	private int loseChocolate=0; //흘려보낸 "총" 초콜릿 개수
	private int criteriaLose=0; //생명(=하트)를 지우는 기준이 되는 흘린 초콜릿 개수 
	
	private JLabel heartText=new JLabel("[생명]"); //문자열을 출력하는 레이블 객체 생성
	private ImageIcon heartIcon=new ImageIcon("heart.png"); //이미지 로딩하여 이미지아이콘 만들기
	private JLabel heart1=new JLabel(heartIcon); //하트 아이콘 이미지레이블1 만들기
	private JLabel heart2=new JLabel(heartIcon); //하트 아이콘 이미지레이블2 만들기
	private JLabel heart3=new JLabel(heartIcon); //하트 아이콘 이미지레이블3 만들기
	
	private boolean heart1Exist=true; //heart1이 존재하면 true, 존재하지 않으면 false
	private boolean heart2Exist=true; //heart2가 존재하면 true, 존재하지 않으면 false
	private boolean heart3Exist=true; //heart3이 존재하면 true, 존재하지 않으면 false
	
	private JLabel textLabel=new JLabel("초콜릿 개수:"); //"초콜릿 개수"라는 문자열을 출력하는 레이블 객체 생성
	private JLabel countLabel=new JLabel(Integer.toString(chocolate)); //현재 초콜릿 수를 출력하는 레이블 객체 생성
	private JLabel loseTextLabel=new JLabel("흘린 초콜릿 개수:"); //"흘린 초콜릿 개수"라는 문자열을 출력하는 레이블 객체 생성
	private JLabel loseCountLabel=new JLabel(Integer.toString(loseChocolate)); //현재 흘려보낸 초콜릿 수를 출력하는 레이블 객체 생성

	//생성자
	public ScorePanel() {
		this.setBackground(new Color(139,69,19)); //배경색을 갈색으로 설정
		setLayout(null); //배치관리자 없애기
		
		textLabel.setBounds(10,10,150,20); //text를 150x20사이즈로 (10,10)에 위치하도록 설정
		textLabel.setForeground(Color.WHITE); //글자 색을 하얀색으로 설정
		add(textLabel); //패널에 textLabel붙이기
		
		countLabel.setBounds(100,10,100,20); //chocolate 수를 100x20사이즈로 (100,10)에 위치하도록 설정
		countLabel.setForeground(Color.WHITE); //글자 색을 하얀색으로 설정
		add(countLabel); //패널에 countLabel붙이기
		
		loseTextLabel.setBounds(10,70,180,20); //180x20사이즈로 (10,70)에 위치하도록 설정
		loseTextLabel.setForeground(Color.WHITE); //글자 색을 하얀색으로 설정
		add(loseTextLabel); //패널에 loseTextLabel붙이기
		
		loseCountLabel.setBounds(130,70,100,20); //loseChocolate 수를 100x20사이즈로 (130,70)에 위치하도록 설정
		loseCountLabel.setForeground(Color.WHITE); //글자 색을 하얀색으로 설정
		add(loseCountLabel); //패널에 loseCountLabel붙이기
	
		heartText.setForeground(Color.WHITE); //레이블의 글자 색을 하얀색으로 설정
		heartText.setBounds(70,150,50,20); //레이블을 50x20사이즈로 (70,150)에 위치하도록 설정
		add(heartText); //패널에 [생명]을 출력하는 레이블 붙이기		
		
		heart1.setBounds(10,180,50,50); //하트 이미지레이블1을 50x50사이즈로 (10,180)에 위치하도록 설정
		add(heart1); //하트 레이블1 붙이기
		heart2.setBounds(60,180,50,50); //하트 이미지레이블2를 50x50사이즈로 (60,180)에 위치하도록 설정
		add(heart2); //하트 레이블2 붙이기
		heart3.setBounds(110,180,50,50); //하트 이미지레이블3을 50x50사이즈로 (110,180)에 위치하도록 설정
		add(heart3); //하트 레이블3 붙이기
	}
	//생명(=하트)를 지우는 기준이 되는 흘린 초콜릿 개수를 0개로 세팅하는 함수
	public void setcriteriaCount() { 
		criteriaLose=0;
	}
	//생명(=하트)를 지우는 기준이 되는 흘린 초콜릿 개수를 카운트하고 알아내는 함수
	public int getCriteriaCount() { 
		criteriaLose+=1; //개수 1개 증가
		return criteriaLose; //개수 리턴
	}
	//프레임 밖으로 흘려보낸 초콜릿 개수를 카운트하여 레이블로 출력하는 함수
	public void losingChocolateCount() { 
		loseChocolate+=1; //흘려보낸 초콜릿 수 1개 올리기
		loseCountLabel.setText(Integer.toString(loseChocolate)); //레이블에 수정된 흘린 초콜릿 수를 출력
	}
	//하트 레이블 없애는 함수
	public void decreaseHeart() {   
		if(heart1Exist==true&&heart2Exist==true&&heart3Exist==true) { //현재 생명 3개가 모두 존재한다면
			this.remove(heart3); //패널에서 하트3 뗴어내기
			heart3Exist=false;//하트3(생명) 유무 false로 변경
			this.repaint(); //패널 다시 그리기
		}
		else if(heart1Exist==true&&heart2Exist==true&&heart3Exist==false) { //현재 생명 2개만 존재한다면
			this.remove(heart2); //패널에서 하트2 떼어내기
			heart2Exist=false;//하트2(생명) 유무 false로 변경
			this.repaint(); //패널 다시 그리기
		}
		else if(heart1Exist==true&&heart2Exist==false&&heart3Exist==false) {//현재 생명 1개만 존재한다면
			this.remove(heart1); //패널에서 하트1 뗴어내기
			heart1Exist=false;//하트1(생명) 유무 false로 변경
			this.repaint();//패널 다시 그리기
		}
	}
	//초콜릿 수를 올리는 함수
	public void increase() { 
		this.chocolate+=1; //초콜릿 수 1개 올리기
		countLabel.setText(Integer.toString(this.chocolate)); //레이블에 수정된 현재 초콜릿 수를 출력
	}
	//초콜릿 수를 내리는 함수
	public void decrease() { 
		if(this.chocolate>=2) //초콜릿 수가 2개보다 같거나 클 때
			this.chocolate-=2; //초콜릿 수 2개 내리기
		else //그 외의 경우라면
			this.chocolate=0; //초콜릿 수 0개로 변경
		countLabel.setText(Integer.toString(this.chocolate)); //레이블에 수정된 현재 초콜릿 수를 출력
	}
	//ScorePanel 초기화 함수
	public void initScorePanel() {
		chocolate=0; //획득 초콜릿 개수 0개로 초기화
		loseChocolate=0; //흘린 초콜릿 개수를 0개로 초기화
		
		countLabel.setText("0"); //레이블의 텍스트를 "0"으로 변경
		loseCountLabel.setText("0"); //레이블의 텍스트를 "0"으로 변경
		
		//현재 생명(하트가 3개 다 존재하지 않는다면, 생명이 아예 없는 경우는 없음)
		if(heart1Exist==true&&heart2Exist==true&&heart3Exist==false) { //현재 생명 2개만 존재한다면
			this.add(heart3); //패널에3 하트3 붙이기
			heart3Exist=true;//하트3(생명) 유무 true로 변경
		}
		else if(heart1Exist==true&&heart2Exist==false&&heart3Exist==false) {//현재 생명 1개만 존재한다면
			this.add(heart2); //패널에3 하트3 붙이기
			this.add(heart3); //패널에3 하트3 붙이기
			heart2Exist=true; //하트2(생명) 유무 true로 변경
			heart3Exist=true;//하트3(생명) 유무 true로 변경
		}
		heart1Exist=true;//하트1(생명) 유무 true로 변경
		heart2Exist=true;//하트2(생명) 유무 true로 변경
		heart3Exist=true;//하트3(생명) 유무 true로 변경
		
		this.repaint(); //점수 패널인 ScorePanel 다시 그리기
	}
	//초콜릿 수를 리턴하는 함수
	public int getChocolateCount() {
		return chocolate; //현재 초콜릿 수를 리턴
	}
}
