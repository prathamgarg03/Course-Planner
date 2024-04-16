package Application.Model;

import AllApiDtoClasses.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

// Manager class to manage the course offering details
public class Manager {
    private static final Map map = new Map();
    public static List<Department> departments = new ArrayList<>();

    public static void run(Path csvPath) throws IOException {
        map.setup(csvPath);
        departments = Map.departments;
    }

    public static void addOffering(Offering offering){
        Map.addOffering(offering);
        departments = Map.departments;
    }
    public List<ApiDepartmentDTO> getDepartment(){
        List<ApiDepartmentDTO> departmentDTOList = new ArrayList<>();
        for(Department department:departments){
            departmentDTOList.add(new ApiDepartmentDTO(department.getDeptId(),department.getSubject()));
        }
        return departmentDTOList;
    }

    public List<ApiCourseDTO> getCourses(long deptId){
        for(Department department:departments){
            if(department.getDeptId() == deptId){
                return convertToCourseApi(department.getCourses());
            }
        }
        return null;
    }
    public List<ApiCourseDTO> convertToCourseApi(List<Course> courses){
        List<ApiCourseDTO> courseDTOList = new ArrayList<>();
        for(Course course:courses){
            courseDTOList.add(new ApiCourseDTO(course.getCourseId(),course.getCatalogNumber()));
        }
        return courseDTOList;
    }

    public List<ApiCourseOfferingDTO> getCourseOffering(long deptID,long courseID){
        for(Department department:departments){
            if(department.getDeptId() == deptID){
                for(Course course:department.getCourses()){
                    if(course.getCourseId() == courseID){
                        return convertToCourseOfferingApi(course.getCourseOfferings());
                    }
                }
            }
        }
        return null;
    }
    public List<ApiCourseOfferingDTO> convertToCourseOfferingApi(List<CourseOffering> courseOfferings){
        List<ApiCourseOfferingDTO> courseOfferingDTOList = new ArrayList<>();
        for(CourseOffering courseOffering:courseOfferings){
            String term = getTerm(courseOffering.getSemester());
            int year = getYear(courseOffering.getSemester());
            courseOfferingDTOList.add(new ApiCourseOfferingDTO(courseOffering.getOfferingId(),courseOffering.getLocation()
            , courseOffering.getInstructors().toString(),term,courseOffering.getSemester(),year));
        }
        return courseOfferingDTOList;
    }

    public int getYear(long semester) {
        semester = semester/10;
        long divisor = 1L;
        while(semester/divisor >=10){
            divisor*=10;
        }
        semester = semester%divisor;
        return 2000+(int)semester;
    }
    public String getTerm(long semester) {
        int lastDigit = (int)(semester%10);
        if(lastDigit == 1){
            return "Spring";
        }
        else if(lastDigit == 4){
            return "Summer";
        }
        else{
            return "Fall";
        }
    }
    public List<ApiOfferingSectionDTO> getSections(long deptId,long courseId,long offeringId) {
        for (Department department : departments) {
            if (department.getDeptId() == deptId) {
                for (Course course : department.getCourses()) {
                    if (course.getCourseId() == courseId) {
                        for (CourseOffering offering : course.getCourseOfferings()) {
                            if (offering.getOfferingId() == offeringId) {
                                return convertToOfferingSectionApi(offering.getSections());
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    public List<ApiOfferingSectionDTO> convertToOfferingSectionApi(List<Section> sections){
        List<ApiOfferingSectionDTO> sectionDTOList = new ArrayList<>();
        for(Section section:sections){
            sectionDTOList.add(new ApiOfferingSectionDTO(section.getComponent(),section.getEnrollmentCap(),section.getEnrollmentTotal()));
        }
        return sectionDTOList;
    }

    public List<ApiGraphDataPointDTO> getGraph(long deptId) {
        List<ApiGraphDataPointDTO> graphList = new ArrayList<>();
        Set<Long> semesters = new HashSet<>();
        for(Department department : departments) {
            if(department.getDeptId() == deptId) {
                for(Course course: department.getCourses()) {
                    for (CourseOffering courseOffering: course.getCourseOfferings()) {
                        semesters.add(courseOffering.getSemester());
                    }

                }

            }
        }
        List<Long> semesterList = new ArrayList<>(semesters);
        Collections.sort(semesterList);
        for(Department department : departments) {
            if(department.getDeptId() == deptId) {
                for(Long semester: semesterList) {
                    long total = 0;
                    for(Course course: department.getCourses()) {
                        for (CourseOffering courseOffering: course.getCourseOfferings()) {
                            if(courseOffering.getSemester() == semester) {
                                for(Section section: courseOffering.getSections()) {
                                    if(section.getComponent().equals("LEC")) {
                                        total += section.getEnrollmentTotal();
                                    }
                                }
                            }
                        }
                    }
                    graphList.add(new ApiGraphDataPointDTO(semester, total));
                }
            }
        }
        if(graphList.isEmpty()){
            return null;
        }
        return graphList;
    }
}