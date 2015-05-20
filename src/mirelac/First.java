package mirelac;

public class First extends UniteTraitement
{

	public First(String id)
	{
		super(id);
	}

	public String toXML(Systeme sys)
	{
		int init = sys.getIdLocation();

		parameters(sys);

		//locations entree
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"0\" y=\"0\"><name x=\"-20\" y=\"-25\">W</name></location>\n";
		for(int j=0;this.getSource().length>j;j++){
			codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"150\" y=\""+(-100*j)+"\"><name x=\"130\" y=\""+((-100*j)-25)+"\">P"+j+"</name><label kind=\"invariant\" x=\"100\" y=\""+((-100*j)+20)+"\">x&lt;"+this.getMax()[j]+"</label></location>\n";
		}
		int id_temp = sys.getIdLocation();
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"300\" y=\"0\"></location>\n";
		//locations sortie
		int nbLoc = sys.nbOutLoc(this.getTarget());
		int n=nbLoc;
		for(int j=0;n>0;j++){
			if(sys.toComp(this.getTarget()[j]) instanceof Memory){
				codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*n)+"\" y=\"100\"><label kind=\"invariant\" x=\""+((150*n)-50)+"\" y=\"120\">x&lt;"+maxMem(sys.toComp(this.getTarget()[j]),this.getName())+"</label></location>\n";
				n--;
				codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*n)+"\" y=\"100\"></location>\n";
				n--;
				if(n>0){
					codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*n)+"\" y=\"100\"></location>\n";
					n--;
				}
			}
			else{
				if(n>0){
					codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*n)+"\" y=\"100\"></location>\n";
					n--;
				}
			}
		}
		//location initiale
		codexml += "<init ref=\""+init+"\"/>\n";
		//transitions entree
		for(int j=0;this.getSource().length>j;j++){
			codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+(init+j+1)+"\"/><label kind=\"synchronisation\" x=\"50\" y=\""+((-100*j)-20)+"\">k_"+this.getSource()[j]+"_"+this.getName()+"?</label><label kind=\"assignment\" x=\"50\" y=\""+((-100*j)+10)+"\">x=0</label>";
			if(j>0){codexml += "<nail x=\"0\" y=\""+(-100*j)+"\"/>";}
			codexml += "</transition>\n";
		}
		for(int j=0;this.getSource().length>j;j++){
			codexml += "<transition><source ref=\""+(init+j+1)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\"200\" y=\""+((-100*j)-20)+"\">x&gt;="+this.getMin()[j]+"</label>";
			if(j>0){codexml += "<nail x=\"300\" y=\""+(-100*j)+"\"/>";}
			codexml += "</transition>\n";
		}
		//transitions sortie
		n = nbLoc;
		for(int j=0;this.getTarget().length>j;j++){
			if(sys.toComp(this.getTarget()[j]) instanceof Memory){
				codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+((150*n)+50)+"\" y=\"80\">lock_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\""+((150*n)+50)+"\" y=\"110\">x=0</label>";
				if(n==nbLoc){codexml += "<nail x=\""+Math.max((150*(n+1)),300)+"\" y=\"0\"/><nail x=\""+Math.max((150*(n+1)),300)+"\" y=\"100\"/>";}
				codexml += "</transition>\n";
				n--;
				codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\""+((150*n)+50)+"\" y=\"80\">x&gt;="+minMem(sys.toComp(this.getTarget()[j]),this.getName())+"</label></transition>\n";
				n--;
				if(n==0){
					codexml += "<transition><source ref=\""+id_temp+"\"/><target ref=\""+init+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"80\">unlock_"+this.getTarget()[j]+"!</label><nail x=\"0\" y=\"100\"/></transition>\n";
				}
				else{
					codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+((150*n)+50)+"\" y=\"80\">unlock_"+this.getTarget()[j]+"!</label></transition>\n";
					n--;
				}
			}
			else{
				if(n==0){
					codexml += "<transition><source ref=\""+id_temp+"\"/><target ref=\""+init+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"80\">k_"+this.getName()+"_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\"50\" y=\"110\">x=0</label>";
					if(n==nbLoc){codexml += "<nail x=\"300\" y=\"100\"/>";}
					codexml += "<nail x=\"0\" y=\"100\"/></transition>\n";
				}
				else{
					codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+((150*n)+50)+"\" y=\"80\">k_"+this.getName()+"_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\""+((150*n)+50)+"\" y=\"110\">x=0</label>";
					if(n==nbLoc){
						if(150*(n+1)>300) codexml += "<nail x=\""+150*(n+1)+"\" y=\"0\"/>";
						codexml += "<nail x=\""+Math.max((150*(n+1)),300)+"\" y=\"100\"/>";
					}
					codexml += "</transition>\n";
					n--;
				}
			}
		}
		codexml += "</template>\n";
		return codexml;
	}
}