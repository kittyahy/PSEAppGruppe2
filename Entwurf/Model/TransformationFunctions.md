# How To create a Data Transformation

## Prerequisites
    - Was für einen Graph will ich erzeugen (PieChart, LineChart)?
    - Welche Spalten in meiner Tabelle will ich in meinem Graph darstellen?
    - Welche Datentypen sind in meiner Tabelle (bzw. in den Spalten, die ich der Transformation übergebe)?

## PieChart, nur numerische Datentypen
    1. FloatSum erstellen
        a) Die Spalten angeben, die du darstellen möchtest
    2. PieChartTransformation erstellen, die eben erstellte Summe übergeben
    3. DataTransformation<Float> erstellen mit der Tabelle des Projektes und der PieChartTransformation

    * ACHTUNG: FloatSum schmeißt Exceptions wenn nicht numerische Werte in den Spalten enthalten sind, die du übergibst!

## PieChart, nicht-numerische Datentypen: Geht momentan nicht

## LineChart<T>, nur numerische Datentypen (T ist der DatenTyp auf der X-Achse)
    1. FloatIdentity erstellen
    2. LineChartTransformation<T> erstellen, die eben erstellte Identity übergeben, sowie die Spalte mit den X-Werten (vom Typ T)
    3. DataTransformation<Float> erstellen mit der Tabelle des Projektes und der LineChartTransformation

    * ACHTUNG: FloatIdentity schmeißt Exception wenn nicht numerische Werte in den Spalten enthalten sind
    * Momentan unterstützte Datentypen für T: Int, Float, LocalDateTime