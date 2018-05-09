package todd;

import com.ford.sumo.domain.CatalogProduct;
import com.ford.sumo.domain.v2.Fulfillment;
import com.ford.sumo.exception.aspect.SumoExceptionAnnotation;
import com.ford.sumo.infrastructure.SumoMessageContext;
import com.ford.sumo.log.Logged;
import com.ford.sumo.ordermanager.client.ProductClient;
import com.ford.sumo.ordermanager.domain.FulfillmentContext;
import com.ford.sumo.pipeline.Step;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Logged
@Slf4j
@AllArgsConstructor
public class PopulateProductForRatePlan implements Step {

    private final ProductClient productClient;

    @Override
    @SumoExceptionAnnotation
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