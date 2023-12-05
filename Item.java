public abstract class Item {
    //abstract class
    //required category //abstract property
    //abstract int property itemID

    //Constructor should have category and itemID
    //TO DO in Item, we will create a unique itemID and pass it to the subclass as we create an instance
    
    protected int itemID;
    protected String category = "";
    protected int condition;

    public Item(String category, int id){
        this.category = category;
        this.itemID = id;
        this.condition = 0;
    }

    public Item(String category, int id, int condition){
        this.category = category;
        this.itemID = id;
        this.condition = condition;
    }

    @Override
    public String toString(){
        return this.category+".itemID" + this.itemID;
    }

    protected int getItemID(Item item){
        return this.itemID;
    }

    protected void setItemID(int newID){
        this.itemID = newID;
    }

    protected int getCondition(Item item){
        return this.condition;
    }

    protected void setCondition(Item item, int condition){
        this.condition = condition;
    }

}
