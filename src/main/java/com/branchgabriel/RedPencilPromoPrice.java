package com.branchgabriel;

import java.util.*;

public class RedPencilPromoPrice {
    public static final float MIN_REDUCTION = .05f;
    public static final float MAX_REDUCTION = 0.3f;
    public static final int STABLE_AT_LEAST = 30;
    public static final int MAX_DURATION = 30;

    private float price = 0.0f;

    private float lowerPrice = 0.0f;
    private Date lowerPriceDate = null;
    private float higherPrice = 0.0f;
    private Date higherPriceDate = null;

    private LinkedHashMap<Date, Float> priceHistory;

    public RedPencilPromoPrice(LinkedHashMap<Date, Float> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public float getPrice() {
        return price;
    }

    public Map<Date, Float> getPriceHistory() {
        return priceHistory;
    }

    public boolean priceHasDecreased() {
        boolean decreaseFound = false;
        Date lastPriceDate = null;
        if (priceHistory != null && priceHistory.size() > 1) {
            ListIterator<Map.Entry<Date, Float>> listIterator =
                    new ArrayList(priceHistory.entrySet()).listIterator(priceHistory.size());

            Float lastPrice = null;
            while (listIterator.hasPrevious()) {
                Map.Entry<Date, Float> historicalPrice = listIterator.previous();
                if (lastPrice != null) {
                    decreaseFound = lastPrice < historicalPrice.getValue();
                    lowerPrice = lastPrice;
                    lowerPriceDate = lastPriceDate;
                    higherPrice = historicalPrice.getValue();
                    higherPriceDate = historicalPrice.getKey();
                    break;
                } else {
                    lastPrice = historicalPrice.getValue();
                    lastPriceDate = historicalPrice.getKey();
                }                  ;
            }
        }
        return decreaseFound;
    }

    public boolean decreaseIsInPromoRange(){
        if(priceHasDecreased()){
            return (MIN_REDUCTION < (higherPrice - lowerPrice)/higherPrice) && (MAX_REDUCTION > (higherPrice - lowerPrice)/higherPrice);
        }
        return false;
    }

    public boolean isStable() {
        if(priceHasDecreased()){
            long timeDifference = lowerPriceDate.getTime() - higherPriceDate.getTime();
            long diffDays = timeDifference / (1000 * 60 * 60 * 24);
            return (STABLE_AT_LEAST <= diffDays);
        }
        return false;
    }
}
