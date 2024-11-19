//현재 레벨을 출력해주는 패널 클래스
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CurrentLevelPanel extends JPanel{
	private JLabel lv1=null;
	private JLabel lv2=null;
	private JLabel lv3=null;
	//생성자
	public CurrentLevelPanel() {
		this.setBackground(new Color(255,255,240)); //배경 색을 상아색으로 설정
		setLayout(new BorderLayout()); //배치관리자를 BorderLayout으로 설정
		
		this.lv1=new JLabel("   Level: 1"); //레벨1임을 출력하는 레이블 생성
		this.lv2=new JLabel("   Level: 2"); //레벨2임을 출력하는 레이블 생성
		this.lv3=new JLabel("   Level: 3"); //레벨3임을 출력하는 레이블 생성
	}
	
	//레벨 1임을 나타내는 레이블을 패널에서 제거하는 함수
	public void removeLV1() { 
		CurrentLevelPanel.this.remove(lv1); //패널에서 lv1레이블 제거
		//CurrentLevelPanel.this.repaint(); //레벨 패널 다시 그리기
	}
	//레벨 2임을 나타내는 레이블을 패널에서 제거하는 함수
	public void removeLV2() {
		CurrentLevelPanel.this.remove(lv2); //패널에서 lv2레이블 제거
		//CurrentLevelPanel.this.repaint(); //레벨 패널 다시 그리기
	}
	//레벨 3임을 나타내는 레이블을 패널에서 제거하는 함수
	public void removeLV3() {
		CurrentLevelPanel.this.remove(lv3); //패널에서 lv3 레이블 제거
		//CurrentLevelPanel.this.repaint(); //레벨 패널 다시 그리기
	}
	
	//레벨 1임을 나타내는 레이블을 패널에 붙이는 함수
	public void addLv1() {
		lv1.setFont(new Font("고딕",Font.BOLD,30)); //폰크 30크기의 굵은 스타일 고딕체 적용
		
		CurrentLevelPanel.this.add(lv1,BorderLayout.CENTER); //레이아웃 중앙에 패널 부착
		lv1.setHorizontalTextPosition(JLabel.CENTER); //텍스트를 레이블의 중앙에 정렬
		CurrentLevelPanel.this.repaint(); //레벨 패널 다시 그리기
	}
	//레벨 2임을 나타내는 레이블을 패널에 붙이는 함수
	public void addLv2() {
		
		lv2.setFont(new Font("고딕",Font.BOLD,30)); //폰크 30크기의 굵은 스타일 고딕체 적용
		
		CurrentLevelPanel.this.add(lv2,BorderLayout.CENTER); //레이아웃 중앙에 패널 부착
		lv2.setHorizontalTextPosition(JLabel.CENTER); //텍스트를 레이블의 중앙에 정렬
		CurrentLevelPanel.this.repaint(); //레벨 패널 다시 그리기
	}
	//레벨 3임을 나타내는 레이블을 패널에 붙이는 함수
	public void addLv3() {

		lv3.setFont(new Font("고딕",Font.BOLD,30)); //폰크 30크기의 굵은 스타일 고딕체 적용
		
		CurrentLevelPanel.this.add(lv3,BorderLayout.CENTER); //레이아웃 중앙에 패널 부착
		lv3.setHorizontalTextPosition(JLabel.CENTER); //텍스트를 레이블의 중앙에 정렬
		CurrentLevelPanel.this.repaint(); //레벨 패널 다시 그리기
	}
	//LevelPanel을 초기화시키는 함수
	public void initLevelPanel() {
		this.removeAll(); //모든 패널 지우기
	}
}
