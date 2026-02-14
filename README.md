# Agro Marketplace Spring Boot API

A layered, domain-oriented backend for a multi-service agricultural marketplace supporting:
- JWT auth with roles (`CLIENT`, `SELLER`, `OWNER`, `ADMIN`)
- Product sales
- Machine sales and rentals
- Land sales and rentals
- Unified rental tracking
- Unified order flow for products, machines, and lands

## Run

```bash
mvn spring-boot:run
```

Default DB is H2 (PostgreSQL compatibility mode) for local development. For PostgreSQL:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/agromarket
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export DB_DRIVER=org.postgresql.Driver
```

## Main Endpoints

- `POST /auth/register`
- `POST /auth/login`
- `GET /products`, `POST /products`
- `GET /machines`, `POST /machines`, `POST /machines/{id}/rent`, `POST /machines/{id}/buy`
- `GET /lands`, `POST /lands`, `POST /lands/{id}/rent`, `POST /lands/{id}/buy`
- `GET /rentals/my`
- `POST /orders`
