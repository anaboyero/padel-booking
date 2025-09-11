package ana.learning.padel.padelBooking.DTO;

import java.time.LocalDate;

public class CreateCalendarRequestDTO {
    private LocalDate startDay;

    public CreateCalendarRequestDTO(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    @Override
    public String toString() {
        return "CreateCalendarRequest{" +
                "startDay=" + startDay +
                '}';
    }

}
