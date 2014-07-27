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
            ListIterator<Map.Entry<Date, Float>>listIterator = new ArrayList(priceHistory.entrySet()).listIterator(priceHistory.size());

            Float lastPrice = null;
            while (listIterator.hasPrevious()) {
                Map.Entry<Date, Float> historicalPrice = listIterator.previous();
                if (lastPrice != null) {
                    decreaseFound = lastPrice < historicalPrice.getValue();
                    break;
                } else {
                    lastPrice = historicalPrice.getValue();
                }

            }
        }
        return decreaseFound;
    }
}
