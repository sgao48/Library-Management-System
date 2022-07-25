package frame;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import panel.*;

public class AdminFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	public AdminFrame() {
		this.setTitle("图书管理系统");
		this.setSize(1000, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setUI(new MyTabbedPaneUI());
		
		tabbedPane.addTab("注册card", new AddCard());
		tabbedPane.addTab("删除card", new DeleteCard());
		tabbedPane.addTab("单本入库", new AddBook());
		tabbedPane.addTab("批量入库", new AddBatchBook());
		tabbedPane.addTab("借/还书", new BorrowBook());
		tabbedPane.addTab("设置", new Setting(0));
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("当前选中的选项卡" + tabbedPane.getSelectedIndex());
			}
		});
		
		tabbedPane.setSelectedIndex(5);
		
		this.setContentPane(tabbedPane);
		this.setVisible(true);
	}
	
	// 测试用面板
	public static JComponent createTextPanel(String text) {
		JPanel panel = new JPanel(new GridLayout(1, 1));
		
		JLabel label = new JLabel(text);
		label.setFont(new Font(null, Font.PLAIN, 50));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel.add(label);
		
		return panel;
	}
	
	public static void main(String[] args) {
		AdminFrame mf = new AdminFrame();
	}
}
