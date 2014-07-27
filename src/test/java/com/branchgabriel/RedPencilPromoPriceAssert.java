package com.branchgabriel;

import org.assertj.core.api.AbstractAssert;

public class RedPencilPromoPriceAssert extends AbstractAssert<RedPencilPromoPriceAssert, RedPencilPromoPrice> {

    public RedPencilPromoPriceAssert(RedPencilPromoPrice actual){
        super(actual, RedPencilPromoPriceAssert.class);
    }

    public static RedPencilPromoPriceAssert assertThat(RedPencilPromoPrice actual) {
        return new RedPencilPromoPriceAssert(actual);
    }

    public RedPencilPromoPriceAssert hasPrice(float price){
        if(actual.getPrice() != price){
            failWithMessage("Expected promo price to be <%s> but was <%s>", price, actual.getPrice() );
        }
        return this;
    }

}
