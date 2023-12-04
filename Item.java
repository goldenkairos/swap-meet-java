public abstract class Item {
    //abstract class
    //required category //abstract property
    //abstract int property itemID

    //Constructor should have category and itemID
    //TO DO in Item, we will create a unique itemID and pass it to the subclass as we create an instance
    
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
    //look into toString insteaed of getItemCategory
}
