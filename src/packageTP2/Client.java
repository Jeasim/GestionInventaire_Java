package packageTP2;

public class Client 
{

private String noCarteBoni;
private String nom;
private int nbPointsAcc;

  public Client(String noCarteBoni, String nom, int nbPointsAcc)
  {
  this.noCarteBoni=noCarteBoni;
  this.nbPointsAcc=nbPointsAcc;
  this.nom = nom;
  
  }

  public String getNoCarte()
  {
  return noCarteBoni;
  }
  
  public String getNom()
  {
  return nom;
  }
  
  public int getNbPointsAcc()
  {
  return nbPointsAcc;
  }
  
  public void modifierPoints ( int nbPointsSupp )
  {
  this.nbPointsAcc+= nbPointsSupp;
  }

 
  public boolean assezArgent ( Commande c, double montant )
  {
   double total = c.calculerGrandTotal();
   if ( montant  >= total )
    return true;
  else 
    return false;
  }
  
  public double paieCommande ( Commande c, double montant )
  {
  double total = c.calculerGrandTotal();
  double change = montant- total;
  int nbPoints = c.calculerPointsBonis();
  if ( change > 0)
      modifierPoints(nbPoints);

  return change;
  }
  
}