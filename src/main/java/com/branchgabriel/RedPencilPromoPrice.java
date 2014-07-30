package com.branchgabriel;

import java.util.*;
import java.util.Map.Entry;

public class RedPencilPromoPrice {
    public static final float MIN_REDUCTION = .05f;
    public static final float MAX_REDUCTION = 0.3f;
    public static final int STABLE_AT_LEAST = 30;
    public static final int PROMO_LENGTH = 30;

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
            return (STABLE_AT_LEAST <= getDateDifference(lowerPriceDate, higherPriceDate));
        }
        return false;
    }

    public boolean hasNoPreviousRedPencilPromo() {
        List<Long> decreased = buildDecreaseDiffs();
        boolean hasNoPreviousPencilPromo=true;
        if (decreased.size() > 1) {
            for (Long dateDiff : decreased) {
                if (dateDiff >= PROMO_LENGTH) {
                    hasNoPreviousPencilPromo = false;
                    break;
                }
            }
        }
        return hasNoPreviousPencilPromo;
    }

    private List<Long> buildDecreaseDiffs() {
        ListIterator<Entry<Date, Float>> listIterator =
                new ArrayList(priceHistory.entrySet()).listIterator(priceHistory.size());
        List<Long> decreased = new ArrayList<Long>();
        Entry<Date, Float> lastPrice = null;
        while (listIterator.hasPrevious()) {
            Entry<Date, Float> historicalPrice = listIterator.previous();
            if (lastPrice != null) {
                if (lastPrice.getValue() < historicalPrice.getValue()) {
                    decreased.add(getDateDifference(lastPrice.getKey(),historicalPrice.getKey()));
                }
            }
            lastPrice = historicalPrice;
        }
        return decreased;
    }

    private long getDateDifference(Date lowerPriceDate, Date higherPriceDate) {
        long timeDifference = lowerPriceDate.getTime() - higherPriceDate.getTime();
        return timeDifference / (1000 * 60 * 60 * 24);
    }
}
