package ana.learning.padel.padelBooking.mappers;

import ana.learning.padel.padelBooking.DTO.BookingCalendarDTO;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingCalendarMapper {
//
//    BookingCalendar toEntity(BookingCalendarDTO dto);
//    BookingCalendarDTO toDTO(BookingCalendar calendar);

    default BookingCalendarDTO customToDTO (BookingCalendar calendar) {
        BookingCalendarDTO dto = new BookingCalendarDTO();
        dto.setId(calendar.getId());
        dto.setStartDay(calendar.getStartDay());
        // conseguir los ids de los booking available de calendar
        List<Long> availableIds = new ArrayList<>();
        List<Booking> availableBookings = calendar.getAvailableBookings();
        availableIds = availableBookings.stream().map(Booking::getId).toList();
        dto.setAvailableBookingsId(availableIds);
        // conseguir los ids de los booking reserved de calendar
        List<Long> reservedIds = new ArrayList<>();
        List<Booking> reservedBookings = calendar.getReservedBookings();
        reservedIds = reservedBookings.stream().map(Booking::getId).toList();
        dto.setReservedBookingsId(reservedIds);
        System.out.println("\n RESULTADO DEL MAPEO DE CALENDAR A DTO:");
        System.out.println(dto.toString());
        return dto;

    }

}