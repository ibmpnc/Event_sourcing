package com.example.bank.validator;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckDateValidator implements ConstraintValidator<CheckDateFormat, String> {

    private String pattern;
    protected Logger logger = Logger.getLogger(CheckDateValidator.class.getName());

    @Override
    public void initialize(CheckDateFormat constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {


    	if ( object == null ) {
            return true;
        }
     	DateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);
       try {
           sdf.parse(object);
            return true;
        } catch (Exception e) {
           return false;
        }
    }
    
    
    
    public static void main(String [] args) {
    	
    	String pattern="MMddyyyy";
    	DateFormat sdf = new SimpleDateFormat(pattern);
        try {
            sdf.parse("1980-12-12");
            System.out.println("valid");
        } catch (ParseException e) {
            System.out.println("invalid");
        }
    }
}

