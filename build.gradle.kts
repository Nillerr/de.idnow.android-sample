// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.1")
        classpath("com.jaredsburrows:gradle-license-plugin:0.9.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

plugins {
    kotlin("android") version "1.8.10" apply false
}
