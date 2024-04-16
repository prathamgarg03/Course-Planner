package Application.Controller;

import AllApiDtoClasses.*;
import Application.Model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

//Rest Controller class
@RestController
public class Controller {
    Manager manager = new Manager();
    List<ApiWatcherDTO> watcherList = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong();

    @GetMapping("/api/about")
    @ResponseStatus(HttpStatus.OK)
    public ApiAboutDTO about() {
        return new ApiAboutDTO("Assignment 5 : Course Planner", "By: Ruben Dua and Pratham Garg");
    }

    @GetMapping("/api/dump-model")
    @ResponseStatus(HttpStatus.OK)
    public String dumpModel() {
        return printModel();
    }

    @GetMapping("/api/departments")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiDepartmentDTO> getAllDepartments() {
        return manager.getDepartment();
    }

    @GetMapping("/api/departments/{deptId}/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiCourseDTO> getAllCourses(@PathVariable("deptId") long deptId) {
        List<ApiCourseDTO> courses = manager.getCourses(deptId);
        if(courses == null){
            throw new IllegalArgumentException();
        }
        return courses;
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseID}/offerings")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiCourseOfferingDTO> getCourseOfferings(@PathVariable("deptId") long deptId,
                                                         @PathVariable("courseID") long courseID) {
        List<ApiCourseOfferingDTO> courseOfferingDTOList = manager.getCourseOffering(deptId,courseID);
        if(courseOfferingDTOList == null){
            throw new IllegalArgumentException();
        }
        return courseOfferingDTOList;

    }
    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings/{offeringId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiOfferingSectionDTO> getSections(@PathVariable("deptId") long deptId,
                                                   @PathVariable("courseId") long courseID,
                                                   @PathVariable("offeringId") long offeringId){

        List<ApiOfferingSectionDTO> sectionDTOList = manager.getSections(deptId, courseID, offeringId);
        if (sectionDTOList == null) {
            throw new IllegalArgumentException();
        }
        return sectionDTOList;
    }

    @GetMapping("/api/stats/students-per-semester")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiGraphDataPointDTO> drawGraph(@RequestParam(value = "deptId", required = true) long id){
        List<ApiGraphDataPointDTO> graphDataPointDTOS =  manager.getGraph(id);
        if(graphDataPointDTOS == null){
            throw new IllegalArgumentException();
        }
        return graphDataPointDTOS;
    }
    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiOfferingSectionDTO addOffering(@RequestBody ApiOfferingDataDTO offeringDataDTO){

        long semester = Long.parseLong(offeringDataDTO.getSemester());
        String subjectName = offeringDataDTO.getSubjectName();
        String catalogNumber = offeringDataDTO.getCatalogNumber();;
        String location = offeringDataDTO.getLocation();
        int enrollmentCap = offeringDataDTO.getEnrollmentCap();
        int enrollmentTotal = offeringDataDTO.getEnrollmentTotal();
        String instructor = offeringDataDTO.getInstructor();
        String component = offeringDataDTO.getComponent();


        Offering newOffering = new Offering(semester,subjectName,catalogNumber,
                location,enrollmentCap,enrollmentTotal,List.of(instructor),component);
        Manager.addOffering(newOffering);

        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        String formattedDate = format.format(currentDate);
        int year = manager.getYear(semester);
        String term = manager.getTerm(semester);

        for(ApiWatcherDTO watcher:watcherList){
            if(watcher.getDepartment().getName().equals(subjectName)
                    && watcher.getCourse().getCatalogNumber().equals(catalogNumber)){
                String event = formattedDate+": Added section "+ component+ " with enrollment ("+ enrollmentTotal
                        +"/"+enrollmentCap+") to offering "+term+" "+year;
                watcher.getEvents().add(event);
            }
        }
        ApiOfferingSectionDTO section = new ApiOfferingSectionDTO(component,enrollmentCap,enrollmentTotal);
        return section;
    }

    @GetMapping("/api/watchers")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiWatcherDTO> getAllWatchers(){
        return watcherList;
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiWatcherDTO addWatcher(@RequestBody ApiWatcherCreateDTO newWatcherCreate){
        ApiDepartmentDTO department = getDepartment(newWatcherCreate.getDeptId());
        ApiCourseDTO course = getCourse(newWatcherCreate.getCourseId(), newWatcherCreate.getDeptId());
        if(department == null || course == null){
            throw new IllegalArgumentException();
        }
        List<String> events = new ArrayList<>();
        ApiWatcherDTO newWatcher = new ApiWatcherDTO(nextId.incrementAndGet(),department,course,events);
        watcherList.add(newWatcher);
        return newWatcher;
    }

    @GetMapping("/api/watchers/{watcherID}")
    @ResponseStatus(HttpStatus.OK)
    public ApiWatcherDTO getWatcher(@PathVariable("watcherID") long watcherID){
        for(ApiWatcherDTO watcher:watcherList){
            if(watcher.getId() == watcherID){
                return watcher;
            }
        }
        throw new IllegalArgumentException();
    }
    @DeleteMapping("/api/watchers/{watcherID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWatcher(@PathVariable("watcherID") long watcherID) {
        Iterator<ApiWatcherDTO> iterator = watcherList.iterator();
        while (iterator.hasNext()) {
            ApiWatcherDTO watcher = iterator.next();
            if (watcher.getId() == watcherID) {
                iterator.remove();
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request ID not found.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {
        // Nothing to do
    }

    public ApiDepartmentDTO getDepartment(long deptId) {
        for (ApiDepartmentDTO departmentDTO : manager.getDepartment()) {
            if (departmentDTO.getDeptId() == deptId) {
                return departmentDTO;
            }
        }
        return null;
    }

    public ApiCourseDTO getCourse(long courseID,long deptID) {
        List<ApiCourseDTO> courseDTOList = manager.getCourses(deptID);
        if(courseDTOList == null){
            return null;
        }
        for (ApiCourseDTO courseDTO :courseDTOList ) {
            if (courseDTO.getCourseId() == courseID) {
                return courseDTO;
            }
        }
        return null;
    }

    public String printModel() {
        for(Department department: Manager.departments) {
            for(Course course: department.getCourses()) {
                System.out.println(department.getSubject() + " " + course.getCatalogNumber());
                for(CourseOffering courseOffering: course.getCourseOfferings()) {
                    System.out.println("\t" +
                            courseOffering.getSemester() +
                            " in " + courseOffering.getLocation() +
                            " " + courseOffering.getInstructors());
                    for(Section section: courseOffering.getSections()) {
                        System.out.println("\t\tTYPE = " +
                                section.getComponent() +
                                ", Enrollment = " +
                                section.getEnrollmentTotal() +
                                "/" +
                                section.getEnrollmentCap());
                    }
                }
            }
        }
        return "Model dumped successfully.";
    }

}
