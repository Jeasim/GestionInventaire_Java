
package packageTP2;

public class Item 
{
private String nomProduit;
private int qteDemandee;
private int numeroCommandeAssociee;

  public Item(String nomProduit, int qteDemandee, int numeroCommandeAssociee)
  {
  this.nomProduit = nomProduit;
  this.qteDemandee = qteDemandee;
  this.numeroCommandeAssociee = numeroCommandeAssociee;
  }

  public String getNomProduit()
  {
  return nomProduit;
  }
  public int getQte()
  {
  return qteDemandee;
  }
  public int getNumeroCommandeAssociee()
  {
  return numeroCommandeAssociee;
  }
  public Produit getProduit()
  {
  return Inventaire.getProduit(nomProduit);
  }
  public String afficherItem ()
  {
  return this.nomProduit+"\r"+this.qteDemandee+"\r"+getProduit().getPrix()*this.qteDemandee;
  }
  public void setQte()
  {
	  this.qteDemandee++;
  }
  

  
}