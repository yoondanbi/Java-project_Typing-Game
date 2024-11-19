//사용자의 순위를 구성하는 패널
import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RankingPanel extends JPanel{
	private Vector<String>dataVector=new Vector<String>(50); //사용자의 이름과 정보 정보가 저장될 벡터
	
	private JLabel checkRank=new JLabel("순위 확인");
	
	private JLabel first=new JLabel("황기태,15"); //1등
	private JLabel second=new JLabel("윤단비,12"); //2등
	private JLabel third=new JLabel("김지혜, 8"); ///3등
	
	private String firstName=null;
	private String secondName=null;
	private String thirdName=null;
	private String firstScore=null;
	private String secondScore=null;
	private String thirdScore=null;
	
	public RankingPanel() {
		this.setBackground(new Color(139,69,19)); //배경색을 갈색으로 설정
		setLayout(null); //배치관리자 없애기
		
		//레이블 폰트 설정
		checkRank.setFont(new Font("Gothic",Font.BOLD,60));
		first.setFont(new Font("Gothic",Font.BOLD,80));
		second.setFont(new Font("Gothic",Font.BOLD,70));
		third.setFont(new Font("Gothic",Font.BOLD,60));
		//레이블의 위치와 사이즈 설정
		checkRank.setBounds(200,50,500,100);
		first.setBounds(150,150,1000,100);
		second.setBounds(180,270,1000,100);
		third.setBounds(200,380,1000,100);
		//레이블의 글자색 설정
		checkRank.setForeground(new Color(245,177,206));
		first.setForeground(new Color(255,255,240));
		second.setForeground(new Color(255,255,240));
		third.setForeground(new Color(255,255,240));
		//RankingPanel에 각 레이블 붙이기
		add(checkRank); add(first); add(second); add(third);
		
		writeTextFile(); //벡터를 리셋하고 파일에서 정보를 읽어 벡터 새로 채우기
	}
	//사용자 데이터 파일에서 새롭게 정보를 읽어 dataVector벡터에 저장하는 함수
	public void writeTextFile() {
		try {
			dataVector.removeAllElements(); //기존 벡터의 데이터 모두 지우기
			
			Scanner scanner=new Scanner(new FileReader("rankData.txt")); //파일에서 읽기
			while(scanner.hasNext()) { //단어가 있는 동안
				String word=scanner.nextLine(); //단어 하나 읽기 //하나의 단어가 이름,점수 꼴로 저장
				StringTokenizer st=new StringTokenizer(word,","); //구분문자로 ","를 사용하여 이름과 점수를 분리
				while(st.hasMoreTokens()) { //토큰이 있는 동안
					this.dataVector.add(st.nextToken()); //단어를 읽어서 벡터에 저장
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) { //파일을 찾을 수 없다면
			System.out.println("사용자 데이터 파일을 찾을 수 없음."); //오류 안내 문구 출력
			System.exit(0); //시스템 종료
		}
		makeRank();////////////////////////////
	}
	//벡터값을 비교하여 랭킹을 만드는 함수
	public void makeRank() {
		int maxScore=0;
		int index=0;
		int i=1;
		try {
			for(int j=0;j<5;j+=2) {
				for(i=1;i<dataVector.size();i+=2) {
					int data=Integer.parseInt((dataVector.get(i))); //홀수 인덱스 값을 정수로 변환
					if(maxScore<data) { //최대값보다 값이 크면
						maxScore=data; //치환
						index=i; //그 때의 인덱스 저장
					}
				}
				dataVector.add(j,dataVector.get(index-1)); //이름 저장
				dataVector.add(j+1,dataVector.get(index)); //점수 저장
				i+=2;
			}
		}
		catch(NumberFormatException e) {
			System.out.println("실패");
		}
		for(int k=0;k<6;k++) {
			System.out.println(dataVector.get(k));
		}///////////////////////
	}

	/*class User { //벡터에서 구한 사용자의 이름과 점수를 가지는 클래스
		int vectorScore;
		String vectorName;
		public User() {
			
		}
	}*/
	
	public void v() {
		for(int i=0;i<dataVector.size();i++) {
			System.out.println(dataVector.get(i));
		}
	}
	
}
