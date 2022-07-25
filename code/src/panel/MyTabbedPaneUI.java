package panel;

import java.awt.*;
import javax.swing.plaf.basic.*;

public class MyTabbedPaneUI extends BasicTabbedPaneUI{
	static final int w = 123;
	
	// 定义选项卡的宽高
	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
		return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 30;
	}
	
	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
		return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 50;
	}
	
	// 定义选项卡上的文本（颜色，字体，大小）
    protected void paintText(Graphics g,
            int tabPlacement, Font font, FontMetrics metrics,
            int tabIndex, String title, Rectangle textRect,
            boolean isSelected) {
    	
    	g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.BOLD, 20));
		
		int h = calculateTabHeight(tabPlacement, tabIndex, 20);
//		int w = calculateTabWidth(tabPlacement, tabIndex, metrics);
		
		// 分别在选项卡上画上文字
		int initWidth = (w-110)/2 + 15;
		g.drawString(title, initWidth + tabIndex*w, h/2 + 11);
	}
}
