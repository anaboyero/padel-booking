He pausado las cancel booking porque todavia no esta implementada la reserva.


 Esta fallando este metodo public void shouldCreateANewCalendarWithAProperDate(){
 

TO DO:
- PRIORIDAD ---> Estan dando error los bookings, no se pueden recuperar en el get.
- PRIORIDAD----> Implementar la reserva de booking.
- PRIORIDAD----> Implementar la cancelacion de la reserva del booking.

- PRIORIDAD----> Repasar todos los DTO, porque ahora mismo al intentar cargar bookings se desborda la pila.
- Manejo de excepciones:
- En la API no estoy pudiendo hacer varias operaciones. Ejemplo: get all bookings.

- Uso de SQL para persistencia de datos.
- Llevar a cloud.


FALLOS:

- Cuando se intenta asignar una residencia a un jugador que no existe devuelve null y 200--> DONE
- Falta delete all calendars --> DONE
- PRIORIDAD: ESTOY CON EL CONTROLADOR DE BOOKING CALENDAR, QUE PARECE QUE NO ME ESTÁ FUNCIONANDO BIEN.
- player tries to book an available booking---> DONE, PEO ENTRO EN UN BUCLE PORQUE ESTOY DEVOLVIENDO DENTRO DEL BOOKINGOWNER UNA LISTA DE BOOKINGS QUE APARECE NULL.
  http://localhost:8080/api/v1/booking-calendars/337/bookings/8101