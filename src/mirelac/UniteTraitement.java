package mirelac;

/**
 * <b>Composant du type UniteTraitement d'un systeme MIRELA.</b>
 * 
 * @author Johan Arcile, Theo Chelim
 * @version 1.1
 */
public abstract class UniteTraitement extends Composant 
{

 	/**
	 * Constructeur UniteTraitement
	 * @param id nom du composant
     */
	public UniteTraitement(String id)
	{
		super(id);
	}

	/** 
	 * Construit la partie xml parametres du composant
	 * @param sys systeme du composant
	 */
	protected void parameters(Systeme sys)
	{
		codexml += "<name>"+this.getName()+"_t</name>\n";
		codexml += "<parameter>";

		sys.addSysTemplate(this.getName()+" = "+this.getName()+"_t(");

		for(int j=0; this.getSource().length>j; j++)
		{
			codexml += "urgent chan &amp;k_"+this.getSource()[j]+"_"+this.getName()+", ";
			sys.addSysTemplate("k_"+this.getSource()[j]+"_"+this.getName()+", ");
		}

		for(int j=0; this.getTarget().length>j; j++)
		{
			String[] targets = this.getTarget();
			if(sys.toComp(targets[j]) instanceof Memory)
			{
				codexml += "urgent chan &amp;lock_"+targets[j]+", urgent chan &amp;unlock_"+targets[j];
				sys.addSysTemplate("lock_"+targets[j]+", unlock_"+targets[j]);
			}
			else
			{
				codexml += "urgent chan &amp;k_"+this.getName()+"_"+targets[j];
				sys.addSysChan("urgent chan k_"+this.getName()+"_"+targets[j]+";\n");
				sys.addSysTemplate("k_"+this.getName()+"_"+targets[j]);
			}
			
			if(targets.length>j+1)
			{
				codexml += ", ";
				sys.addSysTemplate(", ");
			}
		}

		sys.addSysTemplate(");\n");

		codexml += "</parameter>";
		codexml += "<declaration>clock x;</declaration>\n";
	}

	/** 
	 * Construit la partie xml des locations de sortie du composant
	 * @param sys systeme du composant
	 */
	protected void locationsSortie(Systeme sys)
	{
		int h = echelle;

		int nbLoc = sys.nbOutLoc(this.getTarget());
		int n=nbLoc;

		for(int j=0; n>0; j++)
		{
			if(sys.toComp(this.getTarget()[j]) instanceof Memory)
			{
				codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*n)+"\" y=\"100\"><label kind=\"invariant\" x=\""+((150*n)-50)+"\" y=\"120\">x&lt;"+maxMem(sys.toComp(this.getTarget()[j]),this.getName())+"</label></location>\n";
				n--;
				codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*n)+"\" y=\"100\"></location>\n";
				n--;
				if(n>0)
				{
					codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*n)+"\" y=\"100\"></location>\n";
					n--;
				}
			}
			else
			{
				if(n>0)
				{
					codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*n)+"\" y=\"100\"></location>\n";
					n--;
				}
			}
		}
	}

	/** 
	 * Construit la partie xml des transitions de sortie du composant
	 * @param sys systeme du composant
	 * @param init numero de la premiere localite du composant
	 * @param id_temp numero localite du debut des output
	 */
	protected void transitionsSortie(Systeme sys, int init, int id_temp)
	{
		int h = echelle;

		int nbLoc = sys.nbOutLoc(this.getTarget());
		int n=nbLoc;
		
		for(int j=0; this.getTarget().length>j; j++)
		{
			if(sys.toComp(this.getTarget()[j]) instanceof Memory)
			{
				codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+((150*n)+50)+"\" y=\"80\">lock_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\""+((150*n)+50)+"\" y=\"100\">x:=0</label>";
				if(n==nbLoc)
				{
					if(150*(n+1)>((150*decalage)+300)) codexml += "<nail x=\""+150*(n+1)+"\" y=\"0\"/>";
					codexml += "<nail x=\""+Math.max((150*(n+1)), ((150*decalage)+300))+"\" y=\"100\"/>";
				}
				codexml += "</transition>\n";

				n--;
				codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\""+((150*n)+50)+"\" y=\"80\">x&gt;="+minMem(sys.toComp(this.getTarget()[j]),this.getName())+"</label></transition>\n";
			
				n--;
				if(n==0)
				{
					codexml += "<transition><source ref=\""+id_temp+"\"/><target ref=\""+init+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"80\">unlock_"+this.getTarget()[j]+"!</label><nail x=\"0\" y=\"100\"/></transition>\n";
				}
				else
				{
					codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+((150*n)+50)+"\" y=\"80\">unlock_"+this.getTarget()[j]+"!</label></transition>\n";
					n--;
				}
			}
			else
			{
				if(n==0)
				{
					codexml += "<transition><source ref=\""+id_temp+"\"/><target ref=\""+init+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"80\">k_"+this.getName()+"_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\"50\" y=\"100\">x:=0</label>";
					if(n==nbLoc){codexml += "<nail x=\""+((150*decalage)+300)+"\" y=\"100\"/>";}
					codexml += "<nail x=\"0\" y=\"100\"/></transition>\n";
				}
				else
				{
					codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+((150*n)+50)+"\" y=\"80\">k_"+this.getName()+"_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\""+((150*n)+50)+"\" y=\"100\">x:=0</label>";
					if(n==nbLoc)
					{
						if(150*(n+1)>((150*decalage)+300)) codexml += "<nail x=\""+150*(n+1)+"\" y=\"0\"/>";
						codexml += "<nail x=\""+Math.max((150*(n+1)), ((150*decalage)+300))+"\" y=\"100\"/>";
					}
					codexml += "</transition>\n";
					
					n--;
				}
			}
		}
	}
}