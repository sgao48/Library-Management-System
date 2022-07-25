package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import connect.*;

public class DeleteCard extends JPanel implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JLabel cardLb;
	private JTextField cardTxt;
	private JButton deleteBtn;
	
	public DeleteCard() {
		this.setLayout(null);
		
		cardLb = new JLabel();
		cardLb.setText("ÊäÈë¿¨ºÅ: ");
		cardLb.setFont(new Font(null, Font.BOLD, 25));
		cardLb.setSize(150, 40);
		cardLb.setLocation(270, 200);
		
		cardTxt = new JTextField(10);
		cardTxt.setFont(new Font(null, Font.PLAIN, 25));
		cardTxt.setSize(300, 40);
		cardTxt.setLocation(400, 200);
		
		deleteBtn = new JButton("É¾    ³ý");
		deleteBtn.setFont(new Font(null, Font.BOLD, 25));
		deleteBtn.setSize(120, 50);
		deleteBtn.setLocation(460, 300);
		
		this.add(cardLb);
		this.add(cardTxt);
		this.add(deleteBtn);
		
		cardTxt.addKeyListener(this);
		deleteBtn.addActionListener(this);
	}
	
	// °´Å¥¼àÌý
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == deleteBtn) {
			String card = cardTxt.getText();
			
			if(card.length() <= 0) {
				JOptionPane.showMessageDialog(this, "¿¨ºÅ²»ÄÜÎª¿Õ", "´íÎó", JOptionPane.WARNING_MESSAGE);
			}
			else {
				if(new Dao().deleteCard(card)) {
					JOptionPane.showMessageDialog(this, "É¾³ý³É¹¦", "²Ù×÷½á¹û", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(this, "É¾³ýÊ§°Ü", "´íÎó", JOptionPane.WARNING_MESSAGE);	
				}
				cardTxt.setText("");
			}
		}
	}

	// ¼üÅÌÊäÈë¼àÌý
	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();
		if(key<'0' || key>'9') {
			e.consume();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ENTER) {
			deleteBtn.doClick();
		}
	}
	
}
