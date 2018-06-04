# Ghost
> Tool for recording and running tasks using keyboard and mouse.

## Description
This application is written in Kotlin using TornadoFX framework for gui development based on JavaFX. The main goal is 
tracking and recording user inputs from mouse and keyboard on desktop PC and allowing to save/open/run created 
scenarios to complete repeatable actions on Windows/Linux/MacOS GUI applications.

## Build
This project uses Maven for building the application package, to build an executable JAR, run following command:

```sh
mvn package
```

Executable JAR is located in `target/ghost-1.0-SNAPSHOT-jar-with-dependecies.jar`.

## Usage
![alt text](https://raw.githubusercontent.com/Lujo5/ghost/master/resources/app-gui.png)

1. Play - when scenario is finished recording or when scenario is loaded from file, you can run it by pressing this button
2. Pause - used for pausing while recording scenario. (Execution of scenario cannot be paused)
3. Stop - stops the recording of scenario, when once stopped, scenario recording cannot be continued
4. Record - starts the recording of new scenario, previously recorded scenario will be lost if not saved before
5. Save - saves the current scenario to the file (e.g. scenario.gh)
6. Open - opens the scenario file 
7. Icons - keyboard and mouse icons are colored blue when they are making some actions (e.g. mouse movement, key pressed)
8. List - list of all recorded actions from both keyboard and mouse with details
