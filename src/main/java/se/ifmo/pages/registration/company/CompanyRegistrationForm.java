package se.ifmo.pages.registration.company;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyRegistrationForm {
    private String companyName;
    private CompanyType companyType;

}
