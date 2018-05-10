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
public class PopulateProductForRatePlanTest {

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private PopulateProductForRatePlan populateProductForRatePlan;

    private FulfillmentContext fulfillmentContext;
    private Product productFromProductClient;

    private Fulfillment fulfillmentInFulfullmentContext;
    private RequestMetaData requestMetaDataInFulfullmentContext;
    ProductRatePlan populatedRatePlanIDFromProductClient = ProductRatePlan.builder().productRatePlanId("populatedRatePlanIDFromProductClient").build();

    @Before
    public void setUp() {
        givenFulfillmentRequest();
        expectProductFromProductClient();
    }

    @Test
    public void executeReturnsTrue() throws Exception {
        assertTrue(populateProductForRatePlan.execute(fulfillmentContext));
    }

    @Test
    public void executeCallsTheProductClient() throws Exception {
        populateProductForRatePlan.execute(fulfillmentContext);
        verify(productClient).getProductCatalog(fulfillmentInFulfullmentContext, requestMetaDataInFulfullmentContext);
    }

    @Test
    public void executeSetsTheProduct() throws Exception {
        assertNull(productFromFulfillmentContext());
        populateProductForRatePlan.execute(fulfillmentContext);
        assertSame(productFromFulfillmentContext(), productFromProductClient);
    }

    @Test
    public void executeSetsTheProductRatePlan() {
        assertNull(getProductRatePlanFromContext());
        populateProductForRatePlan.execute(fulfillmentContext);
        assertSame(getProductRatePlanFromContext(), populatedRatePlanIDFromProductClient);
    }


    private void expectProductFromProductClient() {
        productFromProductClient = Product.builder().build();
        CatalogProduct catalogProduct = CatalogProduct.builder()
                .product(productFromProductClient)
                .productRatePlanList(Arrays.asList(populatedRatePlanIDFromProductClient))
                .build();


        when(productClient.getProductCatalog(fulfillmentInFulfullmentContext, requestMetaDataInFulfullmentContext)).thenReturn(catalogProduct);
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

    private ProductRatePlan getProductRatePlanFromContext() {
        return fulfillmentContext.getFulfillment().getFulfillmentProduct().getProductRatePlan();
    }

    private Product productFromFulfillmentContext() {
        return fulfillmentContext.getFulfillment().getFulfillmentProduct().getProduct();
    }

}
