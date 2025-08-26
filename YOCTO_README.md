# Yocto BitBake Recipe for rubikpi3-initscripts

This repository contains BitBake recipe files (`.bb`) for building the `rubikpi3-initscripts` package in Yocto/OpenEmbedded build systems.

## Generated Recipe Files

1. **`rubikpi3-initscripts_1.0.1.bb`** - Uses `AUTOREV` to fetch the latest commit from the main branch
2. **`rubikpi3-initscripts_1.0.1-fixed.bb`** - Uses a fixed commit hash for reproducible builds

## Package Description

- **Package Name**: rubikpi3-initscripts
- **Version**: 1.0.1
- **License**: GPL-2.0-or-later
- **Architecture**: arm64 (RUBIK Pi 3 specific)

### What it installs:

- `/etc/initscripts/rubikpi_boot.sh` - Boot initialization script
- `/lib/systemd/system/rubikpi-boot.service` - systemd service unit file
- Automatically enables the service to start at boot

### Functionality:

The boot script performs the following actions:
- Configures the green LED with heartbeat trigger
- Sets LED brightness to level 5
- Enables kernel debug output on non-Ubuntu systems

## Usage in Yocto

### 1. Add Recipe to Your Layer

Copy one of the `.bb` files to your custom layer's recipes directory:

```bash
# For a custom layer structure like:
# meta-mylayer/
#   recipes-utils/
#     rubikpi3-initscripts/
#       rubikpi3-initscripts_1.0.1.bb

mkdir -p meta-mylayer/recipes-utils/rubikpi3-initscripts/
cp rubikpi3-initscripts_1.0.1.bb meta-mylayer/recipes-utils/rubikpi3-initscripts/
```

### 2. Add to Your Image Recipe

Add the package to your image recipe:

```bitbake
IMAGE_INSTALL:append = " rubikpi3-initscripts"
```

### 3. Configure Machine

Ensure your machine configuration is compatible. The recipe includes:

```bitbake
COMPATIBLE_MACHINE = "(^$|rubikpi3|.*arm64.*)"
```

### 4. Build

Build your image as usual:

```bash
bitbake your-image-name
```

## Recipe Details

### Dependencies

- **systemd**: Required for service management
- **Inherit**: systemd class for proper systemd integration

### Systemd Integration

- Service file: `rubikpi-boot.service`
- Auto-enabled: Service starts automatically at boot
- Target: `multi-user.target`

### Source Configuration

**Option 1 - Auto-updating (rubikpi3-initscripts_1.0.1.bb):**
```bitbake
SRC_URI = "git://github.com/hongyang-rp/rubikpi3_initscripts.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
```

**Option 2 - Fixed commit (rubikpi3-initscripts_1.0.1-fixed.bb):**
```bitbake
SRC_URI = "git://github.com/hongyang-rp/rubikpi3_initscripts.git;protocol=https;branch=main"
SRCREV = "4f3a166c57949dc4dd3139746ecfc3d901764806"
```

## Customization

### Modifying the Service

If you need to customize the systemd service behavior, you can:

1. Create a `.bbappend` file
2. Override the `do_install` function
3. Modify the service file before installation

### Machine-Specific Variants

For different hardware variants, you can:

1. Create machine-specific `.bbappend` files
2. Override `COMPATIBLE_MACHINE` for broader compatibility
3. Modify the boot script for different LED configurations

## Troubleshooting

### Build Issues

1. **Network access**: Ensure your build environment can access GitHub
2. **systemd**: Verify systemd is enabled in your distro configuration
3. **Architecture**: Check that you're building for arm64 or compatible architecture

### Runtime Issues

1. **LED paths**: Verify `/sys/class/leds/green/` exists on your target hardware
2. **Permissions**: Ensure the script has proper permissions to modify LED settings
3. **Service status**: Check service status with `systemctl status rubikpi-boot.service`

## License

This recipe is provided under GPL-2.0-or-later license, matching the source package license.