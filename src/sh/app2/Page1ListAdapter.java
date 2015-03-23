package sh.app2;


import java.util.ArrayList;
import java.util.List;

import sh.app2.zsalesorder_v2_srv.v1.entitytypes.OrderHeaderIn;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * List adapter.
 */
public class Page1ListAdapter extends BaseAdapter
{	
	private List<OrderHeaderIn> entries;
	private List<OrderHeaderIn> filteredEntries;

	private Context mContext;

	/**
	 * Constructs a new list adapter with the given context.
	 * @param c - application context.
	 */
	public Page1ListAdapter(Context c)
	{
		mContext = c;
	}

	/**
	 * Returns the list of entries.
	 * @return - list of entries.
	 */
	public List<OrderHeaderIn> getEntries()
	{
		return entries;
	}

	/**
	 * Sets the given entries.
	 * @param entries
	 */
	public void setEntries(List<OrderHeaderIn> entries)
	{
		this.entries = entries;
		this.filteredEntries = entries;
	}

	/**
	 * Returns the number of entries.
	 * @return - the number of entries.
	 */
	public int getCount()
	{
		return filteredEntries.size();
	}

	/**
	 * Returns the item in the given position.
	 * @param position - the position of the desired item.
	 * @return - the item in the given position.
	 */
	public Object getItem(int position)
	{
		return filteredEntries.get(position);
	}

	/**
	 * Returns the id of the item in the given position.
	 * @param position - the position of the item.
	 * @return - the id of the item in the given position.
	 */
	public long getItemId(int position)
	{
		return position;
	}
	
	private static class ViewHolder 
	{
		public LinearLayout ll;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		
		if (rowView == null) 
		{
			LayoutInflater mInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// Inflate a view template
			rowView = mInflater.inflate(sh.app2.R.layout.item_entry, parent, false);
			
			ViewHolder holder = new ViewHolder();
			holder.ll = (LinearLayout) rowView.findViewById(sh.app2.R.id.linearLayout1);
			holder.ll.setPadding(10, 10, 10, 10);
			holder.ll.setOrientation(LinearLayout.VERTICAL);
			
			rowView.setTag(holder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.ll.removeAllViews();
		
		OrderHeaderIn entry = filteredEntries.get(position);
		
		TextView DistrChanTextView = new TextView(mContext);
		//set the first property to bigger font
		DistrChanTextView.setTextSize(22); 
		DistrChanTextView.setText(getPropertyValue(String.valueOf(entry.getDistrChan())));
		holder.ll.addView(DistrChanTextView);	
		
		TextView DivisionTextView = new TextView(mContext);
		DivisionTextView.setText(getPropertyValue(String.valueOf(entry.getDivision())));
		holder.ll.addView(DivisionTextView);	
		
		TextView DocTypeTextView = new TextView(mContext);
		DocTypeTextView.setText(getPropertyValue(String.valueOf(entry.getDocType())));
		holder.ll.addView(DocTypeTextView);	
		
		TextView SalesOrgTextView = new TextView(mContext);
		SalesOrgTextView.setText(getPropertyValue(String.valueOf(entry.getSalesOrg())));
		holder.ll.addView(SalesOrgTextView);	
		return rowView;
	}
	
	/**
	 * Returns the property value.
	 * @param value
	 * @return - property value.
	 */
	public String getPropertyValue(String value)
	{
		if (value.equalsIgnoreCase("null"))
		{
			return mContext.getString(sh.app2.R.string.no_value);
		}

		return value;
	}

	/**
	 * Filters the items by the given constraint.
	 * @param constraint
	 */
	public void filter(CharSequence constraint)
	{
		if (constraint != null)
		{
			constraint = constraint.toString().toLowerCase();
			this.filteredEntries = new ArrayList<OrderHeaderIn>();
			for (OrderHeaderIn entry : entries)
			{
				ArrayList<String> values = new ArrayList<String>();

				values.add(String.valueOf(entry.getDistrChan()).toLowerCase());
				values.add(String.valueOf(entry.getDivision()).toLowerCase());
				values.add(String.valueOf(entry.getDocType()).toLowerCase());
				values.add(String.valueOf(entry.getSalesOrg()).toLowerCase());
				boolean found = false;
				for (String s : values)
				{
					if (s != null && s.contains(constraint))
					{
						found = true;
						break;
					}
				}
				
				if(found)
					filteredEntries.add(entry);
			}
		} 
		else
		{
			this.filteredEntries = entries;
		}
		
		notifyDataSetChanged();
	}
}