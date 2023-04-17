package alowator;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;

public class ParserHandler extends DefaultHandler {

    private People people;
    private Person person;
    private String expectedField = null;

    @Override
    public void startDocument() {
        people = new People();
    }

    @Override
    public void endDocument() {
        people.build();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("people")) {
            people.setCount(Integer.parseInt(attributes.getValue("count")));
        } else if (qName.equals("person")) {
            person = new Person();
            processPersonInlineAttributes(attributes);
        } else {
            setPersonFieldFromInlines(qName, attributes);
        }
    }

    private void setPersonFieldFromInlines(String qName, Attributes attributes) {
        String value = attributes.getValue("value");
        if (value == null) {
            value = attributes.getValue("val");
        }
        if (value == null) {
            value = attributes.getValue("id");
        }

        if (value != null) {
            setPersonField(qName, value);
        } else if (!qName.equals("fullname") && !qName.equals("children") && !qName.equals("siblings")) {
            expectedField = qName;
        }
    }

    private void setPersonField(String qName, String value) {
        if (value.equals("UNKNOWN") || value.equals("NONE")) {
            return;
        }
        qName = qName.replace("-", "");

        switch (qName) {
            case "parent" -> person.addParent(value);
            case "child" -> person.addChild(value);
            case "son" -> person.addSon(value);
            case "daughter" -> person.addDaughter(value);
            case "siblings" -> person.addSibling(value);
            case "brother" -> person.addBrotherName(value);
            case "sister" -> person.addSisterName(value);
            default -> {
                try {
                    Field field = person.getClass().getDeclaredField(qName);
                    field.setAccessible(true);
                    field.set(person, value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void processPersonInlineAttributes(Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String name = attributes.getQName(i);
            String value = attributes.getValue(i);
            setPersonField(name, value);

            if (!name.equals("id") && !name.equals("name")) {
                throw new RuntimeException("Unknown inline field");
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("person")) {
            person.build();
            people.add(person);
        } else if (qName.equals(expectedField)) {
            expectedField = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (expectedField != null) {
            String value = new String(ch, start, length);
            setPersonField(expectedField, value);
        }
    }

    public People getResult() {
        return this.people;
    }
}
