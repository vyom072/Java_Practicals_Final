package service;
import model.*;
import util.AnnotationValidator;
public class Lab07 {
    public static void main(String[] args) {
        Account bad = new SavingsAccount("Tej",-100,0);
        for (String error : AnnotationValidator.validate(bad)) { System.out.println(error); }
        Account good = new SavingsAccount("Riya",1000,0);
        System.out.println("Valid errors: " + AnnotationValidator.validate(good).length);
    }
}
