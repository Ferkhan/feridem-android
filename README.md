# ***Versión de indicaciones: v1.1***

## **Indicaciones generales**

- Ante cualquier actualización de las indicaciones de este README se cambiará la versión del título
- El IDE de desarrollo es ***Android Studio (AS)***
  - Para utilizar los mismos atajos de teclado de Visual Studio Code en AS:
    - Ir a Settings
    - Ir a Plugins
    - Buscar y descargar "VSCode Keymap"
    - Ir a Keymap y seleccionar VSCode
  - El apartado de Plugins funciona de forma similar a la tienda de extensiones de VSCode, pueden
 descargar temas, extensiones y las herramientas que necesiten 
- El nombramiento de las clases se denominan en formato Pascal Case
    - En caso de que se trate de una pantalla, el nombre debe terminar en 'Activity' (PerfilUsuarioActivity)
- El nombramiento de las variables de las clases se denominan en Camel Case
- Intentar nombrar las clases, métodos, variables y recursos en español. Aunque habrá ocasiones en
  las que será necesario nombrarlas en inglés
- Las ramas se realizan en el siguiente formato:
    - Para el desarrollo de una funcionalidad "feature/nombreDeLaFuncion"
    - Para el arreglo de algún problema "hotfix/nombreDelProblema"
- Todo merge de algún feature finalizado o bugfix arreglado se debe realizar con la rama "developer"
- **Solo los merge muy relevantes se realizan sobre la rama "main"**
- La base de datos a utilizar es **SQLite**

## **Gestión de directorios principales**

- Interfaz de usuario: **UI**
  - Contiene las clases de las pantallas (Activity)
- Lógica de negocios:  **BL**
  - Contiene los archivos java que se ocupan del desarrollo de la lógica (clases, interfaces, etc)
- Base de datos:       **DB**
  - Contiene los archivos correspondientes al manejo de los datos