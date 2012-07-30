package gas.Controller.Amministratore;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ScaricaLog extends ActionSupport
{
	private InputStream fileInputStream;
	
	public String execute() throws Exception
	{
		String workingDir = System.getProperty("user.dir");
		File f = new File(workingDir + "/out.log");
		fileInputStream = new FileInputStream(f);
		//Dopo averlo scritto, cancello il file di log dal sistema
		f.delete();
		return Action.SUCCESS;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
}
