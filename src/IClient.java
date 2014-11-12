import java.rmi.Remote;
import java.rmi.RemoteException;

interface IClient extends Remote{
	
	void startConnection() throws RemoteException;
	void closeConnection() throws RemoteException;

}
