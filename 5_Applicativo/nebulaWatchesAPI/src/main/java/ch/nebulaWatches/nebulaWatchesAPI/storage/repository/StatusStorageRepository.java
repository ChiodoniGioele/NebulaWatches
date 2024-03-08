package ch.nebulaWatches.nebulaWatchesAPI.storage.repository;

import ch.nebulaWatches.nebulaWatchesAPI.storage.model.StatusStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusStorageRepository extends JpaRepository<StatusStorage, String> {
    StatusStorage findByName(String name);
}
