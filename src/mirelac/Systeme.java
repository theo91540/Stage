package mirelac;

import java.util.ArrayList;

/**
 * <b>Systeme est un ensemble de composants MIRELA.</b>
 * 
 * @author Johan Arcile, Theo Chelim
 * @version 1.1
 */
public class Systeme
{

	/**
     * La liste des composants du systeme MIRELA
     */
	private ArrayList<Composant> liste_composants;

	/**
	 *	Indice pour la construction du xml
	 */
	private int id_location = 0;

	/**
	 * Systeme channels 
	 */
	private String sys_chan = "";

	/**
	 * Systeme templates
	 */
	private String sys_template = "";

	/**
	 * Constructeur Systeme
     */
	public Systeme()
	{
		this.liste_composants = new ArrayList<Composant>();
	}

	/**
	 *  Ajoute un Composant au systeme
	 *  @param c le composant a ajouter
	 */
	public void addComposant(Composant c)
	{
		liste_composants.add(c);
	}

	/**
	 *	Supprimer un composant du systeme
	 *  @param i indice du composant a retirer 
	 */
	public void remove(int i)
	{
		liste_composants.remove(i);
	}

	/**
	 *	Donne la taille du systeme (nombre de composants)
	 */
	public int getSize()
	{
		return liste_composants.size();
	}

	/**
	 *	@return le systeme sous forme de tableau de Composant
	 */
	public Composant[] toArray()
	{
		return (Composant[]) liste_composants.toArray(new Composant[liste_composants.size()]);
	}

	/**
	 *	Ajoute un chan au sys_chan
	 *	@param s le chan a ajouter
	 */
	public void addSysChan(String s)
	{
		this.sys_chan += s;
	}

	/**
	 *	Ajoute un template au sys_template
	 *	@param s le template a ajouter
	 */
	public void addSysTemplate(String s)
	{
		this.sys_template += s;
	}

	/** 
	 *	@return indice de la location actuelle pendant la construction du xml
	 */
	public int getIdLocation()
	{
		return id_location;
	}

	/**
	 *	Incremente la valeur de la location actuelle pendant la construction du xml
	 *	@return id_location
	 */
	public int setIdLocation()
	{
		return id_location++;
	}

	/**
	 *	Retourne le Composant du systeme a partir d'une chaine de caracteres
	 *	@param s le nom composant a retourner
	 */
	public Composant toComp(String s)
	{
		for(int i=0; i<liste_composants.size(); i++)
		{
			Composant c = liste_composants.get(i);
			if(s.equals(c.getName()))
			 return liste_composants.get(i);
		}
		
		return null;
	}
	
	/**
	 *	Nombre de location output (sortie) pour le composant
	 *	@param target tableau des noms de composants de sortie
	 */
	public int nbOutLoc(String[] target)
	{
		int n = -1;
		for(int i=0; target.length>i; i++)
		{
			n += (toComp(target[i]) instanceof Memory)?3:1;
		}

		return n;
	}

	/**
	 *	Permet de generer l'ensemble des targets (composants cibles) du composant
	 */
	public void generateTargets()
	{
		for(int i=0; i<liste_composants.size(); i++)
		{
			if(liste_composants.get(i).getSource()!=null)
			{
				for(int j=0; j<liste_composants.get(i).getSource().length; j++)
				{
					boolean alreadyIn = false;
					Composant source = toComp(liste_composants.get(i).getSource()[j]);
					if(source.getTarget()!=null)
					{
						for(int k=0;k<source.getTarget().length;k++)
						{
							if(source.getTarget()[k].equals(liste_composants.get(i).getName()))
								alreadyIn = true;
						}
					}
					if(!alreadyIn)
					{
						source.setTarget(addString(source.getTarget(),liste_composants.get(i).getName()));
					}
				}
			}
		}
	}

	/**
	 *	Verifie la consistence du systeme
	 */
	public void checkConsistency()
	{
		for(int i=0;i<liste_composants.size();i++)
		{
			for(int j=i+1;j<liste_composants.size();j++)
			{
				if(liste_composants.get(i).getName().equals(liste_composants.get(j).getName()))
				{
					liste_composants.get(j).setName(liste_composants.get(j).getName()+"\'");
					System.out.println("L'identifiant "+liste_composants.get(i).getName()+" est deja reserve. L'identifiant attribue est "+liste_composants.get(j).getName());
				}

			}

			boolean exist = false;
			if(liste_composants.get(i).getSource()!=null)
			{
				for(int j=0;j<liste_composants.get(i).getSource().length;j++)
				{
					for(int k=0;k<liste_composants.size();k++)
					{
						if(liste_composants.get(i).getSource()[j].equals(liste_composants.get(k).getName()))
						{
							exist = true;
						}
					}

					if(!exist)
					{
						System.out.println("Warning : le composant "+liste_composants.get(i).getSource()[j]+" en entree de "+liste_composants.get(i).getName()+" n'existe pas.");
					}

					exist = false;
				}
			}

			if(liste_composants.get(i).getTarget()!=null)
			{
				exist = false;
				for(int j=0;j<liste_composants.get(i).getTarget().length;j++)
				{
					for(int k=0;k<liste_composants.size();k++)
					{
						if(liste_composants.get(i).getTarget()[j].equals(liste_composants.get(k).getName()))
						{
							exist = true;
						}
					}

					if(!exist)
					{
						System.out.println("Warning : le composant "+liste_composants.get(i).getTarget()[j]+" en sortie de "+liste_composants.get(i).getName()+" n'existe pas.\nCette sortie a ete supprimee.");
						liste_composants.get(i).setTarget(suppr(liste_composants.get(i).getTarget(),j));
					}
				}
			}
		}
	}

	public String[] addString(String[] tab, String s)
	{
		String[] temp = new String[tab.length+1];
		for(int i=0;i<tab.length;i++){
			temp[i]=tab[i];
		}
		temp[tab.length]=s;
		return temp;
	}

	public String[] suppr(String[] tab,int n)
	{
		String[] new_tab = new String[tab.length-1];
		for(int i=0;i<new_tab.length;i++){
			if(i<n){
				new_tab[i]=tab[i];
			}
			if(i>n){
				new_tab[i]=tab[i+1];
			}
		}
		return new_tab;
	}	

	/** 
	 *	Transforme le systeme sous forme xml pour pouvoir visualiser le systeme sur uppaal
	 */
	public String toXML()
	{
		String sys_system = "system ";

		String codexml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
						 "<!DOCTYPE nta PUBLIC '-//Uppaal Team//DTD Flat System 1.1//EN' 'http://www.it.uu.se/research/group/darts/uppaal/flat-1_1.dtd'>\n" +
						 "<nta>\n" +
						 "<declaration></declaration>\n";

		for(int i=0; i<liste_composants.size(); i++)
		{
			Composant c = liste_composants.get(i);
			codexml += c.toXML(this);
			sys_system += c.getName() + ((i<getSize()-1)?", ":";\n");
		}

		return codexml + "<system>\n"+sys_chan+"\n"+sys_template+"\n"+sys_system+"</system>\n</nta>\n";
	}
}
