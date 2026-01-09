package kh.virakchantrak.product_crud.utils;

import kh.virakchantrak.product_crud.common.pagination.FilterParser;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BrandFilterParser implements FilterParser<BrandFilter> {

    @Override
    public BrandFilter from(Map<String, String> params) {
        BrandFilter f = new BrandFilter();

        String name = params.get("name");
        if (name != null && !name.isBlank()) f.setName(name);

        String id = params.get("id");
        if (id != null && !id.isBlank()) try {
            f.setId(Long.parseLong(id));
        } catch (NumberFormatException ignored) {
        }

        return f;
    }
}
