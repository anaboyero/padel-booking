package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.ResidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidenceServiceImpl implements ResidenceService{
    @Autowired
    ResidenceRepository residenceRepository;

    @Override
    public Residence saveResidence(Residence residence) {
        // Logic to save the residence
        // This could involve calling a repository method to persist the residence entity
        return residenceRepository.save(residence); // Placeholder return statement
    }
}
