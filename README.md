# kotlin-workshop

Willkommen zum Kotlin Workshop.

Damit jeder die Workshopinhalte direkt einsetzen kann und selbst kein Kotlinprojekt aufziehen muss, 
soll dieses Projekt genutzt werden.

Folgende Befehle sollten zu einem erfolgreichen Build führen und die Tests ausführen. Genutzt wird dabei 
`Gradle 6.8.2` mit einem `openJDK-11`. Gradle muss nicht extra installiert werden. Das Projekt zieht sich 
seine Buildumgebung automatisch.

    $ git clone https://gitlab.com/christian_schmidt/kotlin-workshop
    $ cd kotlin-workshop
    $ ./gradlew clean test

In den einzelnen Modulen innerhalb des Projekts finden sich folgende Inhalte: 

`examples` Alle theoretischen Blöcke sind hier noch einmal, mit Kommentaren versehen, aufgelistet.  

`spring-kotlin` Ein Beispielservice, an dem wir die meiste Zeit arbeiten werden. 

## Links:

Diese Links liefern Informationen zu Kotlin und den genutzten Testframeworks.

Informationen zu Kotlin, der Syntax und APIs.  
* https://kotlinlang.org/docs/reference/
* https://kotlinlang.org/docs/tutorials/getting-started-eclipse.html

Online Hand-ons. Gut, um ohne Balast Kotlin ausprobieren zu können.
* https://try.kotlinlang.org
* https://kotlinlang.org/docs/tutorials/koans.html

Informationen zu den Kotlin Testframeworks
* https://github.com/kotlintest/kotlintest


## Ablauf

Wir werden in drei auf einander aufbauenden Teilen zunächst einzelne Teile von Kotlin theoretisch kennen lernen 
und sie im Anschluss per Hands-On ausprobieren und einen Beispielservice nach und nach zum Leben erwecken.

Nach dem Training würde ich mich über ein Feedback per Formular freuen:
https://forms.gle/bDQb5CdAVGUBzQF4A

## Schedule
 
### Theorie
* Vorstellung und Agenda
* Übersicht über Kotlin
* Beispielprojekt
* Aufsetzen der Umgebung
* Basic Syntax

### Hands-On
* Mein erster grüner Test!
* Aufgabe 1 - Basics

### Theorie
* Klassen
* Non- nullable types

### Hands-On
* Wir entwickeln Domainobjekte
* Aufgabe 2 - 4

### Theorie
* Kotlin Collection-API
* Lambdas

### Hands-On
* Wir entwickeln Logik
* Aufgabe 5 - 6

### Hands-On
* Wir finalisieren den Service
* Aufgabe 7 