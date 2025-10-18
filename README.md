# Rainbow (Java Swing)

Simple Java Swing program that draws a rainbow.

## Build & run (Windows PowerShell)

Compile:

```powershell
javac Rainbow.java
```

Run:

```powershell
java Rainbow
```

The program opens a window showing a rainbow drawn with arcs. Close the window to exit.
 
## Upgrade to Java 21 (LTS)

If you want to run or build this project using the latest LTS (Java 21), follow these steps on Windows PowerShell.

1) Install a JDK 21 distribution (Temurin/Adoptium, Oracle, Amazon Corretto, etc.). Example via winget (optional):

```powershell
# install Temurin 21 using winget (optional)
winget install --id EclipseAdoptium.Temurin -e
```

2) Set JAVA_HOME and update PATH for the current PowerShell session (replace the path with your JDK 21 install location):

```powershell
# for the current session only - replace the path below with your install location
$env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-21'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

# Or to set JAVA_HOME persistently for your user (restart PowerShell for changes to take effect):
setx JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-21"
setx PATH "%JAVA_HOME%\bin;%PATH%"
```

3) Verify the installed Java version:

```powershell
java -version
javac -version
```

4) Compile and run this small project using Java 21 (you can use the --release flag to compile against the Java 21 API):

```powershell
javac --release 21 Rainbow.java
java Rainbow
```

Notes:
- If you maintain a build file (pom.xml or build.gradle), update the Java toolchain or source/target compatibility to 21. This project is a single-file Swing example and doesn't include build files.
- Automated upgrade tooling was not available in this environment (requires Copilot Pro/Enterprise); follow the steps above to install JDK 21 locally and then run your build or CI with Java 21.
