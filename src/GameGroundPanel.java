//실질적으로 산성비 게임이 이루어지는 패널들(게임이 이루어지는 패널+단어 입력받는 패널)을 통합적으로 이루는 패널
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GameGroundPanel extends JPanel{
	private RealGamePanel realGamePanel=null; //realGamePanel객체
	private ScorePanel scorePanel=null; //ScorePanel객체
	private InputPanel inputPanel=null; //InputPanel객체
	
	//생성자
	public GameGroundPanel(ScorePanel scorePanel,RealGamePanel realGamePanel,InputPanel inputPanel) { //생성자
		this.scorePanel=scorePanel;
		this.realGamePanel=realGamePanel;
		this.inputPanel=inputPanel;
		
		this.setLayout(new BorderLayout()); //배치관리자를 BorderLayout으로 설정
		add(realGamePanel,BorderLayout.CENTER); //패널 중앙에 게임이 이루어지는 패널 붙이기
		add(inputPanel,BorderLayout.SOUTH); //패널의 남쪽에 단어를 입력받는 패널 붙이기
	}
}