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
Alsó sáv mérete: lxde környezetben az ikonok alsó két pixele nem látszik és emiatt fals grafikonok jelennek meg. Ennek elkerülésére csináltam az alsó sáv paramétert, amiben megadható hogy mennyi pixel megy a "kukába" - ezeket rózsaszínnel rajzolja be a program, így ha minden ikon alján megjelenik egy rózsaszín sáv, akkor az érték túl magasra lett állítva. 
* fordítás
  * `mvn clean package`
* futtatás
  * hagyományos módban: `java -Xmx32m -jar target/hwmon-1.0-SNAPSHOT.jar [alsó sáv mérete]`
  * saját rendszeren indításkor: `/usr/bin/java -jar -Xmx32m /home/bongyor/git/hwmon/target/hwmon-1.0-SNAPSHOT.jar 0`

## TODO

* windows, mac