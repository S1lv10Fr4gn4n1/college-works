
public class jpvmConfiguration {
	public int			numHosts;
	public String		hostNames[];
	public jpvmTaskId	hostDaemonTids[];

	public jpvmConfiguration() {
		numHosts = 0;
		hostNames = null;
		hostDaemonTids = null;
	}

	public jpvmConfiguration(int n) {
		numHosts = n;
		hostNames = new String[n];
		hostDaemonTids = new jpvmTaskId[n];
	}
};
