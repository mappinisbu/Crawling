package objects;

public final class Result {
	private String urlName;
	
	private boolean cspEnabled;
	private String  cspDetails;
	
	private boolean strictEnabled;
	private String  strictDetails;
	
	private boolean httpOnlyEnabled;
	private String httpOnlyDetails;
	
	private boolean antiClickEnabled;
	private String antiClickDetails;
	
	private boolean noncesEnabled;
	private String noncesDetails;
	
	//Constructor for Result
	public Result() {
		this.urlName = "";
		this.cspEnabled = false;
		this.cspDetails = "";
		this.strictEnabled = false;
		this.strictDetails = "";
		this.httpOnlyEnabled = false;
		this.httpOnlyDetails = "";
		this.antiClickEnabled = false;
		this.antiClickDetails = "";
		this.noncesEnabled = false;
		this.noncesDetails = "";
	}
	
	//public boolean[] found=new boolean[5];
	//public String[] details=new String[5];
	
	public void setUrlName(String urlName){
			this.urlName = urlName;
	}
	
	public void setCspEnabled(boolean cspEnabled){
		this.cspEnabled = cspEnabled ;
	}

	public void setCspDetails(String cspDetails){
		this.cspDetails = cspDetails;
	}
	
	public void setStrictEnabled(boolean strictEnabled){
		this.strictEnabled = strictEnabled ;
	}

	public void setStrictDetails(String strictDetails){
		this.strictDetails = strictDetails;
	}
	
	public void setHttpOnlyEnabled(boolean httpOnlyEnabled){
		this.httpOnlyEnabled = httpOnlyEnabled ;
	}

	public void setHttpOnlyDetails(String httpOnlyDetails){
		this.httpOnlyDetails = httpOnlyDetails;
	}
	
	public void setAntiClickEnabled(boolean antiClickEnabled){
		this.antiClickEnabled = antiClickEnabled ;
	}

	public void setAntiClickDetails(String antiClickDetails){
		this.antiClickDetails = antiClickDetails;
	}
	
	public void setNoncesEnabled(boolean noncesEnabled){
		this.noncesEnabled = noncesEnabled ;
	}

	public void setNoncesDetails(String noncesDetails){
		this.noncesDetails = noncesDetails;
	}
	


/*	
	public void setFound(boolean foundValue,int index){
		this.found[index]= foundValue;
		//add error checks
	}
	
	public void setDetails(String detailsValue, int index){
		//add error checks
		this.details[index]= detailsValue;
	}
*/
	
/*	public boolean getFound(int index){
		//add error checks
		return found[index];
	}
	
	public String getDetails(int index){
		//add error checks
		return details[index];
	}
*/
	public String getUrlName(){
		return this.urlName;
	}
	
	public boolean getCspEnabled(){
		return this.cspEnabled;
	}

	public String getCspDetails(){
		return this.cspDetails;
	}
	
	public boolean getStrictEnabled(){
		return this.strictEnabled;
	}

	public String getStrictDetails(){
		return this.strictDetails;
	}

	public boolean getHttpOnlyEnabled(){
		return this.httpOnlyEnabled;
	}

	public String getHttpOnlyDetails(){
		return this.httpOnlyDetails;
	}

	public boolean getAntiClickEnabled(){
		return this.antiClickEnabled;
	}

	public String getAntiClickDetails(){
		return this.antiClickDetails;
	}

	public boolean getNoncesEnabled(){
		return this.noncesEnabled;
	}

	public String getNoncesDetails(){
		return this.noncesDetails;
	}



}
