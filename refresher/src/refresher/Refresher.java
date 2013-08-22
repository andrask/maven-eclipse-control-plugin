package refresher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class Refresher {

	private static final int LISTEN_PORT = 43155;
	private final ServerSocket serverSocket;

	public Refresher() {
		try {
			serverSocket = new ServerSocket(LISTEN_PORT);
			new Thread(new RefresherThread()).start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class RefresherThread implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					Socket accept = serverSocket.accept();
					accept.close();
					refreshWorkspace();
				}
			} catch (IOException e) {
			}
		}
	}
	
	private void refreshWorkspace() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			try {
				System.out.println("Updating " + project.getName());
				project.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
}
