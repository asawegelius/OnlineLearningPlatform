
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author asawe
 */
public class Main {
   // Define a static logger variable so that it references the
   // Logger instance named "MyApp".
   static Logger logger = Logger.getLogger(Main.class);

   public static void main(String[] args) {

     // Set up a simple configuration that logs on the console.
     BasicConfigurator.configure();

     logger.info("Entering application.");

     logger.info("Exiting application.");
   }
}
