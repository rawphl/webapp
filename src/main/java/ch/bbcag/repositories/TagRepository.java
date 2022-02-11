package ch.bbcag.repositories;

import ch.bbcag.models.Tag;
import ch.bbcag.models.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends CrudRepository<Tag, Integer> {

}
