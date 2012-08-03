



public
class jpvmTaskStatus {
	public String		hostName;
	public int		numTasks;
	public String		taskNames[];
	public jpvmTaskId	taskTids[];

	public jpvmTaskStatus() {
		hostName = null;
		numTasks = 0;
		taskNames = null;
		taskTids = null;
	}

	public jpvmTaskStatus(int n) {
		hostName = null;
		numTasks = n;
		taskNames = new String[n];
		taskTids = new jpvmTaskId[n];
	}
};
