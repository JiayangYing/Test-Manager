package com.service.servlet.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class is used to demonstrate methods for reading and writing streams.
 *
 */
public class StreamUtils {
	/**
	 * Function: Convert InputStream to byte[], i.e., read the contents of a file into byte[]
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static byte[] streamToByteArray(InputStream is) throws Exception{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); // Create output stream object
		byte[] b = new byte[1024]; // Byte array
		int len;
		while((len=is.read(b))!=-1){ // Loop to read
			bos.write(b, 0, len); // Write the read data to bos
		}
		byte[] array = bos.toByteArray(); // Then convert bos to byte array
		bos.close();
		return array;
	}
	/**
	 * Function: Convert InputStream to String
	 * @param is
	 * @return
	 * @throws Exception
	 */

	public static String streamToString(InputStream is) throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder builder= new StringBuilder();
		String line;
		while((line=reader.readLine())!=null){
			builder.append(line+"\r\n");
		}
		return builder.toString();

	}

}
