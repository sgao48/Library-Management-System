package panel;

import java.util.Enumeration;
import javax.swing.JTable;
import javax.swing.table.*;

public class MyTableModel extends DefaultTableModel{
	private static final long serialVersionUID = 1L;

	// 禁止编辑表格
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	// 表格滚动条和列宽自适应
    public static void FitTableColumns(JTable myTable, int mode) {
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount(); 

        Enumeration columns = myTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) myTable.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(myTable, column.getIdentifier(), false, false, -1, col)
                    .getPreferredSize().getWidth();

            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) myTable.getCellRenderer(row, col)
                        .getTableCellRendererComponent(myTable, myTable.getValueAt(row, col), false, false, row, col)
                        .getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            
            header.setResizingColumn(column);
            if(mode == 1) {
                column.setWidth(width + myTable.getIntercellSpacing().width + 20);
            }
            else if(mode == 2){
            	column.setWidth(width + myTable.getIntercellSpacing().width + 40);
            }
            myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

}
