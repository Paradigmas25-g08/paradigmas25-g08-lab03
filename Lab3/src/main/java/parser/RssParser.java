package parser;

import feed.*;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.text.ParseException;

import java.util.Date;
import java.io.ByteArrayInputStream;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 

    https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm,
*/

public class RssParser extends GeneralParser{

    public Feed getFeed(String feedRss) throws ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException, ParseException {


        DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();

        StringBuilder xmlBuilder = new StringBuilder(); 
        xmlBuilder.append(feedRss);
        ByteArrayInputStream input = new ByteArrayInputStream(xmlBuilder.toString().getBytes("UTF-8"));
        

        Document xmldoc = docBuilder.parse(input);

        Element element = xmldoc.getDocumentElement();

        //Traemos todos los hijos del root
        NodeList rootChilds = element.getChildNodes();
        //Nos quedamos solo con los hijos del nodo Channel
        NodeList nList = rootChilds.item(1).getChildNodes();
        //Extraemos el nombre del sitio
        Node tempo = nList.item(1);
        Element siteT = (Element) tempo;
        String siteTitle = siteT.getTextContent();
        Feed rssFeed = new Feed(siteTitle);

        //Iteramos sobre los hijos de Channel buscando los articulos
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeName() == "item") {
                Element eElement = (Element) nNode;
                String title = eElement.getElementsByTagName("title").item(0).getTextContent();
                String description = eElement.getElementsByTagName("description").item(0).getTextContent();
                Date pubDate = super.stringToDate(eElement.getElementsByTagName("pubDate").item(0).getTextContent());
                String link = eElement.getElementsByTagName("link").item(0).getTextContent();

                Article art = new Article (title, description, pubDate, link);
                rssFeed.addArticle(art);
            }

        }

        return rssFeed;

    }

}