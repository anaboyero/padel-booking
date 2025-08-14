package ana.learning.padel.padelBooking.DTO;

import java.time.LocalDate;

public class CreateCalendarRequest {
    private LocalDate startDay;

    public CreateCalendarRequest(LocalDate startDay) {
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
