package mirea.pracs.productcrud.converter;

import java.util.List;
import mirea.pracs.productcrud.dto.product.ProductGetDto;
import mirea.pracs.productcrud.dto.product.ProductPatchDto;
import mirea.pracs.productcrud.dto.product.ProductPostDto;
import mirea.pracs.productcrud.dto.product.ProductWrapperDto;
import mirea.pracs.productcrud.dto.producttype.ProductTypeGetDto;
import mirea.pracs.productcrud.entity.Product;
import mirea.pracs.productcrud.entity.ProductType;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

  public ProductGetDto convertToProductGetDto(Product product, ProductTypeGetDto productTypeGetDto) {
    return ProductGetDto.builder()
        .productTypeGetDto(productTypeGetDto)
        .productId(product.getProductId())
        .price(product.getPrice())
        .quantityInStock(product.getQuantityInStock())
        .updatedTimestamp(product.getUpdatedTimestamp())
        .description(product.getDescription())
        .imageSrc(product.getImageSrc())
        .name(product.getName())
        .build();
  }

  public Product convertToProduct(ProductPostDto productPostDto, ProductType productType, String imageSrc) {
    return new Product()
        .setProductType(productType)
        .setDescription(productPostDto.getDescription())
        .setImageSrc(imageSrc)
        .setQuantityInStock(productPostDto.getQuantityInStock())
        .setPrice(productPostDto.getPrice())
        .setName(productPostDto.getName());
  }

  public void merge(Product oldProduct, ProductPatchDto newProduct, String imageSrc) {
    if (newProduct.getDescription() != null) {
      oldProduct.setDescription(newProduct.getDescription());
    }
    if (newProduct.getName() != null) {
      oldProduct.setName(newProduct.getName());
    }
    if (newProduct.getPrice() != null) {
      oldProduct.setPrice(newProduct.getPrice());
    }
    if (newProduct.getImage() != null) {
      oldProduct.setImageSrc(imageSrc);
    }
    if (newProduct.getQuantityInStock() != null) {
      oldProduct.setQuantityInStock(newProduct.getQuantityInStock());
    }
  }

  public ProductWrapperDto convertToProductWrapperDto(List<ImmutablePair<Product, ProductTypeGetDto>> productsPairs) {
    var products = productsPairs.stream()
        .map(productPair -> convertToProductGetDto(
            productPair.getLeft(),
            productPair.getRight()
        ))
        .toList();
    return new ProductWrapperDto(products);
  }

}
