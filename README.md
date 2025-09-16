PADEL BOOKING

<img src="/src/main/resources/static/padel-booking-logo.png" alt="Logo de padel Booking" title="PADEL BOOKING" width="300">

MVP: Aplicación de gestión de reservas de la pista de pádel de la urbanización.


--- Cualquiera con acceso a la página podrá consultar el calendario (reservas y tramos disponibles) de una PISTA---> Implementado en API.
--- Un jugador podrá registrarse y obtendrá un ID. ---> Implementado en API.
--- Un jugador podrá asociar su direccion a su ID.---> Implementado en API.
--- Cualquier jugador con direccion registrada podrá hacer una reserva en el calendario, si ese slot está disponible.---> Implementado en API.
--- Un usuario podrá editar o cancelar su reserva (no aplicable a reservas caducadas).

TO DO:

- Manejo de excepciones.
- Refactorización de código.
- Uso de SQL para persistencia de datos.
- Llevar a cloud.

NOTAS: 

- En MVP, un usuario puede hacer reservas sin limite.
- Semanalmente, aparece un nuevo calendario de reservas.

A futuro:

- Escalable a un sistema con más pistas.
- Establecer un sistema de reglas y limitaciones para poder reservar (máximo de reservas activas, máximo de reservas por día… ).


--------

Hay que distinguir entre las cosas que tiene que poder hacer el USUARIO a través del controlador 
y las cosas que necesitamos hacer de manera INTERNA para proporcionar ese servicio.


https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&dark=auto#G1F7YAcZhY5pEYzXbr9k_0oZaryzbQqKlm

