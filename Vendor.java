import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;

public class Vendor {

    //Vendor constructor with inventory list by default
    //When we instantiate an instance of Vendor, we can optionally pass in a list with the keyword argument inventory =>figure it out, constructor overloading???

    //add and remove method: 

    public List<Item> inventory;

    public Vendor(){
        this.inventory = new ArrayList<>();
    }

    public Vendor(List<Item> inventory){
        this.inventory = inventory; /// Use "this" to refer to the instance variable
    }

    public Item add(Item item){
        this.inventory.add(item);        
        return item;
    }

    public Item remove(Item item){
        Iterator<Item> iterator = this.inventory.iterator();

        while (iterator.hasNext()){
            Item unit = iterator.next();
            if (unit.equals(item)){
                iterator.remove();
                return item;
            }
        }
        return null; //when item was not found
    }
}