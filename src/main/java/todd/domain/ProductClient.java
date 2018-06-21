package todd.domain;

public interface ProductClient {
    CatalogProduct getProductCatalog(Fulfillment fulfillment, RequestMetaData requestMetaData);
}
