package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Residence;

public interface ResidenceService {
    public Residence saveResidence(Residence residence) throws InvalidResidenceException;
}
