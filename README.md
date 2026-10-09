# Reclutamiento RRHH – Registro, Login e Inventario de Aspirantes

Java with Ant · JDBC (MySQL) · JavaFX + Scene Builder · BCrypt · DTOs · SceneManager · inyección de dependencias por constructor.

## Estructura
```
src/main/java/com/kinal/reclutamiento
  Main.java            raíz de composición (inyección de dependencias)
  config/              DatabaseConnection (JDBC)
  model/               entidades (Rol, Usuario, Aspirante)
  dto/                 DTOs que viajan entre servicio y controlador
  exception/           AppException, AuthException, ValidacionException, PersistenciaException
  repository/          interfaces + implementaciones JDBC (PreparedStatement)
  service/             lógica de negocio, validaciones, BCrypt
  controller/          Login, Registro, Inventario
  view/                SceneManager, Vista, AlertUtil, SesionUsuario
  util/                PasswordHasher (BCrypt), Validador
src/main/resources/view/*.fxml   vistas (abrir con Scene Builder)
sql/schema.sql                   base de datos normalizada (rol, usuario, aspirante)
```

## Cómo ejecutarlo
Requisitos: JDK 17 o superior, Apache Ant y MySQL/MariaDB (XAMPP sirve).

1. Ejecute `sql/schema.sql` en MySQL/MariaDB (crea `reclutamiento_db`, las 3 tablas y los roles USER y ADMIN).
2. Si su clave de root no está vacía, cámbiela en `src/main/resources/db.properties`.
3. En la carpeta del proyecto: `ant run`
   - La primera vez descarga JavaFX automáticamente en `lib/javafx` (necesita internet).
   - Si ya tiene el JavaFX SDK: `ant -Djavafx.lib=RUTA/DEL/SDK/lib run`

Ya incluidos en el proyecto: BCrypt (`src/main/java/org/mindrot/jbcrypt`) y el driver JDBC (`lib/mariadb-java-client-2.7.6.jar`, compatible con MySQL y MariaDB).

Los usuarios se crean desde "Crear una cuenta" (la contraseña se guarda con BCrypt).

## En Apache NetBeans
1. `File > Open Project...` y seleccione la carpeta `reclutamiento-app` (se reconoce como proyecto).
2. Verifique que NetBeans use JDK 17 o superior (`Tools > Java Platforms`).
3. `Run > Clean and Build Project` (Shift+F11) compila; `Run > Run Project` (F6) lo ejecuta.
   La primera vez descarga JavaFX en `lib/javafx` (necesita internet); si algo aparece en rojo antes de eso, desaparece tras el primer Build.
4. Recuerde ejecutar antes `sql/schema.sql` en su MySQL/MariaDB.
