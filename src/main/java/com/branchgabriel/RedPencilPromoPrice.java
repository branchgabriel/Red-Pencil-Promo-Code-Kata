package com.branchgabriel;

import java.util.*;

public class RedPencilPromoPrice{
    private float price = 0.0f;
    private LinkedHashMap<Date, Float> priceHistory;

    public RedPencilPromoPrice(LinkedHashMap<Date, Float> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public float getPrice(){
        return price;
    }

    public Map<Date, Float> getPriceHistory() {
        return priceHistory;
    }

    public boolean priceHasDecreased() {
        boolean decreaseFound = false;
        if (priceHistory != null && priceHistory.size() > 1) {
            Iterator it = priceHistory.entrySet().iterator();
            Float lastPrice = null;
            while (it.hasNext()) {
                Map.Entry historicalPrice = (Map.Entry) it.next();
                if (lastPrice != null) {
                    decreaseFound = lastPrice > (Float) historicalPrice.getValue();
                }
                if(decreaseFound){
                    break;
                }
                lastPrice = (Float) historicalPrice.getValue();
            }
        }
        return decreaseFound;
    }
}
