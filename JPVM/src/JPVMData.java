import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class JPVMData implements Serializable {
	private static final long	serialVersionUID	= -5122502430744075797L;
	private int					id;
	private List<String>		typeTakesProgrammer	= new ArrayList<String>();
	private List<String>		listMessage			= new ArrayList<String>();

	public JPVMData(int id) {
		this.id = id;
	}

	public JPVMData(String message) {
		this.setMessage(message);
	}

	public int getId() {
		return this.id;
	}

	public void createEffect() {
		this.typeTakesProgrammer.add("1");
		this.typeTakesProgrammer.add("2");
		this.typeTakesProgrammer.add("3");
	}

	public String getTaskProgrammer() {
		if (this.typeTakesProgrammer.size() > 0)
			return this.typeTakesProgrammer.get(0);
		else
			return "";
	}

	public void removeTaskProgrammer(String ef) {
		if (this.typeTakesProgrammer.size() > 0)
			this.typeTakesProgrammer.remove(this.typeTakesProgrammer.indexOf(ef));
	}

	public boolean isTerminate() {
		return (this.typeTakesProgrammer.size() == 0);
	}

	public List<String> getListMessage() {
		return listMessage;
	}

	public void addData(String data) {
		this.listMessage.add(data);
	}

	public void setMessage(String message) {
		String[] str = message.split("#");

		String strAux = "";

		try {
			for (int i = 0; i < str.length; i++) {

				if (str[i] == "id=0#ef=123#ms=")
					continue;

				if (str[i].substring(0, 3).equalsIgnoreCase("id=")) {
					strAux = str[i].replaceFirst("id=", "");
					this.id = Integer.parseInt(strAux);

				} else if (str[i].substring(0, 3).equalsIgnoreCase("ef=")) {
					strAux = str[i].replaceFirst("ef=", "");

					for (int j = 0; j < strAux.length(); j++) {
						this.typeTakesProgrammer.add(String.valueOf(strAux.charAt(j)));
					}

				} else if (str[i].substring(0, 3).equalsIgnoreCase("ms=")) {
					strAux = str[i].replaceFirst("ms=", "");
					String[] strData = strAux.split(";");

					this.listMessage.clear();

					for (int j = 0; j < strData.length; j++) {
						if (strData[j].isEmpty())
							continue;

						this.listMessage.add(strData[j]);
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public String toString() {
		String messages = "";
		String ef = "";

		for (int i = 0; i < this.listMessage.size(); i++) {
			messages += ";" + this.listMessage.get(i);
		}

		for (int i = 0; i < this.typeTakesProgrammer.size(); i++) {
			ef += this.typeTakesProgrammer.get(i);
		}

		return "id=" + this.id + "#ef=" + ef + "#ms=" + messages;
	}

	public List<String> getEffects() {
		return this.typeTakesProgrammer;
	}

	public void setEffects(List<String> effects) {
		this.typeTakesProgrammer.clear();
		this.typeTakesProgrammer.addAll(effects);
	}
}
