package todd;

import lombok.AllArgsConstructor;
import todd.domain.*;

@AllArgsConstructor
public class PopulateProductForRatePlan implements Step {

    private final ProductClient productClient;

    @Override
    public boolean execute(SumoMessageContext sumoMessageContext) {
        FulfillmentContext fulfillmentContext = (FulfillmentContext) sumoMessageContext;

        this.enrichFulfilmentWithProduct(fulfillmentContext);

        return true;
    }

    /**
     * 1. Call the Offer Manager Product Service by ProductRatePlanId
     * 2. Update the fullfillment Object and set it to FulfillmentContext
     *
     * @param fulfillmentContext
     */
    private void enrichFulfilmentWithProduct(FulfillmentContext fulfillmentContext) {
        //String ratePlanId = fulfillmentContext.getFulfillment().getFulfillmentProduct().getProductRatePlan().getProductRatePlanId();
        Fulfillment fulfillment = fulfillmentContext.getFulfillment();

        CatalogProduct catalogProduct = productClient.getProductCatalog(fulfillment, fulfillmentContext.getRequestMetaData());

        fulfillmentContext.getFulfillment().getFulfillmentProduct().setProduct(
                catalogProduct.getProduct());
        fulfillmentContext.getFulfillment().getFulfillmentProduct().setProductRatePlan(
                catalogProduct.getProductRatePlanList().get(0));

        //throw new NonRetryableException("TEST","TEST_VALUE");

    }

}