package net.cirillo.pos;
 
public class Items {
           
            MysqlConnection db;
           
            public Items() {
                         db = new MysqlConnection();
            }
           
            public void SearchItem(String s) {
                         db.connect();
                         db.select("SELECT * FROM item WHERE itemid = " + "'" + s + "'" + " OR name =" + "'" + s + "';" );
                         db.close();
            }
           
            public void addItem(String name, int price) {
                         //Connect to database and add new item then close connection
                         db.connect();
                         db.update("INSERT INTO item VALUES(0,'"+name+"','"+price+"','"+0+"')");
                         db.close();
            }
           
            public void deleteItem(int itemid) {
                        
            db.connect();
            db.delete("DELETE FROM item WHERE itemid = "+itemid+"");
            db.close();
                        
            System.out.println("item with id " + itemid + " was deleted");
            }
           
            public void updateItem(int itemid, String name, int price, int stock) {
                         db.connect();
                         db.update("UPDATE item SET itemid="+itemid+", name='"+name+"', price="+price+", stockamount="+stock+" WHERE itemid="+itemid+"");
                         db.close();
            }
 
}
