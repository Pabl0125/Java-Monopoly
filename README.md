# 🎲 Monopoly Java Edition (MonopolyETSE)

Una implementación completa del clásico juego de mesa **Monopoly** desarrollada en **Java**. Este proyecto es una aplicación de consola que permite simular partidas, gestionar propiedades, realizar tratos y visualizar el estado del juego mediante una interfaz de texto coloreada.

> **Nota:** Este proyecto fue desarrollado como parte de una asignatura universitaria (ETSE). Cumple con los requisitos académicos establecidos, aunque se encuentra en fase de mejora continua.

## 📋 Características Principales

* **Interfaz de Consola Avanzada:** Visualización del tablero completo con soporte para **colores ANSI**, permitiendo distinguir fácilmente los grupos de propiedades y la ubicación de los avatares.
* **Gestión de Jugadores y Avatares:** Soporte para múltiples jugadores con fichas personalizadas.
* **Sistema de Edificación Extendido:** Además de casas y hoteles, incluye construcciones especiales como **Piscinas** y **Pistas Deportivas**.
* **Mecánicas Completas:**
    * Compra y venta de propiedades (Solares, Servicios, Transporte).
    * Sistema de Subastas (implícito en la lógica de compra).
    * Cartas de Suerte y Caja de Comunidad.
    * Cárcel y Parking Gratuito.
    * Pago de impuestos.
* **Lectura de Comandos:** El juego permite la entrada de comandos manuales o la lectura automatizada desde un archivo de texto (`comandos.txt`).
* **Manejo de Excepciones:** Sistema robusto de excepciones propias para gestionar errores de juego (`DineroInsuficiente`, `AccionInvalida`, etc.).

## 🖥️ Visualización del Tablero

Una de las características más destacadas es el comando `ver tablero`, que renderiza el estado actual del juego mostrando la posición de los jugadores (ej. `&Y`, `&A`) y los edificios.

```text
[Parking      ][Solar12 &Y   ][Suerte        ][Solar13       ][Solar14       ][Trans3        ][Solar15       ][Solar16       ][Serv2         ][Solar17       ][IrACarcel     ]
[Solar11      ]                                                                                                                                               [Solar18       ]
[Solar10      ]                                                                                                                                               [Solar19       ]
[Caja         ]                                     M O N O P O L Y                                                                                           [Caja          ]
[Solar9       ]                                                                                                                                               [Solar20       ]
[Trans2       ]                                     Java Edition                                                                                              [Trans4        ]
[Solar8       ]                                                                                                                                               [Suerte        ]
[Solar7       ]                                                                                                                                               [Solar21       ]
[Serv1        ]                                                                                                                                               [Imp2          ]
[Solar6 &A    ]                                                                                                                                               [Solar22       ]
[Carcel       ][Solar5        ][Solar4        ][Suerte        ][Solar3        ][Trans1        ][Imp1          ][Solar2        ][Caja          ][Solar1       ][Salida        ]
```

##📂 Estructura del Proyecto

El código está organizado modularmente en paquetes para separar la lógica del juego, las entidades y la interfaz:
```text
MONOPOLY/
├── monopoly/
│   ├── MonopolyETSE.java      # Clase principal (Main)
│   ├── Juego.java             # Lógica central del bucle de juego
│   ├── Tablero.java           # Representación del tablero y casillas
│   ├── cartas/                # Cartas de Suerte y Caja de Comunidad
│   ├── casillas/              # Tipos de casillas (Propiedad, Impuesto, Especial...)
│   ├── edificios/             # Casas, Hoteles, Piscinas, Pistas
│   ├── excepciones/           # Excepciones personalizadas (Game Logic)
│   ├── interfaces/            # Interfaces para comandos y consola
│   └── comandos.txt           # Archivo de script de comandos
└── partida/
    ├── Jugador.java           # Gestión del jugador (dinero, propiedades)
    ├── Avatar.java            # Ficha del jugador en el tablero
    └── Dado.java              # Simulación de lanzamiento de dados
```

##🚀 Instalación y Ejecución
Requisitos previos
- Java JDK (versión 8 o superior).
- Una terminal que soporte códigos de escape ANSI (Linux/Mac soportan nativamente; en Windows se recomienda usar PowerShell, Git Bash o CMD moderno).

Compilación
Navega a la carpeta raíz del proyecto y compila los archivos Java:
```bash
javac monopoly/*.java partida/*.java monopoly/**/*.java
```
Ejecución

Para iniciar el juego, ejecuta la clase principal MonopolyETSE:
```bash
java monopoly.MonopolyETSE
```

##🎮 Comandos Básicos

El juego se controla mediante comandos de texto. Algunos de los más útiles son:

- crear jugador <nombre> <tipo_avatar>: Añade un nuevo jugador a la partida.
- ver tablero: Muestra el estado gráfico del tablero
- lanzar dados: Tira los dados para mover el avatar.
- comprar <propiedad>: Compra la casilla en la que estás situado.
- edificar <tipo>: Construye edificios (casa, hotel, piscina, pista).
- acabar turno: Pasa el turno al siguiente jugador.
- listar jugadores: Muestra información y estadísticas de los participantes.
- salir: Termina la ejecución del programa.


