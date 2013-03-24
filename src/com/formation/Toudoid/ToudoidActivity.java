package com.formation.Toudoid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.formation.Toudoid.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;

public class ToudoidActivity extends Activity {

	private static final String TAG = ToudoidActivity.class.getName();
	public ExpandableListView expandableList = null;
	private ToudoidAdapter adapter = null;
	private Button addGroupButton; 
	final Context context = this;
	ArrayList<Group> groupes = new ArrayList<Group>();;
	String saveFileName = "toudoidSave.txt";
	String stringOfContents = "";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		expandableList = (ExpandableListView) findViewById(R.id.expandableView);
//		registerForContextMenu(expandableList);

//		registerForContextMenu(findViewById(R.id.taskCB));
		
		// Lecture ou creation du fichier de sauvegarde
		File saveFile = getBaseContext().getFileStreamPath(saveFileName);
		if(!saveFile.exists()){
			writeToFile("");
		}
		else{
			stringOfContents = readFromFile();
			stringToGroupes();
		}

		// affichage pratique : contenu du fichier de sauvegarde
		String out = "";
		for (int i = 0;i<groupes.size();i++){
			out += groupes.get(i).toString();
		}
		Log.i("ToudoidFILE", out);
		
		// initialisation de l'adapter
		adapter = new ToudoidAdapter(this,groupes);
		expandableList.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		// bouton d'ajout de groupe (en bas de l ecran)
		addGroupButton = (Button) findViewById(R.id.Button_addGroup);
		addGroupButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) {
				showAddGroupDialog();
			}
		});
	}

	public void showAddGroupDialog(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		alertDialogBuilder.setTitle("Add a group");
		final EditText input = new EditText(context);
		alertDialogBuilder
		.setView(input)
		.setCancelable(false)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {

				String newGroupName = input.getText().toString();
				if (!newGroupName.equals("")){
					// set title
					Group newGroup = new Group(newGroupName);
					// ajout d'une fausse tache pour le premier element du groupe
					newGroup.addTask(new Task(newGroup,"buttonBottom",false));
					
					groupes.add(newGroup);
					adapter.notifyDataSetChanged();

					return;
				}
				else dialog.cancel();
			}
		})
		.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_toudoid, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.addGroupItemMenu:
			showAddGroupDialog();
			return true;
		case R.id.DevRedItemMenu:
			for(int j=0;j<groupes.size();j++){
				if (expandableList.isGroupExpanded(j)){
					for(int i=0;i<groupes.size();i++){
						expandableList.collapseGroup(i);
					}
					return true;
				}
			}
			for(int i=0;i<groupes.size();i++){
				expandableList.expandGroup(i);
			}
			return true;
		case R.id.deleteAllItemMenu:
			groupes.clear();
			stringOfContents ="";
			writeToFile("");
			expandableList = new ExpandableListView(context);
			adapter.notifyDataSetChanged();
			return true;  
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		Log.i("INFO", "view du contextmenu : "+v.toString());
		
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    
	    switch (item.getItemId()) {
	        case R.id.addTaskItemMenu:
	            //editNote(info.id);
	            return true;
	        case R.id.editGroupNameItemMenu:
	            //deleteNote(info.id);
	            return true;
	        case R.id.deleteGroupItemMenu:
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}

	public void stringToGroupes(){
		groupes = new ArrayList<Group>();
		Scanner scanner = new Scanner(stringOfContents);
		Group newGroup=null;
		Task newTask;
		// parsage de la String decrivant le modele pour construire le modele
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			if(line.substring(0, 2).equals("#G")){
				newGroup = new Group(line.substring(2, line.length()));
				// ajout d'une fausse tache pour le premier element du groupe
				newGroup.addTask(new Task(newGroup,"bottomButton",false));
				
				groupes.add(newGroup);
			}
			if(line.substring(0, 2).equals("#T")){
				boolean check = (line.substring(2, 3).equals("X"));
				newTask = new Task(newGroup,line.substring(3, line.length()),check);
				newGroup.addTask(newTask);
			}
		}
	}

	public void groupesToString(){
		stringOfContents = "";
		for (int i = 0 ; i < groupes.size() ; i++){
			stringOfContents += "#G" + groupes.get(i).getNom();
			stringOfContents += '\n';
			ArrayList<Task> tasklist = groupes.get(i).getObjets();
			for (int j = 1 ; j < tasklist.size(); j++){
				Task t = tasklist.get(j);
				if(t.isChecked()){
					stringOfContents += "#TX"+t.getNom();
				}
				else {
					stringOfContents += "#TO"+t.getNom();
				}
				stringOfContents += '\n';
			}
		}
	}

	public void writeToFile(String data){
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(saveFileName, Context.MODE_PRIVATE));
			String output = String.format(data, System.getProperty("line.separator"));
			outputStreamWriter.write(output);
			outputStreamWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String readFromFile() {
		String ret = "";
		try {
			InputStream inputStream = openFileInput(saveFileName);
			if ( inputStream != null ) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ( (receiveString = bufferedReader.readLine()) != null ) {
					stringBuilder.append(receiveString);
					stringBuilder.append('\n');
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		}
		catch (FileNotFoundException e) {
			Log.e(TAG, "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e(TAG, "Can not read file: " + e.toString());
		}
		return ret;
	}

	@Override
	public void onPause(){
		groupesToString();
		writeToFile(stringOfContents);
		super.onPause();
	}

}