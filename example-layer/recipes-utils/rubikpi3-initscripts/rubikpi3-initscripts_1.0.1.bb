SUMMARY = "Init scripts for RUBIK Pi 3"
DESCRIPTION = "Custom initialization scripts for RUBIK Pi 3 board including LED heartbeat setup and kernel log level configuration"
HOMEPAGE = "https://github.com/hongyang-rp/rubikpi3_initscripts"
SECTION = "utils"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=e2a8e5d88f9fe6055c14bc92a591a8f5"

MAINTAINER = "Hongyang Zhao <hongyang.zhao@thundersoft.com>"

PV = "1.0.1"
PR = "r0"

SRC_URI = "git://github.com/hongyang-rp/rubikpi3_initscripts.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit systemd

SYSTEMD_SERVICE:${PN} = "rubikpi-boot.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

# Architecture specific - designed for arm64
COMPATIBLE_MACHINE = "(^$|rubikpi3|.*arm64.*)"

do_compile() {
    # Nothing to compile - just shell scripts and service files
    :
}

do_install() {
    # Create necessary directories
    install -d ${D}${sysconfdir}/initscripts
    install -d ${D}${systemd_system_unitdir}

    # Install the boot script
    install -m 0755 ${S}/etc/initscripts/rubikpi_boot.sh ${D}${sysconfdir}/initscripts/rubikpi_boot.sh

    # Install the systemd service file
    install -m 0644 ${S}/lib/systemd/system/rubikpi-boot.service ${D}${systemd_system_unitdir}/rubikpi-boot.service
}

FILES:${PN} = "${sysconfdir}/initscripts/rubikpi_boot.sh \
               ${systemd_system_unitdir}/rubikpi-boot.service"

RDEPENDS:${PN} = "systemd"

# Package is architecture specific (arm64) and hardware specific
PACKAGE_ARCH = "${MACHINE_ARCH}"