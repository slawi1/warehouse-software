package app.locations.service;

import app.locations.model.Locations;
import app.locations.repository.LocationsRepository;
import app.web.dto.CreateLocationsRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationsRepository locationsRepository;

    public LocationService(LocationsRepository locationsRepository) {
        this.locationsRepository = locationsRepository;
    }

    public Locations findLocationByCode(String string) {

        Optional<Locations> optionalLocation = locationsRepository.findLocationsByCode(string);
        return optionalLocation.orElseThrow(() -> new RuntimeException("Location does not exists"));
    }

    public Locations createInboundLocation() {
        Locations inboundLocation = Locations.builder()
                .code("INBOUND")
                .build();
        locationsRepository.save(inboundLocation);
        return inboundLocation;
    }

    public void createLocations(List<CreateLocationsRequest> createLocationsRequest) {

        for(CreateLocationsRequest request : createLocationsRequest) {
            Locations location = Locations.builder()
                    .code(request.getCode())
                    .build();
            if (request.getDescription() != null) {
                location.setDescription(request.getDescription());
            }
            Optional<Locations> optionalLocation = locationsRepository.findLocationsByCode(request.getCode());
            if (optionalLocation.isEmpty()) {
                locationsRepository.save(location);
            } else {
                Locations newLocation = optionalLocation.get();
                if (newLocation.getDescription() == null) {
                    newLocation.setDescription(request.getDescription());
                }
                locationsRepository.save(newLocation);
            }
        }
    }
}
