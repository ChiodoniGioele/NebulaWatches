package ch.nebulaWatches.nebulaWatchesAPI.watches.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Integer> {
    @Query("SELECT f FROM Family f WHERE f.brand.name = ?1")
    Page<Family> findByBrand(String brandName, Pageable pageable);

    @Query("SELECT f FROM Family f WHERE f.brand.name = ?1")
    List<Family> findByBrand(String brandName);

    List<Family> findAllByIdIn(List<Integer> familyIds);
}
