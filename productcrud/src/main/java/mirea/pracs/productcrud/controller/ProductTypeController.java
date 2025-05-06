package mirea.pracs.productcrud.controller;

import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.producttype.ProductTypeGetDto;
import mirea.pracs.productcrud.dto.producttype.ProductTypePatchDto;
import mirea.pracs.productcrud.dto.producttype.ProductTypePostDto;
import mirea.pracs.productcrud.dto.producttype.ProductTypeWrapperDto;
import mirea.pracs.productcrud.service.ProductTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/product-types")
public class ProductTypeController {

  private final ProductTypeService productTypeService;

  public ProductTypeController(ProductTypeService productTypeService) {
    this.productTypeService = productTypeService;
  }

  @GetMapping
  public ResponseEntity<ProductTypeWrapperDto> getProductTypes() {
    var productTypesGetDto = productTypeService.getProductTypes();
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(productTypesGetDto);
  }

  @GetMapping("/{productTypeId}")
  public ResponseEntity<ProductTypeGetDto> getProductType(@PathVariable("productTypeId") Long productTypeId) {
    var productTypeGetDto = productTypeService.getProductType(productTypeId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(productTypeGetDto);
  }

  @PostMapping
  public ResponseEntity<PostResponse> postProductType(@RequestBody ProductTypePostDto productTypePostDto) {
    var postResponse = productTypeService.createProductType(productTypePostDto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(postResponse);
  }

  @PatchMapping("/{productTypeId}")
  public ResponseEntity<Void> patchProductType(
      @PathVariable("productTypeId") Long productTypeId,
      @RequestBody ProductTypePatchDto productTypePatchDto
  ) {
    productTypeService.updateProductType(productTypeId, productTypePatchDto);
    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }

  @DeleteMapping("/{productTypeId}")
  public ResponseEntity<Void> deleteProductType(@PathVariable("productTypeId") Long productTypeId) {
    productTypeService.deleteProductType(productTypeId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }

}
