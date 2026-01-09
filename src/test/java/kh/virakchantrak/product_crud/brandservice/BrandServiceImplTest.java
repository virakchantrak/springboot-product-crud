package kh.virakchantrak.product_crud.brandservice;

import kh.virakchantrak.product_crud.dto.response.BrandResponseDTO;
import kh.virakchantrak.product_crud.entity.BrandEntity;
import kh.virakchantrak.product_crud.mapper.BrandMapper;
import kh.virakchantrak.product_crud.repository.BrandRepository;
import kh.virakchantrak.product_crud.service.impl.BrandServiceImpl;
import kh.virakchantrak.product_crud.spec.BrandSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BrandServiceImplTest {

    @InjectMocks
    private BrandServiceImpl brandService;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandMapper brandMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testGetBrands() {
//        // --- 1. Prepare test data ---
//        BrandEntity brand1 = new BrandEntity();
//        brand1.setId(1L);
//        brand1.setName("Samsung");
//
//        BrandEntity brand2 = new BrandEntity();
//        brand2.setId(2L);
//        brand2.setName("Apple");
//
//        List<BrandEntity> brands = List.of(brand1, brand2);
//        Page<BrandEntity> page = new PageImpl<>(brands);
//
//        Map<String, String> params = new HashMap<>();
//        params.put(PageUtil.PAGE_NUMBER, "1");
//        params.put(PageUtil.PAGE_LIMIT, "10");
//
//        // --- 2. Mock repository ---
//        when(brandRepository.findAll(any(BrandSpec.class), any(Pageable.class))).thenReturn(page);
//
//        // --- 3. Mock mapper ---
//        when(brandMapper.toResponse(brand1)).thenReturn(new BrandResponseDTO("1", "Samsung", "2025-12-03T00:00:00", "2025-12-03T00:00:00"));
//        when(brandMapper.toResponse(brand2)).thenReturn(new BrandResponseDTO("2", "Apple", "2025-12-03T00:00:00", "2025-12-03T00:00:00"));
//
//        when(brandMapper.toResponse(anyList())).thenCallRealMethod(); // uses stream mapper
//
//        // --- 4. Call service ---
//        PaginationResponseDTO<BrandResponseDTO> response = brandService.getBrands(params);
//
//        // --- 5. Verify results ---
//        assertNotNull(response);
//        assertEquals(2, response.getData().size());
//        assertEquals(1, response.getPage());
//        assertEquals(2, response.getSize());
//        assertFalse(response.isHasNext());
//
//        // --- 6. Verify repository called ---
//        verify(brandRepository, times(1)).findAll(any(BrandSpec.class), any(Pageable.class));
//    }
}
