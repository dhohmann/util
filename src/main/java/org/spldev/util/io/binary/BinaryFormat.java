/* -----------------------------------------------------------------------------
 * Util Lib - Miscellaneous utility functions.
 * Copyright (C) 2021  Sebastian Krieter
 * 
 * This file is part of Util Lib.
 * 
 * Util Lib is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * Util Lib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Util Lib.  If not, see <https://www.gnu.org/licenses/>.
 * 
 * See <https://github.com/skrieter/utils> for further information.
 * -----------------------------------------------------------------------------
 */
package org.spldev.util.io.binary;

import java.io.*;
import java.nio.charset.*;

import org.spldev.util.io.format.*;

public abstract class BinaryFormat<T> implements Format<T> {

	protected void writeBytes(OutputStream out, byte[] bytes) throws IOException {
		writeInt(out, bytes.length);
		out.write(bytes);
	}

	protected void writeString(OutputStream out, String string) throws IOException {
		writeBytes(out, string.getBytes(StandardCharsets.UTF_8));
	}

	protected void writeInt(OutputStream out, int value) throws IOException {
		final byte[] integerBytes = new byte[Integer.BYTES];
		integerBytes[0] = (byte) ((value >>> 24) & 0xff);
		integerBytes[1] = (byte) ((value >>> 16) & 0xff);
		integerBytes[2] = (byte) ((value >>> 8) & 0xff);
		integerBytes[3] = (byte) (value & 0xff);
		out.write(integerBytes);
	}

	protected void writeByte(OutputStream out, byte value) throws IOException {
		out.write(value);
	}

	protected void writeBool(OutputStream out, boolean value) throws IOException {
		out.write((byte) (value ? 1 : 0));
	}

	protected byte[] readBytes(InputStream in, int size) throws IOException {
		final byte[] bytes = new byte[size];
		final int byteCount = in.read(bytes, 0, bytes.length);
		if (byteCount != bytes.length) {
			throw new IOException();
		}
		return bytes;
	}

	protected String readString(InputStream in) throws IOException {
		return new String(readBytes(in, readInt(in)), StandardCharsets.UTF_8);
	}

	protected int readInt(InputStream in) throws IOException {
		final byte[] integerBytes = new byte[Integer.BYTES];
		final int byteCount = in.read(integerBytes, 0, integerBytes.length);
		if (byteCount != integerBytes.length) {
			throw new IOException();
		}
		return ((integerBytes[0] & 0xff) << 24) | ((integerBytes[1] & 0xff) << 16) | ((integerBytes[2] & 0xff) << 8)
			| ((integerBytes[3] & 0xff));
	}

	protected byte readByte(InputStream in) throws IOException {
		final int readByte = in.read();
		if (readByte < 0) {
			throw new IOException();
		}
		return (byte) readByte;
	}

	protected boolean readBool(InputStream in) throws IOException {
		final int boolByte = in.read();
		if (boolByte < 0) {
			throw new IOException();
		}
		return boolByte == 1;
	}

}
