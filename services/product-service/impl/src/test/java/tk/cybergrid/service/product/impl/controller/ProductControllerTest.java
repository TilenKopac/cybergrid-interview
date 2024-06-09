package tk.cybergrid.service.product.impl.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import tk.cybergrid.service.product.impl.model.Product;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    ProductRepository productRepository;

    private ProductController productController;

    @Before
    public void setUp() {
        productController = new ProductController(productRepository);
    }

    @Test
    public void getProductById() {
        // arrange
        final UUID productId = UUID.randomUUID();

        final Product foundProduct = new Product(productId, null, null, null);
        final Optional<Product> foundProductOptional = Optional.of(foundProduct);
        doReturn(foundProductOptional).when(productRepository).findById(productId);

        // act
        final Product returnedProduct = productController.getProductById(productId);

        // assert
        Assert.assertSame(foundProduct, returnedProduct);
    }

    @Test
    public void getProductById_productNotFound() {
        // arrange
        final UUID productId = UUID.randomUUID();
        doReturn(Optional.empty()).when(productRepository).findById(productId);

        // act
        final ResponseStatusException exception = Assert.assertThrows(ResponseStatusException.class, () ->
                productController.getProductById(productId));

        // assert
        // method should return 404 NOT FOUND status
        Assert.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void updateProduct() {
        // arrange
        final UUID productId = UUID.randomUUID();

        final Product foundProduct = new Product(productId, null, null, null);
        final Optional<Product> foundProductOptional = Optional.of(foundProduct);
        doReturn(foundProductOptional).when(productRepository).findById(productId);

        final Product productUpdate = new Product(UUID.randomUUID(), null, null, null);
        doReturn(productUpdate).when(productRepository).save(productUpdate);

        // act
        final Product returnedProduct = productController.updateProduct(productId, productUpdate);

        // assert
        Assert.assertSame(productUpdate, returnedProduct);
        verify(productRepository, times(1)).save(productUpdate);
    }

    @Test
    public void updateProduct_productNotFound() {
        // arrange
        final UUID productId = UUID.randomUUID();
        doReturn(Optional.empty()).when(productRepository).findById(productId);

        // act
        final ResponseStatusException exception = Assert.assertThrows(ResponseStatusException.class, () ->
                productController.updateProduct(productId, new Product()));

        // assert
        // method should return 404 NOT FOUND status
        Assert.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}