MiniBank requires JDK 17 or newer. Keep the package folders model, model/annotation, util, service and exception together. Retain the files from Practicals 6 to 13 and apply the additions and replacements from Practical 14. Earlier LabNN demo classes may remain; run service.MiniBank for the final application.

Build:
javac -d out exception/*.java model/annotation/*.java model/*.java util/*.java service/*.java
jar cfm minibank.jar manifest.mf -C out .

Run:
java -jar minibank.jar

Choose 1 to open an account. Enter SAVINGS, CURRENT or FD, a nonblank name of at most 30 characters, a valid email, a 10-digit mobile starting 6 to 9, PAN ABCDE1234F, IFSC SBIN0001234, and a positive opening balance. Savings uses a minimum balance of 0; current allows an overdraft of 1000; fixed deposit withdrawals are locked.

Options 2 to 4 perform transactions. Options 5 and 6 show statements and accounts. Option 7 submits a batch of deposits to a four-thread pool. Option 8 writes a report. Option 9 saves accounts and exits. The next launch loads data/accounts.dat. Logs are appended to data/logs/daily.log. Transfer logs contain one withdrawal and one deposit, so the net change is zero. Opening balances are initial capital and are excluded from transaction totals. Report totals cover all retained log entries.

JUnit tests:
Download JUnit Platform Console Standalone 1.10.2 and save it as lib/junit.jar. Test sources stay in test/ and are not included in the application JAR.
javac -cp "out;lib/junit.jar" -d test-out test/BankServiceTest.java
java -jar lib/junit.jar execute --class-path "out;test-out" --scan-class-path
Use a colon instead of a semicolon in classpaths on Linux/macOS.

Git:
Ignore out/, test-out/, data/, lib/ and *.jar. Commit the source, manifest and README. Tag the final version lab-14. Earlier stages use tags lab-01 to lab-13.
