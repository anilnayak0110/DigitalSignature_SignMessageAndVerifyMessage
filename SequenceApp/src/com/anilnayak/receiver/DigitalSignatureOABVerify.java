package com.anilnayak.receiver;
import java.io.FileInputStream;
import java.io.IOException;

import java.security.GeneralSecurityException;

import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.xml.crypto.dsig.XMLSignature;

import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class DigitalSignatureOABVerify {
	
	public static void main(String[] args){
		String signedXMLContentFilePath = "src\\REQ.XML";
		String publicCertFilePath = "MyKeys\\OMAN_ROOT_CA_1.cer";

		
		System.out.println(verifyOABXMLContent(signedXMLContentFilePath,publicCertFilePath));
	}

	public static String verifyOABXMLContent(String signedXMLContentFilePath, String publicCertFilePath){		
		
		String verifyStatus = "Unknown";
		
		Security.addProvider(new BouncyCastleProvider());		
	 
		DigitalSignatureOABVerify signatureVerifier = new DigitalSignatureOABVerify();
		
		boolean signatureStatus = signatureVerifier.verify(signedXMLContentFilePath,publicCertFilePath);
		if(signatureStatus){
			System.out.println("Signature Verification Passed");
			verifyStatus="Passed";
		}else{
			System.out.println("Signature Verification Failed");
			verifyStatus="Failed";
		}
		
		return verifyStatus;
		
		
	}
	public boolean verify(String signedXml,String publicKeyFile) {

		boolean verificationResult = false;

		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    dbf.setNamespaceAware(true);
		    DocumentBuilder builder = dbf.newDocumentBuilder();
		    Document doc = builder.parse(signedXml);
		    
		    //NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "signature");
		    NodeList nl = doc.getElementsByTagName("signature");
			if (nl.getLength() == 0) {
				throw new IllegalArgumentException("Cannot find element");
			}

			XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

			 
			DOMValidateContext valContext = new DOMValidateContext(getCertificateFromFile(publicKeyFile).getPublicKey(), nl.item(0));
              System.out.println("*****"+valContext.getBaseURI());
			XMLSignature signature = fac.unmarshalXMLSignature(valContext);
			verificationResult = signature.validate(valContext);
			
			
		} catch (Exception e) {
			System.out.println("Error while verifying digital signature" + e.getMessage());
			e.printStackTrace();
		}

		return verificationResult;
	}

	private X509Certificate getCertificateFromFile(String certificateFile) throws GeneralSecurityException, IOException {
		FileInputStream fis = null;
		try {
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509", "BC");
			fis = new FileInputStream(certificateFile);
			return (X509Certificate) certFactory.generateCertificate(fis);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

	}
		
}
