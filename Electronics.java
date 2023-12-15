class Electronics extends Item {
    public Electronics(){
        super("Electronics");
    }
    public Electronics(int itemID){
        super("Electronics",itemID);
    }

    public Electronics( double condition){
        super("Electronics",condition);
    }

    public Electronics(int itemID, double condition){
        super("Electronics",itemID,condition);
    }
}
