package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import connect.*;

public class Setting extends JPanel implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JButton infoBtn, quitBtn, confBtn;
	private JLabel infoLb, time, adminLb;
	private JLabel anoLb, anameLb, passwdLb, contactLb;
	private JTextField anoTxt, anameTxt, contactTxt;
	private JPasswordField passwdTxt;
	private int mode;

	public Setting(int mode) {
		this.setLayout(null);
		this.mode = mode;
		
		time = new JLabel();
		time.setFont(new Font(null, Font.BOLD, 25));
		time.setSize(250, 40);
		time.setLocation(20, 20);
		this.setTimer(time);
		
		adminLb = new JLabel();
		if(mode == 0)
			adminLb.setText("管理员: " + Dao.aname + " 您好!");
		if(mode == 1)
			adminLb.setText("用户: " + Dao.cname + " 您好!");
		adminLb.setFont(new Font(null, Font.BOLD, 25));
		adminLb.setSize(500, 40);
		adminLb.setLocation(270, 20);
		
		infoLb = new JLabel();
		infoLb.setText("您的信息");
		infoLb.setFont(new Font("黑体", Font.BOLD, 40));
		infoLb.setSize(200, 50);
		infoLb.setLocation(400, 75);
		
		anoLb = new JLabel();
		anoLb.setText("账    号: ");
		anoLb.setFont(new Font(null, Font.BOLD, 25));
		anoLb.setSize(150, 40);
		anoLb.setLocation(270, 170);
		
		anoTxt = new JTextField(10);
		anoTxt.setFont(new Font(null, Font.PLAIN, 25));
		anoTxt.setSize(300, 40);
		anoTxt.setLocation(390, 170);
		if(mode == 0)
			anoTxt.setText(Dao.ano);
		if(mode == 1)
			anoTxt.setText(Dao.cno);
		anoTxt.setEditable(false);
		
		anameLb = new JLabel();
		anameLb.setText("姓    名: ");
		anameLb.setFont(new Font(null, Font.BOLD, 25));
		anameLb.setSize(150, 40);
		anameLb.setLocation(270, 240);
		
		anameTxt = new JTextField(10);
		anameTxt.setFont(new Font(null, Font.PLAIN, 25));
		anameTxt.setSize(300, 40);
		anameTxt.setLocation(390, 240);
		if(mode == 0)
			anameTxt.setText(Dao.aname);
		if(mode == 1)
			anameTxt.setText(Dao.cname);
		anameTxt.setEditable(false);
		
		passwdLb = new JLabel();
		passwdLb.setText("密    码: ");
		passwdLb.setFont(new Font(null, Font.BOLD, 25));
		passwdLb.setSize(150, 40);
		passwdLb.setLocation(270, 310);
		
		passwdTxt = new JPasswordField(10);
		passwdTxt.setFont(new Font(null, Font.PLAIN, 25));
		passwdTxt.setSize(300, 40);
		passwdTxt.setLocation(390, 310);
		passwdTxt.setEditable(false);
		
		contactLb = new JLabel();
		contactLb.setText("电    话: ");
		contactLb.setFont(new Font(null, Font.BOLD, 25));
		contactLb.setSize(150, 40);
		contactLb.setLocation(270, 380);
		
		contactTxt = new JTextField(10);
		contactTxt.setFont(new Font(null, Font.PLAIN, 25));
		contactTxt.setSize(300, 40);
		contactTxt.setLocation(390, 380);
		if(mode == 0)
			contactTxt.setText(Dao.contact);
		if(mode == 1)
			contactTxt.setText(Dao.ccontact);
		contactTxt.setEditable(false);
		
		confBtn = new JButton("确    认");
		confBtn.setFont(new Font(null, Font.BOLD, 25));
		confBtn.setSize(120, 50);
		confBtn.setLocation(435, 475);
		confBtn.setEnabled(false);
		
		infoBtn = new JButton("修    改");
		infoBtn.setFont(new Font(null, Font.BOLD, 25));
		infoBtn.setSize(120, 50);
		infoBtn.setLocation(820, 525);
		
		quitBtn = new JButton("退    出");
		quitBtn.setFont(new Font(null, Font.BOLD, 25));
		quitBtn.setSize(120, 50);
		quitBtn.setLocation(820, 600);
		
		this.add(time);
		this.add(infoLb);
		this.add(anoLb); this.add(anoTxt);
		this.add(anameLb); this.add(anameTxt);
		this.add(passwdLb); this.add(passwdTxt);
		this.add(contactLb); this.add(contactTxt);
		this.add(adminLb);
		this.add(quitBtn);
		this.add(infoBtn);
		this.add(confBtn);
		
		passwdTxt.addKeyListener(this);
		contactTxt.addKeyListener(this);
		
		quitBtn.addActionListener(this);
		infoBtn.addActionListener(this);
		confBtn.addActionListener(this);
	}
	
	// 按钮监听
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == confBtn) {
			String passwd = String.valueOf(passwdTxt.getPassword());
			String contact = contactTxt.getText();
			String ano = anoTxt.getText();
			
			if(passwd.length()<=0 && contact.length()<=0) {
				JOptionPane.showMessageDialog(this, "信息不能为空", "错误", JOptionPane.WARNING_MESSAGE);
			}
			else if(passwd.length()>6 || contact.length()!=11) {
				JOptionPane.showMessageDialog(this, "密码应小于7位且电话必须为11位", "错误", JOptionPane.WARNING_MESSAGE);
			}
			else {
				int flag = new Dao().ModifyInfo(ano, passwd, contact, mode);
				switch(flag) {
				case 0:
					if(mode == 0)
						JOptionPane.showMessageDialog(this, "没有此管理员", "错误", JOptionPane.WARNING_MESSAGE);
					if(mode == 1)
						JOptionPane.showMessageDialog(this, "没有此用户", "错误", JOptionPane.WARNING_MESSAGE);
					break;
				case 1:
					JOptionPane.showMessageDialog(this, "新密码不能与原来相同", "错误", JOptionPane.WARNING_MESSAGE);
					break;
				case 2:
					JOptionPane.showMessageDialog(this, "修改成功", "操作结果", JOptionPane.INFORMATION_MESSAGE);
					passwdTxt.setEditable(false);
					contactTxt.setEditable(false);
					confBtn.setEnabled(false);
					passwdTxt.setText("");
					if(mode == 0)
						contactTxt.setText(Dao.contact);
					if(mode == 1)
						contactTxt.setText(Dao.ccontact);
					break;
				default:
					JOptionPane.showMessageDialog(this, "发生错误", "错误", JOptionPane.WARNING_MESSAGE);
					break;				
				}
			}
		}
		else if(e.getSource() == infoBtn) {
			passwdTxt.setEditable(true);
			contactTxt.setEditable(true);
			confBtn.setEnabled(true);
		}
		else if(e.getSource() == quitBtn) {
			System.exit(1);
		}
	}
	
	// 键盘输入监听
	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();
		if((key<'0'||key>'9') && (key<'a'||key>'z') && (key<'A'||key>'Z')) {
			e.consume();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ENTER) {
			confBtn.doClick();
		}
	}
	
	// 每1000ms显示一次时间
	private void setTimer(JLabel time){
		final JLabel varTime = time;
		Timer timeAction = new Timer(1000, new ActionListener() {		
			public void actionPerformed(ActionEvent e) {	
				long timemillis = System.currentTimeMillis();
				//转换日期显示格式
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				varTime.setText(df.format(new Date(timemillis)));
			}
		});
		timeAction.start();		
	}
	
}
