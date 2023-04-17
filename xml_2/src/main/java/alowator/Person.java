package alowator;

import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonType")
public class Person {
    @XmlID
    @XmlAttribute
    private String id;
    @XmlElement(name = "name", required = true)
    private String name;
    @XmlElement(name = "gender", required = true)
    private String gender;
    @XmlIDREF
    @XmlElement(name = "wife")
    private Person wifeP;
    @XmlIDREF
    @XmlElement(name = "husband")
    private Person husbandP;
    @XmlIDREF
    @XmlElement(name = "mother")
    private Person motherP;
    @XmlIDREF
    @XmlElement(name = "father")
    private Person fatherP;
    @XmlIDREF
    @XmlElementWrapper
    @XmlElement(name = "son")
    private Set<Person> sons = new HashSet<>();
    @XmlIDREF
    @XmlElementWrapper
    @XmlElement(name = "daughter")
    private Set<Person> daughters = new HashSet<>();
    @XmlIDREF
    @XmlElementWrapper
    @XmlElement(name = "sister")
    private Set<Person> sisters = new HashSet<>();
    @XmlIDREF
    @XmlElementWrapper
    @XmlElement(name = "brother")
    private Set<Person> brothers = new HashSet<>();

    private transient String wife;
    private transient String husband;
    private transient String spouce;
    private transient String mother;
    private transient String father;
    private transient String firstname;
    private transient String first;
    private transient String surname;
    private transient String family;
    private transient String familyname;
    private transient String childrennumber;
    private transient String siblingsnumber;
    private transient Set<String> childrenNames = new HashSet<>();
    private transient Set<String> sonsIds = new HashSet<>();
    private transient Set<String> daughtersIds = new HashSet<>();
    private transient Set<String> parents = new HashSet<>();
    private transient Set<String> siblings = new HashSet<>();
    private transient Set<String> sisterNames = new HashSet<>();
    private transient Set<String> brotherNames = new HashSet<>();

    public void build() {
        if (spouce != null) {
            husband = spouce;
        }
        if (first != null) {
            firstname = first;
        }
        if (familyname != null) {
            family = familyname;
        }
        if (family != null) {
            surname = family;
        }

        if (gender != null) {
            gender = gender.replaceAll("\\s+", " ").trim();
        }
        if (spouce != null) {
            spouce = spouce.replaceAll("\\s+", " ").trim();
        }

        if (name == null && firstname != null && surname != null) {
            firstname = firstname.replaceAll("\\s+", " ").trim();
            surname = surname.replaceAll("\\s+", " ").trim();
            name = firstname + " " + surname;
        }
        if (name != null) {
            name = name.replaceAll("\\s+", " ").trim();
        }
    }

    public void addChild(String value) {
        childrenNames.add(value);
    }

    public void addSon(String value) {
        sonsIds.add(value);
    }

    public void addSon(Person person) {
        sons.add(person);
    }

    public void addDaughter(String value) {
        daughtersIds.add(value);
    }

    public void addDaughter(Person person) {
        daughters.add(person);
    }

    public void addParent(String value) {
        parents.add(value);
    }

    public void addSibling(String value) {
        siblings.addAll(Arrays.asList(value.split("\\s+")));
    }

    public void addBrotherName(String value) {
        brotherNames.add(value.replaceAll("\\s+", " ").trim());
    }

    public void addBrother(Person person) {
        brothers.add(person);
    }

    public void addSisterName(String value) {
        sisterNames.add(value.replaceAll("\\s+", " ").trim());
    }

    public void addSister(Person person) {
        sisters.add(person);
    }

    public void setWife(String wife) {
        this.wife = wife;
    }

    public void setHusband(String husband) {
        this.husband = husband;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getWife() {
        return wife;
    }

    public String getHusband() {
        return husband;
    }

    public String getMother() {
        return mother;
    }

    public String getFather() {
        return father;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getChildrennumber() {
        return childrennumber;
    }

    public String getSiblingsnumber() {
        return siblingsnumber;
    }

    public Set<Person> getSons() {
        return sons;
    }

    public Set<Person> getDaughters() {
        return daughters;
    }

    public Set<String> getChildrenNames() {
        return childrenNames;
    }

    public Set<String> getParents() {
        return parents;
    }

    public Set<String> getSiblings() {
        return siblings;
    }

    public Set<String> getSisterNames() {
        return sisterNames;
    }

    public Set<String> getBrotherNames() {
        return brotherNames;
    }

    public String getSpouce() {
        return spouce;
    }

    public void merge(Person person) {
        id = id == null ? person.getId() : id;
        name = name == null ? person.getName() : name;
        gender = gender == null ? person.getGender() : gender;
        wife = wife == null ? person.getWife() : wife;
        husband = husband == null ? person.getHusband(): husband;
        mother = mother == null ? person.getMother() : mother;
        father = father == null ? person.getFather() : father;
        firstname = firstname == null ? person.getFirstname() : firstname;
        surname = surname == null ? person.getSurname() : surname;
        childrennumber = childrennumber == null ? person.getChildrennumber() : childrennumber;
        siblingsnumber = siblingsnumber == null ? person.getSiblingsnumber() : siblingsnumber;

        parents.addAll(person.getParents());
        siblings.addAll(person.getSiblings());
        brotherNames.addAll(person.getBrotherNames());
        sisterNames.addAll(person.getSisterNames());
        childrenNames.addAll(person.getChildrenNames());
        daughters.addAll(person.getDaughters());
        sons.addAll(person.getSons());
    }

    public void setMother(String id) {
        this.mother = id;
    }

    public void setFather(String id) {
        this.father = id;
    }

    public boolean validate() {
        boolean res = true;
        if (siblingsnumber != null) {
            res = Integer.parseInt(siblingsnumber) == brotherNames.size() + sisterNames.size();
        }
        if (childrennumber != null) {
            res &= Integer.parseInt(childrennumber) == daughters.size() + sons.size();
        }
        return res;
    }


}
