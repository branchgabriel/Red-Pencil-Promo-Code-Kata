package com.branchgabriel;

import static java.util.Calendar.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.*;

import java.util.*;

public class RedPencilPromoPriceTest{

    RedPencilPromoPrice redPencilPromoPrice;

    @Before
    public void setup(){
        redPencilPromoPrice = new RedPencilPromoPrice(buildPriceHistory());
    }

    @Test
    public void redPencilPromoInitializesWithZeroBasedPrice(){
        RedPencilPromoAssertions.assertThat(redPencilPromoPrice).hasPrice(0);
    }

    @Test
    public void redPencilPromoInitializedWithPriceHistoryOfItem(){
        redPencilPromoPrice = new RedPencilPromoPrice(buildPriceHistory());
        assertThat(redPencilPromoPrice.getPriceHistory()).hasSize(4);
    }

    private Map<Date, Float> buildPriceHistory() {
        HashMap<Date, Float> priceHistory = new HashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(31), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(30), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(29), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 100.0f);

        return priceHistory;
    }

    private Date createNewDateBasedOnDaysAgo(int daysAgo){
        Calendar calendar = getInstance();
        calendar.add(DATE, (-1*daysAgo));
        return calendar.getTime();
    }

}