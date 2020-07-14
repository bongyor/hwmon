# hwmon
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

* hálózat használat, disk io grafikonok