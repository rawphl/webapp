package ch.bbcag.repositories;

import ch.bbcag.models.ApplicationUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Integer> {
    @Query("SELECT u FROM ApplicationUser u WHERE u.username LIKE CONCAT('%', :name, '%')")
    ApplicationUser findByUsername(@Param("name") String username);
}
