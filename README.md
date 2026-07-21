# FlujoPyme

Aplicación web de gestión financiera básica para microempresarios del área urbana de Antioquia, Colombia. Proyecto de grado — Ingeniería de Sistemas, UNIMINUTO.

## Problema

Los microempresarios colombianos no tienen visibilidad de su situación financiera real: mezclan gastos personales con los del negocio (confusión patrimonial) y carecen de registros que les permitan acceder a crédito bancario o tomar decisiones informadas.

## Solución

FlujoPyme permite:
- Registrar ingresos y egresos diferenciando negocio de personal.
- Visualizar indicadores financieros básicos (flujo de caja neto, balance mensual, comparación intermensual, distribución de egresos por categoría).
- Generar y exportar reportes mensuales en PDF, legibles por terceros (ej. asesores de crédito).

## Stack técnico

| Capa | Tecnología |
|---|---|
| Backend | Java 21, Spring Boot, Spring Data JPA, Spring Security, JWT |
| Frontend | React, Vite, Tailwind CSS, Recharts |
| Base de datos | PostgreSQL (Neon.tech en producción) |
| Arquitectura | Hexagonal (puertos y adaptadores) |
| Deploy | Railway (backend) · Vercel (frontend) · Neon.tech (BD) |
| Control de versiones | GitHub |
| Metodología | Scrum + tablero Kanban (GitHub Projects) |

## Estructura del repositorio

```
flujopyme/
├── backend/                # Spring Boot — arquitectura hexagonal
│   └── src/main/java/com/flujopyme/
│       ├── domain/         # Modelos y reglas de negocio (sin dependencias de framework)
│       │   ├── model/
│       │   └── port/
│       │       ├── in/     # Casos de uso (interfaces)
│       │       └── out/    # Puertos de salida (repositorios, etc.)
│       ├── application/    # Implementación de casos de uso (servicios)
│       └── infrastructure/
│           ├── adapter/
│           │   ├── in/rest/       # Controllers, DTOs
│           │   └── out/persistence/ # Entidades JPA, repositorios, mappers
│           ├── config/     # Seguridad, CORS, beans
│           └── security/   # JWT filter, provider
├── frontend/                # React + Vite + Tailwind
│   └── src/
│       ├── pages/
│       ├── components/
│       ├── services/        # Clientes HTTP hacia la API
│       ├── hooks/
│       └── routes/
├── docs/                     # Manual técnico, manual de usuario, casos de prueba
├── .gitignore
└── README.md
```

## Flujo de trabajo Git

Estrategia de ramas:

| Rama | Propósito |
|---|---|
| `main` | Código en producción. Solo recibe merges desde `release/*` o `hotfix/*`. Protegida. |
| `develop` | Integración continua de features. Base de todo `feature/*`. |
| `feature/<nombre>` | Una funcionalidad o historia de usuario específica. Nace de `develop`, se fusiona a `develop`. |
| `release/<version>` | Estabilización previa a producción (ej. `release/1.00`). Nace de `develop`, se fusiona a `main` y `develop`. |

Convención de nombres de rama: `feature/rf-04-registro-ingreso`, `feature/rf-10-flujo-caja-neto`.

### Commits semánticos (Conventional Commits)

```
feat:     nueva funcionalidad
fix:      corrección de errores
docs:     cambios en documentación
test:     adición o corrección de pruebas
chore:    tareas de mantenimiento, configuración, dependencias
refactor: cambios de código sin alterar comportamiento
```

Ejemplo: `feat(auth): implementar registro de usuario con validación de correo (RF-01)`

## Configuración local

### Backend
```bash
cd backend
./mvnw spring-boot:run
```
Requiere PostgreSQL local o variable `DATABASE_URL` apuntando a Neon.tech. Configuración base: BD `flujopyme_db`, puerto `5432`, zona horaria `America/Bogota`.

### Frontend
```bash
cd frontend
npm install
npm run dev
```
Servidor de desarrollo en `http://localhost:5173`, con CORS habilitado en el backend hacia ese origen.

## Estado del proyecto

En desarrollo — Fase 0 (configuración inicial). 

## Licencia

Proyecto académico — UNIMINUTO, Ingeniería de Sistemas.
