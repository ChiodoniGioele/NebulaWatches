package ch.chiodoni.cal.repository;

import ch.chiodoni.cal.model.Item;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin(origins = "*")
@Tag(name = "Items")
public interface ItemRepository extends PagingAndSortingRepository<Item, Long>, CrudRepository<Item, Long> {
    List<Item> findByTitle(@Param("title") String title);

}
