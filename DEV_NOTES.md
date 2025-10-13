TO DO:

- Cambiar: que el servicio devuelva MOJOS en lugar de ENTITIES..
- PRIORIDAD----> Implementar la cancelacion de la reserva del booking. DONE, pero no hay seguridad de que solo pueda cancelarla el player que la hizo.
- Delete calendar by id --> DONE (a ver cómo gestionamos lo de reservas activas).
- - Delete booking by id--> DONE (a ver cómo gestionamos lo de reservas activas)
- Delete all bookings of a calendar.
- Si las reservas activas son pasadas, eliminarlas y permitir eliminar el calendario.
- Aprender a usar builder con lombok

IMPORTANTE:

He estado un día entero con problemas con el metodo de reserve boking. me daba un error de lazy initialization
y lo he resuelto cargando las listas de calendar (available y reserved) antes de modificarlas.

Para cargarlas, he usado el metodo size() que es un truco que he visto en internet.




MOLARIA: Tener bien definidos y separados los unit test de los integration test.

- Manejo de excepciones:

- Uso de SQL para persistencia de datos.
- Llevar a cloud.


FALLOS:

- PRIORIDAD: ESTOY CON EL CONTROLADOR DE BOOKING CALENDAR, QUE PARECE QUE NO ME ESTÁ FUNCIONANDO BIEN.
- player tries to book an available booking---> DONE, PERO ENTRO EN UN BUCLE PORQUE ESTOY DEVOLVIENDO DENTRO DEL BOOKINGOWNER UNA LISTA DE BOOKINGS QUE APARECE NULL.
  http://localhost:8080/api/v1/booking-calendars/337/bookings/8101



