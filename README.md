PADEL BOOKING

<img src="/src/main/resources/static/padel-booking-logo.png" alt="Logo de padel Booking" title="PADEL BOOKING" width="300">

MVP: Aplicación de gestión de reservas de la pista de pádel de la urbanización.


--- Cualquiera con acceso a la página podrá consultar el calendario (reservas y tramos disponibles) de una PISTA---> DONE
--- Un jugador podrá registrarse y obtendrá un ID. ---> DONE
--- Un jugador podrá asociar su direccion a su ID.---> DONE
--- Cualquier jugador con direccion registrada podrá hacer una reserva en el calendario, si ese slot está disponible.---> DONE.
--- Un usuario podrá cancelar su reserva (si no es reserva pasada) .---> DONE.
TO DO:

- Manejo de excepciones.
- Diferenciar entre DTOs de salida y entrada.
- En la API no estoy pudiendo hacer las operaciones. Comprobar
- Refactorización de código.

- Uso de SQL para persistencia de datos.
- Llevar a cloud.

NOTAS: 

- En MVP, un usuario puede hacer reservas sin limite.
- Semanalmente, aparece un nuevo calendario de reservas.

FALLOS:

- Cuando se intenta asignar una residencia a un jugador que no existe devuelve null y 200--> DONE
- Falta delete all calendars --> DONE
- PRIORIDAD: ESTOY CON EL CONTROLADOR DE BOOKING CALENDAR, QUE PARECE QUE NO ME ESTÁ FUNCIONANDO BIEN.
- player tries to book an available booking---> DONE, PEO ENTRO EN UN BUCLE PORQUE ESTOY DEVOLVIENDO DENTRO DEL BOOKINGOWNER UNA LISTA DE BOOKINGS QUE APARECE NULL.
  http://localhost:8080/api/v1/booking-calendars/337/bookings/8101
- 
- {
  "id": 8101,
  "bookingDate": "2025-09-25",
  "timeSlot": "TWO_PM",
  "bookingOwner": {
  "id": 577,
  "name": "Ana",
  "bookings": null
  }
  }
- Molaria que el bookingOwner solo devolviera el id y el name, no la lista de bookings.
- 
- player tries to book an already owned booking.
- player tries to book a not available booking.
- probar cancel booking
- get all calendars falla--> DONE
- get all bookings falla--> 
- poner booking dto dentro de dto players para ver las reservas de un jugador

A futuro:

- Escalable a un sistema con más pistas.
- Establecer un sistema de reglas y limitaciones para poder reservar (máximo de reservas activas, máximo de reservas por día… ).


--------

Hay que distinguir entre las cosas que tiene que poder hacer el USUARIO a través del controlador 
y las cosas que necesitamos hacer de manera INTERNA para proporcionar ese servicio.

https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&dark=auto#G1F7YAcZhY5pEYzXbr9k_0oZaryzbQqKlm

