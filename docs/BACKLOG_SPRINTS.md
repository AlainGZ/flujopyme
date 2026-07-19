# FlujoPyme — Product Backlog y Planificación de Sprints

Metodología: Scrum. Tablero: GitHub Projects (columnas `Backlog`, `Sprint actual`, `En progreso`, `En revisión`, `Hecho`). Cada historia de usuario (HU) se registra como Issue de GitHub, etiquetada con su épica y vinculada al RF/RNF correspondiente.

**Convención de estimación:** puntos de historia en escala Fibonacci (1, 2, 3, 5, 8).

---

## Épicas

| Épica | Nombre | RF / RNF cubiertos |
|---|---|---|
| E0 | Configuración Inicial y Arquitectura Base | RNF-12 |
| E1 | Autenticación y Seguridad | RF-01 a RF-03, RNF-09 a RNF-11 |
| E2 | Registro de Movimientos | RF-04 a RF-09 |
| E3 | Indicadores Financieros | RF-10 a RF-14 |
| E4 | Reportes y Exportación | RF-15 a RF-18 |
| E5 | Experiencia de Usuario y Accesibilidad | RNF-01 a RNF-06 |
| E6 | Calidad, Rendimiento y Despliegue | RNF-07, RNF-08, RNF-13 |

---

## E0 — Configuración Inicial y Arquitectura Base

### HU-E0-01 — Estructura hexagonal del backend
**Como** equipo de desarrollo, **quiero** una estructura de paquetes hexagonal definida desde el inicio, **para** mantener el dominio aislado de frameworks y facilitar pruebas y mantenimiento (RNF-12).

**Criterios de aceptación:**
- Dado el proyecto Spring Boot inicializado, cuando se revise `src/main/java/com/flujopyme`, entonces existen los paquetes `domain`, `application`, `infrastructure` separados.
- Dado el paquete `domain`, cuando se inspeccionen sus clases, entonces ninguna importa anotaciones de Spring, JPA o Jackson.
- Dado un caso de uso en `application`, cuando este necesita persistencia, entonces depende únicamente de una interfaz definida en `domain/port/out`.

Story points: 5

---

## E1 — Autenticación y Seguridad

### HU-RF01 — Registro de usuario
**Como** microempresario, **quiero** registrarme con correo y contraseña, **para** acceder a la aplicación de forma personal y segura.

**Criterios de aceptación:**
- Dado un correo no registrado y una contraseña válida, cuando el usuario envía el formulario de registro, entonces la cuenta se crea y la contraseña se almacena cifrada (RNF-11).
- Dado un correo ya registrado, cuando el usuario intenta registrarse con ese correo, entonces el sistema responde con error 409 y un mensaje claro.
- Dado un correo con formato inválido, cuando se envía el formulario, entonces el sistema rechaza la solicitud con error 400 antes de tocar la base de datos.

Story points: 5

### HU-RF02 — Inicio de sesión con JWT
**Como** microempresario registrado, **quiero** iniciar sesión con mis credenciales, **para** obtener acceso autenticado a mis datos.

**Criterios de aceptación:**
- Dado un correo y contraseña correctos, cuando el usuario inicia sesión, entonces recibe un token JWT válido con expiración definida (RNF-09).
- Dado un correo o contraseña incorrectos, cuando el usuario intenta iniciar sesión, entonces el sistema responde 401 sin revelar cuál campo falló.
- Dado un token JWT válido, cuando se usa en el header `Authorization: Bearer`, entonces el backend identifica al usuario sin mantener estado de sesión en servidor (stateless).

Story points: 5

### HU-RF03 — Cierre de sesión
**Como** usuario autenticado, **quiero** cerrar sesión, **para** invalidar mi acceso desde ese dispositivo.

**Criterios de aceptación:**
- Dado un usuario autenticado, cuando ejecuta el cierre de sesión, entonces el token deja de ser aceptado por el backend.
- Dado un token invalidado, cuando se intenta usar en una petición posterior, entonces el sistema responde 401.

Story points: 3

### HU-RNF09 — Expiración de JWT stateless
**Como** administrador del sistema, **quiero** que los tokens JWT tengan expiración definida y no dependan de estado en servidor, **para** garantizar seguridad y escalabilidad horizontal.

**Criterios de aceptación:**
- Dado un token emitido, cuando pasa el tiempo de expiración configurado, entonces el backend lo rechaza automáticamente sin consultar una tabla de sesiones.
- Dado el backend reiniciado, cuando un token previamente válido y no expirado se usa, entonces sigue siendo aceptado (no depende de memoria en servidor).

Story points: 3

### HU-RNF10 — Separación total de datos entre usuarios
**Como** microempresario, **quiero** que mis movimientos financieros sean visibles únicamente para mí, **para** proteger la confidencialidad de mi información.

**Criterios de aceptación:**
- Dado dos usuarios distintos con movimientos registrados, cuando el Usuario A consulta su historial, entonces no aparecen movimientos del Usuario B.
- Dado un usuario autenticado, cuando intenta acceder por API a un recurso (movimiento, reporte) perteneciente a otro usuario mediante su ID, entonces el sistema responde 403.

Story points: 5

### HU-RNF11 — Contraseñas cifradas con BCrypt
**Como** administrador del sistema, **quiero** que ninguna contraseña se almacene en texto plano, **para** cumplir con buenas prácticas de seguridad.

**Criterios de aceptación:**
- Dado un registro de usuario exitoso, cuando se inspecciona la fila en la base de datos, entonces el campo contraseña contiene un hash BCrypt, no el valor original.
- Dado un intento de login, cuando se valida la contraseña, entonces se usa comparación BCrypt (`matches`), nunca comparación de texto plano.

Story points: 2

---

## E2 — Registro de Movimientos

### HU-RF04 — Registro de ingreso
**Como** microempresario, **quiero** registrar un ingreso con monto, categoría, fecha, descripción y clasificación, **para** llevar control de mis entradas de dinero.

**Criterios de aceptación:**
- Dado un formulario con monto positivo, categoría, fecha y clasificación (negocio/personal), cuando se envía, entonces el ingreso queda registrado asociado al usuario autenticado.
- Dado un monto negativo o vacío, cuando se envía el formulario, entonces el sistema rechaza la solicitud con mensaje de validación.
- Dado un ingreso registrado, cuando se consulta el historial, entonces aparece con todos sus datos correctamente asociados.

Story points: 5

### HU-RF05 — Registro de egreso
**Como** microempresario, **quiero** registrar un egreso con monto, categoría, fecha, descripción y clasificación, **para** llevar control de mis salidas de dinero.

**Criterios de aceptación:**
- Dado un formulario con monto positivo, categoría, fecha y clasificación, cuando se envía, entonces el egreso queda registrado asociado al usuario autenticado.
- Dado un monto negativo o vacío, cuando se envía el formulario, entonces el sistema rechaza la solicitud con mensaje de validación.

Story points: 5

### HU-RF06 — Clasificación negocio vs. personal
**Como** microempresario, **quiero** clasificar cada movimiento como negocio o personal, **para** evitar la confusión patrimonial entre mis finanzas.

**Criterios de aceptación:**
- Dado el formulario de ingreso o egreso, cuando se completa, entonces la clasificación (negocio/personal) es un campo obligatorio con solo esas dos opciones.
- Dado un movimiento registrado como "negocio", cuando se calculan indicadores del negocio, entonces solo se incluyen movimientos con esa clasificación.

Story points: 3

### HU-RF07 — Edición de movimiento
**Como** microempresario, **quiero** editar un movimiento ya registrado, **para** corregir errores de digitación.

**Criterios de aceptación:**
- Dado un movimiento existente del usuario autenticado, cuando se edita cualquiera de sus campos, entonces los cambios se guardan y se reflejan en el historial e indicadores.
- Dado un movimiento perteneciente a otro usuario, cuando se intenta editar por ID, entonces el sistema responde 403.

Story points: 3

### HU-RF08 — Eliminación de movimiento
**Como** microempresario, **quiero** eliminar un movimiento registrado por error, **para** mantener mis datos financieros precisos.

**Criterios de aceptación:**
- Dado un movimiento existente del usuario autenticado, cuando se elimina, entonces deja de aparecer en el historial y en los indicadores.
- Dado un movimiento ya eliminado, cuando se intenta eliminar nuevamente, entonces el sistema responde 404.

Story points: 2

### HU-RF09 — Historial filtrable
**Como** microempresario, **quiero** filtrar mi historial de movimientos por período, tipo y clasificación, **para** encontrar información específica rápidamente.

**Criterios de aceptación:**
- Dado un historial con movimientos de distintos meses, cuando se filtra por un rango de fechas, entonces solo se muestran los movimientos dentro de ese rango.
- Dado un historial con ingresos y egresos, cuando se filtra por tipo, entonces solo se muestran movimientos de ese tipo.
- Dado un historial con movimientos negocio y personales, cuando se filtra por clasificación, entonces solo se muestran los que coinciden.

Story points: 5

---

## E3 — Indicadores Financieros

### HU-RF10 — Flujo de caja neto mensual
**Como** microempresario, **quiero** ver el flujo de caja neto de mi negocio por mes, **para** saber si estoy ganando o perdiendo dinero en el negocio.

**Criterios de aceptación:**
- Dado ingresos y egresos clasificados como "negocio" en un mes, cuando se consulta el indicador, entonces el resultado es igual a la suma de ingresos menos la suma de egresos de ese mes.
- Dado un mes sin movimientos de negocio, cuando se consulta el indicador, entonces el resultado es 0, no un error.

Story points: 5

### HU-RF11 — Balance personal mensual
**Como** microempresario, **quiero** ver mi balance personal mensual separado del negocio, **para** entender mis finanzas personales de forma independiente.

**Criterios de aceptación:**
- Dado ingresos y egresos clasificados como "personal" en un mes, cuando se consulta el indicador, entonces el resultado excluye cualquier movimiento clasificado como "negocio".

Story points: 3

### HU-RF12 — Comparación intermensual
**Como** microempresario, **quiero** ver la variación porcentual entre el mes actual y el anterior, **para** identificar tendencias en mi negocio.

**Criterios de aceptación:**
- Dado un flujo de caja neto del mes actual y del mes anterior, cuando se consulta la comparación, entonces se calcula la variación porcentual correctamente ((actual-anterior)/|anterior|×100).
- Dado un mes anterior sin movimientos (base cero), cuando se calcula la variación, entonces el sistema maneja el caso sin generar división por cero.

Story points: 5

### HU-RF13 — Distribución de egresos por categoría
**Como** microempresario, **quiero** ver un gráfico de mis egresos distribuidos por categoría, **para** identificar en qué gasto más.

**Criterios de aceptación:**
- Dado egresos registrados en distintas categorías durante un mes, cuando se consulta el indicador, entonces se retorna el porcentaje y monto por categoría.
- Dado el resultado, cuando se renderiza en el frontend, entonces se visualiza como gráfico (ej. dona/barras con Recharts).

Story points: 5

### HU-RF14 — Etiquetas interpretativas en lenguaje cotidiano
**Como** microempresario sin formación contable, **quiero** que los indicadores incluyan una etiqueta en lenguaje sencillo, **para** entender el resultado sin conocimientos financieros previos.

**Criterios de aceptación:**
- Dado un flujo de caja neto positivo, cuando se muestra el indicador, entonces aparece una etiqueta como "Tu negocio generó ganancia este mes".
- Dado un flujo de caja neto negativo, cuando se muestra el indicador, entonces aparece una etiqueta como "Este mes gastaste más de lo que ingresó tu negocio".

Story points: 3

---

## E4 — Reportes y Exportación

### HU-RF15 — Generación de reporte mensual automático
**Como** microempresario, **quiero** generar un reporte mensual con mis datos financieros, **para** tener un resumen consolidado de mi situación.

**Criterios de aceptación:**
- Dado un mes con movimientos registrados, cuando se solicita el reporte, entonces se genera incluyendo flujo de caja neto, balance personal, comparación intermensual y distribución de egresos.
- Dado un mes sin movimientos, cuando se solicita el reporte, entonces se genera indicando que no hay datos para ese período (sin error).

Story points: 5

### HU-RF16 — Exportación en PDF con un clic
**Como** microempresario, **quiero** exportar el reporte mensual en PDF con un solo clic, **para** compartirlo fácilmente (ej. con un asesor de crédito).

**Criterios de aceptación:**
- Dado un reporte generado en pantalla, cuando el usuario hace clic en "Exportar PDF", entonces se descarga un archivo PDF con el contenido del reporte, sin pasos adicionales (RNF-02).

Story points: 5

### HU-RF17 — Legibilidad por terceros
**Como** asesor de crédito o tercero sin acceso al sistema, **quiero** poder leer el reporte PDF sin conocer FlujoPyme, **para** evaluar la situación financiera del microempresario.

**Criterios de aceptación:**
- Dado el PDF exportado, cuando lo abre una persona externa, entonces incluye encabezados, unidades monetarias y etiquetas explicativas sin necesidad de contexto adicional del sistema.

Story points: 3

### HU-RF18 — Selección de mes y año para el reporte
**Como** microempresario, **quiero** elegir el mes y año del reporte que quiero generar, **para** consultar períodos distintos al actual.

**Criterios de aceptación:**
- Dado un selector de mes y año, cuando el usuario elige un período pasado con datos, entonces el reporte generado corresponde exactamente a ese período.
- Dado un período futuro sin datos, cuando se solicita el reporte, entonces el sistema informa que no hay información disponible.

Story points: 3

---

## E5 — Experiencia de Usuario y Accesibilidad

### HU-RNF01 — Vocabulario cotidiano
**Como** microempresario sin formación contable, **quiero** que toda la interfaz use lenguaje cotidiano, **para** entender la aplicación sin necesidad de conocimientos técnicos o contables.

**Criterios de aceptación:**
- Dado cualquier pantalla de la aplicación, cuando se revisan sus textos, entonces no contienen términos contables técnicos sin explicación (ej. "flujo de caja neto" siempre acompañado de una explicación simple).

Story points: 3

### HU-RNF02 — Máximo 3 pasos por tarea principal
**Como** microempresario, **quiero** completar tareas principales (registrar movimiento, generar reporte) en máximo 3 pasos, **para** que el uso sea rápido y sencillo.

**Criterios de aceptación:**
- Dado el flujo de "registrar movimiento", cuando se cuenta el número de pantallas/clics necesarios desde el dashboard, entonces no supera 3 pasos.
- Dado el flujo de "generar y exportar reporte", cuando se cuenta el número de pasos, entonces no supera 3.

Story points: 5

### HU-RNF03 — Retroalimentación visual en menos de 3 segundos
**Como** microempresario, **quiero** recibir retroalimentación visual inmediata al realizar una acción, **para** saber que el sistema está respondiendo.

**Criterios de aceptación:**
- Dado el envío de un formulario, cuando la operación tarda más de 500ms, entonces se muestra un indicador de carga.
- Dado cualquier acción del usuario, cuando se completa (éxito o error), entonces se muestra retroalimentación visual en menos de 3 segundos.

Story points: 3

### HU-RNF04 — Experiencia de éxito en la primera interacción
**Como** microempresario nuevo, **quiero** lograr una acción exitosa (ej. registrar mi primer movimiento) fácilmente en mi primer uso, **para** ganar confianza en la herramienta.

**Criterios de aceptación:**
- Dado un usuario recién registrado, cuando ingresa por primera vez al dashboard, entonces encuentra una guía o llamado a la acción claro para registrar su primer movimiento.
- Dado el primer movimiento registrado, cuando se completa, entonces se muestra un mensaje de confirmación positivo y visible.

Story points: 3

### HU-RNF05 — Multiplataforma sin instalación
**Como** microempresario, **quiero** usar FlujoPyme desde cualquier navegador sin instalar nada, **para** acceder desde el dispositivo que tenga disponible.

**Criterios de aceptación:**
- Dado el frontend desplegado en Vercel, cuando se accede desde Chrome, Firefox o Safari en escritorio o móvil, entonces la aplicación carga y funciona correctamente sin instalación.

Story points: 2

### HU-RNF06 — Diseño responsivo desde 320px
**Como** microempresario que usa smartphone, **quiero** que la interfaz se adapte a pantallas pequeñas, **para** poder usar la aplicación desde mi celular.

**Criterios de aceptación:**
- Dado un ancho de pantalla de 320px, cuando se carga cualquier pantalla de la aplicación, entonces todos los elementos son visibles y usables sin scroll horizontal.
- Dado un formulario en móvil, cuando se completa, entonces los campos son fácilmente tocables (tamaño mínimo de área táctil adecuado).

Story points: 5

---

## E6 — Calidad, Rendimiento y Despliegue

### HU-RNF07 — Tiempo de respuesta menor a 3 segundos
**Como** microempresario, **quiero** que las acciones del sistema respondan en menos de 3 segundos, **para** tener una experiencia fluida.

**Criterios de aceptación:**
- Dado cualquier endpoint de la API en condiciones normales de carga, cuando se invoca, entonces responde en menos de 3 segundos.

Story points: 3

### HU-RNF08 — Disponibilidad 99% en horario 6am–10pm
**Como** microempresario, **quiero** que el sistema esté disponible durante mi horario de operación comercial, **para** poder registrar movimientos cuando los necesito.

**Criterios de aceptación:**
- Dado el sistema desplegado en producción, cuando se monitorea entre las 6:00 a.m. y 10:00 p.m., entonces la disponibilidad medida es igual o superior al 99%.

Story points: 3

### HU-RNF13 — Cobertura mínima 80% en pruebas unitarias de dominio
**Como** equipo de desarrollo, **quiero** que la capa de dominio tenga al menos 80% de cobertura de pruebas unitarias, **para** garantizar la confiabilidad de las reglas de negocio.

**Criterios de aceptación:**
- Dado el paquete `domain` del backend, cuando se ejecuta el reporte de cobertura (JaCoCo), entonces la cobertura de líneas es igual o superior al 80%.
- Dado un caso de uso del dominio, cuando se modifica su lógica, entonces existe al menos una prueba unitaria (JUnit 5 + Mockito) que falla si la regla de negocio se rompe.

Story points: 5

---

## Planificación de Sprints

> Duración sugerida: 2 semanas por sprint (ajustable según cronograma real de UNIMINUTO). El Sprint 0 puede ser de 1 semana.

| Sprint | Objetivo | Historias incluidas | Fase asociada |
|---|---|---|---|
| **Sprint 0** | Configuración del repositorio, tablero Kanban y scaffold hexagonal | HU-E0-01 | Fase 0 / inicio Fase 1 |
| **Sprint 1** | Autenticación completa y segura | HU-RF01, HU-RF02, HU-RF03, HU-RNF09, HU-RNF10, HU-RNF11 | Fase 2 — Módulo 1 |
| **Sprint 2** | Registro y gestión de movimientos | HU-RF04, HU-RF05, HU-RF06, HU-RF07, HU-RF08, HU-RF09 | Fase 2 — Módulo 2 |
| **Sprint 3** | Indicadores financieros | HU-RF10, HU-RF11, HU-RF12, HU-RF13, HU-RF14 | Fase 2 — Módulo 3 |
| **Sprint 4** | Reportes y exportación PDF | HU-RF15, HU-RF16, HU-RF17, HU-RF18 | Fase 2 — Módulo 4 |
| **Sprint 5** | Frontend, integración y experiencia de usuario | HU-RNF01, HU-RNF02, HU-RNF03, HU-RNF04, HU-RNF05, HU-RNF06 | Fase 3 |
| **Sprint 6** | Calidad, rendimiento y despliegue | HU-RNF07, HU-RNF08, HU-RNF13 + Fase 4 (pruebas) y Fase 5 (deploy) | Fases 4 y 5 |

**Total historias:** 31 (18 RF + 13 RNF) distribuidas en 7 sprints (0 a 6).

## Configuración del tablero Kanban (GitHub Projects)

1. Crear un Project (vista tablero) a nivel de repositorio u organización.
2. Columnas: `Backlog` → `Sprint actual` → `En progreso` → `En revisión` → `Hecho`.
3. Cada HU de este documento se crea como Issue, con:
   - Título: `[HU-RFxx] Nombre corto de la historia`
   - Etiquetas (labels): nombre de la épica (`E1-Autenticacion`, `E2-Movimientos`, etc.) y tipo (`feature`, `technical`).
   - Descripción: historia de usuario + criterios de aceptación copiados de este documento.
   - Campo personalizado "Sprint": número de sprint asignado según la tabla anterior.
4. Automatización sugerida: al abrir un Pull Request vinculado a un Issue, mover automáticamente a `En revisión`; al hacer merge, mover a `Hecho`.
5. Todas las historias del backlog inician en la columna `Backlog`; al inicio de cada sprint se mueven a `Sprint actual`.
