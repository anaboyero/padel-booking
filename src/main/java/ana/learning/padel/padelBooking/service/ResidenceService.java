package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Residence;

public interface ResidenceService {
    Residence saveResidence(Residence residence) throws InvalidResidenceException;
    boolean isACompleteResidence(Residence residence);
}
