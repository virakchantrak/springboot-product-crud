package kh.virakchantrak.product_crud.common.pagination;

import java.util.Map;

@FunctionalInterface
public interface FilterParser<F> {
    F from(Map<String, String> params);
}
