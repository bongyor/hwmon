# hwmon

![Java CI with Maven](https://github.com/bongyor/hwmon/workflows/Java%20CI%20with%20Maven/badge.svg)

Rendszer monitor, ami pici aranyos ikonokat rajzol a taskbarra a cpu és memória használatról

## Működés

* shell hívásokkal összegyűjti a memória és processzor használati adatokat
* az adatok alapján ikonokat rajzol a taskbarra

## Rendszerkövetelmények

* java 17
* maven

## Fordítás, futtatás

* fordítás
  * `mvn clean package`
* futtatás
  * hagyományos módban: `java -Xmx32m -jar target/hwmon-1.0-SNAPSHOT.jar`
  * saját rendszeren indításkor: `/usr/bin/java -jar -Xmx32m /home/bongyor/git/hwmon/target/hwmon-1.0-SNAPSHOT.jar`

## TODO

* windows, mac
### GraalVM natív fordítás
Előfeltételek linuxon: `sudo apt install libxi-dev libxrender-dev libfreetype-dev`

Akadály: https://github.com/oracle/graal/issues/4594