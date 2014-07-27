package com.branchgabriel;

import java.util.*;

public class RedPencilPromoPrice{
    private float price = 0.0f;
    private Map<Date, Float> priceHistory;

    public RedPencilPromoPrice(Map<Date, Float> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public float getPrice(){
        return price;
    }

    public Map<Date, Float> getPriceHistory() {
        return priceHistory;
    }

    public boolean priceHasDecreased() {
        return false;
    }
}
