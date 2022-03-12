package ru.gb.springbootdemoapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.gb.springbootdemoapp.converter.ProductMapper;
import ru.gb.springbootdemoapp.service.ProductService;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;


class ProductControllerTest {

    private ProductController productController;
    private final Model model = new Model() {
        @Override
        public Model addAttribute(String attributeName, Object attributeValue) {
            return null;
        }

        @Override
        public Model addAttribute(Object attributeValue) {
            return null;
        }

        @Override
        public Model addAllAttributes(Collection<?> attributeValues) {
            return null;
        }

        @Override
        public Model addAllAttributes(Map<String, ?> attributes) {
            return null;
        }

        @Override
        public Model mergeAttributes(Map<String, ?> attributes) {
            return null;
        }

        @Override
        public boolean containsAttribute(String attributeName) {
            return false;
        }

        @Override
        public Object getAttribute(String attributeName) {
            return null;
        }

        @Override
        public Map<String, Object> asMap() {
            return null;
        }
    };

    @BeforeEach
    void setUp() {
        ProductService productService = mock(ProductService.class);
        ProductMapper productMapper = mock(ProductMapper.class);
        productController = new ProductController(productService, productMapper);
    }

    @Test
    void checkTemplateProductList() {
        String templateProductList = productController.getAllProducts(model);
        assertEquals("product_list", templateProductList);
    }

    @Test
    void checkTemplateProductInfo() {
        String templateProductInfo = productController.getProductInfo(1L, model);
        assertEquals("product_info", templateProductInfo);
    }
}