



import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public
class jpvmRecvConnection {
	private InputStream	instrm;
	public DataInputStream	strm;
	public jpvmTaskId	tid;
	
	public jpvmRecvConnection() {
		instrm = null;
		strm = null;
		tid   = null;
	}

	public jpvmRecvConnection(Socket sock) {
		if(sock==null) return;
		try {
			instrm = sock.getInputStream();
			instrm = new BufferedInputStream(instrm);
			strm = new DataInputStream(instrm);
			tid = new jpvmTaskId();
			try {
				tid.recv(strm);
			}
			catch (jpvmException jpe) {
				strm  = null;
				tid   = null;
				jpvmDebug.error("jpvmRecvConnection, internal"+
					" error");
			}
			jpvmDebug.note("jpvmRecvConnection, connect to "
				+tid.toString()+" established");
		}
		catch (IOException ioe) {
			strm  = null;
			tid   = null;
			jpvmDebug.error("jpvmRecvConnection, i/o exception");
		}
		if(strm == null) return;
	}

	public boolean hasMessagesQueued() {
		boolean ret = false;
		if(instrm != null) {
			try {
			  if (instrm.available() > 0)
				ret = true;
			}
			catch (IOException ioe) {
				ret = false;
				jpvmDebug.error("jpvmRecvConnection, " +
					"hasMessagesQueued - i/o exception");
			}
		}
		return ret;
	}
};
