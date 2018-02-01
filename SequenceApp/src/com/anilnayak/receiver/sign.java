package com.anilnayak.receiver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import com.readxml.Parse3d;

public class sign {


	 public static void main(String[] args) {
		
		//System.out.println("######"+sbb);
	String keyStore = "MyKeys\\OmanTrust.jks";
	 String keyPass = "oab"; 
	String orgtxt = "<Document xmlns="+"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.05"+"xmlns:ns20="+"http://www.Progressoft.com/ACH"+"><FIToFICstmrCdtTrf><GrpHdr><MsgId>OMAB0IVF10012</MsgId><CreDtTm>2017-11-16T19:43:21.021+07:43</CreDtTm><NbOfTxs>1</NbOfTxs><TtlIntrBkSttlmAmt Ccy="+"OMR"+">20</TtlIntrBkSttlmAmt><IntrBkSttlmDt>2017-11-19</IntrBkSttlmDt><SttlmInf><SttlmMtd>CLRG</SttlmMtd><ClrSys><Prtry>ACH</Prtry></ClrSys></SttlmInf><PmtTpInf><LclInstrm><Prtry>POS</Prtry></LclInstrm><CtgyPurp><Prtry>10</Prtry></CtgyPurp></PmtTpInf><InstgAgt><FinInstnId><BICFI>OMABOMRU</BICFI></FinInstnId><BrnchId><Id>HQ</Id></BrnchId></InstgAgt><InstdAgt><FinInstnId><BICFI>BDOFOMRU</BICFI></FinInstnId></InstdAgt></GrpHdr><CdtTrfTxInf><PmtId><EndToEndId>CLP310171116H0019251</EndToEndId><TxId>OMAB0IVF10012001</TxId></PmtId><IntrBkSttlmAmt Ccy="+"OMR"+">20.00000</IntrBkSttlmAmt><SttlmPrty>NORM</SttlmPrty><ChrgBr>SLEV</ChrgBr><Dbtr><Nm>FATMA SALIM RAJAB BAOMAR</Nm></Dbtr><DbtrAcct><Id><Othr><Id>3101060210700</Id></Othr></Id></DbtrAcct><DbtrAgt><FinInstnId><BICFI>OMABOMRU</BICFI></FinInstnId></DbtrAgt><CdtrAgt><FinInstnId><BICFI>BDOFOMRU</BICFI></FinInstnId></CdtrAgt><Cdtr><Nm>fatma salim baomar</Nm></Cdtr><CdtrAcct><Id><Othr><Id>01011010948002</Id></Othr></Id></CdtrAcct><Purp><Prtry>1003</Prtry></Purp></CdtTrfTxInf><SplmtryData><Envlp><ns20:supplementaryData><ns20:Items><ns20:Item key="+"batchSource"+">2</ns20:Item></ns20:Items></ns20:supplementaryData></Envlp></SplmtryData></FIToFICstmrCdtTrf></Document>"+"2017-11-16T19:43:21";
	String signtxt = "hIUljTwnOAyJRIvJqdorfp3pEcJoiWrS4d2Ch7H+OUBibO3RzyU/kPF/ww3yeaW+igYnYJd09vhi9j0w/z35jKEJefCW+YWmXflnLln/OrvQG4FCW5IcBiTUYAttlIEBzpKXeRwcwlN3Cp0Pm92QYtfs2TpdnvUAVTFKeiBp7El0uL7YdgPDhOKvhnwMuk7jpqxGDpjNsrwo9sFUmUmrJMoze+z2bBFzGM+WVZ17uhiflKd9TCPU62Gj8OCaBXpEAo0dNPo/v9QCxofTLrnqTMz3ThnFm0caCC+hPko+bPToJ+GC/PqdFm6Sr45iZZWlH08pdZePmyiKAQAn3BvsKA==";
	
	 }
	public static String getDigitalsign(String msgContent, String keyStore,
			String keyPass) {
		Signature sig;
		String digSignVal = null;
	//	String Test = null;
		String type = "sign";
		try {
			// String type = "sign";
			KeyStore key = loadKeyStore(new File(keyStore), keyPass, "JKS");
			sig = Signature.getInstance("SHA512withRSA");
			if (type.equalsIgnoreCase("sign")) {
				PrivateKey privateKey = (PrivateKey) key.getKey("oab",
						keyPass.toCharArray());
				// sig.initSign((PrivateKey) key.getKey(alias,
				// pass.toCharArray()));
				sig.initSign(privateKey);
				// Test = StringEscapeUtils.escapeJava(msgContent);
				// /msgContent = "hello";

				System.out.println("input message content testOAB" + msgContent);
				sig.update(StringUtils.getBytesUtf8(msgContent));
				// sig.update(msgContent.getBytes());
				// System.out.println(Base64.encodeBase64String(sig.sign()));
				digSignVal = Base64.encodeBase64String(sig.sign());

			}/*
			 * else {
			 * 
			 * sig.initVerify(key.getCertificate(alias).getPublicKey());
			 * sig.update
			 * (StringUtils.getBytesUtf8(loadfile("originalText.txt")));
			 * System.out
			 * .println(sig.verify(Base64.decodeBase64(loadfile("signedText.txt"
			 * )))); }
			 */
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("digital sign value oab testOAB" + digSignVal);
		return digSignVal;
	}
	public static Boolean verifySign(String orginalTxt,String signtTxt, String keyStore,String keyPass)
	{
		Signature sig;
		String alias = "achmqtest";
		boolean val = false;
		
		try {
			KeyStore key = loadKeyStore(new File(keyStore), keyPass, "JKS");
			sig = Signature.getInstance("SHA256withRSA");
		
			sig.initVerify(key.getCertificate(alias).getPublicKey());
			
			//String txt = loadfile("C:/IIBWorkspace/OmanArabBank/DigitalSignAndVerify_XML_OAB/src/resources/sampleXMlsignedContent.xml");
			//sig.update(StringUtils.getBytesUtf8(txt));
			
			sig.update(StringUtils.getBytesUtf16(orginalTxt));
			
			val = sig.verify(Base64.decodeBase64(signtTxt));
			//System.out.println(sig.verify(Base64.decodeBase64(signtTxt)));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return val;
	}

//	private static byte[] hexStringToByteArray(String signtTxt) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	@SuppressWarnings("deprecation")
	public static KeyStore loadKeyStore(final File keystoreFile,
			final String password, final String keyStoreType)
			throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException {
		if (null == keystoreFile) {
			throw new IllegalArgumentException("Keystore url may not be null");
		}
		final KeyStore keystore = KeyStore.getInstance(keyStoreType);
		InputStream is = null;
		try {
			is = keystoreFile.toURL().openStream();
			keystore.load(is, null == password ? null : password.toCharArray());

		} finally {
			if (null != is) {
				is.close();
			}
		}
		return keystore;
	}

	public static String loadfile(String aFileName) throws IOException {
		String contents = new String(Files.readAllBytes(Paths.get(aFileName)));

		return contents;

	}
}