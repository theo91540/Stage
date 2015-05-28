package mirelac;

public abstract class Capteur extends Composant 
{

	public Capteur(String id)
	{
		super(id);
	}

	protected void parameters(Systeme sys)
	{
		codexml += "<name>"+this.getName()+"_t</name>\n";
		codexml += "<parameter>";
		
		sys.addSysTemplate(this.getName()+" = "+this.getName()+"_t(");

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
		codexml += "<declaration> clock x; </declaration>\n";
	}

	protected void locationsSortie(Systeme sys, int echelle, int decalage)
	{
		int h = echelle;

		int nbLoc = sys.nbOutLoc(this.getTarget());
		int n = nbLoc;

		for(int j=0; n>0; j++)
		{
			if(sys.toComp(this.getTarget()[j]) instanceof Memory)
			{
				codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+((3*h)*(n+decalage))+"\" y=\""+(2*h)+"\"><label kind=\"invariant\" x=\""+(((3*h)*(n+decalage))-h)+"\" y=\""+((2*h)+20)+"\">x&lt;"+maxMem(sys.toComp(this.getTarget()[j]),this.getName())+"</label></location>\n";
				n--;
				codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+((3*h)*(n+decalage))+"\" y=\""+(2*h)+"\"></location>\n";
				n--;
				if(n>0)
				{
					codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+((3*h)*(n+decalage))+"\" y=\""+(2*h)+"\"></location>\n";
					n--;
				}
			}
			else
			{
				if(n>0)
				{
					codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+((3*h)*(n+decalage))+"\" y=\""+(2*h)+"\"></location>\n";
					n--;
				}
			}
		}
	}

	protected void transitionsSortie(Systeme sys, int echelle, int decalage, int init, int id_temp)
	{
		int h = echelle;

		int nbLoc = sys.nbOutLoc(this.getTarget());
		int n = nbLoc;

		for(int j=0; this.getTarget().length>j; j++)
		{
			if(sys.toComp(this.getTarget()[j]) instanceof Memory)
			{
				codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+(((3*h)*(n+decalage))+h)+"\" y=\""+((2*h)-20)+"\">lock_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\""+(((3*h)*(n+decalage))+h)+"\" y=\""+(2*h)+"\">x:=0</label>";
				if(n==nbLoc){codexml += "<nail x=\""+Math.max(((3*h)*(n+1+decalage)),6*h)+"\" y=\"0\"/><nail x=\""+Math.max(((3*h)*(n+decalage+1)),6*h)+"\" y=\""+(2*h)+"\"/>";}
				codexml += "</transition>\n";
				
				n--;
				codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\""+(((3*h)*(n+decalage))+h)+"\" y=\""+((2*h)-20)+"\">x&gt;="+minMem(sys.toComp(this.getTarget()[j]),this.getName())+"</label></transition>\n";
				
				n--;
				if(n==0)
				{
					codexml += "<transition><source ref=\""+id_temp+"\"/><target ref=\""+(init+decalage)+"\"/><label kind=\"synchronisation\" x=\""+((3*h*decalage)+h)+"\" y=\""+((2*h)-20)+"\">unlock_"+this.getTarget()[j]+"!</label><nail x=\""+((3*h)*decalage)+"\" y=\""+(2*h)+"\"/></transition>\n";
				}
				else
				{
					codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+(((3*h)*(n*decalage))+h)+"\" y=\""+((2*h)-20)+"\">unlock_"+this.getTarget()[j]+"!</label></transition>\n";
					n--;
				}
			}
			else
			{
				if(n==0)
				{
					codexml += "<transition><source ref=\""+id_temp+"\"/><target ref=\""+(init+decalage)+"\"/><label kind=\"synchronisation\" x=\""+(((3*h)*decalage)+h)+"\" y=\""+((2*h)-20)+"\">k_"+this.getName()+"_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\""+(((3*h)*decalage)+h)+"\" y=\""+(2*h)+"\">x:=0</label>";
					if(n==nbLoc){codexml += "<nail x=\""+(6*h)+"\" y=\""+(2*h)+"\"/>";}
					codexml += "<nail x=\""+((decalage)*3*h)+"\" y=\""+(2*h)+"\"/></transition>\n";
				}
				else
				{
					codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+(((3*h)*(n+decalage))+h)+"\" y=\""+((2*h)-20)+"\">k_"+this.getName()+"_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\""+(((3*h)*(n+decalage))+h)+"\" y=\""+(2*h)+"\">x:=0</label>";
					if(n==nbLoc){codexml += "<nail x=\""+Math.max(((3*h)*(n+decalage+1)),6*h)+"\" y=\"0\"/><nail x=\""+Math.max(((3*h)*(n+decalage+1)),6*h)+"\" y=\""+(2*h)+"\"/>";}
					codexml += "</transition>\n";
					
					n--;
				}
			}
		}
	}
}