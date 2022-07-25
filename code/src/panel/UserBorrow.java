package panel;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import connect.*;
import base.*;
import panel.*;

public class UserBorrow extends JPanel implements ActionListener, KeyListener{
	private JButton queryBtn;
	private MyTableModel tableModel;
	private JTable table;
	private JScrollPane scroll;
	private JComboBox<String> borrowCB, returnCB;
	private final String[] columnName = {"���", "����֤", "���", "����", "�������", "�黹����", "Ӧ������", "����ԱID"};
	private JLabel infoLb, dateLb, borrowLb, returnLb;
	
	public UserBorrow() {
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
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		MyTableModel.FitTableColumns(table, 2);
		
		RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(tableModel);
		table.setRowSorter(rowSorter);
		
		scroll = new JScrollPane(table);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setSize(850, 450);
		scroll.setLocation(60, 200);
	
		infoLb = new JLabel();
		infoLb.setText("������Ϣ");
		infoLb.setFont(new Font("����", Font.BOLD, 40));
		infoLb.setSize(200, 50);
		infoLb.setLocation(400, 15);
		
		borrowLb = new JLabel();
		borrowLb.setText(this.queryLatest(Dao.cno));
		borrowLb.setFont(new Font(null, Font.BOLD, 20));
		borrowLb.setSize(800, 40);
		borrowLb.setLocation(60, 80);
		
		dateLb = new JLabel();
		dateLb.setText("����ʱ��: ");
		dateLb.setFont(new Font(null, Font.BOLD, 25));
		dateLb.setSize(150, 40);
		dateLb.setLocation(60, 135);
		
		returnLb = new JLabel();
		returnLb.setText("�Ƿ�黹: ");
		returnLb.setFont(new Font(null, Font.BOLD, 25));
		returnLb.setSize(150, 40);
		returnLb.setLocation(400, 135);
		
		String[] borrowData = new String[] {"", "һ����", "һ������", "��������", "һ����"};
		borrowCB = new JComboBox<String>(borrowData);
		borrowCB.setFont(new Font(null, Font.PLAIN, 25));
		borrowCB.setSize(175, 40);
		borrowCB.setLocation(190, 135);
		borrowCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("ѡ��: " + borrowCB.getSelectedIndex() + " = " + borrowCB.getSelectedItem());
				}
			}
		});
		
		String[] returnData = new String[] {"", "�ѹ黹", "δ�黹"};
		returnCB = new JComboBox<String>(returnData);
		returnCB.setFont(new Font(null, Font.PLAIN, 25));
		returnCB.setSize(175, 40);
		returnCB.setLocation(530, 135);
		returnCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("ѡ��: " + returnCB.getSelectedIndex() + " = " + returnCB.getSelectedItem());
				}
			}
		});
		
		queryBtn = new JButton("��    ѯ");
		queryBtn.setFont(new Font(null, Font.BOLD, 25));
		queryBtn.setSize(120, 40);
		queryBtn.setLocation(750, 135);
		
		this.add(scroll);
		this.add(infoLb);
		this.add(borrowLb); this.add(borrowCB);
		this.add(dateLb);
		this.add(returnLb); this.add(returnCB);
		this.add(queryBtn);
		
		borrowCB.addKeyListener(this);
		returnCB.addKeyListener(this);
		queryBtn.addActionListener(this);
	}
	
	// ��ť����
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == queryBtn) {
			ArrayList<BorrowRecord> res = new Dao().QueryUserBorrow(Dao.cno, borrowCB.getSelectedIndex(), returnCB.getSelectedIndex());
			Object[][] data = null;
			if(res.size() > 0) {
				data = BorrowRecord.toData(res);
			}
			tableModel.setDataVector(data, columnName);
			MyTableModel.FitTableColumns(table, 1);
		}
	}

	// �����������
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ENTER) {
			queryBtn.doClick();
		}
	}
	
	// �����Ҫ�黹�������Ϣ
	public String queryLatest(String cnoString) {
		String res = null;
		String flag = new Dao().QueryLatestBorrow(cnoString);
		switch(flag) {
		case "0":
			JOptionPane.showMessageDialog(this, "��������", "����", JOptionPane.WARNING_MESSAGE);
			return null;
		case "1":
			res = "��û����Ҫ�黹����";
			return res;
		default:
			String date = flag.split(" ")[0];
			String title = flag.split(" ")[1];
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate oldDate = LocalDate.parse(date, formatter);
			LocalDate nowDate = LocalDate.now();
			long daysDiff = ChronoUnit.DAYS.between(nowDate, oldDate);
		
			res = "�������Ҫ�黹����Ϊ��" + title + "�����黹����Ϊ��" + date + "  ����컹�У�"  + daysDiff + " ��";
			return res;
		}
	}
	
}
