package frame;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import panel.*;

public class AdminFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	public AdminFrame() {
		this.setTitle("ͼ�����ϵͳ");
		this.setSize(1000, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setUI(new MyTabbedPaneUI());
		
		tabbedPane.addTab("ע��card", new AddCard());
		tabbedPane.addTab("ɾ��card", new DeleteCard());
		tabbedPane.addTab("�������", new AddBook());
		tabbedPane.addTab("�������", new AddBatchBook());
		tabbedPane.addTab("��/����", new BorrowBook());
		tabbedPane.addTab("����", new Setting(0));
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("��ǰѡ�е�ѡ�" + tabbedPane.getSelectedIndex());
			}
		});
		
		tabbedPane.setSelectedIndex(5);
		
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
		AdminFrame mf = new AdminFrame();
	}
}
