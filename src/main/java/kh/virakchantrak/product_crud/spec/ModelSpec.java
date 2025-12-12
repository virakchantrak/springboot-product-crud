package kh.virakchantrak.product_crud.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kh.virakchantrak.product_crud.entity.ModelEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ModelSpec implements Specification<ModelEntity> {

    private final ModelFilter modelFilter;

    @Override
    public Predicate toPredicate(
            Root<ModelEntity> model,
            CriteriaQuery<?> query,
            CriteriaBuilder cb
    ) {

        List<Predicate> predicates = new ArrayList<>();

        // Filter by ID
        if (modelFilter.getId() != null) {
            predicates.add(cb.equal(model.get("id"), modelFilter.getId()));
        }

        // Filter by name (LIKE search)
        if (modelFilter.getName() != null && !modelFilter.getName().isBlank()) {
            predicates.add(
                    cb.like(
                            cb.lower(model.get("name")),
                            "%" + modelFilter.getName().toLowerCase() + "%"
                    )
            );
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
