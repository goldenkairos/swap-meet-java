class Clothing extends Item{
    public Clothing(){
        super("Clothing");
    }

    public Clothing(int itemID){
        super("Clothing",itemID);
    }

    public Clothing( double condition){
        super("Clothing", condition);
    }

    public Clothing(int itemID, double condition){
        super("Clothing", itemID, condition);
    }

}
