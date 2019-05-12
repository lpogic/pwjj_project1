package app.model.chords;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class XMLLoader {

    private InputStream inputStream;

    public XMLLoader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public static List<TrendyChord> loadTrendyChords(InputStream inputStream)throws XMLStreamException{
        return new XMLLoader(inputStream).loadTrendyChords();
    }

    public List<TrendyChord> loadTrendyChords() throws XMLStreamException {

        List<TrendyChord> trendyChords = new ArrayList<>();
        TrendyChord proceedTrendyChord = new TrendyChord();
        String lastCharacters = "";

        Stack<StartElement> elements = new Stack<>();

        XMLEventReader eventReader = XMLInputFactory.newInstance().
                createXMLEventReader(inputStream);

        while(eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();

            switch (event.getEventType()) {

                case XMLStreamConstants.START_ELEMENT:
                    elements.push(event.asStartElement());
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    StartElement element = elements.pop();
                    switch (element.getName().getLocalPart()) {
                        case "item":
                            trendyChords.add(proceedTrendyChord);
                            if(trendyChords.size() > 5)return trendyChords;
                            proceedTrendyChord = new TrendyChord();
                            break;
                        case "chord_ID":
                            proceedTrendyChord.setId(lastCharacters);
                            break;
                        case "chord_HTML":
                            proceedTrendyChord.setHtml(lastCharacters);
                            break;
                        case "probability":
                            proceedTrendyChord.setProbability(Double.parseDouble(lastCharacters));
                            break;
                    }
                    lastCharacters = "";
                    break;
                case XMLStreamConstants.CHARACTERS:
                     lastCharacters = event.asCharacters().getData();
            }
        }

        return trendyChords;
    }


}
