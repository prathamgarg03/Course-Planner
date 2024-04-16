package Application.Model;

import java.util.List;

// Course class to store course details
public class Course {
    long courseId;
    String catalogNumber;
    List<CourseOffering> courseOfferings;

    public Course(long courseId, String catalogNumber, List<CourseOffering> courseOfferings) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
        this.courseOfferings = courseOfferings;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public List<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", catalogNumber='" + catalogNumber + '\'' +
                ", courseOfferings=" + courseOfferings +
                '}';
    }
}
