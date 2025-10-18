***** PRIMERA FASE DEL PROYECTO (MVP) HECHA: api funciona, con lógica de negocio, persistencia en memoria (H2) y testa unitarios y de integración. DONE

****** PRIMERA FASE DEL PROYECTO: IMPLEMENTAR EN LA NUBE.


IMPORTANTE:

He estado un día entero con problemas con el metodo de reserve boking. me daba un error de lazy initialization
y lo he resuelto cargando las listas de calendar (available y reserved) antes de modificarlas.

Para cargarlas, he usado el metodo size() que es un truco que he visto en internet.


MEJORAS A FUTURO:

- PRIORIDAD----> Implementar la cancelacion de la reserva del booking. DONE, pero no hay seguridad de que solo pueda cancelarla el player que la hizo.
- Si las reservas activas son pasadas, eliminarlas y permitir eliminar el calendario.
- Aprender a usar builder con lombok
- MOLARIA: Tener bien definidos y separados los unit test de los integration test.
- Manejo de excepciones: se pueden implementar más excepciones personalizadas.
- Uso de SQL para persistencia de datos.
- Llevar a cloud.




