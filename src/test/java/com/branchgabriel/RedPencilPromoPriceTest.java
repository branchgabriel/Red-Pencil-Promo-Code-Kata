package com.branchgabriel;

import static java.util.Calendar.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.*;

public class RedPencilPromoPriceTest{

    RedPencilPromoPrice redPencilPromoPrice;

    @Test
    public void redPencilPromoInitializedWithPriceHistoryOfItem(){
        redPencilPromoPrice = new RedPencilPromoPrice(withUnstablePriceHistoryContainingDecreasingPrices());
        assertThat(redPencilPromoPrice.getPriceHistory()).hasSize(3);
    }

    @Test
    public void redPencilPromoPriceCanDetermineIfPriceGoesDownGivenUnstableHistory(){
        redPencilPromoPrice = new RedPencilPromoPrice(withUnstablePriceHistoryContainingDecreasingPrices());
        assertThat(redPencilPromoPrice.priceHasDecreased()).isTrue();
    }

    @Test
    public void redPencilPromoPriceCanDetermineIfPriceIsStableGivenStableHistory(){
        redPencilPromoPrice = new RedPencilPromoPrice(withStablePriceHistoryAndNoDecrease());
        assertThat(redPencilPromoPrice.priceHasDecreased()).isFalse();
    }

    @Test
    public void redPencilPromoPriceCanDetermineIfPriceDecreasedGivenUnstableHistoryWithBothIncreasedAndDecreasedPrices(){
        redPencilPromoPrice = new RedPencilPromoPrice(withUnStablePriceHistoryContainingIncreasedAndDecreasedPrices());
        assertThat(redPencilPromoPrice.priceHasDecreased()).isFalse();
    }

    @Test
    public void redPencilPromoPriceCanDetermineIfPriceDecreasedGivenUnstableHistoryWithIncreasedOnlyPrices() {
        redPencilPromoPrice = new RedPencilPromoPrice(withUnStablePriceHistoryContainingIncrease());
        assertThat(redPencilPromoPrice.priceHasDecreased()).isFalse();
    }


    @Test
    public void redPencilPromoPriceDecreaseIsWithinPromoRangeGivenValidRange(){
       redPencilPromoPrice = new RedPencilPromoPrice(withUnstablePriceHistoryContainingDecreasingPrices());
       assertThat(redPencilPromoPrice.decreaseIsInPromoRange()).isTrue();
    }

    @Test
    public void redPencilPromoPriceDecreaseIsNotWithinPromoRangeGivenLargePriceRangeData() {
        redPencilPromoPrice = new RedPencilPromoPrice(withUnStablePriceHistoryContainingOutOfRangeDecrease());
        assertThat(redPencilPromoPrice.decreaseIsInPromoRange()).isFalse();
    }

    @Test
    public void redPencilPromoPriceStableIsFalseWhenNoDecreaseDetected(){
        redPencilPromoPrice = new RedPencilPromoPrice(withStablePriceHistoryAndNoDecrease());
        assertThat(redPencilPromoPrice.isStable()).isFalse();
    }

    @Test
    public void redPencilPromoPriceIsStableIsTrueWhenHistoryHasSableDecreaseEqualing30Days(){
        redPencilPromoPrice = new RedPencilPromoPrice(withStablePriceHistoryFor30DaysAndDecrease());
        assertThat(redPencilPromoPrice.isStable()).isTrue();
    }

    @Test
    public void redPencilPromoPriceStableFor30DaysWhenHistoryHasSableDecreaseWithMoreThan30DayGap() {
        redPencilPromoPrice = new RedPencilPromoPrice(withStablePriceHistoryAndDecreaseWith30DayGap());
        assertThat(redPencilPromoPrice.isStable()).isTrue();
    }

    @Test
    public void redPencilPromoPriceIsStableReturnsFalseWhenPriceChangesAreLessThan30DaysApart(){
        redPencilPromoPrice = new RedPencilPromoPrice(withUnstablePriceHistoryContainingDecreasingPrices());
        assertThat(redPencilPromoPrice.isStable()).isFalse();
    }

    @Test
    public void redPencilPromoPriceCanTellIfPreviousPromoHasExpired(){
        redPencilPromoPrice = new RedPencilPromoPrice(withOlderPromoThatHasExpired());
        assertThat(redPencilPromoPrice.hasPreviousExpiredRedPencilPromo()).isTrue();
    }

    @Test
    public void redPencilPromoPriceCanTellIfPreviousPromoWasEverInEffectWhenProvidedDataWithOUtPreviousPromo(){
        redPencilPromoPrice = new RedPencilPromoPrice(withOlderPromoThatHasNotExpired());
        assertThat(redPencilPromoPrice.hasPreviousActiveRedPencilPromo()).isTrue();
    }

    @Test
    public void getPromoPriceWillKeepPreexistingRedPencilPromoDurationWhileUsingNewerPriceWhenPromoIsInEffect(){
        redPencilPromoPrice = new RedPencilPromoPrice(withOlderPromoThatHasNotExpired());
        assertThat(redPencilPromoPrice.isPromoActive()).isTrue();
    }

    @Test
    public void getPromoPriceIsDeactivatedWhenPriceIncreases(){
        redPencilPromoPrice = new RedPencilPromoPrice(withUnStablePriceHistoryContainingIncrease());
        assertThat(redPencilPromoPrice.isPromoActive()).isFalse();
    }

    @Test
    public void getPromoPriceIsDeactivatedWithActivePromoBeforeIncrease(){
        redPencilPromoPrice = new RedPencilPromoPrice(withPriceHistoryContainingUnExpiredWithNewIncrease());
        assertThat(redPencilPromoPrice.isPromoActive()).isFalse();
    }

    @Test
    public void getPromoPriceIsDeactivatedWhenLoweredPricePercentageGreaterThan30PercentOfOriginalPrice(){
        redPencilPromoPrice = new RedPencilPromoPrice(withExistingPromoWithNewestPriceTooLow());
        assertThat(redPencilPromoPrice.isPromoActive()).isFalse();
    }

    private LinkedHashMap<Date, Float> withUnstablePriceHistoryContainingDecreasingPrices() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(25), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(13), 99.99f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 89.99f);

        return priceHistory;
    }

    private LinkedHashMap<Date, Float> withStablePriceHistoryAndNoDecrease() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(31), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(30), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(29), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 100.0f);

        return priceHistory;
    }

    private LinkedHashMap<Date, Float> withStablePriceHistoryFor30DaysAndDecrease() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(31), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 90.0f);

        return priceHistory;
    }

    private LinkedHashMap<Date, Float> withStablePriceHistoryAndDecreaseWith30DayGap() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(36), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(33), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(31), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 90.0f);

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

    private LinkedHashMap<Date, Float> withUnStablePriceHistoryContainingOutOfRangeDecrease() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(31), 100.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(30), 101.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(29), 89.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 1.0f);

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

    private LinkedHashMap<Date, Float> withPriceHistoryContainingUnExpiredWithNewIncrease() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(40), 99.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(10), 89.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 100.0f);

        return priceHistory;
    }

    private LinkedHashMap<Date, Float> withOlderPromoThatHasExpired() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(70), 99.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(39), 89.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 79.0f);

        return priceHistory;
    }

    private LinkedHashMap<Date, Float> withOlderPromoThatHasNotExpired() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(40), 99.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(10), 89.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 79.0f);

        return priceHistory;
    }


    private LinkedHashMap<Date, Float> withExistingPromoWithNewestPriceTooLow() {
        LinkedHashMap<Date, Float> priceHistory = new LinkedHashMap<Date, Float>();

        priceHistory.put(createNewDateBasedOnDaysAgo(40), 99.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(10), 89.0f);
        priceHistory.put(createNewDateBasedOnDaysAgo(1), 1.0f);

        return priceHistory;
    }

    private Date createNewDateBasedOnDaysAgo(int daysAgo){
        Calendar calendar = getInstance();
        calendar.add(DATE, -1*daysAgo);
        return calendar.getTime();
    }

}