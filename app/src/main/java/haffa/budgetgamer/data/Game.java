package haffa.budgetgamer.data;

/**
 * Created by Peker on 11/24/2016.
 */

public class Game {

    /**
     * internalName : INDIEMEGABUNDLE
     * title : Indie Mega Bundle
     * metacriticLink : null
     * dealID : 6eaYZd%2F5N%2FWTNIYuJ5ZdNkQTxE95erhr%2B47c5t45plg%3D
     * storeID : 3
     * gameID : 162334
     * salePrice : 3.99
     * normalPrice : 179.24
     * isOnSale : 1
     * savings : 97.773934
     * metacriticScore : 0
     * steamRatingText : null
     * steamRatingPercent : 0
     * steamRatingCount : 0
     * releaseDate : 0
     * lastChange : 1479495279
     * dealRating : 10.0
     * thumb : https://www.greenmangaming.com/globalassets/games/green-man-gaming/promo/indie-mega-bundle-2016/indie-mega-bundle-2016_2/prod_verticalmodule_25gameindiepack_178x237.jpg
     */

    private String internalName;
    private String title;
    private String dealID;
    private String storeID;
    private String gameID;
    private String salePrice;
    private String normalPrice;
    private String isOnSale;
    private String savings;
    private Object steamRatingText;
    private String steamRatingPercent;
    private int lastChange;
    private String dealRating;
    private String thumb;
    private int _id;

    // constructor
    public Game(){}

    public Game(int id, String title, String dealID, String storeID, String gameID, String salePrice,
                String normalPrice, String savings, String dealRating, String thumb){
        this._id = id;
        this.title = title;
        this.dealID = dealID;
        this.storeID = storeID;
        this.gameID = gameID;
        this.salePrice = salePrice;
        this.normalPrice = normalPrice;
        this.dealRating = dealRating;
        this.savings = savings;
        this.thumb = thumb;
    }

    public Game(String title, String dealID, String storeID, String gameID, String salePrice,
                String normalPrice, String savings, String dealRating, String thumb){

        this.title = title;
        this.dealID = dealID;
        this.storeID = storeID;
        this.gameID = gameID;
        this.salePrice = salePrice;
        this.normalPrice = normalPrice;
        this.dealRating = dealRating;
        this.savings = savings;
        this.thumb = thumb;
    }



    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDealID() {
        return dealID;
    }

    public void setDealID(String dealID) {
        this.dealID = dealID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(String isOnSale) {
        this.isOnSale = isOnSale;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    public Object getSteamRatingText() {
        return steamRatingText;
    }

    public void setSteamRatingText(Object steamRatingText) {
        this.steamRatingText = steamRatingText;
    }

    public String getSteamRatingPercent() {
        return steamRatingPercent;
    }

    public void setSteamRatingPercent(String steamRatingPercent) {
        this.steamRatingPercent = steamRatingPercent;
    }

    public int getLastChange() {
        return lastChange;
    }

    public void setLastChange(int lastChange) {
        this.lastChange = lastChange;
    }

    public String getDealRating() {
        return dealRating;
    }

    public void setDealRating(String dealRating) {
        this.dealRating = dealRating;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }
}
