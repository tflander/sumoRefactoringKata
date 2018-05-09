package todd.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FulfillmentContext implements SumoMessageContext {
    private Fulfillment fulfillment;
    private RequestMetaData requestMetaData;

}
