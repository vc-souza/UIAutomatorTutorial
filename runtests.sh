#!/usr/bin/env bash

ant build
adb push bin/UIAutomatorSample.jar /data/local/tmp/
adb shell uiautomator runtest UIAutomatorSample.jar -c com.sample.tests.Tests
