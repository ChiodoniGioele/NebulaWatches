package ch.nebulaWatches.nebulaWatchesAPI.watches.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WatchRepository extends JpaRepository<Watch, String> {
    @Query("SELECT w FROM Watch w WHERE w.family.brand.name = ?1")
    List<Watch> findByBrand(String brandName);

    @Query("SELECT w FROM Watch w WHERE w.family.id = ?1")
    List<Watch> findByFamily(int familyId);

    @Query("SELECT w FROM Watch w WHERE w.family.id = ?1")
    Page<Watch> findByFamily(int familyId, Pageable pageable);

    @Query("SELECT w FROM Watch w WHERE w.name LIKE CONCAT('%', :query, '%') OR " +
            "w.description LIKE CONCAT('%', :query, '%') OR " +
            "w.family.name LIKE CONCAT('%', :query, '%') OR " +
            "w.family.brand.name LIKE CONCAT('%', :query, '%')")
    Page<Watch> findBySearchQuery(String query, Pageable pageable);
}
