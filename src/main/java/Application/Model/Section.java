package Application.Model;

// Section class to store section details
public class Section {
    int enrollmentTotal;
    int enrollmentCap;
    String component;

    public Section(int enrollmentTotal, int enrollmentCap, String component) {
        this.enrollmentTotal = enrollmentTotal;
        this.enrollmentCap = enrollmentCap;
        this.component = component;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public String getComponent() {
        return component;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    @Override
    public String toString() {
        return "Section{" +
                "enrollmentTotal=" + enrollmentTotal +
                ", enrollmentCap=" + enrollmentCap +
                ", component='" + component + '\'' +
                '}';
    }
}
