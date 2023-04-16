package alowator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Person {
    private String id;
    private String name;
    private String gender;
    private String wife;
    private String husband;
    private transient String spouce;
    private String mother;
    private String father;
    private String firstname;
    private transient String first;
    private String surname;
    private transient String family;
    private transient String familyname;
    private String childrennumber;
    private String siblingsnumber;
    private transient Set<String> childrenNames = new HashSet<>();
    private transient Set<String> sons = new HashSet<>();
    private transient Set<String> daughters = new HashSet<>();
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
        sons.add(value);
    }

    public void addDaughter(String value) {
        daughters.add(value);
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

    public void addSisterName(String value) {
        sisterNames.add(value.replaceAll("\\s+", " ").trim());
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

    public Set<String> getSons() {
        return sons;
    }

    public Set<String> getDaughters() {
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
