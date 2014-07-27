package com.branchgabriel;

import junit.framework.TestCase;
import static com.branchgabriel.RedPencilPromoAssertions.*;
import org.junit.Test;


public class RedPencilPromoPriceTest extends TestCase {

    public void redPencilPromoInitializesWithZeroBasedPrice(){
        RedPencilPromoPrice redPencilPromoPrice = new RedPencilPromoPrice();
        assertThat(redPencilPromoPrice).hasPrice(0.0f);
    }

}