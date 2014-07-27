package com.branchgabriel;

import org.assertj.core.api.Assertions;

public class RedPencilPromoAssertions extends Assertions {
    public static RedPencilPromoPriceAssert assertThat(RedPencilPromoPrice actual){
        return new RedPencilPromoPriceAssert(actual);
    }
}
