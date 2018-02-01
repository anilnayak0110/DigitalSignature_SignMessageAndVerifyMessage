package com.test;

import java.io.File;

public class TestVerifyXmlDigitalSignature {

    /**
     * Method used to validate an actual signed XML document
     */
    public static void testSignedXMLDoc() {
        String signedXmlFilePath = "xml" + File.separator + "REQ.xml";
        String publicKeyFilePath = "xml" + File.separator + "achmqtest.cbonet.cboroot.om_Device_CA_1_.cer";
        try {
            boolean validFlag = VerifyMessageTest.
                    isXmlDigitalSignatureValid(signedXmlFilePath, publicKeyFilePath);
            System.out.println("Validity of XML Digital Signature : " + validFlag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method used to validate a tampered signed XML document
     */
    public static void testSignedTamperedXMLDoc() {
        String signedXmlFilePath = "xml" + File.separator + "digitallytamperdEmpSal.xml";
        String publicKeyFilePath = "keys" + File.separator + "publickey.key";
        try {
            boolean validFlag = VerifyMessageTest.
                    isXmlDigitalSignatureValid(signedXmlFilePath, publicKeyFilePath);
            System.out.println("Validity of XML Digital Signature : " + validFlag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Test for Valid one
        testSignedXMLDoc();
        //Test for tampered one
       // testSignedTamperedXMLDoc();
    }
}