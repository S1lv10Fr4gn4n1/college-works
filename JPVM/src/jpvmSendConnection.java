



import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public
class jpvmSendConnection {
	public DataOutputStream	strm;
	public jpvmTaskId	tid;
	
	public jpvmSendConnection() {
		strm = null;
		tid   = null;
	}

	public jpvmSendConnection(Socket sock, jpvmTaskId t) {
		if(sock==null || t==null) return;
		tid = t;
		try {
			OutputStream outstrm = sock.getOutputStream();
			outstrm = new BufferedOutputStream(outstrm);
			strm = new DataOutputStream(outstrm);
		}
		catch (IOException ioe) {
			strm = null;
			tid  = null;
			jpvmDebug.error("jpvmSendConnection, i/o exception");
		}
	}

	public static jpvmSendConnection connect(jpvmTaskId t, jpvmTaskId f) 
	    throws jpvmException {
		jpvmSendConnection ret = null;
		try {
			jpvmDebug.note("jpvmSendConnection, "+ 
				"connecting to "+t.toString());

			// Make the new connection...
			Socket sock = new Socket(t.getHost(),t.getPort());
			ret = new jpvmSendConnection(sock,t);

			// Send my identity to the newly connected task...
			f.send(ret.strm);
			ret.strm.flush();
		}
		catch (IOException ioe) {
			jpvmDebug.note("jpvmSendConnection, connect - " +
				" i/o exception");
			throw new jpvmException("jpvmSendConnection, connect - "
				+ " i/o exception: \"" + ioe + "\"");
		}
		return ret;
	}
};
