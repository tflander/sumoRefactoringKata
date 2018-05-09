package todd.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FulfillmentProduct {
    Product product;
    ProductRatePlan productRatePlan;
}
