



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public
class jpvmConnectionServer extends Thread {
	private ServerSocket		connectionSock;
	private int			connectionPort;
	private jpvmConnectionSet	connectionSet;
	private jpvmMessageQueue	queue;

	public jpvmConnectionServer(jpvmConnectionSet c, jpvmMessageQueue q) {
		connectionSet  = c;
		connectionSock = null;
		connectionPort = 0;
		queue = q;
		try {
			connectionSock = new ServerSocket(0);
			connectionPort = connectionSock.getLocalPort();
		}
		catch (IOException ioe) {
			jpvmDebug.error("jpvmConnectionServer, i/o exception");
		}
	}

	public int getConnectionPort() {
		return connectionPort;
	}

	public void run() {
	    while(true) {
	      try {
		jpvmDebug.note("jpvmConnectionServer, blocking on port " +
			connectionSock.getLocalPort());
		Socket newConnSock = connectionSock.accept();
		jpvmDebug.note("jpvmConnectionServer, new connection.");
		jpvmRecvConnection nw = new jpvmRecvConnection(newConnSock);
		connectionSet.insertRecvConnection(nw);

		// Start a thread to recv on this pipe
		jpvmRecvThread rt=new jpvmRecvThread(nw,queue);
		rt.start();
	      }
	      catch (IOException ioe) {
			jpvmDebug.error("jpvmConnectionServer, run - " + 
				"i/o exception");
	      }
	    }
	}
};
