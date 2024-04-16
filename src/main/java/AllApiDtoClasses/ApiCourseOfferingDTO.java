package AllApiDtoClasses;

public class ApiCourseOfferingDTO {
    public long courseOfferingId;
    public String location;
    public String instructors;
    public String term;
    public long semesterCode;
    public int year;

    public long getCourseOfferingId() {
        return courseOfferingId;
    }

    public long getSemesterCode() {
        return semesterCode;
    }

    public String getLocation() {
        return location;
    }

    public String getInstructors() {
        return instructors;
    }

    public String getTerm() {
        return term;
    }

    public int getYear() {
        return year;
    }

    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }

    public ApiCourseOfferingDTO(long courseOfferingId, String location, String instructors, String term, long semesterCode, int year) {
        this.courseOfferingId = courseOfferingId;
        this.location = location;
        this.instructors = instructors;
        this.term = term;
        this.semesterCode = semesterCode;
        this.year = year;
    }

}
