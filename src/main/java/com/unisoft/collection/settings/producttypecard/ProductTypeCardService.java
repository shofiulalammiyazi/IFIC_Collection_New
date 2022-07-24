package com.unisoft.collection.settings.producttypecard;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.productGroup.ProductGroupEntity;
import com.unisoft.collection.settings.productGroup.ProductGroupRepository;
import com.unisoft.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTypeCardService {

    private final ProductTypeCardRepository repository;
    private final AuditTrailService auditTrailService;

    @Autowired
    private ProductGroupRepository productGroupRepository;

    public List<ProductTypeCardEntity> getTypeList() {
      return repository.findAll();
    }


    public List<ProductGroupEntity> getPrductGroupCardList() {
        return productGroupRepository.findByCardAccount("Card");
    }
    public ProductTypeCardEntity getById(Long Id) {
        return repository.findById(Id).orElse(null);
    }
 public List<ProductTypeCardEntity> getByName(String name) {
        return repository.findByName(name);
    }

    public String save(ProductTypeCardEntity productType) {
        boolean isNewEntity = false;
        ProductTypeCardEntity previousEntity = new ProductTypeCardEntity();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (productType.getId() == null) {
            productType.setCreatedBy(user.getUsername());
            productType.setCreatedDate(new Date());
            isNewEntity = true;
        } else {
            ProductTypeCardEntity oldEntity = repository.getOne(productType.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);

            productType.setModifiedBy(user.getUsername());
            productType.setModifiedDate(new Date());
        }
        repository.save(productType);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Product Type", productType);
        else
            auditTrailService.saveUpdatedData("Product Type", previousEntity, productType);
        return "1";
    }

    public List<ProductTypeCardEntity> getAllActive() {
        return repository.findByEnabled(true);
    }

    public List<ProductTypeCardEntity> getByProductGroup(String productGroupCardAccount) {
        return repository.findByProductGroupEntityCardAccount(productGroupCardAccount);
    }
}
