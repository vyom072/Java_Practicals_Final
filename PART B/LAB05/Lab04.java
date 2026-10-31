public class Lab04 {
    public static void main(String[] args) {
        System.out.println(Validator.isValidMobile("9876543210"));
        System.out.println(Validator.isValidMobile("12345"));
        System.out.println(Validator.isValidEmail("abc@xyz.com"));
        System.out.println(Validator.isValidEmail("abc"));
        System.out.println(Validator.isValidPan("ABCDE1234F"));
        System.out.println(Validator.isValidPan("1234"));
        System.out.println(Validator.isValidIfsc("SBIN0001234"));
        System.out.println(Validator.isValidIfsc("INVALID"));
        Command c = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println(c.type() + " " + c.accountNumber() + " " + c.amount());
        System.out.println(StatementFormatter.buildStatement(new Account("Tej",1000)));
    }
}
