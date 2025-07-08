# BitChat Android - Copilot Instructions

<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

## Project Overview

BitChat is a decentralized Bluetooth mesh messaging application for Android that enables secure peer-to-peer communication without internet connectivity. The app uses Bluetooth Low Energy (BLE) for mesh networking and implements end-to-end encryption for secure messaging.

## Architecture

- **MVVM Architecture**: Using ViewModel with LiveData/StateFlow for reactive UI
- **Jetpack Compose**: Modern declarative UI framework
- **Hilt**: Dependency injection framework
- **Room**: Local database for message persistence
- **Coroutines**: Asynchronous programming
- **Bluetooth LE**: For mesh networking and device discovery

## Key Features

1. **Mesh Networking**: Automatic peer discovery and multi-hop message routing
2. **End-to-End Encryption**: Using libsodium (Lazysodium) for cryptographic operations
3. **Store-and-Forward**: Messages persist and forward when recipients come online
4. **Channel Support**: Public and private group messaging
5. **Battery Optimization**: Efficient Bluetooth scanning and connection management
6. **Offline-First**: Works without internet connectivity

## Code Style Guidelines

- Use Kotlin coroutines for asynchronous operations
- Follow Material Design 3 guidelines for UI components
- Use dependency injection with Hilt for all managers and repositories
- Implement proper error handling and logging
- Use StateFlow for reactive state management
- Follow Android security best practices for cryptographic operations

## Important Security Considerations

- All messages must be encrypted before transmission
- Public keys should be verified through out-of-band methods when possible
- Private keys must never be transmitted or logged
- Implement perfect forward secrecy for long-term security
- Validate all incoming data to prevent injection attacks

## Bluetooth Implementation Notes

- Use GATT services for reliable data transfer
- Implement connection pooling for multiple simultaneous connections
- Handle Android permission model for Bluetooth and Location access
- Optimize scanning intervals for battery efficiency
- Implement proper cleanup to prevent memory leaks

## Testing Strategy

- Unit tests for cryptographic functions
- Integration tests for Bluetooth functionality
- UI tests with Compose testing framework
- Mock Bluetooth devices for automated testing
- Battery usage testing for optimization

When working on this project, prioritize security, battery efficiency, and user experience. Ensure all Bluetooth operations are properly handled with appropriate error checking and cleanup.
