//사용자의 이름과 점수를 저장하는 클래스

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserData {
	private RankingPanel rankingPanel=null;
	private String name;
	private int score;
	
	//생성자
	public UserData(String name,int score,RankingPanel rankingPanel) {
		this.name=name;
		this.score=score;
		this.rankingPanel=rankingPanel;
		
		WriteTextFile();
		rankingPanel.writeTextFile();
	}
	
	//얻어낸 정보를 바탕으로 텍스트 파일을 쓰는 함수
	public void WriteTextFile() {
		Scanner scanner=new Scanner(System.in);
		FileWriter fout=null;
		//예외처리
		try {
			fout=new FileWriter("rankData.txt",true); //이전 기록을 지우지 않고 파일과 연결된 출력 문자 스트림 생성
			
			fout.write(this.name); //읽은 사용자 이름을 파일에 저장
			fout.write(","); //점수와 이름을 구별하기 위해 ,를 파일에 저장
			fout.write(Integer.toString(this.score)); //읽은 사용자 점수를 문자열로 바꿔 파일에 저장
			fout.write(","); //점수와 이름을 구별하기 위해 ,를 파일에 저장
			
			fout.close(); //스트림 닫기
		}
		catch(IOException e) {
			return;
		}
	}
}
