package net.sf.openrocket.file;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ProgressMonitorInputStream;

import net.sf.openrocket.aerodynamics.WarningSet;
import net.sf.openrocket.document.OpenRocketDocument;


public abstract class RocketLoader {
	protected final WarningSet warnings = new WarningSet();

	
	public final OpenRocketDocument load(File source, Component parent) 
	throws RocketLoadException {
		warnings.clear();
		
		try {
			return load(new BufferedInputStream(new ProgressMonitorInputStream(
					parent, "Loading " + source.getName(),
					new FileInputStream(source))));
		} catch (FileNotFoundException e) {
			throw new RocketLoadException("File not found: " + source);
		}
	}

	/**
	 * Loads a rocket from the specified File object.
	 */
	public final OpenRocketDocument load(File source) throws RocketLoadException {
		warnings.clear();

		try {
			return load(new BufferedInputStream(new FileInputStream(source)));
		} catch (FileNotFoundException e) {
			throw new RocketLoadException("File not found: " + source);
		}
	}

	/**
	 * Loads a rocket from the specified InputStream.
	 */
	public final OpenRocketDocument load(InputStream source) throws RocketLoadException {
		warnings.clear();

		try {
			return loadFromStream(source);
		} catch (RocketLoadException e) {
			throw e;
		} catch (IOException e) {
			throw new RocketLoadException("I/O error: " + e.getMessage());
		} catch (Exception e) {
			throw new RocketLoadException("An unknown error occurred.  Please report a bug.", e);
		} catch (Throwable e) {
			throw new RocketLoadException("A serious error occurred and the software may be "
					+ "unstable.  Save your designs and restart OpenRocket.", e);
		}
	}

	
	
	/**
	 * This method is called by the default implementations of {@link #load(File)} 
	 * and {@link #load(InputStream)} to load the rocket.
	 * 
	 * @throws RocketLoadException	if an error occurs during loading.
	 */
	protected abstract OpenRocketDocument loadFromStream(InputStream source) throws IOException,
			RocketLoadException;



	public final WarningSet getWarnings() {
		return warnings;
	}
}
