package base;

import java.util.*;

public class BorrowRecord {
	public int no;
	public String cno, bno, title;
	public String borrow_date, return_date, return_ddl;
	public String ano;
	
	public BorrowRecord(int no, String cno, String bno, String title, String borrow_date,
			String return_date, String return_ddl, String ano) {
		this.no = no;
		this.cno = cno;
		this.bno = bno;
		this.title = title;
		this.borrow_date = borrow_date;
		this.return_date = return_date;
		this.return_ddl = return_ddl;
		this.ano = ano;		
	}
	
	public BorrowRecord(BorrowRecord br) {
		this.no = br.no;
		this.cno = br.cno;
		this.bno = br.bno;
		this.title = br.title;
		this.borrow_date = br.borrow_date;
		this.return_date = br.return_date;
		this.return_ddl = br.return_ddl;
		this.ano = br.ano;		
	}
	
	public static Object[][] toData(ArrayList<BorrowRecord> br){
		Object[][] data = new Object[br.size()][8];
		for(int i = 0; i < br.size(); i++) {
			BorrowRecord temp = br.get(i);
			Object[] rowData = {temp.no, temp.cno, temp.bno, temp.title, temp.borrow_date, 
					temp.return_date, temp.return_ddl, temp.ano};
			data[i] = rowData;			
		}	
		return data;
	}
	
}
