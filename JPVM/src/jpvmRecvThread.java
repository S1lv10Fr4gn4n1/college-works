



public
class jpvmRecvThread extends Thread {
	private jpvmRecvConnection	conn;
	private jpvmMessageQueue	queue;
	int my_num;
	static  int num = 0;
	

	public jpvmRecvThread(jpvmRecvConnection c, jpvmMessageQueue q) {
		conn  = c;
		queue = q;
		num++;
		my_num = num;
	}
	
	public void run() {
	    boolean alive = true;
	    while(alive) {
	      try {
		jpvmDebug.note("jpvmRecvThread ("+my_num+") - blocking "+
			"for a message.");
		jpvmMessage nw = new jpvmMessage(conn);
		jpvmDebug.note("jpvmRecvThread ("+my_num+") - got a "+
			"new message.");
		queue.enqueue(nw);
		Thread.yield();
	      }
	      catch (jpvmException jpe) {
			jpvmDebug.note("jpvmRecvThread, " + 
				"connection closed");
		  	alive = false;
	      }
	    }
	}
};
