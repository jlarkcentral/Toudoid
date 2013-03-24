package com.formation.Toudoid;

import java.util.ArrayList;

import com.formation.Toudoid.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ToudoidAdapter extends BaseExpandableListAdapter {

	private ToudoidActivity context;
	private ArrayList<Group> groupes;
	private LayoutInflater inflater;

	public ToudoidAdapter(ToudoidActivity context, ArrayList<Group> groupes) {
		this.context = context;
		this.groupes = groupes;
		inflater = LayoutInflater.from(context);
	}

	public Context getContext(){
		return context;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	public Object getChild(int gPosition, int cPosition) {
		return groupes.get(gPosition).getObjets().get(cPosition);
	}

	public long getChildId(int gPosition, int cPosition) {
		return cPosition;
	}

	public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final Task objet = (Task) getChild(groupPosition, childPosition);
		
		Log.i("INFO", "getChildView : group ="+groupPosition+" ,child ="+childPosition);//+ ",converView ="+convertView.toString()+" ,parent ="+parent.toString());

		final ChildViewHolder childViewHolder;
		
		if(childPosition==0){
//			if (convertView == null) {
				childViewHolder = new ChildViewHolder();
				childViewHolder.type = 0;

				convertView = inflater.inflate(R.layout.addtaskitem_view, null);
				childViewHolder.TVaddtask = (TextView) convertView.findViewById(R.id.TVButtonBottom);
				childViewHolder.TVaddtask.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View v) {
						addTaskDialog(groupPosition);	
					}
				});
				
				childViewHolder.buttonBottom = (Button) convertView.findViewById(R.id.Button_add_bottom);
				childViewHolder.buttonBottom.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View v) {
						addTaskDialog(groupPosition);
					}
				});			
				
				convertView.setTag(childViewHolder);
//			} else {
//				childViewHolder = (ChildViewHolder) convertView.getTag();
//				Log.i("INFO : TYPE ADD", Integer.toString(childViewHolder.type));
//			}
		}
		else {
//			if (convertView == null) {
				childViewHolder = new ChildViewHolder();
				childViewHolder.type = 1;
				convertView = inflater.inflate(R.layout.task_view, null);
				childViewHolder.taskCheckBox = (CheckBox) convertView.findViewById(R.id.taskCB);
				
				context.registerForContextMenu(childViewHolder.taskCheckBox);
				
				convertView.setTag(childViewHolder);
//			} else {
//				childViewHolder = (ChildViewHolder) convertView.getTag();
//				Log.i("INFO : TYPE TASK", Integer.toString(childViewHolder.type));
//			}
			
			Log.i("INFO", "task : " + objet.getNom());
			
			childViewHolder.taskCheckBox.setText(objet.getNom());
			childViewHolder.taskCheckBox.setChecked(objet.isChecked());
			childViewHolder.taskCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton button, boolean check) {
					// TODO Auto-generated method stub
					objet.setChecked(check);
					notifyDataSetChanged();
				}
			});
		}
		return convertView;
	}

	public void addTaskDialog(final int groupPosition){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		alertDialogBuilder.setTitle("Add a task");
		final EditText input = new EditText(context);

		alertDialogBuilder
		.setView(input)
		.setCancelable(false)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				String newTaskName = input.getText().toString();
				if (!newTaskName.equals("")){
					Group currentGroup = groupes.get(groupPosition);
					Task newTask = new Task(currentGroup,newTaskName,false);
					currentGroup.addTask(newTask);
					notifyDataSetChanged();
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
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}
	
	public int getChildrenCount(int gPosition) {
		return groupes.get(gPosition).getObjets().size();
	}

	public Object getGroup(int gPosition) {
		return groupes.get(gPosition);
	}

	public int getGroupCount() {
		return groupes.size();
	}

	public long getGroupId(int gPosition) {
		return gPosition;
	}

	public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupViewHolder gholder;

		final Group group = (Group) getGroup(groupPosition);

		if (convertView == null) {
			gholder = new GroupViewHolder();

			convertView = inflater.inflate(R.layout.header_view, null);
			gholder.textViewGroup = (TextView) convertView.findViewById(R.id.TVGroup);
			gholder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar1);

			convertView.setTag(gholder);
		} else {
			gholder = (GroupViewHolder) convertView.getTag();
		}

		gholder.textViewGroup.setText(group.getNom());
		gholder.progressBar.setMax(group.getObjets().size()-1);
		gholder.progressBar.setProgress(group.howManyChecked());
		notifyDataSetChanged();

		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	class GroupViewHolder {
		public TextView textViewGroup;
		public ProgressBar progressBar;
	}

	class ChildViewHolder {
		public CheckBox taskCheckBox;
		public Button buttonBottom;
		public TextView TVaddtask;
		public int type; // 0 = bouton add, 1 = checkbox de tache
	}

}

