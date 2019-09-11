package packageTP2;
import java.util.*;
public class EnsembleClients 
{
private static Hashtable<String, Client> listeClients = new Hashtable<String,Client>();

 

  public static Hashtable<String, Client> getListe()
  {
  return listeClients;
  }

  public static Client getClient ( String noCarteBoni )
  {
  return listeClients.get(noCarteBoni);
  }

  public static void ajouterClient ( Client c)
  {
  listeClients.put(c.getNoCarte(), c);
  }

}