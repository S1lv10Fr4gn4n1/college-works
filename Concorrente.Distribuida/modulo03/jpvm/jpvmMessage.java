package jpvm;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public
class jpvmMessage {
	public int 		messageTag;
	public jpvmTaskId	sourceTid;
	public jpvmTaskId	destTid;
	public jpvmBuffer	buffer;

	public jpvmMessage() {
		messageTag = -1;
		sourceTid = null;
		destTid = null;
		buffer = null;
	}

	public jpvmMessage(jpvmBuffer buf, jpvmTaskId dest, jpvmTaskId src,
	    int tag) {
		messageTag = tag;
		sourceTid = src;
		destTid = dest;
		buffer = buf;
	}

	public jpvmMessage(jpvmRecvConnection conn) throws jpvmException {
		messageTag = -1;
		sourceTid = null;
		destTid = null;
		buffer = null;
		recv(conn);
	}

	public void send(jpvmSendConnection conn) throws jpvmException {
		DataOutputStream strm = conn.strm;
		try {
			strm.writeInt(messageTag);
			sourceTid.send(strm);
			destTid.send(strm);
			buffer.send(conn);
			strm.flush();
		}
		catch (IOException ioe) {
			jpvmDebug.note("jpvmMessage, send - i/o exception");
			throw new jpvmException("jpvmMessage, send - " +
				"i/o exception");
		}
	}

	public void recv(jpvmRecvConnection conn) throws jpvmException {
		DataInputStream strm = conn.strm;
		try {
			messageTag = strm.readInt();
			sourceTid = new jpvmTaskId();
			sourceTid.recv(strm);
			destTid = new jpvmTaskId();
			destTid.recv(strm);
			buffer = new jpvmBuffer();
			buffer.recv(conn);
		}
		catch (IOException ioe) {
			jpvmDebug.note("jpvmMessage, recv - i/o exception");
			throw new jpvmException("jpvmMessage, recv - " +
				"i/o exception");
		}
	}
};
