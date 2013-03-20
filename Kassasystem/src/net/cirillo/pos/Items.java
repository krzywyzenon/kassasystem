package net.cirillo.pos;

public class Items {
	
	MysqlConnection db;
	
	public Items() {
		db = new MysqlConnection();
	}
	
	public void SearchItem(String s) {
		db.connect();
		db.select("SELECT * FROM item WHERE itemid = " + "'" + s + "'" + " OR name like" + "'%" + s + "%';" );
		db.close();
	}
	
	public void addItem(String name, int price) {
		//Connect to database and add new item then close connection
		db.connect();
		db.update("INSERT INTO item VALUES(0,'"+name+"','"+price+"','"+0+"')");
		db.close();
	}
	
	public void removeItem(String s) {
		
		
	}

}