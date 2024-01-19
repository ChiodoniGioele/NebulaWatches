package ch.chiodoni.cal.api;

import ch.chiodoni.cal.model.Item;
import ch.chiodoni.cal.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class Category {
    public final static Logger LOGGER = LoggerFactory.getLogger(ch.chiodoni.cal.api.Category.class);

    @Autowired
    private ItemRepository repository;


    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @GetMapping("/categories")
    @ResponseBody
    public List<CategoryModel> categories() {
        LOGGER.info("categories called");
        return List.of(
                new CategoryModel("foo"),
                new CategoryModel("bar")
        );
    }

    @GetMapping("/test1")
    public Item test1() {
        Item i = new Item();
        i.setTo(LocalDateTime.now());
        i.setFrom(LocalDateTime.now().minus(Duration.ofHours(1)));
        i.setDescription("foo");
        i.setTitle("bar");
        repository.save(i);
        throw new RuntimeException();
    }

    @Transactional
    @GetMapping("/test2")
    public Item test2() {
        Item i = new Item();
        i.setTo(LocalDateTime.now());
        i.setFrom(LocalDateTime.now().minus(Duration.ofHours(1)));
        i.setDescription("foo");
        i.setTitle("bar");
        repository.save(i);
        throw new RuntimeException();
    }

    @GetMapping("/test3")
    public Item test3() {
        return transactionTemplate.execute(status -> {
            Item i = new Item();
            i.setTo(LocalDateTime.now());
            i.setFrom(LocalDateTime.now().minus(Duration.ofHours(1)));
            i.setDescription("foo");
            i.setTitle("bar");
            i = repository.save(i);
            throw new RuntimeException();

        });
    }

    @GetMapping("/test4")
    public Item test4() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        definition.setTimeout(3);
        TransactionStatus status = transactionManager.getTransaction(definition);

        Item i = new Item();
        i.setTo(LocalDateTime.now());
        i.setFrom(LocalDateTime.now().minus(Duration.ofHours(1)));
        i.setDescription("foo");
        i.setTitle("bar");
        i = repository.save(i);
//        transactionManager.rollback(status);
        transactionManager.commit(status);
        return i;
//        throw new RuntimeException();
    }

    private record CategoryModel(String name) {
    }
}
