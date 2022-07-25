package base;

import java.util.ArrayList;

public class Book {
	public String bno, category, title, press, author;
	public int year, total, stock;
	public float price;
	
	public Book(String bno, String category, String title, String press,
			int year, String author, float price, int total, int stock) {
		this.bno = bno;
		this.category = category;
		this.title = title;
		this.press = press;
		this.year = year;
		this.author = author;
		this.price = price;
		this.total = total;
		this.stock = stock;		
	}
	
	public static Object[][] toData(ArrayList<Book> bookArray){
		Object[][] res = new Object[bookArray.size()][9];
		for(int i = 0; i < bookArray.size(); i++) {
			Book book = bookArray.get(i);
			Object[] temp = {book.bno, book.category, book.title, book.press, book.year, book.author, book.price, book.total, book.stock};
			res[i] = temp;
		}	
		return res;
	}
}
