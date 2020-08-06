package com.blackjack.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

public class FileManager<T> {
	private String filename;

	public FileManager(String filename){
		this.filename = filename;
	}

	public void write(Set<T> objects) throws IOException {
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
			objectOutputStream.writeObject(objects);
		}
	}
	private boolean isFileExists() {
		File file = new File(filename);
		return file.exists();
	}
	public Object read() throws FileNotFoundException, IOException, ClassNotFoundException {
		if (isFileExists() == false)
			return new HashSet<T>();
		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
			return objectInputStream.readObject();
		}
	}
}