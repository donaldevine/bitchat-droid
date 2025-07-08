# BitChat Android

A decentralized Bluetooth mesh messaging application that enables secure peer-to-peer communication without internet connectivity.

## Features

- **Decentralized Mesh Network**: Automatic peer discovery and multi-hop message routing using Bluetooth LE
- **End-to-End Encryption**: Military-grade encryption using libsodium (NaCl) cryptographic library
- **Store-and-Forward Messaging**: Messages persist and automatically forward when recipients come online
- **Channel Support**: Create public and private group channels for organized communication
- **Offline-First Design**: Works completely without internet connectivity or cellular service
- **Battery Optimized**: Efficient Bluetooth scanning and connection management
- **Modern UI**: Beautiful Material Design 3 interface built with Jetpack Compose

## Architecture

- **Framework**: Android with Kotlin
- **UI**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM with Repository pattern
- **Dependency Injection**: Hilt
- **Database**: Room with SQLite
- **Networking**: Bluetooth Low Energy (BLE) GATT
- **Cryptography**: Lazysodium (libsodium for Android)
- **Async Processing**: Kotlin Coroutines and Flow

## Security

BitChat implements state-of-the-art cryptographic security:

- **Public Key Cryptography**: X25519 key exchange for secure communication
- **Symmetric Encryption**: XSalsa20 stream cipher with Poly1305 authentication
- **Digital Signatures**: Ed25519 for message authenticity and non-repudiation
- **Perfect Forward Secrecy**: Ephemeral keys for long-term security
- **Secure Random**: ChaCha20-based CSPRNG for all random number generation

## Getting Started

### Prerequisites

- Android Studio Arctic Fox (2020.3.1) or later
- Android SDK API level 26 or higher
- Device with Bluetooth LE support
- Android 8.0 (API level 26) or later

### Building the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/bitchat-android.git
   cd bitchat-android
   ```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Build and run on your device:
   ```bash
   ./gradlew assembleDebug
   ```

### Permissions Required

BitChat requires the following permissions:

- `BLUETOOTH` - Basic Bluetooth functionality
- `BLUETOOTH_ADMIN` - Bluetooth administration
- `BLUETOOTH_ADVERTISE` - Bluetooth LE advertising (Android 12+)
- `BLUETOOTH_CONNECT` - Bluetooth connections (Android 12+)
- `BLUETOOTH_SCAN` - Bluetooth LE scanning (Android 12+)
- `ACCESS_FINE_LOCATION` - Required for Bluetooth LE scanning
- `FOREGROUND_SERVICE` - Background mesh networking
- `POST_NOTIFICATIONS` - Message notifications (Android 13+)

## How It Works

### Mesh Networking

1. **Discovery**: Devices continuously advertise their presence using Bluetooth LE
2. **Connection**: Nearby devices automatically establish GATT connections
3. **Routing**: Messages are intelligently routed through the mesh using optimal paths
4. **Store-and-Forward**: Messages persist until they reach their destination

### Message Flow

1. User types a message in the app
2. Message is encrypted with recipient's public key
3. Message is signed with sender's private key
4. Encrypted message is transmitted through the mesh
5. Intermediate nodes forward the message toward the destination
6. Recipient decrypts and verifies the message

### Encryption Details

- **Key Generation**: Ed25519 signing keys and X25519 encryption keys generated on first run
- **Message Encryption**: Each message encrypted with ephemeral X25519 keys
- **Channel Encryption**: Channels use shared symmetric keys for group messaging
- **Key Exchange**: Public keys exchanged during initial peer discovery

## Project Structure

```
app/src/main/java/com/bitchat/android/
├── data/
│   ├── database/          # Room database and DAOs
│   └── model/             # Data models (Message, Peer, Channel)
├── bluetooth/             # Bluetooth mesh networking
├── crypto/                # Cryptographic operations
├── di/                    # Dependency injection modules
└── ui/                    # Jetpack Compose UI components
    ├── theme/             # Material Design theming
    └── navigation/        # Navigation components
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Please ensure all code follows the project's coding standards and includes appropriate tests.

## Security Considerations

- Never commit private keys or sensitive cryptographic material
- All cryptographic operations should use the CryptographyManager
- Validate all incoming data to prevent injection attacks
- Use secure coding practices for Bluetooth operations
- Implement proper key verification for trust establishment

## Performance Optimization

- Bluetooth scanning intervals optimized for battery life
- Connection pooling for efficient peer management
- Message batching for reduced overhead
- Background processing with WorkManager
- Database queries optimized with proper indexing

## Testing

Run the test suite:

```bash
./gradlew test
./gradlew connectedAndroidTest
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [libsodium](https://libsodium.org/) - Cryptographic library
- [Lazysodium](https://github.com/terl/lazysodium-android) - Java/Android bindings for libsodium
- Android Bluetooth LE documentation and examples
- Material Design 3 design system

## Disclaimer

BitChat is designed for educational and research purposes. While we strive for security, please conduct your own security audit before using in production environments. The developers are not responsible for any security breaches or data loss.
