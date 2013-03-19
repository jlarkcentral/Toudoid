package com.formation.Toudoid;

import java.util.ArrayList;

import com.formation.Toudoid.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

public class ToudoidActivity extends Activity {

	private ExpandableListView expandableList = null;
	private ToudoidAdapter adapter = null;
	private Button addGroupButton; 
	final Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		expandableList = (ExpandableListView) findViewById(R.id.expandableView);


		final ArrayList<Group> groupes = new ArrayList<Group>();
		Group groupe = new Group("General");
		ArrayList<Task> donnees = new ArrayList<Task>();
		for (int x = 1; x < 5; x++) {
			donnees.add(new Task(groupe, "Tache " + x));
		}
		groupe.setObjets(donnees);
		groupes.add(groupe);
	
		
		adapter = new ToudoidAdapter(this,groupes);
		expandableList.setAdapter(adapter);

		addGroupButton = (Button) findViewById(R.id.Button_addGroup);
		//Log.i("Toudoid", addTaskButton.toString());

		addGroupButton.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set title
				alertDialogBuilder.setTitle("Add a group");

				final EditText input = new EditText(context);
				
				// set dialog message
				alertDialogBuilder
				//.setMessage("")
				.setView(input)
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
				
						String newGroupName = input.getText().toString();
						if (newGroupName!=""){
							Group newGroup = new Group(newGroupName);
							groupes.add(newGroup);
							adapter.notifyDataSetChanged();
							return;
						}
						else dialog.cancel();
					}
				})
				.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		});

				
	}
	
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged(); // refresh..
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
	            //
	            return true;
	        case R.id.DevRedItemMenu:
	            //
	            return true;
	        case R.id.deleteAllItemMenu:
	            //
	            return true;    
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}