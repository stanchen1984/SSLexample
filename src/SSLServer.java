import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class SSLServer {

	private static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory
				.getDefault();
		ServerSocket ss = ssf.createServerSocket(PORT);
		Socket s = ss.accept();
		System.out.println("server is running");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				s.getInputStream()));

		String line = null;
		OutputStream out = s.getOutputStream();
		if (((line = in.readLine()) != null)) {
			System.out.println(line);
			out = s.getOutputStream();
			out.write("\nServer side is ready.\n\n".getBytes());
			out.flush();
		}
		new sendBack(out).start();
		while (((line = in.readLine()) != null)) {
			System.out.println(s.getRemoteSocketAddress() + ": "+line);
		}
		in.close();
		s.close();
	}
}

class sendBack extends Thread {
	OutputStream out;

	public sendBack(OutputStream out) {
		this.out = out;
	}

	public void run() {
		try {
			int theCharacter = 0;
			theCharacter = System.in.read();
			while (theCharacter != '~') // The '~' is an escape character to
										// exit
			{
				out.write(theCharacter);
				out.flush();
				theCharacter = System.in.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}