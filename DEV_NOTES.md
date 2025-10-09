FALLOS: Al crear un calendario no guarda en las bookings el id del calendario



IMPORTANTE:

He estado un d ía entero con problemas con el metodo de reserve boking. me daba un error de lazy initialization 
y lo he resuelto cargando las listas de calendar (available y reserved) antes de modificarlas. 

Ahora el test pasa, pero tengo que repasar el tema de los entity managers y el tema de las conexiones a la base de datos. 



TO DO:

He pausado las cancel booking porque todavia no esta implementada la reserva.

- PRIORIDAD----> Implementar la reserva de booking. Falta ver si funciona en API. 
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



