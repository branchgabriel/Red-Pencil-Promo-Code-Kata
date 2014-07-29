package com.branchgabriel;

import java.util.*;

public class RedPencilPromoPrice {
    public static final float MIN_REDUCTION = .05f;
    public static final float MAX_REDUCTION = 0.3f;
    public static final int STABLE_AT_LEAST = 30;
    public static final int MAX_DURATION = 30;

    private float price = 0.0f;
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
        if (priceHistory != null && priceHistory.size() > 1) {
            ListIterator<Map.Entry<Date, Float>> listIterator =
                    new ArrayList(priceHistory.entrySet()).listIterator(priceHistory.size());

            Float lastPrice = null;
            while (listIterator.hasPrevious()) {
                Map.Entry<Date, Float> historicalPrice = listIterator.previous();
                if (lastPrice != null) {
                    decreaseFound = lastPrice < historicalPrice.getValue();
                    break;
                } else {
                    lastPrice = historicalPrice.getValue();
                }                  ;
            }
        }
        return decreaseFound;
    }

    public boolean decreaseIsInPromoRange(){
        float higherPrice = 0.0f;
        ListIterator<Map.Entry<Date, Float>> listIterator =
                new ArrayList(priceHistory.entrySet()).listIterator(priceHistory.size());

        Float lowerPrice = null;
        while (listIterator.hasPrevious()) {
            Map.Entry<Date, Float> historicalPrice = listIterator.previous();
            if (lowerPrice != null) {
                if(lowerPrice < historicalPrice.getValue()){
                    higherPrice = historicalPrice.getValue();
                    break;
                }
            } else {
                lowerPrice = historicalPrice.getValue();
            }
        }
        return (MIN_REDUCTION < (higherPrice - lowerPrice)/higherPrice) && (MAX_REDUCTION > (higherPrice - lowerPrice)/higherPrice);
    }

    public boolean isStable() {
        return false;
    }
}
