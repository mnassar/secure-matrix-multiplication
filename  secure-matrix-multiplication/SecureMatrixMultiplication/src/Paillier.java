/**
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for 
 * more details. 
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.math.*;
import java.util.*;

/**
 * Paillier Cryptosystem <br><br>
 * References: <br>
 * [1] Pascal Paillier, "Public-Key Cryptosystems Based on Composite Degree Residuosity Classes," EUROCRYPT'99.
 *    URL: <a href="http://www.gemplus.com/smart/rd/publications/pdf/Pai99pai.pdf">http://www.gemplus.com/smart/rd/publications/pdf/Pai99pai.pdf</a><br>
 * 
 * [2] Paillier cryptosystem from Wikipedia. 
 *    URL: <a href="http://en.wikipedia.org/wiki/Paillier_cryptosystem">http://en.wikipedia.org/wiki/Paillier_cryptosystem</a>
 * @author Kun Liu (kunliu1@cs.umbc.edu)
 * @version 1.0
 */
public class Paillier {

    /**
     * p and q are two large primes. 
     * lambda = lcm(p-1, q-1) = (p-1)*(q-1)/gcd(p-1, q-1).
     */
    private BigInteger p,  q,  lambda;
    /**
     * n = p*q, where p and q are two large primes.
     */
    public BigInteger n;
    /**
     * nsquare = n*n
     */
    public BigInteger nsquare;
    /**
     * a random integer in Z*_{n^2} where gcd (L(g^lambda mod n^2), n) = 1.
     */
    private BigInteger g;
    /**
     * number of bits of modulus
     */
    private int bitLength;
    /*
     * this is part of the decryption/private key
     */
    BigInteger u;
    /**
     * Constructs an instance of the Paillier cryptosystem.
     * @param bitLengthVal number of bits of modulus
     * @param certainty The probability that the new BigInteger represents a prime number will exceed (1 - 2^(-certainty)). The execution time of this constructor is proportional to the value of this parameter.
     */
    public Paillier(int bitLengthVal, int certainty) {
        KeyGeneration(bitLengthVal, certainty);
    }

    /**
     * Constructs an instance of the Paillier cryptosystem with 512 bits of modulus and at least 1-2^(-64) certainty of primes generation.
     */
    public Paillier() {
        KeyGeneration(512, 64);
    }

    /**
     * Sets up the public key and private key.
     * @param bitLengthVal number of bits of modulus.
     * @param certainty The probability that the new BigInteger represents a prime number will exceed (1 - 2^(-certainty)). The execution time of this constructor is proportional to the value of this parameter.
     */
    public void KeyGeneration(int bitLengthVal, int certainty) {
        bitLength = bitLengthVal;
        /*Constructs two randomly generated positive BigIntegers that are probably prime, with the specified bitLength and certainty.*/
        // p = new BigInteger(bitLength / 2, certainty, new Random());
        // q = new BigInteger(bitLength / 2, certainty, new Random());
        //System.out.println(p);
        //System.out.println(q);
        p=new BigInteger("106674062652470011722999462305866409866628949683086571681608391599509776798191");
        q=new BigInteger("60102994545889252293317527974424411394173715781033466987434669891107412488401");
        n = p.multiply(q);
        nsquare = n.multiply(n);

        g = new BigInteger("2");
        lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)).divide(
                p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
        /* check whether g is good.*/
        if (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1) {
            System.out.println("g is not good. Choose g again.");
            System.exit(1);
        }
        u = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
    }

    /**
     * Encrypts plaintext m. ciphertext c = g^m * r^n mod n^2. This function explicitly requires random input r to help with encryption.
     * @param m plaintext as a BigInteger
     * @param r random plaintext to help with encryption
     * @return ciphertext as a BigInteger
     */
    public BigInteger Encryption(BigInteger m, BigInteger r) {
        return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
    }

    /**
     * Encrypts plaintext m. ciphertext c = g^m * r^n mod n^2. This function automatically generates random input r (to help with encryption).
     * @param m plaintext as a BigInteger
     * @return ciphertext as a BigInteger
     */
    public BigInteger Encryption(BigInteger m) {
        BigInteger r = new BigInteger(bitLength, new Random());
        return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);

    }

    /**
     * Decrypts ciphertext c. plaintext m = L(c^lambda mod n^2) * u mod n, where u = (L(g^lambda mod n^2))^(-1) mod n.
     * @param c ciphertext as a BigInteger
     * @return plaintext as a BigInteger
     */
    public BigInteger Decryption(BigInteger c) {
        return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n);
    }

    /**
     * main function
     * @param str intput string
     */
    public static void main(String[] str) {
        /* instantiating an object of Paillier cryptosystem*/
        Paillier paillier = new Paillier();
        /* instantiating two plaintext msgs*/
        BigInteger m1 = new BigInteger("20");
        BigInteger m2 = new BigInteger("20");
        /* encryption*/
        BigInteger em1 = paillier.Encryption(m1);
        BigInteger em2 = paillier.Encryption(m2);
        /* printout encrypted text*/
        //System.out.println(em1);
        //System.out.println(em2);
        //System.out.println(em1.multiply(em2.modPow(new BigInteger("-1"), paillier.nsquare)).mod(paillier.nsquare));
        //System.out.println(paillier.Decryption(BigInteger.ONE).toString());
        /* printout decrypted text */
        //System.out.println(paillier.Decryption(em1).toString());
        //System.out.println(paillier.Decryption(em2).toString());
        System.out.println(paillier.Decryption(new BigInteger("28512581754359024254340100999242007308363347024739618871136580172405891528998620042401831995007920232907906644111182393145338963805513033502305059027687428997535122836930054601588230915689846370360202707541235197872842079573923679459491217117212878794161586505714439276929484328807612614483975198309197911773")));

        /* test homomorphic properties -> D(E(m1)*E(m2) mod n^2) = (m1 + m2) mod n */
        BigInteger product_em1em2 = em1.multiply(em2).mod(paillier.nsquare);
        BigInteger sum_m1m2 = m1.add(m2).mod(paillier.n);
        System.out.println("original sum: " + sum_m1m2.toString());
        System.out.println("decrypted sum: " + paillier.Decryption(product_em1em2).toString());

        /* test homomorphic properties -> D(E(m1)^m2 mod n^2) = (m1*m2) mod n */
        BigInteger expo_em1m2 = em1.modPow(m2, paillier.nsquare);
        BigInteger prod_m1m2 = m1.multiply(m2).mod(paillier.n);
        System.out.println("original product: " + prod_m1m2.toString());
        System.out.println("decrypted product: " + paillier.Decryption(expo_em1m2).toString());
        //System.out.println(paillier.nsquare);
       // System.out.println("decrypted product: " + 
        //paillier.Decryption(em2).multiply(new BigInteger("-1")).add(paillier.n).toString());
       // BigInteger m11 = paillier.Decryption(paillier.Encryption(em1));
       // BigInteger m12 = paillier.Decryption(em1);
      //  System.out.println("decryption of encryption of encryption: " + m11); 
      //  System.out.println("decryption of encryption of encryption: " + m12);
    }
}