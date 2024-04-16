package AllApiDtoClasses;

public final class ApiWatcherCreateDTO {
    public long deptId;
    public long courseId;

    public ApiWatcherCreateDTO(long deptId, long courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }

    public long getDeptId() {
        return deptId;
    }

    public long getCourseId() {
        return courseId;
    }
}
