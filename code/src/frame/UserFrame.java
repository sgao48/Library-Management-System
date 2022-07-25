package frame;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import panel.*;

public class UserFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public UserFrame() {
		this.setTitle("�û�����ϵͳ");
		this.setSize(1000, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setUI(new MyTabbedPaneUI());
		
		tabbedPane.addTab("������Ϣ", new UserBorrow());
		tabbedPane.addTab("����", new Setting(1));
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("��ǰѡ�е�ѡ�" + tabbedPane.getSelectedIndex());
			}
		});
		
		tabbedPane.setSelectedIndex(1);
		
		this.setContentPane(tabbedPane);
		this.setVisible(true);
	}
	
	// ���������
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
