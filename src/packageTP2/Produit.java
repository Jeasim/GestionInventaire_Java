package packageTP2;

public class Produit 
{

private int code;
private String nom;
private int qteStock;
private double prix;
private int points;

public Produit ( int code, String nom,int qteStock, double prix, int points )
{
this.code = code;
this.qteStock=qteStock;
this.prix=prix;
this.points=points;
this.nom = nom;
}
public String getNom()
{
return nom;
}
public int getCode ()
{
return code;
}
public int getQteStock()
{
return qteStock;
}
public double getPrix()
{
return prix;
}
public int getPoints()
{
return points;
}
public boolean modifierQteStock ( int qteAchetee)
{
if ( qteAchetee <= qteStock )
  {
  this.qteStock-=qteAchetee;
  return true;
  }
else
  return false; 
}

public void setQteStock(int ajustement)
{
	this.qteStock+=ajustement;
}
}