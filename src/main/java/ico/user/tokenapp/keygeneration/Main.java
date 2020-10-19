package ico.user.tokenapp.keygeneration;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.spongycastle.util.encoders.Hex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {

  public static void main(String[] args) throws IOException {
    long startTime = System.nanoTime();

    if (args.length != 2 || !args[0].equals("-n")) {
      showUsage();
      System.exit(0);
    }
    Long noOfKeys = 0L;
    try {
      noOfKeys = Long.valueOf(args[1]);
    } catch (Exception e) {
      showUsage();
      System.exit(0);
    }

    // Two files one for all information (third-party) and one with only public keys
    CSVPrinter printerSecretList = new CSVPrinter(new FileWriter(new File("secret.csv")),
        CSVFormat.DEFAULT.withHeader("btcPrivate", "btcPublic", "ethPrivate", "ethPublic"));
    CSVPrinter printerPublicList = new CSVPrinter(new FileWriter(new File("public.csv")),
        CSVFormat.DEFAULT.withHeader("btcPublic", "ethPublic"));

    for (long i=0; i < noOfKeys; i++) {
      // Print progress
      if (i % 10_000 == 0) System.out.println(String.format("%s%%", (double)Math.round((i*1000/noOfKeys)) / 10));

      // Generate keys
      org.bitcoinj.core.ECKey bitcoinKey = new org.bitcoinj.core.ECKey();
      org.ethereum.crypto.ECKey ethereumKey = new org.ethereum.crypto.ECKey();

      final String btcPrivate = Hex.toHexString(bitcoinKey.getPrivKeyBytes());
      final String btcPublic  = Hex.toHexString(bitcoinKey.getPubKey());
      final String ethPrivate = Hex.toHexString(ethereumKey.getPrivKeyBytes());
      final String ethPublic  = Hex.toHexString(ethereumKey.getPubKey());

      // Print to CSV
      printerSecretList.printRecord(btcPrivate, btcPublic, ethPrivate, ethPublic);
      printerPublicList.printRecord(btcPublic, ethPublic);
    }

    printerSecretList.close();
    printerPublicList.close();

    // Measure elapsed time
    long difference = System.nanoTime() - startTime;
    System.out.println("Total execution time: " +
        String.format("%d min, %d sec",
            TimeUnit.NANOSECONDS.toHours(difference),
            TimeUnit.NANOSECONDS.toSeconds(difference) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference))));
  }

  private static void showUsage() {
    System.out.println("Usage ./keys -n number\n\tnumber:\tNumber of key pairs to generate");
  }

}
