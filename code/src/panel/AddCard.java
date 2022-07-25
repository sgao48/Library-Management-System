package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import connect.*;

public class AddCard extends JPanel implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JLabel cardLb, nameLb, deptLb, cateLb;
	private JTextField cardTxt, nameTxt, deptTxt;
	private JComboBox<String> cateCB;
	private JButton confBtn;
	
	public AddCard() {
		this.setLayout(null);
		
		cardLb = new JLabel();
		cardLb.setText("��  ��: ");
		cardLb.setFont(new Font(null, Font.BOLD, 25));
		cardLb.setSize(150, 40);
		cardLb.setLocation(280, 100);
		
		cardTxt = new JTextField(10);
		cardTxt.setFont(new Font(null, Font.PLAIN, 25));
		cardTxt.setSize(300, 40);
		cardTxt.setLocation(380, 100);
		
		nameLb = new JLabel();
		nameLb.setText("��  ��: ");
		nameLb.setFont(new Font(null, Font.BOLD, 25));
		nameLb.setSize(150, 40);
		nameLb.setLocation(280, 180);
		
		nameTxt = new JTextField(10);
		nameTxt.setFont(new Font(null, Font.PLAIN, 25));
		nameTxt.setSize(300, 40);
		nameTxt.setLocation(380, 180);
		
		deptLb = new JLabel();
		deptLb.setText("��  ��: ");
		deptLb.setFont(new Font(null, Font.BOLD, 25));
		deptLb.setSize(150, 40);
		deptLb.setLocation(280, 260);
		
		deptTxt = new JTextField(10);
		deptTxt.setFont(new Font(null, Font.PLAIN, 25));
		deptTxt.setSize(300, 40);
		deptTxt.setLocation(380, 260);
		
		cateLb = new JLabel();
		cateLb.setText("��  ��: ");
		cateLb.setFont(new Font(null, Font.BOLD, 25));
		cateLb.setSize(150, 40);
		cateLb.setLocation(280, 340);
		
		String[] listData = new String[] {"������", "�о���", "��ʦ", "����"};
		cateCB = new JComboBox<String>(listData);
		cateCB.setFont(new Font(null, Font.BOLD, 25));
		cateCB.setSize(300, 40);
		cateCB.setLocation(380, 340);
		cateCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("ѡ��: " + cateCB.getSelectedIndex() + " = " + cateCB.getSelectedItem());
				}
			}	
		});
		
		confBtn = new JButton("��    ��");
		confBtn.setFont(new Font(null, Font.BOLD, 25));
		confBtn.setSize(120, 50);
		confBtn.setLocation(450, 450);
		
		this.add(cardLb); this.add(cardTxt);
		this.add(nameLb); this.add(nameTxt);
		this.add(deptLb); this.add(deptTxt);
		this.add(cateLb); this.add(cateCB);
		this.add(confBtn);
		
		confBtn.addActionListener(this);
		cardTxt.addKeyListener(this);
		nameTxt.addKeyListener(this);
		deptTxt.addKeyListener(this);
		cateCB.addKeyListener(this);
	}
	
	// ��ť����
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == confBtn) {
			String card = cardTxt.getText();
			String name = nameTxt.getText();
			String dept = deptTxt.getText();
			int type = cateCB.getSelectedIndex() + 1;
			
			if(card.length()<=0 || name.length()<=0 || dept.length()<=0) {
				JOptionPane.showMessageDialog(this, "����֤��Ϣ����Ϊ��", "����", JOptionPane.WARNING_MESSAGE);
			}
			else if(card.length() != 7) {
				JOptionPane.showMessageDialog(this, "���ű���Ϊ7λ", "����", JOptionPane.WARNING_MESSAGE);
			}
			else {
				if(new Dao().addCard(card, name, dept, type)) {
					JOptionPane.showMessageDialog(this, "��ӳɹ�", "�������", JOptionPane.INFORMATION_MESSAGE);
					cardTxt.setText("");
					nameTxt.setText("");
					deptTxt.setText("");
					cateCB.setSelectedIndex(0);
				}
				else {
					JOptionPane.showMessageDialog(this, "���ʧ��", "����", JOptionPane.WARNING_MESSAGE);	
				}
			}
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
			confBtn.doClick();
		}
	}
	
}
