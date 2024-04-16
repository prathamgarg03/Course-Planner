package AllApiDtoClasses;

public class ApiDepartmentDTO {
    public long deptId;
    public String name;

    public ApiDepartmentDTO(long deptId, String name) {
        this.deptId = deptId;
        this.name = name;
    }

    public long getDeptId() {
        return deptId;
    }

    public String getName() {
        return name;
    }
}
