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
import java.util.concurrent.TimeUnit;

public class Admin {

  public static void main(String[] args) throws IOException, InterruptedException {
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

  public void signUp(String user, String password) throws IOException, InterruptedException {
    Scanner scanner = new Scanner(System.in);
    long waiting_time = 0;
    while(!isValidPassword(password)) {
      System.out.println("\nContraseña Invalida");
      if(waiting_time!=0) {
        System.out.println("\nReitentenlo de Nuevo en: " + waiting_time + " segundos");
      }
      //TODO medir fuerza
      TimeUnit.SECONDS.sleep(waiting_time);
      System.out.print("Password: ");
      password = scanner.nextLine();
      if(waiting_time <= 10) {
        waiting_time += 1;
      }
    }
    this.user = user;
    this.password = password;
    //TODO registerUser()
    System.out.println("Naiz");
  }

  //poner en otra clase
  private boolean isValidPassword(String password) throws IOException {
    PasswordData passwordData = new PasswordData(password);
    //creo lista de reglas - Reglas: aquellas que tiene que cumplir una contraseña para ser válida
    List<Rule> ruleList = new ArrayList<>();
    //creo regla - Contraseñas entre 8 y 64 caracteres
    LengthRule lengthRule = new LengthRule(8,64);
    //añado regla a la lista
    ruleList.add(lengthRule);

    // cro una lista case sensitive a partir de un file
    ArrayWordList awl = WordLists.createFromReader(
        new FileReader[] {new FileReader("src\\main\\resources\\Top 10.000 Worst Passwords - OSWASP.txt")},
        true,
        new ArraysSort());

    //transformo la lista de reglas en un diccionario
    WordListDictionary dict = new WordListDictionary(awl);
    //creo regla - Los strings hallados en el file anteriormente no pueden coincidir con la contraseña
    DictionaryRule dictRule = new DictionaryRule(dict);
    //añado regla a la lista
    ruleList.add(dictRule);

    //creo validador de contraseñas con la lista de reglas anteriormente setteada
    PasswordValidator passwordValidator = new PasswordValidator(ruleList);
    //válido la contraseña almacenada en passwordData contra las reglas almacenadas en passwordValidator, y los resulados se guardan en result
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