package com.branchgabriel;

import static java.util.Calendar.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.*;

import java.util.*;

public class RedPencilPromoPriceTest{

    RedPencilPromoPrice redPencilPromoPrice;

    @Before
    public void setup(){
        redPencilPromoPrice = new RedPencilPromoPrice(withUnstablePriceHistoryContainingDecrease());
    }

    @Test
    public void redPencilPromoInitializesWithZeroBasedPrice(){
        RedPencilPromoAssertions.assertThat(redPencilPromoPrice).hasPrice(0);
    }

    @Test
    public void redPencilPromoInitializedWithPriceHistoryOfItem(){
        redPencilPromoPrice = new RedPencilPromoPrice(withUnstablePriceHistoryContainingDecrease());
        assertThat(redPencilPromoPrice.getPriceHistory()).hasSize(4);
    }

    @Test
    public void redPencilPromoPriceCanDetermineIfPriceGoesDownGivenUnstableHistory(){
        redPencilPromoPrice = new RedPencilPromoPrice(withUnstablePriceHistoryContainingDecrease());
        assertThat(redPencilPromoPrice.priceHasDecreased()).isEqualTo(true);
    }

    @Test
    public void redPencilPromoPriceCanDetermineIfPriceIsStableGivenStableHistory(){
        redPencilPromoPrice = new RedPencilPromoPrice(withStablePriceHistory());
        assertThat(redPencilPromoPrice.priceHasDecreased()).isEqualTo(false);
    }

    @Test
    public void redPencilPromoPriceCanDetermineIfPriceDecreasedGivenUnstableHistoryWithBothIncreasedAndDecreasedPrices(){
        redPencilPromoPrice = new RedPencilPromoPrice(withUnStablePriceHistoryContainingIncreasedAndDecreasedPrices());
        assertThat(redPencilPromoPrice.priceHasDecreased()).isEqualTo(false);
    }

    @Test
    public void redPencilPromoPriceCanDetermineIfPriceDecreasedGivenUnstableHistoryWithIncreasedOnlyPrices() {
        redPencilPromoPrice = new RedPencilPromoPrice(withUnStablePriceHistoryContainingIncrease());
        assertThat(redPencilPromoPrice.priceHasDecreased()).isEqualTo(false);
    }


    @Test
    public void redPencilPromoPriceDecreaseIsWithinPromoRange(){

    }

    private LinkedHashMap<Date, Float> withUnstablePriceHistoryContainingDecrease() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(31), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(30), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(29), 99.99f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 89.99f);

        return priceHistory;
    }

    private LinkedHashMap<Date, Float> withStablePriceHistory() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(31), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(30), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(29), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 100.0f);

        return priceHistory;
    }

    private LinkedHashMap<Date,Float> withUnStablePriceHistoryContainingIncreasedAndDecreasedPrices() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(31), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(30), 101.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(29), 89.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 100.0f);

        return priceHistory;
    }

    private LinkedHashMap<Date, Float> withUnStablePriceHistoryContainingIncrease() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(31), 99.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(30), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(29), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 110.0f);

        return priceHistory;
    }

    private Date createNewDateBasedOnDaysAgo(int daysAgo){
        Calendar calendar = getInstance();
        calendar.add(DATE, -1*daysAgo);
        return calendar.getTime();
    }

}