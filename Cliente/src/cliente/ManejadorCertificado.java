package cliente;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v1CertificateBuilder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.bouncycastle.operator.bc.BcRSAContentVerifierProviderBuilder;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.encoders.Base64;

public class ManejadorCertificado {



	AlgorithmIdentifier sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find("SHA1withRSA");
	AlgorithmIdentifier digAlgId = new DefaultDigestAlgorithmIdentifierFinder().find(sigAlgId);



	/**
	 * 
	 * @return String con las lineas del certificado, x509 version 3
	 */
	public PublicKey creationYProcesamiento(KeyPair llaveAsim, OutputStream outputStream, PrintWriter pw, BufferedReader bf)
	{
		Security.addProvider(new BouncyCastleProvider());

		//System.out.println("ya agrega el provider");
		try {

			ContentSigner sigGen = new JcaContentSignerBuilder("SHA1withRSA").setProvider("BC").build(llaveAsim.getPrivate());


			byte[] publickeyb= llaveAsim.getPublic().getEncoded();//Probar
			//SubjectPublicKeyInfo subPubKeyInfo = ....;
			SubjectPublicKeyInfo subPubKeyInfo = new SubjectPublicKeyInfo( sigAlgId, publickeyb);


			Date startDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
			Date endDate = new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000);

			X509v3CertificateBuilder v3CertGen = new JcaX509v3CertificateBuilder(
					new X500Name("CN=EmisorName"), 
					BigInteger.ONE, 
					startDate, endDate, 
					new X500Name("CN=SubjectName"), 
					llaveAsim.getPublic());

			X509CertificateHolder certHolder = v3CertGen.build(sigGen);

			/**
			ContentVerifierProvider contentVerifierProvider = new BcRSAContentVerifierProviderBuilder(
					new DefaultDigestAlgorithmIdentifierFinder())
					.build(certHolder);

			if (!certHolder.isSignatureValid(contentVerifierProvider))
			{
				System.err.println("signature invalid");
			}*/


			pw.println("-----BEGIN CERTIFICATE-----");
			byte [] certificado = certHolder.getEncoded();
			//codifico en base 64
			Base64.encode(certificado, outputStream);
			pw.println("\n-----END CERTIFICATE-----");
			
			PEMParser pem = new PEMParser(bf);
			X509CertificateHolder sCert = (X509CertificateHolder) pem.readObject();
			
			X509Certificate serverCert509 = new JcaX509CertificateConverter().setProvider("BC").getCertificate(sCert);
			
			PublicKey publicKeyServer = serverCert509.getPublicKey();
			
			return publicKeyServer;

		} 
		catch (Exception e)
		{
			e.printStackTrace();

		}
		return null;

	}


}
