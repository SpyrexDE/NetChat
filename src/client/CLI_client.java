package client;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "Greet", header = "%n@|green Hello world demo|@")
public class CLI_client implements Runnable {

  @Option(names = {"-u", "--user"}, required = true, description = "The user name.")
  String userName;

  public void run() {
    System.out.println("Hello, " + userName);
  }
}