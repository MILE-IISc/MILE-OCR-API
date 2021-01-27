package org.iisc.mile.ocr.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.iisc.mile.ocr.model.OcrPage;

public class MileOcrEngineService {

	private static MileOcrEngineService uniqueInstance = null;

	public static MileOcrEngineService getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new MileOcrEngineService();
		}
		return uniqueInstance;
	}

	private Unmarshaller unmarshaller = null;
	private Marshaller marshaller = null;

	private MileOcrEngineService() {
	}

	void writeXML(OcrPage ocrPage, OutputStream os) throws JAXBException {
		if (marshaller == null) {
			JAXBContext jc = JAXBContext.newInstance("org.iisc.mile.ocr.model");
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		}
		marshaller.marshal((new org.iisc.mile.ocr.model.ObjectFactory()).createPage(ocrPage), os);
	}

	OcrPage readXML(InputStream is) throws JAXBException {
		if (unmarshaller == null) {
			JAXBContext jc = JAXBContext.newInstance("org.iisc.mile.ocr.model");
			unmarshaller = jc.createUnmarshaller();
		}
		Object object = unmarshaller.unmarshal(is);
		OcrPage ocrPage = ((JAXBElement<OcrPage>) object).getValue();
		return ocrPage;
	}

	String hostName = "127.0.0.1";
	int portNumber = 8182;

	OcrPage runOcr(OcrPage ocrPage)
			throws IOException, ClassNotFoundException, InterruptedException, JAXBException {
		System.out.println("Connecting to server " + hostName + ":" + portNumber + "...");
		// establish socket connection to server
		Socket socket = new Socket(hostName, portNumber);
		System.out.println("Successfully connected to server.");
		// write to socket using ObjectOutputStream
		System.out.println("Sending data to server:");
		try {
			OutputStream os = socket.getOutputStream();
			writeXML(ocrPage, os);
			socket.shutdownOutput();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		System.out.println("Waiting for response from server ...");
		OcrPage outputPage = readXML(socket.getInputStream());
		System.out.println("Closing server connection");
		socket.close();
		System.out.println("Successfully closed server connection\n");
		return outputPage;
	}
}
