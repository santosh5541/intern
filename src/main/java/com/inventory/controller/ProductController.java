    package com.inventory.controller;

    import com.inventory.dto.ProductDTO;
    import com.inventory.exception.ResourceNotFoundException;
    import com.inventory.model.Product;
    import com.inventory.service.ProductService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/products")
    public class ProductController {

        @Autowired
        private ProductService productService;

        @PostMapping("/create/{supplierId}")
        public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO, @PathVariable("supplierId") int supplierId) {
            ProductDTO createdProductDTO = productService.createProduct(productDTO, supplierId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDTO);
        }

        @GetMapping
        public ResponseEntity<List<ProductDTO>> viewAll() {
            List<ProductDTO> productDTOs = productService.viewAll();
            return ResponseEntity.ok(productDTOs);
        }

        @GetMapping("{id}")
        public ResponseEntity<ProductDTO> viewProductById(@PathVariable("id") int id) {
            try {
                Optional<ProductDTO> productDTOById = productService.viewProductById(id);
                return ResponseEntity.ok(productDTOById.orElseThrow(() -> new ResourceNotFoundException("Product not found")));
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("{id}")
        public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
            try {
                productService.deleteProductById(id);
                return ResponseEntity.ok("Deleted successfully");
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @PutMapping("{id}")
        public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable("id") int id) {
            try {
                ProductDTO updatedProductDTO = productService.updateProduct(id, productDTO);
                return ResponseEntity.ok(updatedProductDTO);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @GetMapping("supplier/{supplierId}")
        public ResponseEntity<List<ProductDTO>> getProductBySupplier(@PathVariable("supplierId") int supplierId) {
            List<ProductDTO> productBySupplier = productService.findProductBySupplier(supplierId);
            return new ResponseEntity<List<ProductDTO>>(productBySupplier, HttpStatus.OK);
        }

    }