package panel;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import connect.*;
import base.*;

public class BorrowBook extends JPanel implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JLabel cardLb, bnoLb;
	private JTextField cardTxt, bnoTxt;
	private JButton queryBtn, borrowBtn, returnBtn, clearBtn, continueBtn;
	private MyTableModel tableModel;
	private JTable table;
	private JScrollPane scroll;
	private final String[] columnName = {"序号", "借书证", "书号", "书名", "借出日期", "归还日期", "应还日期", "管理员ID"};
	
	public BorrowBook() {
		this.setLayout(null);
		
		Object[][] data = null;
		tableModel = new MyTableModel();
		tableModel.setDataVector(data, columnName);
		
		table = new JTable(tableModel);
		table.setFont(new Font(null, Font.PLAIN, 20));
		table.getTableHeader().setFont(new Font(null, Font.BOLD, 25));
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		MyTableModel.FitTableColumns(table, 1);
		
		RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(tableModel);
		table.setRowSorter(rowSorter);
		
		scroll = new JScrollPane(table);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setSize(800, 450);
		scroll.setLocation(80, 80);
		
		bnoLb = new JLabel();
		bnoLb.setText("书    号: ");
		bnoLb.setFont(new Font(null, Font.BOLD, 25));
		bnoLb.setSize(150, 40);
		bnoLb.setLocation(80, 550);
		
		bnoTxt = new JTextField(10);
		bnoTxt.setFont(new Font(null, Font.PLAIN, 25));
		bnoTxt.setSize(300, 40);
		bnoTxt.setLocation(200, 550);
		
		cardLb = new JLabel();
		cardLb.setText("借书证: ");
		cardLb.setFont(new Font(null, Font.BOLD, 25));
		cardLb.setSize(150, 40);
		cardLb.setLocation(80, 25);
		
		cardTxt = new JTextField(10);
		cardTxt.setFont(new Font(null, Font.PLAIN, 25));
		cardTxt.setSize(300, 40);
		cardTxt.setLocation(200, 25);
		
		queryBtn = new JButton("查    询");
		queryBtn.setFont(new Font(null, Font.BOLD, 25));
		queryBtn.setSize(120, 40);
		queryBtn.setLocation(550, 25);
		
		borrowBtn = new JButton("借    书");
		borrowBtn.setFont(new Font(null, Font.BOLD, 25));
		borrowBtn.setSize(120, 40);
		borrowBtn.setLocation(550, 550);
		
		returnBtn = new JButton("还    书");
		returnBtn.setFont(new Font(null, Font.BOLD, 25));
		returnBtn.setSize(120, 40);
		returnBtn.setLocation(720, 550);
		
		clearBtn = new JButton("清    空");
		clearBtn.setFont(new Font(null, Font.BOLD, 25));
		clearBtn.setSize(120, 40);
		clearBtn.setLocation(720, 25);
		
		continueBtn = new JButton("续    借");
		continueBtn.setFont(new Font(null, Font.BOLD, 25));
		continueBtn.setSize(120, 40);
		continueBtn.setLocation(550, 610);
		
		this.add(scroll);
		this.add(bnoLb); this.add(bnoTxt);
		this.add(cardLb); this.add(cardTxt);
		this.add(queryBtn);
		this.add(borrowBtn);
		this.add(returnBtn);
		this.add(clearBtn);
		this.add(continueBtn);
		
		cardTxt.addKeyListener(this);
		bnoTxt.addKeyListener(this);
		queryBtn.addActionListener(this);
		borrowBtn.addActionListener(this);
		returnBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		continueBtn.addActionListener(this);
	}
	
	// 按钮监听
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == queryBtn) {
			String cno = cardTxt.getText();
			
			if(cno.length() <= 0) {
				JOptionPane.showMessageDialog(this, "卡号不能为空", "错误", JOptionPane.WARNING_MESSAGE);
			}
			else {
				this.query(cno);
			}
		}
		else if(e.getSource() == borrowBtn) {
			String bno = bnoTxt.getText();
			String cno = cardTxt.getText();
			
			if(bno.length()<=0 || cno.length()<=0) {
				JOptionPane.showMessageDialog(this, "借书信息不能为空", "错误", JOptionPane.WARNING_MESSAGE);
			}
			else {
				String flag = new Dao().BorrowBook(cno, bno);
				switch(flag) {
				case "0":
					JOptionPane.showMessageDialog(this, "您已借此书且未还，请先归还", "错误", JOptionPane.WARNING_MESSAGE);
					break;
				case "1":
					JOptionPane.showMessageDialog(this, "借书成功", "操作结果", JOptionPane.INFORMATION_MESSAGE);
					this.query(cno);
					break;
				case "2":
					JOptionPane.showMessageDialog(this, "查无此书", "错误", JOptionPane.WARNING_MESSAGE);
					break;
				case "3":
					JOptionPane.showMessageDialog(this, "操作发生错误，请重试", "错误", JOptionPane.WARNING_MESSAGE);
					break;
				case "4":
					JOptionPane.showMessageDialog(this, "您已达借书上限", "错误", JOptionPane.WARNING_MESSAGE);
					break;
				default:
					JOptionPane.showMessageDialog(this, "该书暂无库存，最近归还时间："+flag, "操作结果", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
				bnoTxt.setText("");
			}
		}
		else if(e.getSource() == returnBtn) {
			String bno = bnoTxt.getText();
			String cno = cardTxt.getText();
			
			if(bno.length()<=0 || cno.length()<=0) {
				JOptionPane.showMessageDialog(this, "还书信息不能为空", "错误", JOptionPane.WARNING_MESSAGE);
			}
			else {
				int flag = new Dao().ReturnBook(cno, bno);
				switch(flag) {
				case 0:
					JOptionPane.showMessageDialog(this, "查无此书", "错误", JOptionPane.WARNING_MESSAGE);
					break;
				case 1:
					JOptionPane.showMessageDialog(this, "您未借或已归还此书", "错误", JOptionPane.WARNING_MESSAGE);
					break;
				case 2:
					JOptionPane.showMessageDialog(this, "还书成功", "操作结果", JOptionPane.INFORMATION_MESSAGE);
					this.query(cno);
					break;
				default:
					JOptionPane.showMessageDialog(this, "操作发生错误，请重试", "错误", JOptionPane.WARNING_MESSAGE);
					break;
				}
				bnoTxt.setText("");
			}
		}
		else if(e.getSource() == clearBtn) {
			Object[][] data = null;
			tableModel.setDataVector(data, columnName);
			MyTableModel.FitTableColumns(table, 1);			
			cardTxt.setText("");
			bnoTxt.setText("");
		}
		else if(e.getSource() == continueBtn) {
			String cno = cardTxt.getText();
			String bno = bnoTxt.getText();
			
			if(bno.length()<=0 || cno.length()<=0) {
				JOptionPane.showMessageDialog(this, "借书信息不能为空", "错误", JOptionPane.WARNING_MESSAGE);
			}
			else {
				String flag = new Dao().ContinueBorrow(cno, bno);
				switch(flag) {
				case "0":
					JOptionPane.showMessageDialog(this, "操作发生错误，请重试", "错误", JOptionPane.WARNING_MESSAGE);
					break;
				case "1":
					JOptionPane.showMessageDialog(this, "您未借此书或此书不存在", "错误", JOptionPane.WARNING_MESSAGE);
					break;
				case "2":
					JOptionPane.showMessageDialog(this, "续借成功", "操作结果", JOptionPane.INFORMATION_MESSAGE);
					this.query(cno);
					break;
				}
				bnoTxt.setText("");
			}
		}
	}
	
	// 键盘输入监听
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
			queryBtn.doClick();
		}
	}
	
	public void query(String cno) {
		ArrayList<BorrowRecord> res = new Dao().queryBorrowBook(cno);
		if(res.size() == 0) {
			JOptionPane.showMessageDialog(this, "查无此卡号", "错误", JOptionPane.WARNING_MESSAGE);
			cardTxt.setText("");
		}
		else {
			Object[][] data = BorrowRecord.toData(res);
			tableModel.setDataVector(data, columnName);
			MyTableModel.FitTableColumns(table, 1);
		}
	}
}
