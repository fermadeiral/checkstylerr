package name.neuhalfen.projects.crypto.bouncycastle.openpgp.algorithms;


public final class DefaultPGPAlgorithmSuites {

  // no construction
  private DefaultPGPAlgorithmSuites() {
  }

  /**
   * GPG default algorithms
   */
  private final static PGPAlgorithmSuite DEFAULT_GPG = new PGPAlgorithmSuite(
      PGPHashAlgorithms.SHA1,
      PGPSymmetricEncryptionAlgorithms.CAST5,
      PGPCompressionAlgorithms.ZLIB);

  /**
   * GPG strong crypto algorithms
   */
  private final static PGPAlgorithmSuite STRONG_GPG = new PGPAlgorithmSuite(
      PGPHashAlgorithms.SHA_256,
      PGPSymmetricEncryptionAlgorithms.AES_128,
      PGPCompressionAlgorithms.ZLIB);

  public static PGPAlgorithmSuite defaultSuiteForGnuPG() {
    return DEFAULT_GPG;
  }

  public static PGPAlgorithmSuite strongSuite() {
    return STRONG_GPG;
  }

}
