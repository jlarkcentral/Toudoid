package com.formation.Toudoid;

import java.util.ArrayList;

import com.formation.Toudoid.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class ToudoidActivity extends Activity {

	private ExpandableListView expandableList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		expandableList = (ExpandableListView) findViewById(R.id.expandableView);

		ArrayList<Group> groupes = new ArrayList<Group>();


			Group groupe = new Group("General");

			ArrayList<Task> donnees = new ArrayList<Task>();

			for (int x = 1; x < 5; x++) {
				donnees.add(new Task(groupe, "Objet " + x));
			}

			groupe.setObjets(donnees);

			groupes.add(groupe);
		

		ToudoidAdapter adapter = new ToudoidAdapter(this, groupes);

		expandableList.setAdapter(adapter);
	}
}