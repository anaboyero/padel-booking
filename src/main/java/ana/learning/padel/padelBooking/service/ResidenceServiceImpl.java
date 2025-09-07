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
    public Residence saveResidence(Residence residence) throws InvalidResidenceException {
        if (residence.getBuilding()==null || residence.getFloor()==null || residence.getLetter()==null ){
            throw new InvalidResidenceException("Residence could not be persisted. There are missing fields");
        }
        return residenceRepository.save(residence);

    }

    @Override
    public boolean isACompleteResidence(Residence residence) {
        return residence.isACompleteResidence();
    }
}
