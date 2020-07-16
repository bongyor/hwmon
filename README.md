# hwmon

![Java CI with Maven](https://github.com/bongyor/hwmon/workflows/Java%20CI%20with%20Maven/badge.svg)

Rendszer monitor, ami pici aranyos ikonokat rajzol a taskbarra a cpu és memória használatról

## Működés

* shell hívásokkal összegyűjti a memória és processzor használati adatokat
* az adatok alapján ikonokat rajzol a taskbarra

## Rendszerkövetelmények

* java 11
* maven

## Fordítás, futtatás

* fordítás
  * `mvn clean package`
* futtatás
  * modulos módban: `java -Xmx10m --module-path target/hwmon-1.0-SNAPSHOT.jar --module hwmon/hu.hwmon.api.Main`
  * hagyományos módban: `java -Xmx10m -jar target/hwmon-1.0-SNAPSHOT.jar`

## TODO

* /proc resource használata mindenhol
* windows?
* Olyan tray ikon megoldás keresése, ami nem villog az ikon frissítésnél
    * Eddigi próbálkozások
        * java.awt.TrayIcon (ez van most)
            * villog és nem is lehet vele mit csinálni
        * https://github.com/dorkbox/SystemTray
            * egyetlen ikont tud kezelni alkalmazásonként
            * csak 64 karakteres tooltip-et engedélyez 