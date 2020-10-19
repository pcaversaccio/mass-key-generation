# Mass Public Private Key Generation (Bitcoin & Ethereum)
The address generation works by creating a new ECDSA keypair. The address can be derived from the public key. The private key allows to spend any funds sent to this address.

## Building
```bash
./gradlew assembleDist
```
will generate a `tokenapp-keygeneration-1.1.zip` and `tokenapp-keygeneration-1.1.tar` in `build/distributions`.

## Usage
The tool generates an arbitrary amount of ECDSA keypairs and saves them into two files: `secret.csv` and `public.csv`.

Unpack the application and run:

```bash
unzip tokenapp-keygeneration-1.1.zip
cd tokenapp-keygeneration/bin
./tokenapp-keygeneration/bin -n numberOfKeypairs
```

### Example
```bash
./tokenapp-keygeneration -n 1000
```
Runtime for 10'000'000 keypairs is expected to be around 2 hours on modern hardware.

## Output
The `secret.csv` contains the following fiels
* _btcPrivate_: The 32 byte private key in hex format for the bitcoin address
* _btcPublic_: The 32 byte public key in hex format for the bitcoin address
* _ethPrivate_: The 32 byte private key in hex format for the ethereum address
* _ethPublic_: The 64 byte public key in hex format for the ethereum address

The `public.csv` contains the following fields
* _btcPublic_: The 32 byte public key in hex format for the bitcoin address
* _ethPublic_: The 64 byte public key in hex format for the ethereum address