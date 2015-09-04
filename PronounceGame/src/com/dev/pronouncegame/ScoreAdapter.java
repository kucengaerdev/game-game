package com.dev.pronouncegame;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScoreAdapter extends ArrayAdapter<ScoreItem> {
	
	private Activity context;
	private int resId;
	RowHolder holder;
	private ArrayList<ScoreItem> data = new ArrayList<ScoreItem>();

	public ScoreAdapter(Activity context, int resource, ArrayList<ScoreItem> list) {
		super(context, resource, resource, list);
		// TODOs Auto-generated constructor stub
		this.context = context;
		this.resId = resource;
		this.data = list;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODOs Auto-generated method stub
		View row = convertView;
		holder = null;
		if(row == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(resId, parent, false);
			holder = new RowHolder();
			
			holder.nomer = (TextView)row.findViewById(R.id.row_nomer);
			holder.nama = (TextView)row.findViewById(R.id.row_name);
			holder.nilai = (TextView)row.findViewById(R.id.row_score);
			
			row.setTag(holder);
			
		}else{
			
			holder = (RowHolder)row.getTag();
			
		}
		ScoreItem scoreData = data.get(position);
		
		holder.nomer.setText(scoreData.getNomer());
		holder.nama.setText(scoreData.getNama());
		holder.nilai.setText(scoreData.getNilai());
		
		return row;
	}




	static class RowHolder{
		TextView nama,nilai,nomer;
	}

}
