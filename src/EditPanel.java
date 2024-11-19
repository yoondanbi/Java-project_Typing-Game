//단어를 편집하는 패널
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EditPanel extends JPanel{
	
	private JTextField wordInput = new JTextField(15); //15크기의 텍스트필드 객체 생성
	private JButton addButton=new JButton("Add"); //단어를 더하는 버튼 객체 생성
	private JButton deleteButton=new JButton("Delete"); //단어를 뺴는 버튼 객체 생성
	
	private String word=null;
	private Vector<String>newWordVector=new Vector<String>(30000); //새로운 문자열 벡터 생성
	private JList<String>wordList=new JList<String>(newWordVector);
	
	//생성자
	public EditPanel() { 
		this.setBackground(new Color(255,255,240)); //배경 색을 상아색으로 설정
		this.setLayout(new FlowLayout());//배치 관리자를 FlowLayout으로 설정
	
		wordList.setVisibleRowCount(23); //리스트가 보여주는 행의 수를 23으로 설정
		wordList.setFixedCellWidth(700); //리스트의 폭
		add(new JScrollPane(wordList)); //리스트에 스크롤팬 달기
		
		add(wordInput);  //TextField 붙이기
		
		addButton.addActionListener(new addWordAction()); //add버튼에 등록할 이벤트 리스너
		add(addButton); //저장 버튼 붙이기
		deleteButton.addActionListener(new deleteWordAction());
		add(deleteButton); //삭제 버튼 붙이기
		
		try {
			Scanner scanner=new Scanner(new FileReader("words.txt")); 
			while(scanner.hasNext()) {
				String word=scanner.nextLine(); //단어 하나 읽기
				this.newWordVector.add(word); //단어를 읽어서 벡터에 저장
			}
			scanner.close();
		} catch (FileNotFoundException e) { //파일을 찾을 수 없다면
			System.out.println("단어 파일을 찾을 수 없음."); //오류 안내 문구 출력
			System.exit(0); //시스템 종료
		}
	}
	//add버튼에 등록할 액션리스너
	class addWordAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String word=getWord(); //사용자가 입력한 문자열 구하기
			newWordVector.add(word); //얻어낸 단어를 문자열 벡터에 저장
			wordInput.setText(null); //텍스트 창 비우기
			wordList.setListData(newWordVector); //새로 세팅된 벡터의 모든 단어로 리스트 새로 만들기
			renualTextFile(); //새로 세팅된 벡터로 텍스트 파일 리뉴얼
		}
	}
	//delete버튼에 등록할 액션 리스너
	class deleteWordAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String word=getWord(); //사용자가 입력한 문자열 구하기
			for(int i=0;i<newWordVector.size();i++) { //벡터 크기만큼 돌면서
				if(newWordVector.get(i).equals(word)) { //기존 벡터에 있는 문자열이 사용자가 입력한 문자열과 같다면
					newWordVector.remove(i); //해당 단어 지우기
					break;
				}
			}	
			wordInput.setText(null); //텍스트 창 비우기
			wordList.setListData(newWordVector); //새로 세팅된 벡터의 모든 단어로 리스트 새로 만들기
			renualTextFile();//새로 세팅된 벡터로 텍스트 파일 리뉴얼
		}
	}
	//사용자가 텍스트 필드에 입력한 문자열을 리턴하는 함수
	public String getWord() {
		this.word=this.wordInput.getText(); //사용자가 입력한 문자열 구하기
		return this.word; //구한 문자열 리턴
	}

	//텍스트 파일을 새로운 벡터로 써서 리뉴얼하는 함수
	public void renualTextFile() {
		Vector<String>v=this.newWordVector; //벡터 변수값에 벡터 레퍼런스를 저장
		
		Scanner scanner=new Scanner(System.in);
		FileWriter fout=null;
		try {
			fout=new FileWriter("words.txt"); //파일과 연결된 출력 문자 스트림 생성
			for(int i=0;i<v.size();i++) { //벡터의 크기만큼 돌면서
				fout.write(v.get(i)); //읽은 벡터 요소를 파일에 저장
				fout.write("\r\n"); //한 줄 띄기 위해 "\r\n"을 파일에 저장
			}
			fout.close(); //스트림 닫기
		}
		catch(IOException e) {
			return;
		}
	}
}