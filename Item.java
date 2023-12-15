public abstract class Item {
    //abstract class
    //required category //abstract property
    //abstract int property itemID

    //Constructor should have category and itemID
    //TO DO in Item, we will create a unique itemID and pass it to the subclass as we create an instance
    private static int itemCount = 0;
    private static final Object lockObject = new Object();

    protected int itemID;
    protected String category = "";
    protected double condition;
    protected int age;

    public Item(String category){
        this.category = category;
            synchronized(lockObject){
                this.itemID = ++itemCount;
            }
        // this.itemID = id;
        this.condition = 0.0;
    }

    public Item(String category, double condition){
        this.category = category;
        // this.itemID = id;
        synchronized(lockObject){
                this.itemID = ++itemCount;
            }
        this.condition = condition;
    }

    @Override
    public String toString(){
        return this.category+".itemID" + this.itemID;
    }

    protected int getItemID(){
        return this.itemID;
    }

    protected void setItemID(int newID){
        this.itemID = newID;
    }

    protected double getCondition(){
        return this.condition; //or item.condition?
    }

    protected void setCondition(double condition){
        if(condition >5 || condition <0){
            System.out.println("Please enter item condition between 0 and 5");
        } else {
            this.condition = condition;
        }
        
    }

    protected String conditionDescription(){
        String description = "";
        if (this.condition == 0){
            description ="Vintage, but not in a good way. Collecting dust and memories.";
        } else if (this.condition <= 1){
            description ="Gently pre-loved, like a well-worn teddy bears.";
        } else if (this.condition <= 2){
            description ="Showing signs of a life well-lived and some battle scars.";
        } else if (this.condition <= 3){
            description ="In its prime, like a fine cheese but without the aroma.";
        } else if (this.condition <= 4){
            description ="Almost new, just a few minor scratches—character building, right?.";
        } else if (this.condition <= 5){
            description ="Mint condition. It's practically a museum piece.";
        }
        return description;
    }
}
