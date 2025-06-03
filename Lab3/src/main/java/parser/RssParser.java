package parser;

import feed.*;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.ByteArrayInputStream;

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
import java.util.regex.*;
import java.util.*;


/* Esta clase implementa el parser de feed de tipo rss (xml)
 

    https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm,
*/

public class RssParser extends GeneralParser{

    public Feed getFeed(String feedRss) throws ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException, ParseException {


        DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();

        
        ByteArrayInputStream input = new ByteArrayInputStream(feedRss.getBytes("UTF-8"));
        

        Document xmldoc = docBuilder.parse(input);

        Element element = xmldoc.getDocumentElement();
        NodeList nList = null;
        String siteTitle = "";
        // si el feed vino en formato Atom
        if(element.getNodeName() == "feed"){
            nList = element.getChildNodes();
            for (int i=0; i< nList.getLength(); i++){
                Node n = nList.item(i);
                if (n.getNodeName() == "title"){
                    siteTitle = n.getTextContent();
                }
                
            }

        } else {
            //Traemos todos los hijos del root
            NodeList rootChilds = element.getChildNodes();
            //Nos quedamos solo con los hijos del nodo Channel
            for (int i = 0 ; i < rootChilds.getLength(); i++){
                Node n = rootChilds.item(i);
                if (n.getNodeName() == "channel"){
                    nList = n.getChildNodes();
                }
            }
            if(nList == null){
                return null;
            }
            //Extraemos el nombre del sitio
            Node tempo = nList.item(1);
            Element siteT = (Element) tempo;
            siteTitle = siteT.getTextContent();
        }

        Feed rssFeed = new Feed(siteTitle);

        //Iteramos sobre los hijos de Channel buscando los articulos

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeName() == "item" || nNode.getNodeName() == "entry") {
                Element eElement = (Element) nNode;
                String title = eElement.getElementsByTagName("title").item(0).getTextContent();
                String link = "";
                String description = "";
                Date pubDate = null;
                if(element.getNodeName()== "feed"){
                    description = this.parseContentHtml(eElement.getElementsByTagName("content").item(0).getTextContent());
                    pubDate = super.stringToDateAtom(eElement.getElementsByTagName("published").item(0).getTextContent());
                    link = ((Element) eElement.getElementsByTagName("link").item(0)).getAttribute("href");
                } else {
                    if(eElement.getElementsByTagName("description").item(0) != null){
                        description = eElement.getElementsByTagName("description").item(0).getTextContent();
                    }
                    if (eElement.getElementsByTagName("pubDate").item(0) != null){
                        pubDate = super.stringToDate(eElement.getElementsByTagName("pubDate").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("link").item(0) != null){
                        link = eElement.getElementsByTagName("link").item(0).getTextContent();
                    }
                }

                Article art = new Article (title, description, pubDate, link);
                rssFeed.addArticle(art);
            }

        }

        return rssFeed;

    }

    public static String parseContentHtml(String html) {

        Pattern pattern = Pattern.compile("<p>(.*?)</p>");
        Matcher matcher = pattern.matcher(html);

        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            sb.append(matcher.group(1).replaceAll("<[^>]+>", "")).append(" ");
        }

        String result = sb.toString().replace("&#39;", "'").trim(); 
        return result;
    }

}