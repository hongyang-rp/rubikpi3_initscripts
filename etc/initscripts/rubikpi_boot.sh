#!/bin/sh

# heartbeat led init
echo heartbeat > /sys/class/leds/green/trigger
echo 5 > /sys/class/leds/green/brightness

cat /etc/os-release | grep Ubuntu
if [ $? -ne 0 ]; then
	echo 0 > /sys/module/printk/parameters/ignore_loglevel
fi

