package todd;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import todd.domain.*;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PopulateProductForRatePlanStepTest {

    @InjectMocks
    private PopulateProductForRatePlanStep populateProductForRatePlanStep;

    @Mock
    private ProductClient productClient;

    private FulfillmentContext fulfillmentContext;
    private Product expectedProduct;

    private Fulfillment fulfillmentInFulfullmentContext;
    private RequestMetaData requestMetaDataInFulfullmentContext;
    ProductRatePlan expectedRatePlanId;

    @Before
    public void setUp() {
        givenFulfillmentRequest();
        CatalogProduct catalogProduct = expectedCatalogProduct();

        when(productClient.getProductCatalog(fulfillmentInFulfullmentContext, requestMetaDataInFulfullmentContext)).thenReturn(catalogProduct);

        expectedProduct = catalogProduct.getProduct();
        expectedRatePlanId = catalogProduct.getProductRatePlanList().get(0);
    }

    @Test
    public void execute_setsTheProductInTheFulfillmentContext() throws Exception {
        assertNull(fulfillmentContext.getFulfillment().getFulfillmentProduct().getProduct());
        populateProductForRatePlanStep.execute(fulfillmentContext);
        assertSame(fulfillmentContext.getFulfillment().getFulfillmentProduct().getProduct(), expectedProduct);
    }

    @Test
    public void execute_setsTheProductRatePlanInTheFulfillmentContext() {
        assertNull(fulfillmentContext.getFulfillment().getFulfillmentProduct().getProductRatePlan());
        populateProductForRatePlanStep.execute(fulfillmentContext);
        assertSame(fulfillmentContext.getFulfillment().getFulfillmentProduct().getProductRatePlan(), expectedRatePlanId);
    }

//    @Test
//    public void executeReturnsTrue() throws Exception {
//        assertTrue(populateProductForRatePlanStep.execute(fulfillmentContext));
//    }

    private CatalogProduct expectedCatalogProduct() {
        ProductRatePlan ratePlanId = ProductRatePlan.builder().productRatePlanId("expectedRatePlanId").build();
        Product product = Product.builder().build();
        CatalogProduct catalogProduct = CatalogProduct.builder()
                .product(product)
                .productRatePlanList(Arrays.asList(ratePlanId))
                .build();

        return catalogProduct;
    }

    private void givenFulfillmentRequest() {
        fulfillmentInFulfullmentContext = Fulfillment.builder()
                .fulfillmentProduct(FulfillmentProduct
                        .builder()
                        .build())
                .build();

        requestMetaDataInFulfullmentContext = RequestMetaData.builder().build();

        fulfillmentContext = FulfillmentContext.builder()
                .fulfillment(fulfillmentInFulfullmentContext)
                .requestMetaData(requestMetaDataInFulfullmentContext)
                .build();
    }

}
