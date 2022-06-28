# kotlin-workshop

Willkommen zum Kotlin Workshop.

Damit jeder die Workshopinhalte direkt einsetzen kann und selbst kein Kotlinprojekt aufziehen muss,
soll dieses Projekt genutzt werden.

## Precheck
Folgende Befehle sollten ohne Fehler auf einem Commandprompt ausgeführt werden können, um zu sehen, dass die Entwicklungsumgebung richtig konfiguriert ist:

Wir nutzen GIT als Versionskontrollsystem. Eine Version um 2.20 oder höher sollte installiert sein:
    
    git --version 

Unser Schulungsservice wird auf einer Java Virtual Machine (JVM) laufen. Die Java Runtime sollte in der Version 17 oder höher vorliegen: 

     java --version

Um Kotlincode kompilieren zu können, wird neben der Java Virtual Machine das Java Development Kit (JDK)). Der Compiler sollte dieselbe Version wie die Runtime besitzen.

    javac --version

Eine IDE hilft eklatant bei der Entwicklung mit Kotlin. Selbstverständlich ist eine IDE immer eine persönliche Sache. Für die Schulung empfehle ich aber `IntelliJ` in der Version `2022.X`

## Setup

Folgende Befehle sollten zu einem erfolgreichen Build des Schulungsprojekt führen und die Tests ausführen. Genutzt wird dabei das Buildtool
`Gradle` in der Version `7.4.2` mit einem `openJDK-17` als JVM. Gradle muss nicht extra installiert werden. Das Projekt zieht sich
seine Buildumgebung automatisch.


Per `git clone` wird das Projekt zunächst von der Git Hostingplattform gitlab kopiert.

    git clone https://gitlab.com/christian_schmidt/kotlin-workshop

Über einen Sprung ins Projekt wird dann `gradlew` ausgeführt. Es handelt sich dabei um ein Shellskript. Bitte schauen Sie für welches Betriebssystem Sie welches Skript ausführen.

    $ cd kotlin-workshop
    $ ./gradlew clean test

Der Aufruf von `gradlew` lädt das Gradle-Builtool herunter. Im Anschluss wird `gradle` selbst ausgeführt
und lädt alle Abhängigkeiten des Repositories herunter und führt dann den Compiler und die Testsuites aus. Der Projektbuild muss mit einem `BUILD SUCCESSFULL` am Ende quittieren. Etwaige Warning und Exceptions während des Build können ignoriert werden. Dies sind Artefakte aus den Schulungsunterlagen.  

## Übersicht
In den einzelnen Modulen innerhalb des Projekts finden sich folgende Inhalte:

`examples` Alle theoretischen Blöcke sind hier noch einmal, mit Kommentaren versehen, aufgelistet.

`service` Ein Beispielservice, an dem wir die meiste Zeit arbeiten werden.

Jedes Modul hat eine feste Verzeichnisstruktur:

    ./build                 # Buildartefakte. Kompilierte Dateien, gepackte Archive. Geparste Ressourcen
    ./src                   # Alle Source-Files des Moduls
    ./src/main              # Alle produktiven Sourcefiles für den späteren Service, bzw. Programm.
    ./src/main/kotlin       # Die Kotlin-Sourcefiles
    ./src/main/resources    # Alle Resource-Files die neben Programmdateien existieren. Konfigurationsdaten, etc.
    ./src/test              # Alle Test-Sourcefiles. Hier liegen alle Unit- und Componenttests
    ./src/test/kotlin       # Die Kotlin Test-Sourcefiles
    ./src/test/resources    # Alle Test-Ressourcen. Konfiguration, etc.
    ./build.gradle          # Die Gradle-Konfiguration. Dependencies, Buildkonfiguration, etc.

## Links:

Diese Links liefern Informationen zu Kotlin und den genutzten Testframeworks.

Ein gutes GIT Cheatsheet
* https://education.github.com/git-cheat-sheet-education.pdf

Informationen zu Kotlin, der Syntax und APIs.

* https://kotlinlang.org/docs/getting-started.html
* https://kotlinlang.org/docs/reference/

Online Hand-ons. Gut, um ohne Balast Kotlin ausprobieren zu können.

* https://play.kotlinlang.org/koans/overview
* https://play.kotlinlang.org

Informationen zum genutzten Spring Boot Framework:
* https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

Informationen zu den genutzten Testframework JUnit 5:

* https://junit.org/junit5/docs/current/user-guide/

## Ablauf

Wir werden in aufeinander aufbauenden Teilen zunächst einzelne Teile von Kotlin theoretisch kennenlernen
und sie im Anschluss per Hands-On ausprobieren und einen Beispielservice nach und nach zum Leben erwecken.

## Schedule

### Willkommen
* Vorstellung und Agenda

### Basics
* Was ist die JVM? Historie / Funktion
* Was ist GIT? Crashkurs Versionskontrollsysteme
* Gemeinsames Aufsetzen der Umgebung
* IntelliJ - Kurzer Überblick der IDE

### Theorie
* Übersicht über Kotlin
* Beispielprojekt
* Basic Syntax

### Hands-On
* Mein erster grüner Test!
* Aufgabe 1 - Basics

### Theorie
* Theorie objektorientierte Programmierung
* Klassen
* Non-nullable types

### Hands-On
* Wir entwickeln Domainobjekte
* Aufgabe 2 - 4

### Theorie
* Theorie funktionale Programmierung
* Kotlin Collection-API
* Lambdas

### Hands-On
* Wir entwickeln Logik
* Aufgabe 5 - 6

### Theorie
* Grundlagen Domain Driven Design
* Hexagonale Architektur

### Hands-On
* Wir finalisieren den Service
* Aufgabe 7

### Theorie
* Übersicht Optin-Service
* Grundlagen CI/CD
* Vorstellung OpenShift/Kubernets

### Hands-On
* Wir deployen unseren Service in die private Cloud
