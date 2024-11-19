//사용자로부터 단어를 입력받는 패널
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel{ //단어를 입력받는 패널
	private JTextField inputWord=new JTextField(40); //40크기의 텍스트필트 생성
	private Vector<JLabel>labelVector=null; //레이블 벡터 객체변수 만들기
	private ScorePanel scorePanel=null;
	private RealGamePanel realGamePanel=null;
	private boolean isSueHappy=true; //슈의 기분(부착된 이미지)을 나타내는 값, happySue=true, sadSue=false
	
	//생성자
	public InputPanel(Vector<JLabel>labelVector,ScorePanel scorePanel,RealGamePanel realGamePanel) {
		this.labelVector=labelVector;
		this.scorePanel=scorePanel;
		this.realGamePanel=realGamePanel;
		
		setLayout(new FlowLayout());//디폴트가 FlowLayoout이지만 확실히 하기 위해
		this.setBackground(new Color(139,69,19));//패널 배경색을 브라운 컬러로 설정
		
		inputWord.addActionListener(new TextFieldActionListener());//텍스트필드에 액션리스너를 등록
		add(inputWord); //텍스트 필드를 InputPanel에 붙이기
	}
	//텍스트 필드에 등록할 액션리스너(초콜릿 수 관련)
	class TextFieldActionListener implements ActionListener{ 
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField tf=(JTextField)e.getSource(); //이벤트 소스 구하기
			String inWord=tf.getText(); //사용자가 입력한 문자열 구하기
			
			int i;
			for(i=0;i<labelVector.size();i++) { //벡터 크기만큼 돌면서
				if(labelVector.get(i).getText().equals(inWord)) { //벡터안에 있는 해당 단어 맞추기 성공하면
					scorePanel.increase();//함수 호출하여 초콜릿 수 올리기
					inputWord.setText(null); //텍스트 입력 창 비우기
					realGamePanel.remove(labelVector.remove(i)); //맞춘 단어에 해당하는 레이블 지우기
					
					if(isSueHappy==false) { //슈 아이콘이 슬픈 슈가 붙어있다면
						realGamePanel.deleteSadSueLabel(); //realGame패널에서 슬픈 슈 레이블 떼어내기
						realGamePanel.attachHappySueLabel(); //realGame패널에 행복한 슈 레이블 붙이기
						isSueHappy=true; //슈 상태 바꿔주기
					}
					
					realGamePanel.repaint(); //다시 그리기
					scorePanel.repaint(); //스코어 패널 다시 그리기
					break;
				}
			}
			//단어 맞추기 실패하면
			if(i==labelVector.size()&&labelVector.size()!=0) { //인덱스값이 벡터의 사이즈와 같으면서 벡터의 사이즈가 0이(=처음 정답을 맞추는 경우가) 아니라면,
				scorePanel.decrease(); //함수 호출하여 초콜릿 수 감소시키기
				inputWord.setText(null); //텍스트 입력창 비우기
				
				if(isSueHappy==true) { //슈 아이콘이 행복한 슈가 붙어있다면
					realGamePanel.deleteHappySueLabel(); //realGame패널에서 행복한 슈 레이블 떼어내기
					realGamePanel.attachSadSueLabel(); //realGame패널에 슬픈 슈 레이블 붙이기
					isSueHappy=false; //슈 상태 바꿔주기
				}
				scorePanel.repaint(); //스코어 패널 다시 그리기
			}
		}
	}
}