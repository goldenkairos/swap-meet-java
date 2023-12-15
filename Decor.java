class Decor extends Item {
    public Decor(){
        super("Decor");
    }

    public Decor(int itemID){
        super("Decor", itemID);
    }

    public Decor(double condition){
        super("Decor", condition);
    }

    public Decor(int itemID, double condition){
        super("Decor", itemID, condition);
    }
}
