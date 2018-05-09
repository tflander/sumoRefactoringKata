package todd.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CatalogProduct {
    private Product product;
    private List<ProductRatePlan> productRatePlanList;
}
