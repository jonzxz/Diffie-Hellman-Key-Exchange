import java.math.BigInteger;
import java.util.Random;

public class T2 {

	private static BigInteger q;
	private static BigInteger p;
	private static BigInteger h;
	private static BigInteger g;

	//alice
	private static BigInteger a; // pvtk
	private static BigInteger yA;
	//bob
	private static BigInteger b; // pvtk
	private static BigInteger yB;

	public static void main(String[] args) {

		Random rnd = new Random();
		boolean isPSafePrime = false;
		boolean isHQualified = false;
		boolean isAQualified = false;
		boolean isBQualified = false;

		// p and q
		// repeat process of generating q until p is considered a safe prime
		while (!isPSafePrime) {
			// 100 bit prime q
			q = BigInteger.probablePrime(100, rnd);
			p = (q.multiply(BigInteger.TWO)).add(BigInteger.ONE);

			if (p.isProbablePrime(1)) isPSafePrime = true;
		}

		// h and g
		while (!isHQualified) {
			// 1 - 100 bits
			h = new BigInteger(rnd.nextInt(100)+1, rnd);
			// if h > 2 && h < p-2
			if (h.compareTo(BigInteger.TWO) == 1 && h.compareTo(p.subtract(BigInteger.TWO)) == -1) {
				isHQualified = true;
			}
		}

		g = h.modPow(BigInteger.TWO, p);

		System.out.printf("Q: %s\n", q.toString());
		System.out.printf("P: %s\n", p.toString());
		System.out.printf("H: %s\n", h.toString());
		System.out.printf("G: %s\n", g.toString());

		while (!isAQualified) {
			a = new BigInteger(rnd.nextInt(100)+1, rnd);
			if (a.compareTo(BigInteger.ONE) == 1 && a.compareTo(q.subtract(BigInteger.ONE)) == -1) {
				isAQualified = true;
			}
		}

		while (!isBQualified) {
			b = new BigInteger(rnd.nextInt(100)+1, rnd);
			if (b.compareTo(BigInteger.ONE) == 1 && b.compareTo(q.subtract(BigInteger.ONE)) == -1) {
				isBQualified = true;
			}
		}

		yA = g.modPow(a, p);
		yB = g.modPow(b, p);

		System.out.printf("A (A PvtK): %s\n", a.toString());
		System.out.printf("B (B PvtK): %s\n", b.toString());
		System.out.printf("yA (A PK) : %s\n", yA.toString());
		System.out.printf("yB (B PK) : %s\n", yB.toString());

		System.out.println("---------------------------------------");
		System.out.println("Alice will calculate the Diffie-Hellman key as follows: y_B^a = (g^b mod p)^a mod p = g^ab mod p");
		BigInteger sA = yA.modPow(b, p);
		System.out.println("Bob will calculate Diffie-Hellman key as follows      : y_A^b = (g^a mod p)^b mod p = g^ba mod p");
		BigInteger sB = yB.modPow(a, p);

		System.out.println("Alice's yB^a: " + sA.toString());
		System.out.println("Bob's yA^b  : " + sB.toString());

	}
}
