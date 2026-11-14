# Diario espacial – PC02 DAM 2026-2

App Android (Kotlin + Jetpack Compose) que consume la API abierta de la NASA (APOD y NeoWs) y guarda los datos en SQLite con Room.

## Integrantes
| Rol | Integrante | Código | Opción |
|---|---|---|---|
| Alumno 1 | Pariacuri | 22200415 | Imágenes (APOD) |
| Alumno 2 | Albornoz | 22102018 | Asteroides (NeoWs) |

## Cómo ejecutar
1. Clonar el repositorio y abrirlo en Android Studio.
2. Generar una API key en https://api.nasa.gov (sección *Generate API Key*).
3. En el archivo **`local.properties`** (raíz del proyecto, NO se sube a GitHub) agregar:
   ```
   NASA_API_KEY=tu_clave_aqui
   ```
4. *Sync Gradle* y ejecutar en un emulador o dispositivo con internet.

La clave se lee en `app/build.gradle.kts` y se expone como `BuildConfig.NASA_API_KEY`; `RetrofitClient` la agrega a cada petición con un interceptor.

## Arquitectura
```
data/
  remote/      RetrofitClient (común), apod/ y neows/ (DTO + interfaz Retrofit)
  local/       AppDatabase (común), ApodEntity/ApodDao, AsteroidEntity/AsteroidDao
  mapper/      DTO -> Entity
  repository/  ApodRepository, AsteroidRepository
ui/
  navigation/  Routes, AppNavigation (barra inferior, común)
  components/  FavoriteButton, LoadingView, EmptyView (común)
  apod/        pantallas + ViewModel (Alumno 1)
  asteroids/   pantallas + ViewModel (Alumno 2)
```
Flujo: API -> DTO -> Entity -> Room -> Flow -> ViewModel (StateFlow) -> pantalla. Las pantallas siempre leen de Room.

## Acuerdo de coordinación (base común)
La base común se dividió en dos capas, una por integrante:

| PR | Rama | Responsable | Contenido | Revisó |
|---|---|---|---|---|
| 1 | `setup/web-api` | Alumno 1 | Dependencias de red, API key con BuildConfig, permiso INTERNET, RetrofitClient (interceptor de api_key), navegación principal con barra inferior, componentes compartidos y DateUtils | Alumno 2 |
| 2 | `setup/sqlite` | Alumno 2 | Plugin KSP, dependencias de Room, AppDatabase con las dos tablas