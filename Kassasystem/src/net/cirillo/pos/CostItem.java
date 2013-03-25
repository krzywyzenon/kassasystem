package net.cirillo.pos;


public class CostItem {
String itemName;
Integer itemCost;
Integer itemAmount;
String buttonName = "X";

 
public CostItem(String name, int cost, int amount){
itemName = name;
itemCost = cost;
itemAmount = amount;
}

public String getColumn(int col) {
switch(col){
case 0: return itemName;
case 1: return itemCost.toString();
case 2: return itemAmount.toString();
case 3: return buttonName;
}
return "";
}

public void setValueAt(int col, Object value) {
switch(col){
case 0: itemName = value.toString();
case 1: itemCost = Integer.parseInt(value.toString());
case 2: itemAmount = Integer.parseInt(value.toString());
case 3: buttonName = value.toString();
}
}
}
