package Inventory;
/**
 *
 * @author Justin
 */
public class Product {
    private String itemType;
    private Number onHand;
    private Number amtSold;
    private Number endAmt;
    private Number atCost;
    private Number pricing;
    private Number percentCost;
            
    //Constructor
    public Product(String itemType, int onHand, int amtSold, 
            int endAmt, double atCost, double pricing){
        this.setItemType(itemType);
        this.setOnHand(onHand);
        this.setAmtSold(amtSold);
        this.setEndAmt(endAmt);
        this.setAtCost(atCost);
        this.setPricing(pricing);
        this.setPercentCost();
    }
    
    //Methods to get and set item type
    public void setItemType(String itemType){
        this.itemType = itemType;
    }
    public String getItemType(){
       return this.itemType;
    }
    
    //Methods to get and set on hands
    public void setOnHand(Number onHand){
        this.onHand = onHand;
    }
    public Number getOnHand(){
        return this.onHand;
    }
    
    //Method to set and get amount sold
    public void setAmtSold(int amtSold){
        this.amtSold = amtSold;
    }
    public Number getAmtSold(){
        return this.amtSold;
    }
    
    //Method to set and get ending amout on hand
    public void setEndAmt(int endAmt){
        this.endAmt = endAmt;
    }
    public Number getEndAmt(){
        return this.endAmt;
    }
    
    //Method to set and get the at cost of each item
    public void setAtCost(double atCost){
        this.atCost = atCost;
    }
    public Number getAtCost(){
        return this.atCost;
    }
    
    //Method to set and get pricing of each item
    public void setPricing(double pricing){
        this.pricing = pricing;
    }
    public Number getPricing(){
        return this.pricing;
    }
    
    //Method to set and get percent of cost
    public void setPercentCost(){
       Double percent;
       percent =(this.getAmtSold().intValue()*this.getAtCost().doubleValue())/(this.getAmtSold().intValue()*this.getPricing().doubleValue());
       percent = 100-(percent*100);
       this.percentCost = percent;
    }
    public Number getPercentCost(){
        return this.percentCost;
    }
    
    //The print out of each product
    @Override
    public String toString(){
        return this.getItemType() + "," + this.getOnHand() + "," + this.getAmtSold() + "," +
                                this.getEndAmt() + "," + this.getAtCost() + "," + this.getPricing() + "," + this.getPercentCost();
    }
}
