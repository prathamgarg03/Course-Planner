package Application.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Offering class to store course offering details
public class Offering {
    public long semester;
    public String subject;
    public String catalogNumber;
    public String location;
    public int enrollmentCapacity;
    public int enrollmentTotal;
    public List<String> instructors;
    public String componentCode;

    public String getSubject() {
        return subject;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public long getSemester() {
        return semester;
    }

    public String getLocation() {
        return location;
    }

    public int getEnrollmentCapacity() {
        return enrollmentCapacity;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public List<String> getInstructors() {
        return instructors;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public Offering(long semester, String subject, String catalogNumber, String location, int enrollmentCapacity, int enrollmentTotal, List<String> instructors, String componentCode) {
        this.semester = semester;
        this.subject = subject;
        this.catalogNumber = catalogNumber;
        this.location = location;
        this.enrollmentCapacity = enrollmentCapacity;
        this.enrollmentTotal = enrollmentTotal;
        this.instructors = new ArrayList<>(); // Initialize instructors list
        for (String instructor : instructors) {
            Arrays.stream(instructor.split(","))
                    .map(String::trim)
                    .filter(item -> !"(null)".equals(item) && !"<null>".equals(item))
                    .forEach(this.instructors::add);
        }
        this.componentCode = componentCode;
    }
    @Override
    public String toString() {
        return "\n\tOffering\n" +
                "\t{\n" +
                "\t\tsemester = " + semester + "\n" +
                "\t\tsubject = " + subject + '\n' +
                "\t\tcatalogNumber = " + catalogNumber + '\n' +
                "\t\tlocation = " + location + '\n' +
                "\t\tenrollmentCapacity = " + enrollmentCapacity + '\n' +
                "\t\tenrollmentTotal = " + enrollmentTotal + '\n' +
                "\t\tinstructors = " + instructors + '\n' +
                "\t\tcomponentCode = " + componentCode + '\n' +
                "\t}\n";
    }

}
