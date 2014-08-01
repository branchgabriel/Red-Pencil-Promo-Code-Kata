Red Pencil Promo Code Kata
========

TDD exercise using the following requirements:

* A red pencil promotion starts due to a price reduction.
* The price has to be reduced by at least 5% but at most be 30%
* The previous price had to be stable for at least 30 days.

* A red pencil promotion lasts 30 days as the maximum length.

* If the price is further reduced during the red pencil promotion the promotion will not be prolonged by that reduction.
* If the price is increased during the red pencil promotion the promotion will be ended immediately.
* If the price if reduced during the red pencil promotion so that the overall reduction is more than 30% with regard to the original price, the promotion is ended immediately.
* After a red pencil promotion is ended additional red pencil promotions may follow – as long as the start condition is valid: the price was stable for 30 days and these 30 days don’t intersect with a previous red pencil promotion.

Observation: these requirements do not involve retrieving the price for use and only describe activation and deactivation behavior. 
