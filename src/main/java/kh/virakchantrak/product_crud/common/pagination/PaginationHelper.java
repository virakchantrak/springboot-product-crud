package kh.virakchantrak.product_crud.common.pagination;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@UtilityClass
public class PaginationHelper {

    public Pageable pageable(Map<String, String> params) {
        int limit = parseInt(params, PageUtil.PAGE_LIMIT, PageUtil.DEFAULT_PAGE_LIMIT);
        int page  = parseInt(params, PageUtil.PAGE_NUMBER, PageUtil.DEFAULT_PAGE_NUMBER);
        return PageUtil.getPageable(page, limit);
    }

    public <E, D> PaginationResponseDTO<D> toResponse(Page<E> page, Function<List<E>, List<D>> mapper) {
        return PaginationResponseDTO.<D>builder()
                .data(mapper.apply(page.getContent()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .hasNext(page.hasNext())
                .build();
    }

    private int parseInt(Map<String, String> params, String key, int defaultValue) {
        try {
            return params.containsKey(key) ? Integer.parseInt(params.get(key)) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
