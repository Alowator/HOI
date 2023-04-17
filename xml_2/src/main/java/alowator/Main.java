package alowator;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, JAXBException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        ParserHandler parserHandler = new ParserHandler();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream stream = classLoader.getResourceAsStream("people.xml")) {
            saxParser.parse(stream, parserHandler);
            People people = parserHandler.getResult();
            System.out.println("Validation: " + people.validate());

            XmlWriter xmlWriter = new XmlWriter();
            xmlWriter.write(people, new FileOutputStream("result.xml"));
        }
    }
}
