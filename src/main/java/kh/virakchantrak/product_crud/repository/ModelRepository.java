package kh.virakchantrak.product_crud.repository;

import kh.virakchantrak.product_crud.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
    Optional<ModelEntity> findByNameAndBrandId(String name, Long brandId);

}
