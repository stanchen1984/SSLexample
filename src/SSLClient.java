import java.io.*;
import java.net.*;

import javax.net.ssl.*;

public class SSLClient {

	private static String HOST = "localhost";

	private static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		System.out.println("Input the host IP address. ");
		System.out.print(">");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			HOST = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error trying to read your name!");
	         System.exit(1);
	      }
		Socket s = sf.createSocket(HOST, PORT);
		OutputStream out = s.getOutputStream();
		out.write("\nClient side is ready.\n\n".getBytes());
		out.flush();
		new receive(s).start();
		int theCharacter = 0;
		theCharacter = System.in.read();
		while (theCharacter != '~') // The '~' is an escape character to exit
		{
			out.write(theCharacter);
			out.flush();
			theCharacter = System.in.read();
		}

		out.close();
		s.close();
	}
}

class receive extends Thread {
	Socket s;

	public receive(Socket s) {
		this.s = s;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			String line = null;
			while (((line = in.readLine()) != null)) {
				System.out.println(s.getRemoteSocketAddress() + ": "+line);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}