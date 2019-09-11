package packageTP2;
import java.util.*;
public class Inventaire 
{
private static Hashtable<String, Produit> listeProduits = new Hashtable<String, Produit>();

 

  public static Hashtable<String, Produit> getListe()
  {
  return listeProduits;
  }

  public static Produit getProduit ( String  nom )
  {
  return listeProduits.get(nom);
  }

  public static void ajouterProduit ( Produit p)
  {
  listeProduits.put(p.getNom(), p);
  }

 
  
}