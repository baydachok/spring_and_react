package mirea.pracs.productcrud.service;

import jakarta.transaction.Transactional;
import mirea.pracs.productcrud.converter.ProductTypeConverter;
import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.producttype.ProductTypeGetDto;
import mirea.pracs.productcrud.dto.producttype.ProductTypePatchDto;
import mirea.pracs.productcrud.dto.producttype.ProductTypePostDto;
import mirea.pracs.productcrud.dto.producttype.ProductTypeWrapperDto;
import mirea.pracs.productcrud.entity.ProductType;
import mirea.pracs.productcrud.exceptions.NotFoundException;
import mirea.pracs.productcrud.repository.ProductTypeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeService {

  private final ProductTypeRepository productTypeRepository;
  private final ProductTypeConverter productTypeConverter;

  public ProductTypeService(ProductTypeRepository productTypeRepository, ProductTypeConverter productTypeConverter) {
    this.productTypeRepository = productTypeRepository;
    this.productTypeConverter = productTypeConverter;
  }

  public ProductTypeWrapperDto getProductTypes() {
    var productTypes = productTypeRepository.findAll();
    return productTypeConverter.convertToProductTypeWrapperDto(productTypes);
  }

  public ProductTypeGetDto getProductType(Long productTypeId) {
    var productType = productTypeRepository.findById(productTypeId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Product type with id %d not found", productTypeId)
        ));
    return productTypeConverter.convertToProductTypeGetDto(productType);
  }

  public ProductType getProductTypeEntity(Long productTypeId) {
    return productTypeRepository.findById(productTypeId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Product type with id %d not found", productTypeId)
        ));
  }

  @PreAuthorize("{hasAuthority('MANAGER')}")
  @Transactional
  public PostResponse createProductType(ProductTypePostDto productTypePostDto) {
    var productType = productTypeConverter.convertToProductType(productTypePostDto);
    productTypeRepository.save(productType);
    return new PostResponse(productType.getProductTypeId());
  }

  @PreAuthorize("{hasAuthority('MANAGER')}")
  @Transactional
  public void updateProductType(Long productTypeId, ProductTypePatchDto productTypePatchDto) {
    var productTypeOld = productTypeRepository.findById(productTypeId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Product type with id %d not found", productTypeId)
        ));
    productTypeConverter.merge(productTypeOld, productTypePatchDto);
  }

  @PreAuthorize("{hasAuthority('MANAGER')}")
  @Transactional
  public void deleteProductType(Long productTypeId) {
    productTypeRepository.deleteById(productTypeId);
  }

}
