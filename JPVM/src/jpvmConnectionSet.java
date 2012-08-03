




class jpvmSendConnectionListNode {
	public jpvmSendConnection		conn;
	public jpvmSendConnectionListNode	next;
	public jpvmSendConnectionListNode() {
		conn = null;
		next = null;
	}
	public jpvmSendConnectionListNode(jpvmSendConnection c) {
		conn = c;
		next = null;
	}
};

class jpvmRecvConnectionListNode {
	public jpvmRecvConnection		conn;
	public jpvmRecvConnectionListNode	next;
	public jpvmRecvConnectionListNode() {
		conn = null;
		next = null;
	}
	public jpvmRecvConnectionListNode(jpvmRecvConnection c) {
		conn = c;
		next = null;
	}
};

public
class jpvmConnectionSet {
	private jpvmRecvConnectionListNode		recvList;
	private jpvmSendConnectionListNode		sendList;
	private jpvmRecvConnectionListNode		recvListIter;

	jpvmConnectionSet() {
		recvList = null;
		sendList = null;
	}

	public synchronized jpvmRecvConnection
	lookupRecvConnection(jpvmTaskId tid) {
		jpvmRecvConnectionListNode tmp = recvList;
		while(tmp != null) {
			if(tmp.conn.tid.equals(tid))
				return tmp.conn;
			tmp = tmp.next;
		}
		return null;
	}

	public synchronized void insertRecvConnection(jpvmRecvConnection c) {
		jpvmRecvConnectionListNode nw;
		nw = new jpvmRecvConnectionListNode(c);
		nw.next = recvList;
		recvList = nw;
	}

	public synchronized jpvmRecvConnection
	firstIterRecv() {
		recvListIter = recvList;
		if(recvListIter!=null)
			return recvListIter.conn;
		return null;
	}

	public synchronized jpvmRecvConnection
	nextIterRecv() {
		if(recvListIter != null)
			recvListIter = recvListIter.next;
		if(recvListIter != null)
			return recvListIter.conn;
		return null;
	}

	public synchronized jpvmSendConnection
	lookupSendConnection(jpvmTaskId tid) {
		jpvmSendConnectionListNode tmp = sendList;
		while(tmp != null) {
			if(tmp.conn.tid.equals(tid))
				return tmp.conn;
			tmp = tmp.next;
		}
		return null;
	}

	public synchronized void insertSendConnection(jpvmSendConnection c) {
		jpvmSendConnectionListNode nw;
		nw = new jpvmSendConnectionListNode(c);
		nw.next = sendList;
		sendList = nw;
	}

};
