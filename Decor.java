class Decor extends Item {
    public Decor(int itemID){
        super("Decor", itemID);
    }

    public Decor(int itemID, double condition){
        super("Decor", itemID, condition);
    }
}
