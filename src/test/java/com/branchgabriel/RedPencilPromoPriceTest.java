package com.branchgabriel;

import static java.util.Calendar.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.*;

import java.util.*;

public class RedPencilPromoPriceTest{

    RedPencilPromoPrice redPencilPromoPrice;

    @Before
    public void setup(){
        redPencilPromoPrice = new RedPencilPromoPrice(buildUnstablePriceHistory());
    }

    @Test
    public void redPencilPromoInitializesWithZeroBasedPrice(){
        RedPencilPromoAssertions.assertThat(redPencilPromoPrice).hasPrice(0);
    }

    @Test
    public void redPencilPromoInitializedWithPriceHistoryOfItem(){
        redPencilPromoPrice = new RedPencilPromoPrice(buildUnstablePriceHistory());
        assertThat(redPencilPromoPrice.getPriceHistory()).hasSize(4);
    }

    @Test
    public void redPencilPromoPriceInitiatesWhenPriceGoesDown(){
        redPencilPromoPrice = new RedPencilPromoPrice(buildUnstablePriceHistory());
        assertThat(redPencilPromoPrice.priceHasDecreased()).isEqualTo(true);
    }

    private LinkedHashMap<Date, Float> buildUnstablePriceHistory() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(31), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(30), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(29), 99.99f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 89.99f);

        return priceHistory;
    }

    private Date createNewDateBasedOnDaysAgo(int daysAgo){
        Calendar calendar = getInstance();
        calendar.add(DATE, (-1*daysAgo));
        return calendar.getTime();
    }

}