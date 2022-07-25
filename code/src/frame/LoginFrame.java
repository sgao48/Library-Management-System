package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import connect.*;
import panel.*;

public class LoginFrame extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JLabel userLb, pwdLb, idLb;
	private JTextField userTxt;
	private JPasswordField pwdTxt;
	private JButton logBtn, quitBtn, queryBtn;
	private JComboBox<String> idCB;
	private JLabel time;
	
	public LoginFrame() {
		this.setTitle("�㽭��ѧxxͼ���");
		this.setSize(1000, 800);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		userLb = new JLabel();
		userLb.setText("�û���: ");
		userLb.setFont(new Font(null, Font.BOLD, 30));
		userLb.setSize(150, 50);
		userLb.setLocation(240, 180);
		
		pwdLb = new JLabel();
		pwdLb.setText("��    ��: ");
		pwdLb.setFont(new Font(null, Font.BOLD, 30));
		pwdLb.setSize(150, 50);
		pwdLb.setLocation(240, 280);
		
		userTxt = new JTextField(10);
		userTxt.setFont(new Font(null, Font.PLAIN, 30));
		userTxt.setSize(310, 50);
		userTxt.setLocation(400, 180);
		
		pwdTxt = new JPasswordField(10);
		pwdTxt.setFont(new Font(null, Font.PLAIN, 30));
		pwdTxt.setSize(310, 50);
		pwdTxt.setLocation(400, 280);
		
		idLb = new JLabel();
		idLb.setText("��    ��: ");
		idLb.setFont(new Font(null, Font.BOLD, 30));
		idLb.setSize(150, 50);
		idLb.setLocation(240, 380);
		
		String[] idData = new String[] {"����Ա", "��ͨ�û�"};
		idCB = new JComboBox<String>(idData);
		idCB.setFont(new Font(null, Font.BOLD, 30));
		idCB.setSize(310, 50);
		idCB.setLocation(400, 380);
		idCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("ѡ��: " + idCB.getSelectedIndex() + " = " + idCB.getSelectedItem());
				}
			}
		});
		
		logBtn = new JButton("��    ¼");
		logBtn.setFont(new Font(null, Font.BOLD, 25));
		logBtn.setSize(150, 50);
		logBtn.setLocation(435, 480);
		
		queryBtn = new JButton("ͼ���ѯ");
		queryBtn.setFont(new Font(null, Font.BOLD, 25));
		queryBtn.setSize(150, 50);
		queryBtn.setLocation(775, 550);
		
		quitBtn = new JButton("��    ��");
		quitBtn.setFont(new Font(null, Font.BOLD, 25));
		quitBtn.setSize(150, 50);
		quitBtn.setLocation(775, 625);		
		
		time = new JLabel();
		time.setFont(new Font(null, Font.BOLD, 25));
		time.setSize(250, 40);
		time.setLocation(20, 20);
		this.setTimer(time);
		
		this.add(userLb);
		this.add(pwdLb);
		this.add(userTxt);
		this.add(pwdTxt);
		this.add(logBtn);
		this.add(queryBtn);
		this.add(quitBtn);
		this.add(idCB); this.add(idLb);
		this.add(time);
		
		logBtn.addActionListener(this);
		quitBtn.addActionListener(this);
		queryBtn.addActionListener(this);
		
		userTxt.addKeyListener(this);
		pwdTxt.addKeyListener(this);
		idCB.addKeyListener(this);
		
		this.setVisible(true);
	}

	// ��ť����
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == logBtn) {
			checkAdmin(e);
		}
		else if(e.getSource() == quitBtn) {
			System.exit(1);
		}
		else if(e.getSource() == queryBtn) {
			this.dispose();
			LibraryFrame lf = new LibraryFrame();
		}
	}
	
	// �����������
	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();
		if((key>=33&&key<=47) || (key>=58&&key<=64) || (key>=91&&key<=96) || (key>=123&&key<=126)) {
			e.consume();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ENTER) {
			logBtn.doClick();
		}
	}
	
	// ����Ա��¼���
	private void checkAdmin(ActionEvent e) {
		// ��ȡ������û���������
		String user = userTxt.getText();
		String pwd = String.valueOf(pwdTxt.getPassword());
		
		// �ж������Ƿ�Ϊ��
		if(user.length()<=0 || pwd.length()<=0) {
			JOptionPane.showMessageDialog(this, "�û��������벻��Ϊ��", "��¼����", JOptionPane.WARNING_MESSAGE);
		}
		else {
			// �ж��Ƿ�ɹ���¼
			int mode = idCB.getSelectedIndex();
			if(new Dao().loginJudge(user, pwd, mode)) {
				JOptionPane.showMessageDialog(this, "��¼�ɹ�", "�������", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
				if(mode == 0) {
					AdminFrame mf = new AdminFrame();
				}
				else if(mode == 1) {
					UserFrame uf = new UserFrame();
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "�û������������", "��¼����", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	// ÿ1000ms��ʾһ��ʱ��
	private void setTimer(JLabel time){
		final JLabel varTime = time;
		Timer timeAction = new Timer(1000, new ActionListener() {		
			public void actionPerformed(ActionEvent e) {	
				long timemillis = System.currentTimeMillis();
				//ת��������ʾ��ʽ
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				varTime.setText(df.format(new Date(timemillis)));
			}
		});
		timeAction.start();		
	}
	
	public static void main(String[] args) {
		LoginFrame lf = new LoginFrame();		
	}
}
