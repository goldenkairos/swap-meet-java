public abstract class Item {
    //abstract class
    //required category //abstract property
    //abstract int property itemID

    //Constructor should have category and itemID

    
    protected int itemID;
    protected String category = "";

    public Item(String category, int id){
        this.category = category;
        this.itemID = id;
    }

    protected int getItemID(Item item){
        return this.itemID;
    }

    protected void setItemID(int newID){
        this.itemID = newID;
    }

    protected String getItemCategory(){
        return this.category;
    }
}
