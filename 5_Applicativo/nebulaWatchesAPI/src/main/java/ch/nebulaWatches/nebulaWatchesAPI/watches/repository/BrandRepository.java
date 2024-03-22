package ch.nebulaWatches.nebulaWatchesAPI.watches.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Brand;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    List<Brand> findAllByNameIn(List<String> brands);
}
