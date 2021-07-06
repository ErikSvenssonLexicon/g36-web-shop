package se.lexicon.g36webshop.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g36webshop.data.ProductCategoryDAO;
import se.lexicon.g36webshop.exception.AppResourceNotFoundException;
import se.lexicon.g36webshop.model.dto.product_category.ProductCategoryDTO;
import se.lexicon.g36webshop.model.entity.ProductCategory;
import se.lexicon.g36webshop.service.interfaces.ProductCategoryService;
import se.lexicon.g36webshop.service.interfaces.conversion.ProductCategoryFactory;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryDAO productCategoryDAO;
    private final ProductCategoryFactory productCategoryFactory;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductCategory create(ProductCategoryDTO productCategoryDTO) {
        ProductCategory newProductCategory = productCategoryFactory.createFromDTO(productCategoryDTO);
        return productCategoryDAO.save(newProductCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductCategory findById(String id) {
        return productCategoryDAO.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find ProductCategory with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ProductCategory> findAll() {
        return productCategoryDAO.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductCategory update(String id, ProductCategoryDTO productCategoryDTO) {
        if(productCategoryDTO == null) throw new IllegalArgumentException("Update aborted ProductCategoryDTO was null");
        if(productCategoryDTO.getCategoryId() != null && !productCategoryDTO.getCategoryId().equals(id)){
            throw new IllegalArgumentException("Update aborted, cause: productCategoryDTO.categoryId did not match id");
        }

        ProductCategory productCategory = findById(id);
        if(productCategoryDTO.getValue() != null){
            productCategory.setValue(productCategoryDTO.getValue().trim());
        }

        productCategory = productCategoryDAO.save(productCategory);

        return productCategory;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(String id) {
        ProductCategory productCategory = findById(id);
        productCategory.setProductsWithCategory(null);
        productCategoryDAO.delete(productCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ProductCategory> findByFirstLetter(String firstLetter, Pageable pageable) {
        return productCategoryDAO.findByValueStartingWith(firstLetter, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ProductCategory> findByValueContains(String value) {
        return productCategoryDAO.findByValue(value);
    }
}
