# kotlin-workshop

Willkommen zum tarent Academy Kotlin Workshop.

Damit jeder die Workshopinhalte direkt einsetzen kann und selbst kein Kotlinprojekt aufziehen muss, kann dieses Projekt genutzt werden.

Folgende Befehle sollten zu einem erfolgreichen Build führen und die Tests ausführen. Genutzt wird dabei `Maven 3.5.0` mit einem `openJDK-11`.

    $ git clone https://github.com/DarkToast/kotlin-workshop.git
    $ cd kotlin-workshop
    $ mvn clean verify

In den einzelnen Modulen innerhalb des Projekts finden sich folgende Inhalte: 

`examples` Alle theoretischen Blöcke sind hier noch einmal, mit Kommentaren versehen, aufgelistet.  

`spring-kotlin` Ein Beispielservice, an dem wir die meiste Zeit arbeiten werden. 

`workshopspace` Ein vorgefertigtes, aber leeres, Kotlin-Mavenprojekt.

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

## Schedule
 
### 10:00 - 10:30
* Vorstellung und Agenda

### 10:30 - 11:00
* Übersicht über Kotlin
* Beispielprojekt
* Aufsetzen der Umgebung

### 11:00 - 12:00
* Hands-On! 
* Basic Syntax
* Variablen
* Methoden
* mein erster grüner Test!

### 12:00 - 13:00
PAUSE!

### 13:00 - 13.30
* Klasse
* Non- nullable types

### 13:30 - 14:30
* Hands-On!
* Wir entwickeln Domainobjekte

### 14:40 - 15:00
* Kotlin Collection-API
* Lambdas

### 15:00 - 16:00
* Hands-On!
* Wir entwickeln Logik
