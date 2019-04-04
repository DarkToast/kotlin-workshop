# kotlin-workshop

Willkommen zum tarent Academy Kotlin Workshop.

Damit jeder die Workshopinhalte direkt einsetzen kann und selbst kein Kotlinprojekt aufziehen muss, kann dieses Projekt genutzt werden.

Folgende Befehle sollten zu einem erfolgreichen Build führen und die Tests ausführen. Genutzt wird dabei `Maven 3.5.0` mit einem `openJDK-11`.

    $ git clone https://github.com/DarkToast/kotlin-workshop.git
    $ cd kotlin-workshop
    $ mvn clean verify

In den einzelnen Modulen innerhalb des Projekts finden sich folgende Inhalte: 

`examples` Alle theoretischen Blöcke sind hier noch einmal, mit Kommentaren versehen, aufgelistet.  

`workshopspace` Ein vorgefertigtes, aber leeres, Kotlin-Mavenprojekt.

`spring-kotlin` Ein Beispiel, wie sich Kotlin mit Spring Boot 2 verheiraten lässt.


## Links:

Diese Links liefern Informationen zu Kotlin und den genutzten Testframeworks.

Informationen zu Kotlin, der Syntax und APIs.  
* https://kotlinlang.org/docs/reference/
* https://kotlinlang.org/docs/tutorials/getting-started-eclipse.html

Online Hand-ons. Gut, um ohne Balast Kotlin ausprobieren zu können.
* https://try.kotlinlang.org
* https://kotlinlang.org/docs/tutorials/koans.html

Informationen zu den Kotlin Testframeworks
* http://spekframework.org/docs/latest/#_overview
* https://github.com/kotlintest/kotlintest


## Ablauf

Ich werde an maximal fünf Tagen für jeweils 2,5h eine Workshopeinheit halten. Zunächst werde ich euch in 30 - 45min 
Syntax und Sprachelemente zeigen. Im Anschluss werden wir in kleinen Grüppchen oder, wer will, alleine Übungen machen.
Dazu eigenen sich Coding Dojos. Dies sind kleine vorgefertigte Problemstellungen, die man in gut einer Stunde lösen kann.

Das Hauptaugenmerk soll dabei eben nicht auf den Problemstellungen liegen, sondern darauf, etwas mit Kotlin zu machen. 
Eine Auswahl an schönen Dojos finden sich hier: http://ccd-school.de/coding-dojo/

Jeder einzelne kann aber auch sein Wunschdojo aussuchen.

## Schedule
 
### 10:00 - 10:30
* Vorstellung und Agenda

### 10:30 - 11:30
* Übersicht über Kotlin
* Beispielprojekt
* Aufsetzen der Umgebung

### 11:30 - 12:30
* Hands-On! 
* Basic Syntax
* Variablen
* Methoden
* mein erster grüner Test!

### 12:30 - 13:30
PAUSE!

### 13:30 - 14:00
* Klasse
* Non- nullable types
* Lambdas - Collection-API


### Teil 2
* Data classes
* functions
* lambdas

### Teil 3
* Custom accessors
* oop types
* sealed classs
* visibility modifier

### Teil 4
* Delegation
* Extensions
* Operators