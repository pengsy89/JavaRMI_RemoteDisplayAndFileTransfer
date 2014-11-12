import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class RMIClient {
	public RMIClient() {
		try{
			IClient skeleton=new Client();
			//method 1
			Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Client", skeleton);
            //method 2
//			Naming.rebind("rmi://localhost:1099/CalculatorService", c);
		}catch(Exception e){
			System.out.println("Trouble:"+e);
		}
	}

	public static void main(String[] args) {
		new RMIClient();

	}
}
