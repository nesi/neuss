package neuss.model;

import neuss.ExternalCommand;
import neuss.User;
import neuss.control.ExternalCommandException;

public interface ExternalUser {
	
	public boolean isRegistered();

	/** Registers the user at the service.
	 * 
	 * @return the username of the user in the new service
	 * @throws ExternalCommandException if the user can't be registered
	 */
	public String register() throws ExternalCommandException;
	/**
	 * Removes the user from the service.
	 * 
	 * No exception is thrown if the user wasn't member of the service in the first place.

	 * @throws ExternalCommandException if the user either could not be removed from the service
	 */
	public void deregister() throws ExternalCommandException;
	/**
	 * The username for this user on the external service.
	 * 
	 * @return the username
	 */
	public String getUserName();
	public void setUser(User user);
}
