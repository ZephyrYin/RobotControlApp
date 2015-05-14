package com.example.control;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;

import android.support.v7.app.ActionBarActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

public class MainActivity extends ActionBarActivity {
	private Button forwardButton;
	private Button backButton;
	private Button leftButton;
	private Button rightButton;
	private Button runButton;
	private Button graspButton;
	private Button looseButton;
	private Button onButton;
	private Button offButton;
	private Button beepButton;
	private Button automaticButton;
	
	final String nxt1 = "00:16:53:1B:FD:95";
	BluetoothAdapter localAdapter;
	BluetoothSocket socket_nxt;
	boolean success=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		enableBT();
		setContentView(R.layout.activity_main);
																	// stop 0
		forwardButton = (Button) findViewById(R.id.buttonForward);  //1
		backButton = (Button) findViewById(R.id.buttonBack); 		//2
		leftButton = (Button) findViewById(R.id.buttonLeft);		//3
		rightButton = (Button) findViewById(R.id.buttonRight);		//4		
		runButton = (Button) findViewById(R.id.buttonRun);		//5
		graspButton = (Button) findViewById(R.id.buttonGrasp);		//6
		looseButton = (Button) findViewById(R.id.buttonLoose);		//7
		onButton = (Button) findViewById(R.id.buttonOn);	
		offButton = (Button) findViewById(R.id.buttonOff);		//8
		beepButton = (Button) findViewById(R.id.buttonBeep);		//9
		automaticButton = (Button) findViewById(R.id.buttonAutomatic); // 10
		
		forwardButton.setEnabled(false);
		backButton.setEnabled(false);
		leftButton.setEnabled(false);
		rightButton.setEnabled(false);
		runButton.setEnabled(false);
		graspButton.setEnabled(false);
		looseButton.setEnabled(false);
		offButton.setEnabled(false);
		beepButton.setEnabled(false);
		automaticButton.setEnabled(false);
		
		forwardButton.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                	sendMsg((byte) 1,"nxt1");
                	return true;
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                	sendMsg((byte) 0, "nxt1");
                	return true;
                }
                return false;
            }
		});
		
		backButton.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                	sendMsg((byte) 2,"nxt1");
                	return true;
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                	sendMsg((byte) 0, "nxt1");
                	return true;
                }
                return false;
            }
		});
		
		leftButton.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                	sendMsg((byte) 3,"nxt1");
                	return true;
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                	sendMsg((byte) 0, "nxt1");
                	return true;
                }
                return false;
            }
		});
		
		
		rightButton.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                	sendMsg((byte) 4,"nxt1");
                	return true;
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                	sendMsg((byte) 0, "nxt1");
                	return true;
                }
                return false;
            }
		});
		
		runButton.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                	sendMsg((byte) 5,"nxt1");
                	return true;
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                	sendMsg((byte) 0, "nxt1");
                	return true;
                }
                return false;
            }
		});
		
		graspButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				sendMsg((byte) 6, "nxt1");
			}
		});
		
		looseButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				sendMsg((byte) 7, "nxt1");
			}
		});
		
		automaticButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				sendMsg((byte) 10, "nxt1");
				offButton.setEnabled(false);
				onButton.setEnabled(true);
				forwardButton.setEnabled(false);
				backButton.setEnabled(false);
				leftButton.setEnabled(false);
				rightButton.setEnabled(false);
				runButton.setEnabled(false);
				graspButton.setEnabled(false);
				looseButton.setEnabled(false);
				beepButton.setEnabled(false);
				automaticButton.setEnabled(false);
				try{
					Thread.sleep(1000);
				}catch(Exception ex){
					System.exit(0);
				}
				try {
					socket_nxt.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		onButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				boolean status = connectToNXTs();
				if(status){
					onButton.setEnabled(false);
					offButton.setEnabled(true);
					forwardButton.setEnabled(true);
					backButton.setEnabled(true);
					leftButton.setEnabled(true);
					rightButton.setEnabled(true);
					runButton.setEnabled(true);
					graspButton.setEnabled(true);
					looseButton.setEnabled(true);
					beepButton.setEnabled(true);
					automaticButton.setEnabled(true);
					Toast.makeText(getApplicationContext(), "connection success", 
							   Toast.LENGTH_LONG).show();
				}
				else{
					onButton.setEnabled(true);
					offButton.setEnabled(false);
					Toast.makeText(getApplicationContext(), "connection fail", 
							   Toast.LENGTH_LONG).show();
				}
			}
		});
		
		offButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				Toast.makeText(getApplicationContext(), "off", 
						   Toast.LENGTH_LONG).show();
				sendMsg((byte) 8, "nxt1");
				offButton.setEnabled(false);
				onButton.setEnabled(true);
				forwardButton.setEnabled(false);
				backButton.setEnabled(false);
				leftButton.setEnabled(false);
				rightButton.setEnabled(false);
				runButton.setEnabled(false);
				graspButton.setEnabled(false);
				looseButton.setEnabled(false);
				beepButton.setEnabled(false);
				automaticButton.setEnabled(false);
				try{
					Thread.sleep(1000);
				}catch(Exception ex){
					System.exit(0);
				}
				try {
					socket_nxt.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		beepButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				sendMsg((byte) 9, "nxt1");
			}
		});
	}
	
	public void enableBT(){
	    localAdapter=BluetoothAdapter.getDefaultAdapter();
	    //If Bluetooth not enable then do it
	    if(localAdapter.isEnabled()==false){
	        localAdapter.enable();
	        while(!(localAdapter.isEnabled())){
	        }
	    }
	}
	
	public void sendMsg(byte msg, String nxt){
		try {
			writeMessage( msg ,nxt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void writeMessage(byte msg, String nxt) throws InterruptedException{
	    BluetoothSocket connSock;

	    //Swith nxt socket
	    if(nxt.equals("nxt1")){
	        connSock=socket_nxt;
	    }else{
	        connSock=null;
	    }

	    if(connSock!=null){
	        try {

	            OutputStreamWriter out=new OutputStreamWriter(connSock.getOutputStream());
	            out.write(msg);
	            out.flush();

	            Thread.sleep(1000);


	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }else{
	        //Error
	    }
	}

	
	public  boolean connectToNXTs(){
	    //get the BluetoothDevice of the NXT
	    BluetoothDevice nxt_1 = localAdapter.getRemoteDevice(nxt1);
	    //try to connect to the nxt
	    try {
	        socket_nxt = nxt_1.createRfcommSocketToServiceRecord(UUID
	                .fromString("00001101-0000-1000-8000-00805F9B34FB"));
	        socket_nxt.connect();
	        success = true;
	    } catch (IOException e) {
	        Log.d("Bluetooth","Err: Device not found or cannot connect");
	        success=false;
	    }
	    return success;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
