package todd;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import todd.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PopulateProductForRatePlanTest {

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private PopulateProductForRatePlan classUnderTest;

    private FulfillmentContext fulfillmentContext;

    @Before
    public void setUp() {

        Fulfillment fulfillment = Fulfillment.builder()
                .fulfillmentProduct(FulfillmentProduct
                        .builder()
                        .product(Product.builder().build())
                        .productRatePlan(ProductRatePlan.builder().productRatePlanId("ratePlanID").build())
                        .build())
                .vehicle(Vehicle.builder()
                        .vin(VehicleIdentificationNumber.builder().value("vin").build())
                        .build())
                .user(User.builder().userGuid(UserGuid.builder()
                        .value("guid")
                        .build())
                        .accountKey("accountKey")
                        .customerType("F")
                        .build())
                .build();

        RequestMetaData requestMetaData = RequestMetaData.builder()
                .traceId("traceID")
                .build();

        fulfillmentContext = FulfillmentContext.builder()
                .fulfillment(fulfillment)
                .requestMetaData(requestMetaData)
                .build();

        List<ProductRatePlan> productRatePlans = new ArrayList<>();

        productRatePlans.add(ProductRatePlan.builder().productRatePlanId("populatedRatePlanID").build());

        CatalogProduct catalogProduct = CatalogProduct.builder()
                .product(Product.builder().sku("sku").build())
                .productRatePlanList(productRatePlans)
                .build();


        when(productClient.getProductCatalog(fulfillment, requestMetaData)).thenReturn(catalogProduct);
    }


    @Test
    public void test_execute() {

        ArgumentCaptor<Fulfillment> fulfillmentCaptor = ArgumentCaptor.forClass(Fulfillment.class);
        ArgumentCaptor<RequestMetaData> requestMetaDataCaptor = ArgumentCaptor.forClass(RequestMetaData.class);

        assertTrue(classUnderTest.execute(fulfillmentContext));
        verify(productClient, times(1)).getProductCatalog(fulfillmentCaptor.capture(), requestMetaDataCaptor.capture());

        assertEquals(fulfillmentContext.getFulfillment(), fulfillmentCaptor.getValue());
        assertEquals("traceID", requestMetaDataCaptor.getValue().getTraceId());
        assertEquals("populatedRatePlanID", fulfillmentContext.getFulfillment().getFulfillmentProduct()
                .getProductRatePlan().getProductRatePlanId());
        assertEquals("sku", fulfillmentContext.getFulfillment().getFulfillmentProduct().getProduct().getSku());

    }
}
