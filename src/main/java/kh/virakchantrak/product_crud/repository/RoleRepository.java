package kh.virakchantrak.product_crud.repository;

import kh.virakchantrak.product_crud.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findByNameIn(Set<String> names);
}
