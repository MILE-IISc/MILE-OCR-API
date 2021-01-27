package org.iisc.mile.ocr.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.iisc.mile.ocr.MileXmlToAltoXmlConverter;
import org.iisc.mile.ocr.model.OcrPage;
import org.iisc.mile.ocr.model.mets_alto.Alto;

@Path("/")
public class OpticalCharacterRecognitionResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHome() throws URISyntaxException {
		return Response.temporaryRedirect(new URI("openapi/ui/")).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public OcrPage runOCR(OcrPage ocrPage) throws IOException {
		if (ocrPage.isSetImageData()) {
			try {
				return MileOcrEngineService.getInstance().runOcr(ocrPage);
			} catch (IOException | ClassNotFoundException | InterruptedException | JAXBException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Path("/convert")
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Alto convertToAltoXML(OcrPage ocrPage) throws IOException {
		return MileXmlToAltoXmlConverter.createAltoXML(ocrPage);
	}

}
