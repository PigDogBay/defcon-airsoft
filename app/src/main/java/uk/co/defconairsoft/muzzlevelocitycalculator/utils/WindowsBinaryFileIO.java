/**
 * MPD Bailey Technology 2015
 * 4th June 2015
 * 
 * @author Mark Bailey
 * 
 * Replicates .Net's BinaryWriter/Reader
 *
 */
package uk.co.defconairsoft.muzzlevelocitycalculator.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class WindowsBinaryFileIO 
{
	RandomAccessFile raf;
	ByteBuffer byteBuffer;
	byte[] buffer;
	public WindowsBinaryFileIO(String fileName) throws FileNotFoundException
	{
		raf = new RandomAccessFile(fileName, "rw");
		buffer = new byte[4];
		byteBuffer = ByteBuffer.wrap(buffer);
		//Java is Big Endian, hence why cannot use DataOutputStream
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
	}
	
	public void Close() throws IOException
	{
		raf.close();
		raf=null;
		byteBuffer=null;
		buffer=null;
	}
	
	public long getLength() throws IOException
	{
		return raf.length();
	}
	public void setPosition(long pos) throws IOException
	{
		raf.seek(pos);
	}
	
	public long getPosition() throws IOException
	{
		return raf.getFilePointer();
	}
	
	public int ReadInt32() throws IOException
	{
		raf.read(buffer);
		return byteBuffer.getInt(0);
	}
	public short ReadInt16() throws IOException
	{
		raf.read(buffer,0,2);
		return byteBuffer.getShort(0);
	}
	public int ReadUInt32() throws IOException
	{
		return ReadInt32();
	}
	public short ReadUInt16() throws IOException
	{
		return ReadInt16();
	}
	
	public void Write(int value) throws IOException
	{
		byteBuffer.putInt(0, value);
		raf.write(buffer);
	}
	public void Write(short value) throws IOException
	{
		byteBuffer.putShort(0, value);
		raf.write(buffer,0,2);
	}
	
	public void ReadBytes(byte[] data, int offset, int len) throws IOException
	{
		raf.read(data,0,len);
	}
	public void Write(byte[] data) throws IOException
	{
		raf.write(data);
	}
	public void Write(byte[] data, int offset, int len) throws IOException
	{
		raf.write(data,offset,len);
	}
	
	public void Flush()
	{
		
	}

    public byte[] ReadChars(int i) throws IOException {
        byte[] bytes = new byte[i];
        raf.read(bytes,0,i);
        return bytes;
    }
}
