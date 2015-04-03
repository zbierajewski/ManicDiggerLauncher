import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;


public class MainWindow {

	private static final File GameHome = new File(System.getProperty("user.home") + "/.manicdigger/");
	private JFrame frmManicDiggerLauncher;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmManicDiggerLauncher.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmManicDiggerLauncher = new JFrame();
		frmManicDiggerLauncher.setTitle("Manic Digger Launcher");
		frmManicDiggerLauncher.setResizable(false);
		frmManicDiggerLauncher.setBounds(100, 100, 241, 90);
		frmManicDiggerLauncher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JLabel lblUpdating = new JLabel("Waiting...");
		frmManicDiggerLauncher.getContentPane().add(lblUpdating, BorderLayout.NORTH);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(50);
		frmManicDiggerLauncher.getContentPane().add(progressBar, BorderLayout.CENTER);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setValue(75);
		frmManicDiggerLauncher.getContentPane().add(progressBar_1, BorderLayout.SOUTH);
		createdir();
		Updater Updater = new Updater();
		Thread one = new Thread() {
			private void downloadupdate(String updateurl){
				DefaultHttpClient httpclient = new DefaultHttpClient();
				 
				HttpGet httpget = new HttpGet(updateurl);
				CloseableHttpResponse response = null;
				try {
					response = httpclient.execute(httpget);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpEntity entity = response.getEntity();
				if (entity != null) {
				    FileOutputStream fos = null;
					try {
						fos = new java.io.FileOutputStream(GameHome);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    try {
						entity.writeTo(fos);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		    public void run() {
		    	File f = new File("/usr/bin/mono");
		    	if(!f.exists()){
		    		try {
		    			System.out.println("Launching Mono installer for Mac os X.");
						Desktop.getDesktop().open(new File(GameHome + "/MonoFramework-MRE-3.4.0.macos10.xamarin.x86.pkg"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		        //System.out.println("Does it work?");
		        lblUpdating.setText("Downloading update...");
				downloadupdate("https://github.com/manicdigger/manicdigger-builds/blob/gh-pages/ManicDiggerLatestBinary.zip?raw=true");
				lblUpdating.setText("Done! Launching!");

				//System.out.println("Nope, it doesnt...again.");
		    }  
		};
		one.start();
		try {
			//Desktop.getDesktop().open(new File(GameHome + "/MonoFramework-MRE-3.4.0.macos10.xamarin.x86.pkg"));
			
			
		    }
		    catch (Exception err) {
		      err.printStackTrace();
		    }
	}
	
	private void createdir(){

		  // if the directory does not exist, create it
		  if (!GameHome.exists()) {
		    boolean result = false;

		    try{
		    	GameHome.mkdir();
		        result = true;
		     } catch(SecurityException se){
		        //handle it
		     }        
		     if(result) {    
		       System.out.println("DIR created");  
		     }
		  }
	}
	}

