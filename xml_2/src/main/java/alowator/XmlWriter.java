package alowator;


import org.xml.sax.SAXException;

import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;

public class XmlWriter {
    public void write(People people, OutputStream outputStream) throws JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(People.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL schemaURL = XmlWriter.class.getResource("/people.xsd");
        Schema schema = schemaFactory.newSchema(schemaURL);

        marshaller.setSchema(schema);
        marshaller.setEventHandler(event -> {
            System.out.println("Validation error: " + event.getMessage());
            return false;
        });

        marshaller.marshal(people, outputStream);
    }
}
