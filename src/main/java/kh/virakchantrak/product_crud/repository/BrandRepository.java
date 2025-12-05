package kh.virakchantrak.product_crud.repository;

import kh.virakchantrak.product_crud.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<BrandEntity, Long>, JpaSpecificationExecutor<BrandEntity> {

    Optional<BrandEntity> findByName(String name);
}
