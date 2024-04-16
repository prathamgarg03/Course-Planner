package AllApiDtoClasses;
import java.util.List;

public class ApiWatcherDTO {
    public long id;
    public ApiDepartmentDTO department;
    public ApiCourseDTO course;
    public List<String> events;

    public ApiWatcherDTO() {
    }

    public ApiWatcherDTO(long id, ApiDepartmentDTO department, ApiCourseDTO course, List<String> events) {
        this.id = id;
        this.department = department;
        this.course = course;
        this.events = events;
    }

    public long getId() {
        return id;
    }

    public ApiDepartmentDTO getDepartment() {
        return department;
    }

    public ApiCourseDTO getCourse() {
        return course;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }
}
