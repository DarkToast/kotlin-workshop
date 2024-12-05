## Weitere Übungen

1. Erweitern Sie den ShoppingCart Service um die Möglichkeit:
    * Produkte aus dem Warenkorb zu löschen
    * Den gesamten Warenkorb zu leeren

2. Machen Sie die Beschränkungen von `Amount`, `Money` und `ShoppingCart` konfigurierbar.
   * Der Wert der Beschränkung soll in der `application.yml` konfigurierbar sein.
   * Die Beschränkungen sollen per Flag deaktivierbar sein.

3. Fügen Sie eine neue API hinzu, um Kunden und ihre Adressen zu verwalten.
    * Ein Kunde hat eine Liste von Adressen.
    * Ein Kunde kann eine Adresse löschen.
    * Ein Kunde kann eine Adresse bearbeiten.
    * Ein Kunde kann eine neue Adresse hinzufügen.
    * Ein Kunde kann eine Adresse als Standardadresse festlegen.

4. Verknüpfen Sie den ShoppingCart Domain mit dem Customer.
    * Ein Kunde kann einen oder mehrere Warenkörbe haben.
    * Ein Warenkorb gehört immer nur zu einem Kunden.