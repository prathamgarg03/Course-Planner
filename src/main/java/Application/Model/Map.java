package Application.Model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

// Map class to map the course offering details
public class Map {
    private static final CSV csv = new CSV();
    private static List<Offering> offeringList = new ArrayList<>();
    public static List<Department> departments = new ArrayList<>();
    private static long deptId;
    private static long courseId;
    private static long courseOfferingId;
    public void setup(Path csvPath) throws IOException {
        offeringList = csv.loadListFromCsv(csvPath);
        map();
    }
    public static Department departmentPresent(String dept) {
        for(Department department: departments) {
            if(department.getSubject().equals(dept)) {
                return department;
            }
        }
        return null;
    }
    public static void addOffering(Offering offering){
        Department department= departmentPresent(offering.getSubject());
        if (department == null) {
            List<Course> courses = new ArrayList<>();
            List<CourseOffering> courseOfferings = new ArrayList<>();
            List<Section> sections = new ArrayList<>();
            Section section = new Section(offering.getEnrollmentTotal(),
                    offering.getEnrollmentCapacity(),
                    offering.getComponentCode());
            sections.add(section);
            sections.sort(Comparator.comparing(Section::getComponent));
            CourseOffering courseOffering = new CourseOffering(courseOfferingId++,
                    offering.getSemester(),
                    offering.getLocation(),
                    offering.getInstructors(),
                    sections);
            courseOfferings.add(courseOffering);
            courseOfferings.sort(Comparator.comparing(CourseOffering::getSemester)
                    .thenComparing(CourseOffering::getLocation));
            Course course = new Course(courseId++, offering.getCatalogNumber(), courseOfferings);
            courses.add(course);
            courses.sort(Comparator.comparing(Course::getCatalogNumber));
            departments.add(new Department(deptId++, offering.getSubject(), courses));
            departments.sort(Comparator.comparing(Department::getSubject));
        } else {
            checkCourses(department,offering);
        }
    }
    public static void map() {
        for (Offering offering : offeringList) {
            addOffering(offering);
        }
        departments.sort(Comparator.comparing(Department::getSubject));
    }

    public static Course coursePresent(String catalogNumber, List<Course> courses) {
        for(Course course: courses) {
            if(course.getCatalogNumber().equals(catalogNumber)) {
                return course;
            }
        }
        return null;
    }

    public static void checkCourses(Department department,Offering offering){
        Course course= coursePresent(offering.getCatalogNumber(),department.getCourses());
        if(course == null){
            //new course
            List<CourseOffering> courseOfferings = new ArrayList<>();
            List<Section> sections = new ArrayList<>();
            Section section = new Section(offering.getEnrollmentTotal(),
                    offering.getEnrollmentCapacity(),
                    offering.getComponentCode());
            sections.add(section);
            sections.sort(Comparator.comparing(Section::getComponent));
            CourseOffering courseOffering = new CourseOffering(courseOfferingId++,
                    offering.getSemester(),
                    offering.getLocation(),
                    offering.getInstructors(),
                    sections);
            courseOfferings.add(courseOffering);
            courseOfferings.sort(Comparator.comparing(CourseOffering::getSemester)
                    .thenComparing(CourseOffering::getLocation));
            Course newCourse = new Course(courseId++, offering.getCatalogNumber(), courseOfferings);
            department.getCourses().add(newCourse);
            department.getCourses().sort(Comparator.comparing(Course::getCatalogNumber));
        }
        else{
            checkCourseOffering(course,offering);
        }
    }
    public static CourseOffering offeringPresent(long semester,String location, List<CourseOffering> courseOfferings) {
        for(CourseOffering courseOffering: courseOfferings) {
            if(courseOffering.getLocation().equals(location) && courseOffering.getSemester() == semester) {
                return courseOffering;
            }
        }
        return null;
    }

    public static void checkCourseOffering(Course course,Offering offering){
        CourseOffering courseOffering= offeringPresent(offering.getSemester(),offering.getLocation(),course.getCourseOfferings());
        //if null create new course offering
        if(courseOffering == null){
            List<Section> sections = new ArrayList<>();
            Section section = new Section(offering.getEnrollmentTotal(),
                    offering.getEnrollmentCapacity(),
                    offering.getComponentCode());
            sections.add(section);
            sections.sort(Comparator.comparing(Section::getComponent));
            CourseOffering newCourseOffering = new CourseOffering(courseOfferingId++,
                    offering.getSemester(),
                    offering.getLocation(),
                    offering.getInstructors(),
                    sections);
            course.getCourseOfferings().add(newCourseOffering);
            course.getCourseOfferings().sort(Comparator.comparing(CourseOffering::getSemester)
                    .thenComparing(CourseOffering::getLocation));
        }
        //else add to instructor list and check on component
        else{
            // add instructor
            Set<String> newInstructors = new HashSet<>();
            newInstructors.addAll(courseOffering.getInstructors());
            newInstructors.addAll(offering.getInstructors());
            courseOffering.setInstructors(newInstructors);
            checkSections(courseOffering,offering);
        }
    }
    public static Section sectionPresent(String component,List<Section> sectionList){
        for(Section section:sectionList){
            if(section.getComponent().equals(component)){
                return section;
            }
        }
        return null;
    }
    public static void checkSections(CourseOffering courseOffering,Offering offering){
        Section section = sectionPresent(offering.getComponentCode(),courseOffering.getSections());
        if(section == null){
            Section newSection = new Section(offering.getEnrollmentTotal(),
                    offering.getEnrollmentCapacity(),
                    offering.getComponentCode());
            courseOffering.getSections().add(newSection);
            courseOffering.getSections().sort(Comparator.comparing(Section::getComponent));
        }
        else{
            section.setEnrollmentCap(section.getEnrollmentCap() + offering.getEnrollmentCapacity());
            section.setEnrollmentTotal(section.getEnrollmentTotal() + offering.getEnrollmentTotal());
        }
    }
}
