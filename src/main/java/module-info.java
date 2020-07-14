module hwmon {
    requires java.desktop;
    requires jdk.management;
    requires static lombok;
    opens hu.hwmon.api;
}