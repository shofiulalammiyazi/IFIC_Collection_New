package com.unisoft.collection.settings.productType;

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
public class ProductTypeService {

    private final ProductTypeRepository repository;
    private final AuditTrailService auditTrailService;

    @Autowired
    private ProductGroupRepository productGroupRepository;

    public List<ProductTypeEntity> getTypeList() {
       return repository.findAll();
    }
    public List<ProductGroupEntity> getPrductGroupLoanList() {
       return productGroupRepository.findByCardAccount("Loan");
    }

    public ProductTypeEntity getById(Long Id) {
        return repository.findById(Id).orElse(null);
    }

    public String save(ProductTypeEntity productType) {
        boolean isNewEntity = false;
        ProductTypeEntity previousEntity = new ProductTypeEntity();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (productType.getId() == null) {
            productType.setCreatedBy(user.getUsername());
            productType.setCreatedDate(new Date());
            isNewEntity = true;
        } else {
            ProductTypeEntity oldEntity = repository.getOne(productType.getId());
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

    public List<ProductTypeEntity> getAllActive() {
        return repository.findByEnabled(true);
    }

    public List<ProductTypeEntity> getByProductGroup(String productGroupCardAccount) {
        return repository.findByProductGroupEntityCardAccount(productGroupCardAccount);
    }
}
