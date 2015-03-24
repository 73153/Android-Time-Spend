package com.smart.framework;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;


import com.ijoomer.common.classes.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class SmartListAdapterIndexScrolling extends ArrayAdapter<SmartListItem> implements SectionIndexer {

	private Context context;
	private ArrayList<SmartListItem> items;
	private ItemView target;
	private HashMap<String, Integer> alphaIndexer;
	String[] sections;
	ArrayList<String> sectionArray;
	String[] AlphaIndex={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

	public SmartListAdapterIndexScrolling(Context context, int resource, ArrayList<SmartListItem> items, String[] sections, HashMap<String, Integer> alphaIndexer,
                                          ArrayList<String> sectionArray, ItemView target) {
		super(context, resource, items);
		this.items = items;
		this.context = context;
		this.target = target;
		this.sectionArray = sectionArray;
		this.alphaIndexer = alphaIndexer;
		this.sections = sections;

	}

	public ArrayList<SmartListItem> getSmartListItems() {
		return items;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		Log.v("SmartListAdapter", observer.toString());
		super.registerDataSetObserver(observer);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if ((convertView == null) || (convertView.getTag() == null)) {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(items.get(position).getItemLayout(), null);
			holder = new ViewHolder();
		} else
			holder = (ViewHolder) convertView.getTag();

		target.setItemView(position, convertView, items.get(position), holder);

		convertView.setTag(holder);

		return convertView;
	}

	@Override
	public int getPositionForSection(int section) {
		
		for(int i=sections.length-1;i>=0;i--)
		{
			if(sections[i].charAt(0)<=AlphaIndex[section].charAt(0))
			{
				return alphaIndexer.get(sectionArray.get(i));
			}
		}
		return alphaIndexer.get(sectionArray.get(0));
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		return AlphaIndex;
	}

}