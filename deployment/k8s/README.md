# Deploy en Red Hat OpenShift Sandbox

Manifiestos para desplegar el book-store en OpenShift. La infra **solo expone 2 puertos** hacia fuera: el **front** (React + API `/books`) y **Grafana**.

---

## Comandos (todo con `oc`)

En OpenShift usá **siempre `oc`** (reemplaza a `kubectl` y añade Routes, etc.):

```bash
# 1. Crear proyecto
oc new-project book-store

# 2. Aplicar recursos en orden
oc apply -f namespace.yaml
oc apply -f postgresql-deployment.yml
oc apply -f consul-deployment.yml
oc apply -f app-authors-deployment.yml
oc apply -f app-books-deployment.yml
oc apply -f app-customers-deployment.yml
oc apply -f app-react-deployment.yml
oc apply -f front-proxy.yaml
oc apply -f prometheus-grafana.yaml
oc apply -f routes.yaml
```

O con Kustomize (desde la raíz del repo):

```bash
oc apply -k deployment/k8s
```

---

## URLs expuestas (solo 2)

Después de aplicar las Routes:

```bash
oc get routes -n book-store
```

Solo verás **2 hosts** públicos:

| Route   | Uso |
|--------|-----|
| **front**   | App web: React en `/` y API Books en `/books`. Una sola URL para todo el front. |
| **grafana** | Dashboards y métricas (usuario/contraseña por defecto: `admin` / `admin`). |

El resto (authors, books, customers, consul, prometheus) son **solo internos** (ClusterIP).

---

## Recompilar el front para que apunte al backend

Vos decidís cuándo recompilar el front y con qué URL base usa la API.

- **Detrás del proxy (este deploy)**  
  El front se sirve desde la misma URL que la API (Route **front**). La API está en `/books` en ese mismo host.  
  Compilá el front con **URL base vacía** (misma origin):

  ```bash
  cd app-web-react
  # Sin variable o vacía → pide a la misma origin (ej. https://front-book-store..../books)
  npm run build
  # O explícito:
  VITE_API_BASE_URL= npm run build
  ```

  Luego construí la imagen y subila (ej. a Docker Hub o al registry de OpenShift):

  ```bash
  docker build -t emil2245/app-web-react:latest .
  docker push emil2245/app-web-react:latest
  ```

- **Si en el futuro exponés Books en otra URL**  
  Compilá con esa URL como base:

  ```bash
  VITE_API_BASE_URL=https://books-book-store.mi-proyecto.apps.cluster.example.com npm run build
  ```

El backend Books no necesita CORS cuando el front se sirve desde el proxy (misma origin).

---

## Modelo de base de datos en app-books

**Con la configuración actual, app-books no crea ni actualiza el modelo de la BD automáticamente.**

- No usa **Flyway** (no hay migraciones en app-books).
- No tiene `quarkus.hibernate-orm.database.generation` en `application.properties`, así que Hibernate no genera DDL.

Opciones:

1. **Añadir Flyway a app-books** (recomendado): mismas migraciones que en app-authors, para que el esquema se cree/actualice al arrancar.
2. **O** activar generación Hibernate solo para desarrollo, por ejemplo en un perfil:
   `quarkus.hibernate-orm.database.generation=update` (no recomendado en producción).

Mientras tanto, el esquema debe existir en Postgres (por ejemplo creado por app-authors con Flyway, o a mano).

---

## Imágenes

Los manifiestos usan imágenes de Docker Hub: `emil2245/app-authors`, `emil2245/app-books`, `emil2245/app-customers`, `emil2245/app-web-react`.

- Si son públicas, no hace falta nada.
- Si usás el registry de OpenShift, reemplazá la imagen por la ruta del ImageStream o del registry y, si aplica, añadí `imagePullSecrets` al Deployment.

---

## Sin Consul

Si no desplegás Consul, en **app-books** tenés que apuntar a authors y customers por URL fija. En `app-books-deployment.yml` podés comentar `CONSUL_HOST`/`CONSUL_PORT` y configurar las URLs estáticas correspondientes en la app (descubrimiento estático).

---

## Recursos

- **PostgreSQL**: PVC 1Gi, Service `postgresq`.
- **front-proxy**: nginx que sirve React en `/` y reenvía `/books` a app-books (solo este servicio + Grafana tienen Route).
- **Prometheus / Grafana**: internos; solo Grafana tiene Route.
- **Authors/Books/Customers**: ClusterIP; HPA en app-authors (1–5 réplicas).
