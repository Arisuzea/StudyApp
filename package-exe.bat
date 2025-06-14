@echo off
echo Starting Maven build...
mvn clean package || goto error

echo Running jpackage...
jpackage ^
  --input target ^
  --name StudyApp ^
  --main-jar studyapp-1.0-SNAPSHOT-shaded.jar ^
  --main-class studyapp.MainApp ^
  --type app-image ^
  --dest target/output ^
  --module-path "C:\javafx-sdk-20\lib" ^
  --add-modules javafx.controls,javafx.fxml || goto error

echo Build successful. Output in target\output\StudyApp
pause
exit /b 0

:error
echo An error occurred during build or packaging.
pause
exit /b 1
