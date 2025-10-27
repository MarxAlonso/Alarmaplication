# Alarma Inteligente Android

Este es un proyecto Android que implementa una aplicación de alarma con notificaciones interactivas. La aplicación permite configurar una alarma a una hora específica y recibir una notificación con opciones para confirmar si la tarea se completó o si debe repetirse.

## Características

- Configuración de alarmas con un `TimePicker`.
- Notificación con opciones para marcar la alarma como completada o repetirla en 1 minuto.
- Soporta permisos de notificación en Android 13 (y versiones superiores).
- Alarma exacta en dispositivos con Android 12 y superior.
- Canal de notificación para garantizar que la notificación se muestre de manera consistente.

## Funcionalidades

1. **Configuración de la Alarma:**
   - El usuario puede seleccionar la hora y minutos mediante un `TimePicker`.
   - La alarma se puede configurar para que se active a esa hora, incluso si ya ha pasado, se programa para el día siguiente.

2. **Notificación Interactiva:**
   - Cuando la alarma se activa, se muestra una notificación con dos acciones:
     - "✅ Sí" (Para confirmar que la tarea fue completada).
     - "🔁 No" (Para repetir la alarma en 1 minuto).
   
3. **Permisos:**
   - El proyecto maneja permisos para mostrar notificaciones y programar alarmas exactas en dispositivos con Android 12 o superior.

4. **Repetición de Alarma:**
   - Si el usuario no ha confirmado que la tarea fue realizada ("No"), la alarma se reprograma para sonar nuevamente en 1 minuto.

## Requisitos

- Android Studio 2021.1.1 o superior.
- Gradle 7.2 o superior.
- Android API 24 o superior (mínimo recomendado).

## Permisos Requeridos

- `POST_NOTIFICATIONS` (para mostrar notificaciones en Android 13+).
- `SCHEDULE_EXACT_ALARM` (para permitir alarmas exactas en dispositivos con Android 12+).
- `SET_ALARM` (permiso para establecer alarmas en el dispositivo).

## Instalación

### Clonar el repositorio

Puedes clonar este proyecto a tu máquina local utilizando el siguiente comando:

```bash
git clone https://github.com/MarxAlonso/Alarmaplication.git
