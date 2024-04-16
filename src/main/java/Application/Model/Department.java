package Application.Model;

import java.util.List;

// Department class to store department details
public class Department {
    long deptId;
    String subject;
    List<Course> courses;

    public long getDeptId() {
        return deptId;
    }

    public String getSubject() {
        return subject;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Department(long deptId, String subject, List<Course> course) {
        this.deptId = deptId;
        this.subject = subject;
        this.courses = course;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", subject='" + subject + '\'' +
                ", courses=" + courses +
                '}';
    }
}
