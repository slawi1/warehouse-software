package app.locations.repository;

import app.locations.model.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationsRepository extends JpaRepository<Locations, Long> {

    Optional<Locations> findLocationsByCode(String code);
}
