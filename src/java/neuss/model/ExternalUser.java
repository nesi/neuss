package neuss.model;

import neuss.ExternalCommand;
import neuss.control.ExternalCommandException;

public interface ExternalUser {
	
	public boolean isRegistered();
	public ExternalCommand register() throws ExternalCommandException;
	public ExternalCommand deregister() throws ExternalCommandException;
	public String getUserName();
}
