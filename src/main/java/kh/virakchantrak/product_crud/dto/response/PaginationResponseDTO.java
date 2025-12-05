package kh.virakchantrak.product_crud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponseDTO<T> {

    private List<T> data;
    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
    private boolean hasNext;
}

