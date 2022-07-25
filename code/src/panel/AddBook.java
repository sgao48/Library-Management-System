package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import connect.*;
import base.*;

public class AddBook extends JPanel implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JLabel bnoLb, cateLb, titleLb, pressLb, yearLb,
				authorLb, priceLb, numLb;
	private JTextField bnoTxt, cateTxt, titleTxt, pressTxt, yearTxt,
				authorTxt, priceTxt, numTxt;
	private JButton queryBtn, addBtn, clearBtn;
	private boolean hasQueried, flag;
	
	public AddBook() {
		this.setLayout(null);
		flag = false;
		
		bnoLb = new JLabel();
		bnoLb.setText("书    号: ");
		bnoLb.setFont(new Font(null, Font.BOLD, 25));
		bnoLb.setSize(150, 40);
		bnoLb.setLocation(280, 50);
		
		bnoTxt = new JTextField(10);
		bnoTxt.setFont(new Font(null, Font.PLAIN, 25));
		bnoTxt.setSize(300, 40);
		bnoTxt.setLocation(400, 50);
		
		cateLb = new JLabel();
		cateLb.setText("类    别: ");
		cateLb.setFont(new Font(null, Font.BOLD, 25));
		cateLb.setSize(150, 40);
		cateLb.setLocation(280, 110);
		
		cateTxt = new JTextField(10);
		cateTxt.setFont(new Font(null, Font.PLAIN, 25));
		cateTxt.setSize(300, 40);
		cateTxt.setLocation(400, 110);
		
		titleLb = new JLabel();
		titleLb.setText("书    名: ");
		titleLb.setFont(new Font(null, Font.BOLD, 25));
		titleLb.setSize(150, 40);
		titleLb.setLocation(280, 170);
		
		titleTxt = new JTextField(10);
		titleTxt.setFont(new Font(null, Font.PLAIN, 25));
		titleTxt.setSize(300, 40);
		titleTxt.setLocation(400, 170);
		
		pressLb = new JLabel();
		pressLb.setText("出版社: ");
		pressLb.setFont(new Font(null, Font.BOLD, 25));
		pressLb.setSize(150, 40);
		pressLb.setLocation(280, 230);
		
		pressTxt = new JTextField(10);
		pressTxt.setFont(new Font(null, Font.PLAIN, 25));
		pressTxt.setSize(300, 40);
		pressTxt.setLocation(400, 230);
		
		yearLb = new JLabel();
		yearLb.setText("年    份: ");
		yearLb.setFont(new Font(null, Font.BOLD, 25));
		yearLb.setSize(150, 40);
		yearLb.setLocation(280, 290);
		
		yearTxt = new JTextField(10);
		yearTxt.setFont(new Font(null, Font.PLAIN, 25));
		yearTxt.setSize(300, 40);
		yearTxt.setLocation(400, 290);
		
		authorLb = new JLabel();
		authorLb.setText("作    者: ");
		authorLb.setFont(new Font(null, Font.BOLD, 25));
		authorLb.setSize(150, 40);
		authorLb.setLocation(280, 350);
		
		authorTxt = new JTextField(10);
		authorTxt.setFont(new Font(null, Font.PLAIN, 25));
		authorTxt.setSize(300, 40);
		authorTxt.setLocation(400, 350);
		
		priceLb = new JLabel();
		priceLb.setText("价    格: ");
		priceLb.setFont(new Font(null, Font.BOLD, 25));
		priceLb.setSize(150, 40);
		priceLb.setLocation(280, 410);
		
		priceTxt = new JTextField(10);
		priceTxt.setFont(new Font(null, Font.PLAIN, 25));
		priceTxt.setSize(300, 40);
		priceTxt.setLocation(400, 410);
		
		numLb = new JLabel();
		numLb.setText("数    量: ");
		numLb.setFont(new Font(null, Font.BOLD, 25));
		numLb.setSize(150, 40);
		numLb.setLocation(280, 470);
		
		numTxt = new JTextField(10);
		numTxt.setFont(new Font(null, Font.PLAIN, 25));
		numTxt.setSize(300, 40);
		numTxt.setLocation(400, 470);
		
		queryBtn = new JButton("查    询");
		queryBtn.setFont(new Font(null, Font.BOLD, 25));
		queryBtn.setSize(120, 40);
		queryBtn.setLocation(725, 50);
		
		addBtn = new JButton("入        库");
		addBtn.setFont(new Font(null, Font.BOLD, 25));
		addBtn.setSize(150, 50);
		addBtn.setLocation(325, 550);
		
		clearBtn = new JButton("清空信息");
		clearBtn.setFont(new Font(null, Font.BOLD, 25));
		clearBtn.setSize(150, 50);
		clearBtn.setLocation(550, 550);
		
		this.add(bnoLb); this.add(bnoTxt);
		this.add(cateLb); this.add(cateTxt);
		this.add(titleLb); this.add(titleTxt);
		this.add(pressLb); this.add(pressTxt);
		this.add(yearLb); this.add(yearTxt);
		this.add(authorLb); this.add(authorTxt);
		this.add(priceLb); this.add(priceTxt);
		this.add(numLb); this.add(numTxt);
		this.add(queryBtn); this.add(clearBtn);
		this.add(addBtn);
		
		bnoTxt.addKeyListener(this);
		cateTxt.addKeyListener(this);
		titleTxt.addKeyListener(this);
		pressTxt.addKeyListener(this);
		yearTxt.addKeyListener(this);
		authorTxt.addKeyListener(this);
		priceTxt.addKeyListener(this);
		numTxt.addKeyListener(this);
		
		queryBtn.addActionListener(this);
		addBtn.addActionListener(this);
		clearBtn.addActionListener(this);
	}
	
	// 按钮监听
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == queryBtn) {
			String bno = bnoTxt.getText();
			Book book = new Dao().addBookQuery(bno);
			flag = true;
			
			if(bno.length() <= 0) {
				JOptionPane.showMessageDialog(this, "书号不能为空", "错误", JOptionPane.WARNING_MESSAGE);
			}
			else {
				if(book == null) {
					hasQueried = false;
					JOptionPane.showMessageDialog(this, "查无此书，请手动输入", "错误", JOptionPane.WARNING_MESSAGE);
					clearInfo(false);
				}
				else {
					hasQueried = true;
					JOptionPane.showMessageDialog(this, "查到此书，自动填充信息", "操作结果", JOptionPane.INFORMATION_MESSAGE);
					cateTxt.setText(book.category);
					titleTxt.setText(book.title);
					pressTxt.setText(book.press);
					yearTxt.setText(String.valueOf(book.year));
					authorTxt.setText(book.author);
					priceTxt.setText(String.valueOf(book.price));
				}				
			}
		}
		else if(e.getSource() == addBtn) {
			String bno = bnoTxt.getText();
			String category = cateTxt.getText();
			String title = titleTxt.getText();
			String press = pressTxt.getText();
			String year = yearTxt.getText();
			String author = authorTxt.getText();
			String price = priceTxt.getText();
			String num = numTxt.getText();
			
			if(bno.length()<=0 || category.length()<=0 || title.length()<=0 || press.length()<=0 ||
					year.length()<=0 || author.length()<=0 || price.length()<=0 || num.length()<=0) {
				JOptionPane.showMessageDialog(this, "书籍信息不能为空", "错误", JOptionPane.WARNING_MESSAGE);
			}
			else if(!flag) {
				JOptionPane.showMessageDialog(this, "入库前请先查询", "错误", JOptionPane.WARNING_MESSAGE);
			}
			else {
				if(new Dao().addBook(bno, category, title, press, Integer.parseInt(year), author, 
						Float.parseFloat(price), Integer.parseInt(num), hasQueried)) {
					JOptionPane.showMessageDialog(this, "入库成功", "操作结果", JOptionPane.INFORMATION_MESSAGE);
					clearInfo(true);
				}
				else {
					JOptionPane.showMessageDialog(this, "入库失败", "错误", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		else if(e.getSource() == clearBtn) {
			clearInfo(true);
		}
	}
	
	// 键盘输入监听
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ENTER) {
			addBtn.doClick();
		}
	}

	public void clearInfo(boolean flag) {
		cateTxt.setText("");
		titleTxt.setText("");
		pressTxt.setText("");
		yearTxt.setText("");
		authorTxt.setText("");
		priceTxt.setText("");
		numTxt.setText("");
		if(flag) {
			bnoTxt.setText("");
		}
	}
}
