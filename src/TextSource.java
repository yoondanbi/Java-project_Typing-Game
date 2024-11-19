//텍스트 파일로부터 단어를 읽어 벡터에 저장하는 클래스
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TextSource{
	private Vector<String>wordVector=new Vector<String>(30000); //크기 30000인 벡터 생성
	
	//생성자
	public TextSource(JFrame parent) { 
		try {
			Scanner scanner=new Scanner(new FileReader("words.txt")); //파일에서 읽기
			while(scanner.hasNext()) {
				String word=scanner.nextLine(); //단어 하나 읽기
				this.wordVector.add(word); //단어를 읽어서 벡터에 저장
			}
			scanner.close();
			JOptionPane.showMessageDialog(parent,"단어 로딩을 완료했습니다."); //단어 읽기를 완료헀음을 알리는 메시지창 띄우기
		} catch (FileNotFoundException e) { //파일을 찾을 수 없다면
			System.out.println("파일을 찾을 수 없음."); //오류 안내 문구 출력
			System.exit(0); //시스템 종료
		}
	}
	
	//벡터에 저장된 단어들 중 하나를 랜덤으로 선정하여 제공하는 함수
	public String get() { 
		int n=wordVector.size(); //n에 벡터의 사이즈 저장
		int index=(int)(Math.random()*n); //랜덤한 인덱스 구하기
		return wordVector.get(index); //랜덤한 인덱스에 해당하는 단어 리턴
	}
	
	//리뉴얼 된 텍스트 파일을 다시 읽어 wordVector를 다시 세팅하는 함수
	public void renewalWordVector() {
		wordVector.removeAllElements(); //기존 벡터의 모든 요소 지우기
		try {
			Scanner scanner=new Scanner(new FileReader("words.txt")); //파일에서 읽기
			while(scanner.hasNext()) {
				String word=scanner.nextLine(); //단어 하나 읽기
				this.wordVector.add(word); //단어를 읽어서 벡터에 저장
			}
			scanner.close();
		} catch (FileNotFoundException e) { //파일을 찾을 수 없다면
			System.out.println("단어 파일을 찾을 수 없음."); //오류 안내 문구 출력
			System.exit(0); //시스템 종료
		}
		System.out.println("단어를 가진 벡터 새롭게 세팅 완료");
	}
}