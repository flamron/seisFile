package edu.sc.seis.seisFile.gcf;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.iris.dmc.seedcodec.Utility;

public class SerialTransportHeader {

    public SerialTransportHeader(int blockSeqNum, int blockSize) {
        super();
        this.blockSeqNum = blockSeqNum;
        this.blockSize = blockSize;
    }
    
    public void write(DataOutput out) throws IOException {
        out.writeByte(ASCII_G);
        out.writeByte(getBlockSeqNum());
        out.writeShort(getBlockSize());
    }

    public static SerialTransportHeader fromBytes(byte[] data, int offset) throws GCFFormatException {
        if (data[offset] == 'G') {
            SerialTransportHeader header = new SerialTransportHeader(data[offset + 1], Utility.bytesToInt(data[offset + 2],
                                                                                                          data[offset + 3],
                                                                                                          false));
            return header;
        } else {
            throw new GCFFormatException("serial transport header must start with 'G'");
        }
    }

    public byte[] toBytes() {
        byte[] out = new byte[SIZE];
        out[0] = 'G';
        out[1] = (byte)blockSeqNum;
        out[2] = (byte)((blockSize >> 8) & 0xff);
        out[3] = (byte)((blockSize) & 0xff);
        return out;
    }

    public int getBlockSeqNum() {
        return blockSeqNum;
    }

    public int getBlockSize() {
        return blockSize;
    }
    
    public String toString() {
        return ASCII_G+" "+ getBlockSeqNum()+" "+getBlockSize();
    }

    int blockSeqNum;

    int blockSize;
    
    public static final int ASCII_G = 71;
    
    public static final int SIZE = 4;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + blockSeqNum;
        result = prime * result + blockSize;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SerialTransportHeader other = (SerialTransportHeader)obj;
        if (blockSeqNum != other.blockSeqNum)
            return false;
        if (blockSize != other.blockSize)
            return false;
        return true;
    }
    
    
}
