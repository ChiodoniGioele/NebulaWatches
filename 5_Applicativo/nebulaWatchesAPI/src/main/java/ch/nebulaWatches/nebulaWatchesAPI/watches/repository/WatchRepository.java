package ch.nebulaWatches.nebulaWatchesAPI.watches.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.dto.WatchDTO;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface WatchRepository extends JpaRepository<Watch, String> {
    @Query("SELECT w FROM Watch w WHERE w.family.brand.name = ?1")
    List<Watch> findByBrand(String brandName);

    @Query("SELECT w FROM Watch w WHERE w.family.brand.name = ?1")
    Page<Watch> findByBrand(String brandName, Pageable pageable);

    @Query("SELECT w FROM Watch w WHERE w.family.id = ?1")
    List<Watch> findByFamily(int familyId);

    Optional<Watch> findByReference(String reference);
    @Query("SELECT w FROM Watch w WHERE w.family.id = ?1")
    Page<Watch> findByFamily(int familyId, Pageable pageable);

    @Query("SELECT w.reference, w.name, w.isLimitedTo FROM Watch w WHERE w.name LIKE CONCAT('%', :query, '%') OR " +
            "w.description LIKE CONCAT('%', :query, '%') OR " +
            "w.family.name LIKE CONCAT('%', :query, '%') OR " +
            "w.family.brand.name LIKE CONCAT('%', :query, '%')")
    Page<Watch> findBySearchQuery(String query, Pageable pageable);

    @Query("SELECT w.reference, w.name, w.isLimitedTo FROM Watch w " +
            "WHERE (:query IS NULL OR LOWER(w.name) LIKE %:query% OR LOWER(w.description) LIKE %:query%) " +
            "AND (:brands IS NULL OR w.family.brand.name IN :brands) " +

            "AND (:watchShapesNames IS NULL OR w.watchShape.name IN :watchShapesNames) " +
            "AND (:dialColorsNames IS NULL OR w.dialColor.name IN :dialColorsNames) " +
            "AND (:dialFinishesNames IS NULL OR w.dialFinish.name IN :dialFinishesNames) " +
            "AND (:watchIndexesNames IS NULL OR w.watchIndexes.name IN :watchIndexesNames) " +

            "AND (:families IS NULL OR w.family.id IN :families) " +
            "AND (:materialNames IS NULL OR EXISTS (SELECT 1 FROM WatchUsesMaterials wum " +
            "WHERE wum.watch.reference = w.reference AND wum.material.name IN :materialNames))")
    Page<Watch> findByCriteria(String query, List<String> brands, List<Long> families, List<String> materialNames,
                               List<String> watchShapesNames, List<String> watchIndexesNames, List<String> dialColorsNames,
                               List<String> dialFinishesNames, Pageable pageable);

    //Page<WatchDTO> findByFilters(String query, List<String> brands, List<String> families, List<String> materials, Pageable pageable);
}
