# Rick and Morty App

## Decisiones Técnicas Tomadas

- **Arquitectura MVVM:** Separación clara de responsabilidades entre la UI (Compose), la lógica de presentación (ViewModel) y la capa de datos (Repository).
- **Jetpack Compose:** Uso de Compose para una interfaz declarativa y reactiva.
- **Material 3:** Implementación de componentes de Material Design 3.
- **Retrofit & OkHttp:** Para el consumo de la API REST, incluyendo un interceptor de logs para facilitar el debugging.
- **Moshi:** Para el parseo de JSON por su eficiencia y compatibilidad con Kotlin.
- **Coil:** Librería moderna para la carga de imágenes optimizada.
- **StateFlow:** Manejo de estados de la UI de forma reactiva y segura ante cambios de configuración.

## Qué mejoraría con más tiempo

- **Persistencia Local:** La implementación de Room para visualización offline.
- **Búsqueda y Filtros:** Funcionalidad para buscar personajes por nombre o filtrar por estado/especie.
- **Inyección de Dependencias:** Uso de Hilt o Koin para desacoplar la creación de repositorios y ViewModels.
- **Arquitectura de Capas:** Introducción de Use Cases (Domain Layer) para reglas de negocio más complejas.

## Uso de AI

Se utilizó asistencia de IA en las siguientes partes:

1.  **Rediseño:** Usando Google Stitch para un mockup de un diseño personalizado de Rick and Morty.
2.  **Solución de Errores:** Diagnóstico de errores de compilación tras la actualización del BOM de Compose y migración de temas.
3.  **Boilerplate:** Creación de los models y sugerencias autocompletado.
