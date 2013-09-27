package edu.sc.seis.seisFile.fdsnws.quakeml;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import edu.sc.seis.seisFile.SeisFileException;
import edu.sc.seis.seisFile.fdsnws.FDSNEventQuerier;
import edu.sc.seis.seisFile.fdsnws.stationxml.StationXMLException;

public class USGSExampleTest {

    @Test
    public void test() throws IOException, SeisFileException, XMLStreamException, StationXMLException, SAXException {
        String[] filenames = new String[] {"usgs/B000I68313Short.quakeml",
                                           "usgs/201330/201330_1374538994_C000IRGM_24_Long.quakeml_Verified"};
        for (String filename : filenames) {
            try {
                URL url = QuakeMLTest.loadResourceURL(filename);
                XMLInputFactory factory = XMLInputFactory.newInstance();
                XMLEventReader r = factory.createXMLEventReader(url.toString(), QuakeMLTest.loadResource(filename));
                FDSNEventQuerier.validateQuakeML(factory.createXMLStreamReader(url.toString(),
                                                                               QuakeMLTest.loadResource(filename)));
                Quakeml qml = new Quakeml(r);
                assertTrue("sceham version", qml.checkSchemaVersion());
                EventParameters ep = qml.getEventParameters();
                EventIterator it = ep.getEvents();
                while (it.hasNext()) {
                    System.out.println(it.next());
                }
            } catch(Exception ex) {
                ex.printStackTrace(System.err);
                Throwable subEx = ex;
                while (subEx.getCause() != null) {
                    subEx = subEx.getCause();
                }
                if (subEx instanceof SAXParseException) {
                    SAXParseException saxEx = (SAXParseException)subEx;
                    fail("SAX Trouble with " + filename + " at " + saxEx.getLineNumber() + ":"
                            + saxEx.getColumnNumber()+" "+ex);
                } else {
                    fail("Trouble with " + filename+" "+ex);
                }
            }
        }
    }
}
