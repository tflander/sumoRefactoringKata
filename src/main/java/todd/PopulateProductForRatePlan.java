package todd;

import lombok.AllArgsConstructor;
import todd.domain.*;

@AllArgsConstructor
public class PopulateProductForRatePlan implements Step /* implements Step */ {

    private final ProductClient productClient;

    @Override
    public boolean execute(SumoMessageContext sumoMessageContext) {
        this.enrichFulfilmentWithProduct((FulfillmentContext) sumoMessageContext);
        return true;
    }

    /**
     * TODO: 1. Call the Offer Manager Product Service by ProductRatePlanId
     * 2. Update the fullfillment Object and set it to FulfillmentContext
     *
     * @param fulfillmentContext
     */
    private void enrichFulfilmentWithProduct(FulfillmentContext fulfillmentContext) {
        //String ratePlanId = fulfillmentContext.getFulfillment().getFulfillmentProduct().getProductRatePlan().getProductRatePlanId();
        CatalogProduct catalogProduct = lookupCatalogProduct(fulfillmentContext);
        FulfillmentProduct fulfillmentProduct = fulfillmentContext.getFulfillment().getFulfillmentProduct();
        updateProductAndRatePlanFromCatalogProduct(fulfillmentProduct, catalogProduct);
    }

    private void updateProductAndRatePlanFromCatalogProduct(FulfillmentProduct fulfillmentProduct, CatalogProduct catalogProduct) {
        fulfillmentProduct.setProduct(catalogProduct.getProduct());
        fulfillmentProduct.setProductRatePlan(catalogProduct.getProductRatePlanList().get(0));
    }

    private CatalogProduct lookupCatalogProduct(FulfillmentContext fulfillmentContext) {
        Fulfillment fulfillment = fulfillmentContext.getFulfillment();
        return productClient.getProductCatalog(fulfillment, fulfillmentContext.getRequestMetaData());
    }

}