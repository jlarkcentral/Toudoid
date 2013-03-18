package com.formation.Toudoid;

import java.util.ArrayList;

import com.formation.Toudoid.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ToudoidAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<Group> groupes;
	private LayoutInflater inflater;
	
	public ToudoidAdapter(Context context, ArrayList<Group> groupes) {
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

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final Task objet = (Task) getChild(groupPosition, childPosition);
		
		ChildViewHolder childViewHolder;
		
        if (convertView == null) {
        	childViewHolder = new ChildViewHolder();
        	
            convertView = inflater.inflate(R.layout.task_view, null);
            
            childViewHolder.taskCheckBox = (CheckBox) convertView.findViewById(R.id.taskCB);
            //childViewHolder.buttonChild = (Button) convertView.findViewById(R.id.BTChild);
            
            convertView.setTag(childViewHolder);
        } else {
        	childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        
        childViewHolder.taskCheckBox.setText(objet.getNom());
        
        //childViewHolder.buttonChild.setText(objet.getNom());
        
        //childViewHolder.buttonChild.setOnClickListener(new OnClickListener() {
			
//			public void onClick(View v) {
//				Toast.makeText(context, "Groupe : " + objet.getGroupe().getNom() + " - Bouton : " + objet.getNom(), Toast.LENGTH_SHORT).show();				
//			}
//		});
        
        return convertView;
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

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupViewHolder gholder;
		
		Group group = (Group) getGroup(groupPosition);
		
        if (convertView == null) {
        	gholder = new GroupViewHolder();
        	
        	convertView = inflater.inflate(R.layout.header_view, null);
        	
        	gholder.textViewGroup = (TextView) convertView.findViewById(R.id.TVGroup);
        	gholder.buttonGroup = (Button) convertView.findViewById(R.id.Button_add);
        	///
        	gholder.buttonGroup.setOnClickListener(new View.OnClickListener()
    		{

    			public void onClick(View v) {
    				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
    						context);

    				// set title
    				alertDialogBuilder.setTitle("Add a task");

    				// set dialog message
    				alertDialogBuilder
    				.setMessage("")
    				.setCancelable(false)
    				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						// if this button is clicked, close
    						// current activity
    						dialog.cancel();
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
        	
        	////
        	convertView.setTag(gholder);
        } else {
        	gholder = (GroupViewHolder) convertView.getTag();
        }
        
        gholder.textViewGroup.setText(group.getNom());
        
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
		public Button buttonGroup;
	}
	
	class ChildViewHolder {
		public CheckBox taskCheckBox;
		//public Button buttonChild;
	}

}

