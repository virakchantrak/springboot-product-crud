package kh.virakchantrak.product_crud.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSpec<E, F> implements Specification<E> {

    protected final F filter;

    protected BaseSpec(F filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        addPredicates(predicates, root, query, cb);
        return cb.and(predicates.toArray(Predicate[]::new));
    }

    protected abstract void addPredicates(
            List<Predicate> predicates,
            Root<E> root,
            CriteriaQuery<?> query,
            CriteriaBuilder cb
    );

    // ---------- helper methods ----------
    protected void eq(List<Predicate> predicates, CriteriaBuilder cb, Path<?> path, Object value) {
        if (value != null) predicates.add(cb.equal(path, value));
    }

    protected void likeIgnoreCase(List<Predicate> predicates, CriteriaBuilder cb, Path<String> path, String value) {
        if (value != null && !value.isBlank()) {
            predicates.add(cb.like(cb.lower(path), "%" + value.toLowerCase() + "%"));
        }
    }
}
