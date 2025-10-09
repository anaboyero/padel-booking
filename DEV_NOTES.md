ERROR ACTUAL:         Booking result = bookingCalendarService.reserveBooking(bookingId, playerDTO).get();

Despues de esta reserva me esta colocando las mismas reservas en el calendario, tanto en available como en reserved. 



He pausado las cancel booking porque todavia no esta implementada la reserva.

TO DO:

- PRIORIDAD----> Implementar la reserva de booking. Falta el controlador.
- PRIORIDAD----> Implementar la cancelacion de la reserva del booking.
- Delete calendar by id
- Delete booking by id
- Aprender a usar builder con lombok

- Manejo de excepciones:

- Uso de SQL para persistencia de datos.
- Llevar a cloud.


FALLOS:

- PRIORIDAD: ESTOY CON EL CONTROLADOR DE BOOKING CALENDAR, QUE PARECE QUE NO ME ESTÁ FUNCIONANDO BIEN.
- player tries to book an available booking---> DONE, PEO ENTRO EN UN BUCLE PORQUE ESTOY DEVOLVIENDO DENTRO DEL BOOKINGOWNER UNA LISTA DE BOOKINGS QUE APARECE NULL.
  http://localhost:8080/api/v1/booking-calendars/337/bookings/8101



