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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

public class ToudoidActivity extends Activity {

	private ExpandableListView expandableList = null;
	private ToudoidAdapter adapter = null;
	private Button addGroupButton; 
	private String newTask_Text = "";
	final Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		expandableList = (ExpandableListView) findViewById(R.id.expandableView);


		ArrayList<Group> groupes = new ArrayList<Group>();
		Group groupe = new Group("General");
		ArrayList<Task> donnees = new ArrayList<Task>();
		for (int x = 1; x < 5; x++) {
			donnees.add(new Task(groupe, "Tache " + x));
		}
		groupe.setObjets(donnees);
		groupes.add(groupe);

		adapter = new ToudoidAdapter(this,groupes); 	

		addGroupButton = (Button) findViewById(R.id.Button_addGroup);
		//Log.i("Toudoid", addTaskButton.toString());

		addGroupButton.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set title
				alertDialogBuilder.setTitle("Add a group");

				// set dialog message
				alertDialogBuilder
				.setMessage("")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						ToudoidActivity.this.finish();
					}
				})
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
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

				ToudoidAdapter adapter = new ToudoidAdapter(this, groupes);
		
				expandableList.setAdapter(adapter);
	}
}