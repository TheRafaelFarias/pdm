package me.bristermitten.pdmlibs.pom;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class PomParser
{

    private static final DocumentBuilderFactory dbFactory;

    static
    {
        dbFactory = DocumentBuilderFactory.newInstance("com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl", null);
        dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    }

    public <T> T parse(@NotNull final ParseProcess<T> parseProcess, @NotNull final InputStream pomContent)
    {

        final Document document = getDocument(pomContent);

        return parseProcess.parse(document);
    }

    @NotNull
    public Document getDocument(@NotNull final InputStream pomContent)
    {
        try
        {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(pomContent);
            doc.normalizeDocument();

            return doc;
        }
        catch (ParserConfigurationException | SAXException | IOException e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
