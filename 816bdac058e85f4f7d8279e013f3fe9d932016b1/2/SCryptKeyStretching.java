package name.neuhalfen.projects.crypto.symmetric.keygeneration.impl.stretching;

import java.io.Serializable;
import java.util.Objects;
import org.bouncycastle.crypto.generators.SCrypt;


public class SCryptKeyStretching implements KeyStretching {

  public static SCryptKeyStretching forConfig(SCryptKeyStretchingParameters cfg) {
    return new SCryptKeyStretching(cfg);
  }

  public SCryptKeyStretching(SCryptKeyStretchingParameters cfg) {
    this.cfg = cfg;
  }


  public final static class SCryptKeyStretchingParameters implements Serializable {

    /**
     * Generate a workload factor for sensitive ( ~5 seconds in 2017) derivation with very weak
     * input key material.
     *
     * Examples would be user supplied passwords, etc.
     *
     * @return N:=2^20, r:=8, p:=1
     */
    public static SCryptKeyStretchingParameters forWeakInputKeyMaterial() {
      return new SCryptKeyStretchingParameters(1 << 20, 8, 1);
    }

    /**
     * Generate a workload factor for quick (~10ms in 2017) derivation. <p> You can use this if the
     * secret has a very high entropy but is not perfect.
     *
     * @return N:=2^12, r:=4, p:=1
     */
    public static SCryptKeyStretchingParameters forModeratelyStongInputKeyMaterial() {
      return new SCryptKeyStretchingParameters(1 << 12, 4, 1);
    }


    /**
     * Generate a workload factor for quickest (~1ms in 2017) derivation. <p> **Only use this if
     * your input key material is very good**
     *
     * Candidates for good key material are * very long, random passwords (~22 chars for a 128 bit
     * key) * passwords generated by e.g. a password manager <p> This brings effectively
     * close-to-zero protection against brute force attacks.
     *
     * @return N:=2^8, r:=4, p:=1
     */
    public static SCryptKeyStretchingParameters forStrongInputKeyMaterial() {
      return new SCryptKeyStretchingParameters(1 << 8, 4, 1);
    }

    private final int N, r, p;

    public int getN() {
      return N;
    }

    public int getR() {
      return r;
    }

    public int getP() {
      return p;
    }

    public SCryptKeyStretchingParameters(int N, int r, int p) {
      this.N = N;
      this.r = r;
      this.p = p;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      SCryptKeyStretchingParameters that = (SCryptKeyStretchingParameters) o;
      return N == that.N &&
          r == that.r &&
          p == that.p;
    }

    @Override
    public int hashCode() {
      return Objects.hash(N, r, p);
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("PBKDF2KeyStretchingParameters{");
      sb.append("N=").append(N);
      sb.append(", r=").append(r);
      sb.append(", p=").append(p);
      sb.append('}');
      return sb.toString();
    }
  }

  private final SCryptKeyStretchingParameters cfg;

  @Override
  public byte[] strengthenKey(byte[] salt, byte[] keyToStrengthen, int desiredKeyLengthInBit) {
    if (desiredKeyLengthInBit % 8 != 0) {
      throw new IllegalArgumentException("desiredKeyLengthInBit must be a multiple of 8");
    }

    final int desiredKeyLengthInBytes = desiredKeyLengthInBit / 8;
    final byte[] key = SCrypt.generate(keyToStrengthen, salt, cfg.getN(), cfg.getR(), cfg.getP(),
        desiredKeyLengthInBytes);
    return key;
  }
}
