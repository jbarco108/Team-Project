import java.io.IOException;
import java.net.Socket;

public class Application {

        public static void main(String[] args) throws IOException {

            //copy paste the PlatformDatabase code here, and each time we want to get info from the server, use this code :
            //////////////////////////////////
            // create socket on agreed upon port (and local host for this example)...
            //connection works, after connection we should run our main method through here:
            Socket socket = new Socket("localhost", 4343);

        }


}
