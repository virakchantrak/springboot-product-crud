package kh.virakchantrak.product_crud.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kh.virakchantrak.product_crud.entity.BrandEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BrandSpec implements Specification<BrandEntity> {

    private final BrandFilter brandFilter;

    @Override
    public Predicate toPredicate(
            Root<BrandEntity> brand,
            CriteriaQuery<?> query,
            CriteriaBuilder cb
    ) {

        List<Predicate> predicates = new ArrayList<>();

        // Filter by ID
        if (brandFilter.getId() != null) {
            predicates.add(cb.equal(brand.get("id"), brandFilter.getId()));
        }

        // Filter by name (LIKE search)
        if (brandFilter.getName() != null && !brandFilter.getName().isBlank()) {
            predicates.add(
                    cb.like(
                            cb.lower(brand.get("name")),
                            "%" + brandFilter.getName().toLowerCase() + "%"
                    )
            );
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
