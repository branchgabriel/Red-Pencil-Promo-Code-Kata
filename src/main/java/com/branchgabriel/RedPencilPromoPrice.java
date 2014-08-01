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
        initializePriceData();
    }

    public boolean isPromoActive() {
        boolean isActive = false;
        if (priceHasDecreased()) {
            if (!hasPreviousExpiredRedPencilPromo() && hasPreviousActiveRedPencilPromo() && !isStable()) {
                isActive = true;
            }
            if (isStable() && decreaseIsInPromoRange()) {
                isActive = true;
            }
        }
        return isActive;
    }

    protected Map<Date, Float> getPriceHistory() {
        return priceHistory;
    }

    public boolean priceHasDecreased(){
        return lowerPrice < higherPrice;
    }

    public boolean decreaseIsInPromoRange(){
        return (MIN_REDUCTION < (higherPrice - lowerPrice)/higherPrice) && (MAX_REDUCTION > (higherPrice - lowerPrice)/higherPrice);
    }

    public boolean isStable() {
        return (STABLE_AT_LEAST <= getDateDifference(lowerPriceDate, higherPriceDate));
    }
    public boolean hasPreviousExpiredRedPencilPromo() {
        return hasPreviousRedPencilPromoByStatus(PromoStatus.Expired);
    }
    public boolean hasPreviousActiveRedPencilPromo() {
        return hasPreviousRedPencilPromoByStatus(PromoStatus.Active);
    }

    private boolean hasPreviousRedPencilPromoByStatus(PromoStatus status) {
        ListIterator<Entry<Date, Float>> listIterator =
                new ArrayList(priceHistory.entrySet()).listIterator(priceHistory.size());
        boolean hasPromo = false;
        Entry<Date, Float> lastPrice = null;
        while (listIterator.hasPrevious()) {
            Entry<Date, Float> historicalPrice = listIterator.previous();
            if (lastPrice != null && lastPrice.getValue() < historicalPrice.getValue()) {
                if (status.equals(PromoStatus.Expired) &&
                        getDateDifference(lastPrice.getKey(), historicalPrice.getKey()) > PROMO_LENGTH) {
                    hasPromo = true;
                    break;
                } else if (status.equals(PromoStatus.Active) &&
                        getDateDifference(lastPrice.getKey(), historicalPrice.getKey()) <= PROMO_LENGTH) {
                    hasPromo = true;
                    break;
                }
            }
            lastPrice = historicalPrice;
        }
        return hasPromo;
    }

    private long getDateDifference(Date lowerPriceDate, Date higherPriceDate) {
        long timeDifference = (lowerPriceDate.getTime() - higherPriceDate.getTime());
        return Math.abs(timeDifference / (1000 * 60 * 60 * 24));
    }

    private void initializePriceData() {
        Date lastPriceDate = null;
        if (priceHistory != null && priceHistory.size() > 1) {
            ListIterator<Map.Entry<Date, Float>> listIterator =
                    new ArrayList(priceHistory.entrySet()).listIterator(priceHistory.size());
            Float lastPrice = null;
            while (listIterator.hasPrevious()) {
                Map.Entry<Date, Float> historicalPrice = listIterator.previous();
                if (lastPrice != null) {
                    lowerPrice = lastPrice;
                    lowerPriceDate = lastPriceDate;
                    higherPrice = historicalPrice.getValue();
                    higherPriceDate = historicalPrice.getKey();
                    break;
                } else {
                    lastPrice = historicalPrice.getValue();
                    lastPriceDate = historicalPrice.getKey();
                }
            }
        }
    }

}
