PADEL BOOKING

<img src="/src/main/resources/static/padel-booking-logo.png" alt="Logo de padel Booking" title="PADEL BOOKING" width="2048">

MVP: Aplicación de gestión de reservas de la pista de pádel de la urbanización.


--- Cualquiera con acceso a la página podrá consultar el calendario (reservas y tramos disponibles) de una PISTA---> DONE
--- Un jugador podrá registrarse con su direccion y obtendrá un ID. ---> DONE
--- Cualquier jugador registrado podrá hacer una reserva en el calendario, si ese slot está disponible.---> DONE.
--- Un usuario podrá cancelar su reserva (si no es reserva pasada) .---> DONE.


NOTAS: 

- En MVP, un usuario puede hacer reservas sin limite.
- Semanalmente, aparece un nuevo calendario de reservas.


- 
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

