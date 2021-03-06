package dev.fujioka.eltonleite.presentation.assembler;

import java.util.List;
import java.util.stream.Collectors;

import dev.fujioka.eltonleite.domain.model.product.Product;
import dev.fujioka.eltonleite.presentation.dto.product.ProductRequestTO;
import dev.fujioka.eltonleite.presentation.dto.product.ProductResponseTO;

public final class ProductAssembler {

    private ProductAssembler() {
    }

    public static Product from(ProductRequestTO requestTO) {
        return new Product(requestTO.getName(), requestTO.getDescription(), requestTO.getManufactureYear());
    }

    public static ProductResponseTO from(Product product) {
        return new ProductResponseTO(product.getId(), product.getName(), product.getDescription(),
                product.getManufactureYear());
    }

    public static List<ProductResponseTO> from(List<Product> products) {
        return products.stream().map(ProductAssembler::from).collect(Collectors.toList());
    }

}
