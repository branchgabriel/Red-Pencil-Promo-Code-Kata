package com.branchgabriel;

import static com.branchgabriel.RedPencilPromoAssertions.assertThat;

import org.junit.*;

public class RedPencilPromoPriceTest{

    RedPencilPromoPrice redPencilPromoPrice;

    @Before
    public void setup(){
        redPencilPromoPrice = new RedPencilPromoPrice();
    }

    @Test
    public void testRedPencilPromoInitializesWithZeroBasedPrice(){
        assertThat(redPencilPromoPrice).hasPrice(0);
    }




}