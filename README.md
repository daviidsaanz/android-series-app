# SeriesApp - Descubre las mejores series de TV

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Android_Compose-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android Compose">
  <img src="https://img.shields.io/badge/Jetpack-4285F4?style=for-the-badge&logo=google&logoColor=white" alt="Jetpack">
  <img src="https://img.shields.io/badge/TMDB_API-01B4E4?style=for-the-badge&logo=themoviedatabase&logoColor=white" alt="TMDB API">
</p>

**SeriesApp** es una aplicación moderna para Android que te permite explorar, descubrir y seguir las series de televisión más populares. Con una interfaz intuitiva y características inteligentes, es tu compañero perfecto para encontrar tu próxima serie favorita.

## ✨ Características Principales

### 🎬 Exploración de Contenido
- **Listado de series populares** con paginación infinita
- **Detalles completos** de cada serie con sinopsis, rating, temporadas y creadores
- **Diseño adaptativo** que funciona en orientación vertical y horizontal
- **Modo claro y oscuro** con paletas de colores distintivas

### 🌍 Experiencia Multilingüe
- **Soporte para español e inglés**
- **Cambio dinámico de idioma** con un solo toque
- **Contenido adaptado** a la preferencia lingüística del usuario

### 📱 Funcionalidades Avanzadas
- **Modo offline** - Accede a series previamente vistas sin conexión
- **Sistema de recomendaciones inteligente** basado en tus preferencias
- **Detección automática de conexión** con manejo elegante de estados
- **Interfaz moderna y fluida** construida con Jetpack Compose

### 🤖 Sistema de Recomendaciones Inteligente
La aplicación analiza tus preferencias de visualización y te sugiere series personalizadas:

- **Análisis de géneros favoritos** - Detecta automáticamente los tipos de series que más te interesan
- **Recomendaciones personalizadas** - Sugiere series que probablemente disfrutarás
- **Aprendizaje continuo** - Mejora sus sugerencias mientras más usas la app
- **Badge "AI"** - Identifica las series recomendadas por el algoritmo inteligente

## 🛠️ Tecnologías Utilizadas

### Arquitectura y Patrones
- **Arquitectura MVVM** (Model-View-ViewModel) para separación clara de responsabilidades
- **Inyección de dependencias con Hilt** para gestión automática de dependencias
- **Patrón Repository** para abstraer el acceso a datos
- **Flows y State Management** para manejo reactivo del estado

### Capa de Datos
- **Retrofit 2** para comunicación con API REST
- **Room Database** para almacenamiento local offline
- **Converters personalizados** para tipos de datos complejos
- **Paginación inteligente** para carga eficiente de datos

### Interfaz de Usuario
- **Jetpack Compose** para UI declarativa moderna
- **Material Design 3** con componentes personalizados
- **Animaciones y transiciones** fluidas
- **Diseño responsivo** para diferentes tamaños de pantalla

### Integraciones y APIs
- **The Movie Database (TMDB) API** como fuente principal de datos
- **Sistema de manejo de conexión** nativo de Android
- **Gestión de recursos multilingües** integrada
- **Soporte para imágenes con Coil**

---

## 🤖 Funcionalidad Extra: Sistema de Recomendaciones

### ¿Qué he implementado?
Un **sistema de recomendaciones inteligente** que analiza las series que ves y sugiere nuevas series que podrían interesarte basándose en tus gustos.

### ¿Cómo funciona?
1. **Analiza** los géneros de las series que más consultas
2. **Identifica** tus preferencias (ej: Drama, Comedia, Ciencia Ficción)
3. **Busca** series similares que aún no hayas visto
4. **Te recomienda** las 5 mejores coincidencias con un badge especial "AI"

### ¿Por qué esta solución?
- **Útil para el usuario**: Ayuda a descubrir contenido relevante
- **Fácil de entender**: La lógica es clara y transparente
- **Preparada para crecer**: Se puede mejorar fácilmente con tecnología más avanzada
- **Integrada naturalmente**: Forma parte de la experiencia sin complicarla

### ¿Cómo podría evolucionar?
- **IA real**: Conectar con servicios como OpenAI para recomendaciones más precisas
- **Más datos**: Considerar no solo géneros, sino también rating, actores, duración, etc.
- **Aprendizaje automático**: Usar TensorFlow Lite para que el sistema aprenda de cada interacción
- **Recomendaciones sociales**: Ver qué series gustan a personas con gustos similares

Esta funcionalidad demuestra cómo se puede añadir inteligencia a una app de manera sencilla pero efectiva, preparando el terreno para futuras mejoras con tecnologías más avanzadas.
