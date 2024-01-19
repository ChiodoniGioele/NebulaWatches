package ch.chiodoni.cal.service;

import ch.chiodoni.cal.repository.ItemRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ItemCounter {

    public final static Logger LOGGER = LoggerFactory.getLogger(ItemCounter.class);

    private final ItemRepository repository;
    private final ObservationRegistry observationRegistry;

    public ItemCounter(ItemRepository repository, ObservationRegistry observationRegistry) {
        this.repository = repository;
        this.observationRegistry = observationRegistry;
    }

    @Scheduled(fixedDelayString = "${cal.service.counter.fixedDelay.in.milliseconds}")
    void count() {
        Observation.createNotStarted("itemCounter", observationRegistry)
                .observe(() -> {
                    LOGGER.info("Number of items: {}", repository.count());
                });
    }
}
