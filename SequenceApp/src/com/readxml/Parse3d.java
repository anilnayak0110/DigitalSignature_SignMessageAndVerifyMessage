package com.readxml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.anilnayak.receiver.sign;

public class Parse3d {
	public static void main(String[] args) {
		Parse3d p = new Parse3d();

	}

	// ------------------
	public Parse3d() {
		Document doc = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse("src\\REQ.XML");

		} catch (Exception e) {
			System.out.println("Parsing error...");
		}
		traverse(doc);

	}

	// --------------------
	private void traverse(Node node) {
		// is there anything to do?
		if (node == null) {
			return;
		}
		int type = node.getNodeType();
		switch (type) {
		// print document
		case Node.DOCUMENT_NODE: {
			traverse(((Document) node).getDocumentElement());
			break;
		}
		case Node.ELEMENT_NODE: {
			NodeList children = node.getChildNodes();
			if (children != null) {
				int len = children.getLength();

				for (int i = 0; i < len; i++) {
					traverse(children.item(i));
				}
			} // if

			break;
		}
		// handle entity reference nodes
		case Node.ENTITY_REFERENCE_NODE: {
			NodeList children = node.getChildNodes();
			if (children != null) {
				int len = children.getLength();
				for (int i = 0; i < len; i++) {
					traverse(children.item(i));
				}
			}
			break;
		}

		case Node.CDATA_SECTION_NODE: {
			String str = node.getNodeValue().replaceAll("[\r\n]+", "");
			//System.out.println(node.getNodeValue().replaceAll("[\r\n]+", ""));
			String strWithoutSpace = null;
			int i;
			strWithoutSpace = str.replaceAll(" ", "");

			//System.out.println(strWithoutSpace);

			// 2. Without Using replaceAll() Method

			char[] strArray = str.toCharArray();

			StringBuffer sb = new StringBuffer();

			for (i = 0; i < strArray.length; i++) {
				if ((strArray[i] != ' ') && (strArray[i] != '\t')) {
					sb.append(strArray[i]);
				}
			}

			System.out.println("******"+sb.toString()+"2017-11-16T19:43:21");
			String originalText = sb.toString()+"2017-11-16T19:43:21";
			String keyStore = "MyKeys\\Oman_Trust_Store.jks";
			 String keyPass = "changeit"; 
			 String signtxt = "hIUljTwnOAyJRIvJqdorfp3pEcJoiWrS4d2Ch7H+OUBibO3RzyU/kPF/ww3yeaW+igYnYJd09vhi9j0w/z35jKEJefCW+YWmXflnLln/OrvQG4FCW5IcBiTUYAttlIEBzpKXeRwcwlN3Cp0Pm92QYtfs2TpdnvUAVTFKeiBp7El0uL7YdgPDhOKvhnwMuk7jpqxGDpjNsrwo9sFUmUmrJMoze+z2bBFzGM+WVZ17uhiflKd9TCPU62Gj8OCaBXpEAo0dNPo/v9QCxofTLrnqTMz3ThnFm0caCC+hPko+bPToJ+GC/PqdFm6Sr45iZZWlH08pdZePmyiKAQAn3BvsKA==";
			 boolean result = sign.verifySign(originalText, signtxt, keyStore, keyPass);
			 System.out.println("Result:::"+result);
			break;
		}

		case Node.TEXT_NODE: {

			Node pn = node.getParentNode();
			String pnn = pn.getNodeName();
			break;
		}

		} // switch
	} // traverse(Node)

	// --------------------
}