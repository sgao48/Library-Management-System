package frame;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import connect.*;
import base.*;
import panel.*;

public class LibraryFrame extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JButton queryBtn, clearBtn, returnBtn;
	private JLabel typeLb, titleLb, pressLb, authorLb, yearLb, priceLb;
	private JTextField typeTxt, titleTxt, pressTxt, authorTxt;
	private JComboBox<String> yearCB, priceCB;
	private MyTableModel tableModel;
	private JTable table;
	private JScrollPane scroll;
	private final String[] columnName = {"���", "���", "����", "������", "���", "����", "�۸�", "�ܲ�����", "���"};

	public LibraryFrame() {
		this.setTitle("ͼ���ѯ");
		this.setSize(1000, 800);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
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
		scroll.setSize(900, 450);
		scroll.setLocation(37, 250);
		
		typeLb = new JLabel();
		typeLb.setText("��    ��: ");
		typeLb.setFont(new Font(null, Font.BOLD, 25));
		typeLb.setSize(150, 40);
		typeLb.setLocation(195, 50);
		
		typeTxt = new JTextField(10);
		typeTxt.setFont(new Font(null, Font.PLAIN, 25));
		typeTxt.setSize(175, 40);
		typeTxt.setLocation(295, 50);
		
		titleLb = new JLabel();
		titleLb.setText("��    ��: ");
		titleLb.setFont(new Font(null, Font.BOLD, 25));
		titleLb.setSize(150, 40);
		titleLb.setLocation(495, 50);
		
		titleTxt = new JTextField(10);
		titleTxt.setFont(new Font(null, Font.PLAIN, 25));
		titleTxt.setSize(175, 40);
		titleTxt.setLocation(595, 50);
		
		pressLb = new JLabel();
		pressLb.setText("������: ");
		pressLb.setFont(new Font(null, Font.BOLD, 25));
		pressLb.setSize(150, 40);
		pressLb.setLocation(195, 120);
		
		pressTxt = new JTextField(10);
		pressTxt.setFont(new Font(null, Font.PLAIN, 25));
		pressTxt.setSize(175, 40);
		pressTxt.setLocation(295, 120);
		
		authorLb = new JLabel();
		authorLb.setText("��    ��: ");
		authorLb.setFont(new Font(null, Font.BOLD, 25));
		authorLb.setSize(150, 40);
		authorLb.setLocation(495, 120);
		
		authorTxt = new JTextField(10);
		authorTxt.setFont(new Font(null, Font.PLAIN, 25));
		authorTxt.setSize(175, 40);
		authorTxt.setLocation(595, 120);
		
		yearLb = new JLabel();
		yearLb.setText("��    ��: ");
		yearLb.setFont(new Font(null, Font.BOLD, 25));
		yearLb.setSize(150, 40);
		yearLb.setLocation(195, 190);
		
		String[] yearData = new String[] {"", "?-1980", "1981-2000", "2001-2010", "2011-2020"};
		yearCB = new JComboBox<String>(yearData);
		yearCB.setFont(new Font(null, Font.PLAIN, 25));
		yearCB.setSize(175, 40);
		yearCB.setLocation(295, 190);
		yearCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("ѡ��: " + yearCB.getSelectedIndex() + " = " + yearCB.getSelectedItem());
				}
			}
		});
		
		priceLb = new JLabel();
		priceLb.setText("��    ��: ");
		priceLb.setFont(new Font(null, Font.BOLD, 25));
		priceLb.setSize(150, 40);
		priceLb.setLocation(495, 190);
		
		String[] priceData = new String[] {"", "0-50", "50-100", "100-200", ">200"};
		priceCB = new JComboBox<String>(priceData);
		priceCB.setFont(new Font(null, Font.PLAIN, 25));
		priceCB.setSize(175, 40);
		priceCB.setLocation(595, 190);
		priceCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("ѡ��: " + priceCB.getSelectedIndex() + " = " + priceCB.getSelectedItem());
				}
			}
		});
		
		returnBtn = new JButton("��    ��");
		returnBtn.setFont(new Font(null, Font.BOLD, 25));
		returnBtn.setSize(120, 50);
		returnBtn.setLocation(50, 115);
		
		queryBtn = new JButton("��    ѯ");
		queryBtn.setFont(new Font(null, Font.BOLD, 25));
		queryBtn.setSize(120, 50);
		queryBtn.setLocation(800, 80);
		
		clearBtn = new JButton("��    ��");
		clearBtn.setFont(new Font(null, Font.BOLD, 25));
		clearBtn.setSize(120, 50);
		clearBtn.setLocation(800, 160);
		
		this.add(scroll);
		this.add(typeLb); this.add(typeTxt);
		this.add(titleLb); this.add(titleTxt);
		this.add(pressLb); this.add(pressTxt);
		this.add(authorLb); this.add(authorTxt);
		this.add(yearLb); this.add(yearCB);
		this.add(priceLb); this.add(priceCB);
		this.add(queryBtn);
		this.add(clearBtn);
		this.add(returnBtn);
		
		typeTxt.addKeyListener(this);
		titleTxt.addKeyListener(this);
		pressTxt.addKeyListener(this);
		authorTxt.addKeyListener(this);
		yearCB.addKeyListener(this);
		priceCB.addKeyListener(this);
		
		queryBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		returnBtn.addActionListener(this);
		
		this.setVisible(true);
	}
		
	// ��ť����
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == queryBtn) {
			String titleStr = titleTxt.getText();
			String typeStr = typeTxt.getText();
			String authorStr = authorTxt.getText();
			String pressStr = pressTxt.getText();
			int priceInd = priceCB.getSelectedIndex();
			int yearInd = yearCB.getSelectedIndex();
			
			ArrayList<Book> bookArray = new Dao().QueryBook(typeStr, titleStr, pressStr, authorStr, yearInd, priceInd);
			Object[][] data = Book.toData(bookArray);
			tableModel.setDataVector(data, columnName);
			MyTableModel.FitTableColumns(table, 2);
		}
		else if(e.getSource() == clearBtn) {
			titleTxt.setText("");
			typeTxt.setText("");
			authorTxt.setText("");
			pressTxt.setText("");
			priceCB.setSelectedIndex(0);
			priceCB.setSelectedIndex(0);
			
			Object[][] data = null;
			tableModel.setDataVector(data, columnName);
			MyTableModel.FitTableColumns(table, 2);
		}
		else if(e.getSource() == returnBtn) {
			this.dispose();
			LoginFrame lf = new LoginFrame();
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
	
	public static void main(String[] args) {
		LibraryFrame lf = new LibraryFrame();
	}
}
