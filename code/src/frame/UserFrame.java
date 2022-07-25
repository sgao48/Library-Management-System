package frame;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import panel.*;

public class UserFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public UserFrame() {
		this.setTitle("用户管理系统");
		this.setSize(1000, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setUI(new MyTabbedPaneUI());
		
		tabbedPane.addTab("借阅信息", new UserBorrow());
		tabbedPane.addTab("设置", new Setting(1));
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("当前选中的选项卡" + tabbedPane.getSelectedIndex());
			}
		});
		
		tabbedPane.setSelectedIndex(1);
		
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
		UserFrame uf = new UserFrame();
	}
}
