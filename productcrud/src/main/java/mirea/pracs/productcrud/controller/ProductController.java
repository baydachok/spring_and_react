package mirea.pracs.productcrud.controller;

import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.product.ProductGetDto;
import mirea.pracs.productcrud.dto.product.ProductPatchDto;
import mirea.pracs.productcrud.dto.product.ProductPostDto;
import mirea.pracs.productcrud.dto.product.ProductWrapperDto;
import mirea.pracs.productcrud.service.ImageService;
import mirea.pracs.productcrud.service.ProductService;
import mirea.pracs.productcrud.service.ProductTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;
  private final ProductTypeService productTypeService;
  private final ImageService imageService;

  public ProductController(ProductService productService, ProductTypeService productTypeService, ImageService imageService) {
    this.productService = productService;
    this.productTypeService = productTypeService;
    this.imageService = imageService;
  }

  @GetMapping
  public ResponseEntity<ProductWrapperDto> getProducts(
      @RequestParam(name = "search", required = false) String search
  ) {
    var productWrapperDto = search != null ?
        productService.getProducts(search) :
        productService.getProducts();
    return ResponseEntity.ok(productWrapperDto);
  }

  @PostMapping(consumes = {"multipart/form-data"})
  public ResponseEntity<PostResponse> postProduct(@ModelAttribute ProductPostDto productPostDto) {
    var productType = productTypeService.getProductTypeEntity(productPostDto.getProductTypeId());
    var postResponse = productService.createProduct(productPostDto, productType);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(postResponse);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductGetDto> getProduct(@PathVariable Long productId) {
    var productGetDto = productService.getProduct(productId);
    return ResponseEntity.ok(productGetDto);
  }

  @PatchMapping("/{productId}")
  public ResponseEntity<Void> patchProduct(
      @PathVariable Long productId,
      @RequestBody ProductPatchDto productPatchDto) {
    productService.updateProduct(productId, productPatchDto);
    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
    var imageSrc = productService.deleteProduct(productId);
    imageService.deleteImage(imageSrc);
    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }

}
