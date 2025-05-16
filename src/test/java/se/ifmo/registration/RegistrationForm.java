package se.ifmo.registration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationForm {
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String birthdate;
    private Boolean moveAvailable;
    private String phoneNumber;
    private  String salary;
    private  String employmentType;

    @Data
    @Builder
    public static class WorkExpirience{
        private String type;
        private String company;
        private String companySite;
        private String companyCity;
        private String companyDescription;
        private String startMonth;
        private String endMonth;
        private String startYear;
        private String endYear;

        private boolean currentlyWork;
        private String workResponsibilities;
        private String achievements;
        private boolean haveExpirience;

    }
    private WorkExpirience workExpirience;

    private String[] professionalSkills;

    private String desiredPosition;
}

