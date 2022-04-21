package domain;

import org.passay.*;
import org.passay.dictionary.ArrayWordList;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin {

  public static void main(String[] args) throws IOException {
    Admin admin = new Admin();
    Scanner scanner = new Scanner(System.in);
    System.out.print("User: ");
    String user = scanner.nextLine();
    System.out.println(user);
    System.out.print("Password: ");
    String password = scanner.nextLine();
    admin.signUp(user, password);

  }

  String user;
  String password;

  public void signUp(String user, String password) throws IOException {
    if(isValidPassword(password)) {
      this.user = user;
      this.password = password;
      //TODO registerUser()
      System.out.println("Naiz");
    }else{
      System.out.println("Cambia la contra pa");
      //TODO
    }
  }

  private boolean isValidPassword(String password) throws IOException {
    PasswordData passwordData = new PasswordData(password);
    List<Rule> ruleList = new ArrayList<>();
    LengthRule lengthRule = new LengthRule(8,64);
    ruleList.add(lengthRule);

    // create a case sensitive word list and sort it
    ArrayWordList awl = WordLists.createFromReader(
        new FileReader[] {new FileReader("src\\main\\resources\\Top 10.000 Worst Passwords - OSWASP.txt")},
        true,
        new ArraysSort());

    WordListDictionary dict = new WordListDictionary(awl);
    DictionarySubstringRule dictRule = new DictionarySubstringRule(dict);
    ruleList.add(dictRule);

    PasswordValidator passwordValidator = new PasswordValidator(ruleList);
    RuleResult result = passwordValidator.validate(passwordData);

    System.out.println(passwordData);

    if (result.isValid()) {
      System.out.println("Valid Password");
      return true;
    } else {
      System.out.println("Invalid Password");
      return false;
    }
  }


  public void login(String user, String password) {
    // TODO validation(user, password);
    // TODO access();

  }
}