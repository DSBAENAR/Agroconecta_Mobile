# AgroConecta Mobile

Aplicación móvil Android que conecta productores agrícolas con compradores de forma directa, eliminando intermediarios y promoviendo el comercio justo de productos del campo colombiano.

## Características

### Para Compradores
- Explorar el **Top 10** de productos más vendidos
- Navegar por categorías (Frutas, Verduras, Tubérculos, Cereales, Semillas)
- Buscar productos en el **Marketplace** con filtros por categoría y ubicación
- Ver **detalle del producto**: foto, descripción, precio por unidad (kg/lb/arroba), stock disponible, ubicación y perfil del agricultor
- Agregar productos al carrito y contactar al agricultor
- Dashboard con historial de compras

### Para Vendedores (Agricultores)
- **Publicar productos** con foto, categoría, descripción, precio por unidad, cantidad disponible y ubicación
- Dashboard con estadísticas: productos activos, pedidos pendientes, ventas del mes
- Gestión de productos y pedidos (tabs Productos/Pedidos/Análisis)
- Análisis con **insights de IA**: predicción de precios, recomendaciones de cosecha y logística

### General
- Onboarding con selección de rol (Comprador/Vendedor)
- Registro e inicio de sesión por rol
- Perfil de usuario
- Navegación con bottom bar adaptativa según rol

## Tech Stack

| Componente | Tecnología |
|---|---|
| Lenguaje | Kotlin |
| UI | Jetpack Compose + Material Design 3 |
| Navegación | Navigation Compose 2.7.6 |
| Iconos | Material Icons Extended |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 34 (Android 14) |
| Build | Gradle 8.6 + AGP 8.2.2 |

## Estructura del Proyecto

```
app/src/main/java/com/agroconecta/mobile/
├── MainActivity.kt
├── data/
│   └── model/
│       └── Models.kt              # Product, Farmer, Purchase, AIInsight
└── ui/
    ├── components/
    │   └── BottomNavBar.kt         # Barras de navegación (Buyer/Farmer)
    ├── navigation/
    │   ├── AppNavigation.kt        # Grafo de navegación (15 rutas)
    │   └── Screen.kt              # Definición de rutas
    ├── screens/
    │   ├── buyer/                  # BuyerDashboardScreen
    │   ├── farmer/                 # FarmerDashboard, CreatePublication, AIAnalysis
    │   ├── home/                   # HomeScreen (Top 10, categorías)
    │   ├── login/                  # LoginScreen
    │   ├── marketplace/            # MarketplaceScreen (búsqueda, filtros, grid)
    │   ├── onboarding/             # Welcome, RoleSelection
    │   ├── product/                # ProductDetailScreen
    │   ├── profile/                # ProfileScreen
    │   └── register/               # RegisterScreen
    └── theme/
        ├── Color.kt               # Paleta de colores
        ├── Theme.kt               # AgroConectaTheme
        └── Type.kt                # Tipografía
```

## Requisitos

- **Android Studio** Hedgehog (2023.1.1) o superior
- **JDK** 17+
- **Android SDK** 34

## Instalación

```bash
# Clonar el repositorio
git clone https://github.com/DSBAENAR/Agroconecta_Mobile.git
cd Agroconecta_Mobile

# Compilar
./gradlew assembleDebug

# Instalar en dispositivo/emulador conectado
./gradlew installDebug
```

## Navegación

```
Welcome → RoleSelection
              ├── LoginBuyer  → BuyerHome  → Marketplace → ProductDetail
              │                              BuyerDashboard
              │                              Profile
              ├── LoginFarmer → FarmerHome → Marketplace → ProductDetail
              │                              FarmerDashboard → CreatePublication
              │                                             → AIAnalysis
              │                              Profile
              ├── RegisterBuyer  → BuyerHome
              └── RegisterFarmer → FarmerHome
```

## Estado Actual

La app tiene la UI completa con datos de ejemplo (hardcoded). Pendiente por implementar:

- [ ] Backend / API REST
- [ ] Autenticación real (Firebase Auth o similar)
- [ ] Persistencia local (Room)
- [ ] Carga de imágenes reales (Coil/Glide)
- [ ] Integración con mapa para ubicación
- [ ] Notificaciones push
- [ ] Chat entre comprador y vendedor
- [ ] Pasarela de pagos

## Autores

- David Salomón Baena Rubio
