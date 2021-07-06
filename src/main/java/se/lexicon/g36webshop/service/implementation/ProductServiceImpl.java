package se.lexicon.g36webshop.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g36webshop.data.ProductDAO;
import se.lexicon.g36webshop.exception.AppResourceNotFoundException;
import se.lexicon.g36webshop.model.dto.ProductDTO;
import se.lexicon.g36webshop.model.entity.Product;
import se.lexicon.g36webshop.service.interfaces.ProductFactory;
import se.lexicon.g36webshop.service.interfaces.ProductService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final ProductFactory productFactory;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Product create(ProductDTO productDTO) {
        Product newProduct = productFactory.createFromDTO(productDTO);
        return productDAO.save(newProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(String id) {
        return productDAO.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException("Product with id " + id + " was not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Product update(String id, ProductDTO productDTO) {
        if(productDTO == null) throw new IllegalArgumentException("Update aborted, cause: productDTO was null");

        if(productDTO.getProductId() != null && !productDTO.getProductId().equals(id)){
            throw new IllegalArgumentException("Update aborted, cause: productDTO.productId did not match id");
        }

        //1. Find the original
        Product original = findById(id);

        //2. Update the original where needed
        if(productDTO.getProductName() != null){
            original.setProductName(productDTO.getProductName().trim());
        }

        if(productDTO.getProductPrice() != null){
            original.setProductPrice(productDTO.getProductPrice());
        }

        if(productDTO.getDescription() != null){
            original.setDescription(productDTO.getDescription().trim());
        }

        //3. Save the original
        original = productDAO.save(original);

        //4. Return the original
        return original;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(String id) {
        productDAO.deleteById(id);
    }
}