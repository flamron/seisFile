
package edu.sc.seis.seisFile.mseed;

/**
 * SeedRecord.java
 *
 *
 * Created: Thu Apr  8 11:54:07 1999
 *
 * @author Philip Crotwell
 * @version
 */
import java.util.Vector;

public abstract class SeedRecord  {
    
    public SeedRecord(ControlHeader header) {
	this.header = header;
    }
    
    public void addBlockette(Blockette b) throws SeedFormatException {
	blockettes.addElement(b);
    }

    public Blockette[] getBlockettes() {
	Blockette[] allB = new Blockette[blockettes.size()];
	blockettes.copyInto(allB);
	return allB;
    }

    public Blockette[] getBlockettes(int type) {
	Vector v = new Vector();
	for (int i=0; i< blockettes.size(); i++) {
	    if (((Blockette)blockettes.elementAt(i)).getType() == type) {
		v.addElement(blockettes.elementAt(i));
	    }
	}

	Blockette[] allB = new Blockette[v.size()];
	v.copyInto(allB);
	return allB;
    }

    public String toString() {
	String s = "Record for "+header+"\n";
	s += "Blockettes:\n";
	for (int i=0; i< blockettes.size(); i++) {
	    s += blockettes.elementAt(i)+"\n";
	}
	return s;
    }

    protected ControlHeader header;

    protected Vector blockettes = new Vector();

} // SeedRecord
