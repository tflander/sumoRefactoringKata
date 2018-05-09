package todd.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Fulfillment {
    private FulfillmentProduct fulfillmentProduct;
    private Vehicle vehicle;
    private User user;
}
