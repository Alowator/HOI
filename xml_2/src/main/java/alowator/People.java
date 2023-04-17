package alowator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
public class People {
    private int count;
    private final Map<String, Person> peopleById = new HashMap<>();
    private final Map<String, Person> peopleByName = new HashMap<>();

    @XmlElement(name = "person")
    private final List<Person> people = new ArrayList<>();

    public void setCount(int count) {
        this.count = count;
    }

    public void build() {
        for (Person person : peopleById.values()) {
            person.build();
            peopleByName.put(person.getName(), person);
        }

        for (Person person : peopleById.values()) {
            collectSiblings(person);
            collectParents(person);
            collectChildren(person);
            collectSpouce(person);
        }

        people.addAll(peopleById.values());
    }

    private void collectSpouce(Person person) {
        String spouceName = person.getSpouce();
        if (spouceName != null) {
            Person spouce = peopleByName.get(spouceName);

            if (spouce.getGender().toLowerCase().startsWith("f")) {
                person.setWife(spouce.getName());
                spouce.setHusband(person.getName());
            } else if (spouce.getGender().toLowerCase().startsWith("m")) {
                person.setHusband(spouce.getName());
                spouce.setWife(person.getName());
            } else {
                throw new RuntimeException("Unknown gender");
            }
        }
    }

    private void collectChildren(Person person) {
        for (String childName : person.getChildrenNames()) {
            Person child = peopleByName.get(childName);
            if (child != null) {
                if (child.getGender().toLowerCase().startsWith("f")) {
                    person.addDaughter(child);
                } else if (child.getGender().toLowerCase().startsWith("m")) {
                    person.addSon(child);
                } else {
                    throw new RuntimeException("Unknown gender");
                }

                if (person.getGender().toLowerCase().startsWith("f")) {
                    child.setMother(person.getId());
                } else if (person.getGender().toLowerCase().startsWith("m")) {
                    person.setFather(person.getId());
                } else {
                    throw new RuntimeException("Unknown gender");
                }
            }
        }
    }

    private void collectParents(Person person) {
        for (String id : person.getParents()) {
            Person parent = peopleById.get(id);
            if (parent.getGender().toLowerCase().startsWith("f")) {
                person.setMother(id);
            } else if (parent.getGender().toLowerCase().startsWith("m")) {
                person.setFather(id);
            } else {
                throw new RuntimeException("Unknown gender");
            }

            if (person.getGender().toLowerCase().startsWith("f")) {
                parent.addDaughter(person);
            } else if (person.getGender().toLowerCase().startsWith("m")) {
                parent.addSon(person);
            } else {
                throw new RuntimeException("Unknown gender");
            }
        }
    }

    private void collectSiblings(Person person) {
        for (String id : person.getSiblings()) {
            Person sibling = peopleById.get(id);
            if (sibling.getGender().toLowerCase().startsWith("f")) {
                person.addSister(sibling);
            } else if (sibling.getGender().toLowerCase().startsWith("m")) {
                person.addBrother(sibling);
            } else {
                throw new RuntimeException("Unknown gender");
            }

            if (person.getGender().toLowerCase().startsWith("f")) {
                sibling.addSister(person);
            } else if (person.getGender().toLowerCase().startsWith("m")) {
                sibling.addBrother(person);
            } else {
                throw new RuntimeException("Unknown gender");
            }
        }
    }

    public void add(Person person) {
        if (person.getId() == null && person.getName() == null) {
            throw new RuntimeException("id or name must be set");
        }

        Person originalPerson = peopleById.get(person.getId());
        Person originalPersonByName = peopleByName.get(person.getName());

        if (originalPerson == null && originalPersonByName != null) {
            person.merge(originalPersonByName);
            originalPerson = person;
        } else if (originalPerson == null) {
            originalPerson = person;
        } else {
            originalPerson.merge(person);
        }

        originalPerson.build();

        if (originalPerson.getId() != null)
            peopleById.put(originalPerson.getId(), originalPerson);
        if (originalPerson.getName() != null)
            peopleByName.put(originalPerson.getName(), originalPerson);
    }

    public boolean validate() {
        boolean sizeValidation = count == peopleById.size();
        boolean everyPersonValidation = peopleById.values().stream().allMatch(Person::validate);

        return sizeValidation;
    }
}
