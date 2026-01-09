package kh.virakchantrak.product_crud.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kh.virakchantrak.product_crud.entity.BrandEntity;

import java.util.List;

public class BrandSpec extends BaseSpec<BrandEntity, BrandFilter> {

    public BrandSpec(BrandFilter filter) {
        super(filter);
    }

    @Override
    protected void addPredicates(
            List<Predicate> predicates,
            Root<BrandEntity> root,
            CriteriaQuery<?> query,
            CriteriaBuilder cb
    ) {
        eq(predicates, cb, root.get("id"), filter.getId());
        likeIgnoreCase(predicates, cb, root.get("name"), filter.getName());
    }
}
