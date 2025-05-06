package mirea.pracs.productcrud.service;

import mirea.pracs.productcrud.converter.ProductConverter;
import mirea.pracs.productcrud.converter.ProductTypeConverter;
import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.product.ProductGetDto;
import mirea.pracs.productcrud.dto.product.ProductPatchDto;
import mirea.pracs.productcrud.dto.product.ProductPostDto;
import mirea.pracs.productcrud.dto.product.ProductWrapperDto;
import mirea.pracs.productcrud.entity.Product;
import mirea.pracs.productcrud.entity.ProductType;
import mirea.pracs.productcrud.exceptions.NotFoundException;
import mirea.pracs.productcrud.repository.ProductRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductConverter productConverter;
  private final ProductTypeConverter productTypeConverter;
  private final ImageService imageService;

  public ProductService(
      ProductRepository productRepository, ProductConverter productConverter, ProductTypeConverter productTypeConverter,
      ImageService imageService
  ) {
    this.productRepository = productRepository;
    this.productConverter = productConverter;
    this.productTypeConverter = productTypeConverter;
    this.imageService = imageService;
  }

  public ProductWrapperDto getProducts() {
    var productPairs = productRepository.findAll()
        .stream()
        .map(product -> new ImmutablePair<>(
            product,
            productTypeConverter.convertToProductTypeGetDto(product.getProductType())
        ))
        .toList();
    return productConverter.convertToProductWrapperDto(productPairs);
  }

  public ProductGetDto getProduct(Long productId) {
    var product = productRepository
        .findById(productId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Product with id %d not found", productId)
        ));
    return productConverter.convertToProductGetDto(
        product,
        productTypeConverter.convertToProductTypeGetDto(product.getProductType())
    );
  }

  public Product getProductEntity(Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Product with id %d not found", productId)
        ));
  }

  @PreAuthorize("{hasAuthority('MANAGER')}")
  @Transactional
  public PostResponse createProduct(ProductPostDto productPostDto, ProductType productType) {
    var imageSrc = imageService.saveImage(productPostDto.getImage());
    var product = productConverter.convertToProduct(productPostDto, productType, imageSrc);
    product.setImageSrc(imageSrc);
    productRepository.save(product);
    return new PostResponse(product.getProductId());
  }

  @PreAuthorize("{hasAuthority('MANAGER')}")
  @Transactional
  public void updateProduct(Long productId, ProductPatchDto productPatchDto) {
    Product oldProduct = productRepository
        .findById(productId)
        .orElseThrow(RuntimeException::new);
    productConverter.merge(oldProduct, productPatchDto, null);
  }

  @PreAuthorize("{hasAuthority('MANAGER')}")
  @Transactional
  public String deleteProduct(Long productId) {
    var product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Product with id %d not found", productId)
        ));
    productRepository.deleteById(productId);
    return product.getImageSrc();
  }

  public ProductWrapperDto getProducts(String search) {
    var productPairs = productRepository.findByNameContaining(search)
        .stream()
        .map(product -> new ImmutablePair<>(
            product,
            productTypeConverter.convertToProductTypeGetDto(product.getProductType())
        ))
        .toList();
    return productConverter.convertToProductWrapperDto(productPairs);
  }

}
