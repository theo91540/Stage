public abstract class Capteur extends Composant 
{

	public Capteur(String id)
	{
		super(id);
	}

	protected void parameters(Systeme sys)
	{
		codexml = "<template>\n<name>"+this.getName()+"_t</name>\n<parameter>";
		
		sys.addSysTemplate(this.getName()+" = "+this.getName()+"_t(");

		//parametres
		for(int j=0;this.getTarget().length>j;j++)
		{
			String[] targets = this.getTarget();
			if(sys.toComp(targets[j]) instanceof Memory){
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

		codexml += "</parameter><declaration>clock x;</declaration>\n";
	}
}