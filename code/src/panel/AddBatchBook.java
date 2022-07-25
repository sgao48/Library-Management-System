package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import connect.*;
import base.*;

public class AddBatchBook extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel infoLb;
	private JTextArea infoTxt;
	private JButton addBtn;
	private JScrollPane scroll;
	private boolean hasQueried;
	
	public AddBatchBook() {
		this.setLayout(null);
		
		infoLb = new JLabel();
		infoLb.setText("图书信息");
		infoLb.setFont(new Font(null, Font.BOLD, 40));
		infoLb.setSize(200, 40);
		infoLb.setLocation(400, 25);
		
		infoTxt = new JTextArea();
		infoTxt.setFont(new Font(null, Font.PLAIN, 25));
		infoTxt.setSize(800, 450);
		infoTxt.setLocation(80, 80);
		
		scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setSize(800, 450);
		scroll.setLocation(80, 80);;
		scroll.setViewportView(infoTxt);
		
		addBtn = new JButton("入        库");
		addBtn.setFont(new Font(null, Font.BOLD, 25));
		addBtn.setSize(150, 50);
		addBtn.setLocation(412, 575);
		
		this.add(infoLb);
		this.add(addBtn);
		this.add(scroll);
	
		addBtn.addActionListener(this);
	}
	
	// 按钮监听
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addBtn) {
			String txt = infoTxt.getText();
			
			if(txt.length() <= 0) {
				JOptionPane.showMessageDialog(this, "书籍信息不能为空", "错误", JOptionPane.WARNING_MESSAGE);
			}
			else {
				String[] bookInfo = txt.split("\n");
				int count = 0;
				
				for(int i = 0; i < bookInfo.length; i++) {
					String[] info = bookInfo[i].split(",");
					
					String bno = info[0].trim();
					String category = info[1].trim();
					String title = info[2].trim();
					String press = info[3].trim();
					String year = info[4].trim();
					String author = info[5].trim();
					String price = info[6].trim();
					String num = info[7].trim();
					// 判断书籍是否已经存在
					Book book = new Dao().addBookQuery(bno);
					if(book == null) {
						hasQueried = false;
					}
					else {
						hasQueried = true;
					}
					// 入库
					if(new Dao().addBook(bno, category, title, press, Integer.parseInt(year), author, 
							Float.parseFloat(price), Integer.parseInt(num), hasQueried)) {
						count++;
					}
				}
				
				if(count == bookInfo.length) {
					JOptionPane.showMessageDialog(this, "入库成功", "操作结果", JOptionPane.INFORMATION_MESSAGE);
					infoTxt.setText("");
				}
				else {
					JOptionPane.showMessageDialog(this, "入库失败", "错误", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
}
