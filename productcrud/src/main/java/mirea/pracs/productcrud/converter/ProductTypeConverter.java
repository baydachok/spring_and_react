package mirea.pracs.productcrud.converter;

import java.util.List;
import mirea.pracs.productcrud.dto.producttype.ProductTypeGetDto;
import mirea.pracs.productcrud.dto.producttype.ProductTypePatchDto;
import mirea.pracs.productcrud.dto.producttype.ProductTypePostDto;
import mirea.pracs.productcrud.dto.producttype.ProductTypeWrapperDto;
import mirea.pracs.productcrud.entity.ProductType;
import org.springframework.stereotype.Component;

@Component
public class ProductTypeConverter {

  public ProductTypeGetDto convertToProductTypeGetDto(ProductType productType) {
    return ProductTypeGetDto.builder()
        .productTypeId(productType.getProductTypeId())
        .name(productType.getName())
        .updatedTimestamp(productType.getUpdatedTimestamp())
        .build();
  }

  public ProductType convertToProductType(ProductTypePostDto productTypePostDto) {
    ProductType productType = new ProductType();
    productType.setName(productTypePostDto.getName());
    return productType;
  }

  public ProductTypeWrapperDto convertToProductTypeWrapperDto(List<ProductType> productTypes) {
    var productTypesGetDto = productTypes.stream()
        .map(this::convertToProductTypeGetDto)
        .toList();
    return new ProductTypeWrapperDto(productTypesGetDto);
  }

  public void merge(ProductType productTypeOld, ProductTypePatchDto productTypeNew) {
    if (productTypeNew.getName() != null) {
      productTypeOld.setName(productTypeNew.getName());
    }
  }
}
