package ch.nebulaWatches.nebulaWatchesAPI.watches.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Brand;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    List<Brand> findAllByNameIn(List<String> brands);

    @Query("SELECT b FROM Brand b WHERE (SELECT count(w.reference) FROM Watch w WHERE w.family.brand.name LIKE b.name) > 0")
    List<Brand> findAllNotWatchEmptyBrands();

    @Query("SELECT b FROM Brand b WHERE (SELECT count(w.reference) FROM Watch w WHERE w.family.brand.name LIKE b.name) > 0")
    Page<Brand> findAllNotWatchEmptyBrands(Pageable pageable);
}
