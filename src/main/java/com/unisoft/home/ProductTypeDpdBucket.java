package com.unisoft.home;

import lombok.Data;

import java.util.Objects;

@Data
public class ProductTypeDpdBucket {
    public String productGroup;
    public String dpdBucket;

    public ProductTypeDpdBucket() {
    }

    public ProductTypeDpdBucket(String productGroup, String dpdBucket) {
        this.productGroup = productGroup;
        this.dpdBucket = dpdBucket;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getDpdBucket() {
        return dpdBucket;
    }

    public void setDpdBucket(String dpdBucket) {
        this.dpdBucket = dpdBucket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductTypeDpdBucket)) return false;
        ProductTypeDpdBucket that = (ProductTypeDpdBucket) o;
        return Objects.equals(productGroup, that.productGroup) &&
                Objects.equals(dpdBucket, that.dpdBucket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productGroup, dpdBucket);
    }
}
