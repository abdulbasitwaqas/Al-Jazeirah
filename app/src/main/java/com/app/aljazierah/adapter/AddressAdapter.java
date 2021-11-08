package com.app.aljazierah.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aljazierah.AppController;
import com.app.aljazierah.R;
import com.app.aljazierah.UserSession;
import com.app.aljazierah.object.login.Address;
import com.app.aljazierah.object.login.User;
import com.app.aljazierah.object.request.DeleteAddress;
import com.app.aljazierah.utils.APIManager;
import com.roam.appdatabase.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    private List<Address> addressList;
    private Context mContext;
    private Activity activity;
    boolean isArabic;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView addressName, addressDetail;
        private ImageView deleteAddress;
        private LinearLayout layoutMain;
        private RadioButton radioSelectedAddress;

        public MyViewHolder(View view) {
            super(view);
            addressName =  view.findViewById(R.id.address_name);
            addressDetail =  view.findViewById(R.id.address_detail);
            deleteAddress =  view.findViewById(R.id.delete_address);
            radioSelectedAddress =  view.findViewById(R.id.radioSelectedAddress);
            layoutMain =  view.findViewById(R.id.layoutMain);
        }
    }


    public AddressAdapter(List<Address> addressList, Context context,Activity activity) {
        this.addressList = addressList;
        this.mContext = context;
        this.activity=activity;
        isArabic= AppController.setLocale();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        final Address address = addressList.get(position);
        holder.addressName.setText(address.addName);
        String street = "", buildingNumber = "", floorNumber = "", completeAddress = "";
        completeAddress = address.addDetail;

        holder.addressDetail.setText(completeAddress);

        holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteAddressDialog(address);
            }
        });


        try {

            holder.radioSelectedAddress.setEnabled(false);
            JSONObject addressObj = new JSONObject(UserSession.getInstance().getSaveAddressObject());
            if (addressObj.getString("address_id").equals(address.addId)) {
                holder.radioSelectedAddress.setChecked(true);
                holder.deleteAddress.setVisibility(View.GONE);
                holder.layoutMain.setBackgroundResource(R.drawable._background_green_shadow);
            } else {
                holder.radioSelectedAddress.setChecked(false);
                holder.deleteAddress.setVisibility(View.VISIBLE);
                holder.layoutMain.setBackgroundResource(R.drawable._background_white_shadow);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeAddressDialog(addressList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public void setAddressList(List<Address> moviesList) {
        this.addressList = moviesList;
        notifyDataSetChanged();
    }


    private void openDeleteAddressDialog(final Address address) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText(mContext.getResources().getString(R.string.deleting_confirm));
        TextView message = (TextView) dialog.findViewById(R.id.message);
        message.setText(mContext.getResources().getString(R.string.deleting_address));

        Button ok = (Button) dialog.findViewById(R.id.okBtn);
        Button cancel = (Button) dialog.findViewById(R.id.cancelBtn);
        dialog.setCancelable(false);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Address.changeDefaultAddress(address.addId);
                User user = DatabaseManager.getInstance().getFirstOfClass(User.class);
                DeleteAddress deleteAddress = new DeleteAddress(user.userId, address.addId);
                APIManager.getInstance().deleteAddress(new APIManager.Callback() {
                    @Override
                    public void onResult(boolean z, String response) {
                        if (z) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").toLowerCase().equals("true")) {
                                    address.delete();
                                    addressList.remove(address);
                                    notifyDataSetChanged();
                                }else {
                                    Toast.makeText(mContext, ""+jsonObject.optString("Message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, deleteAddress);
                dialog.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
//        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
//        int width = metrics.widthPixels;
//        int height = metrics.heightPixels;
//        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    private void openChangeAddressDialog(final Address address) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText(mContext.getResources().getString(R.string.alert));
        TextView message = (TextView) dialog.findViewById(R.id.message);
        message.setText(mContext.getResources().getString(R.string.change_address));

        Button ok = (Button) dialog.findViewById(R.id.okBtn);
        Button cancel = (Button) dialog.findViewById(R.id.cancelBtn);
        dialog.setCancelable(false);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Address.changeDefaultAddress(address.addId);
                addressList = DatabaseManager.getInstance().getAllOfClass(Address.class);
                setAddressList(addressList);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("lat", address.addLatitude+"");
                    jsonObject.put("lng", address.addLongitude+"");
                    jsonObject.put("areaId", ""+address.addArea);
                    jsonObject.put("cityId", ""+address.addCity);
                    jsonObject.put("addressName", "Address: " + address.addName );
                    jsonObject.put("details", ""+address.addDetail);
                    jsonObject.put("address_id", ""+address.addId);
                    jsonObject.put("add_type", address.add_type);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                UserSession.getInstance().setSaveHomeAddressObject(jsonObject+"");
                broadcastData();
                activity.finish();


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void broadcastData() {
        Intent intent = new Intent("data_action_add_change");
        intent.putExtra("onAddressChange", "" + "changed");
        mContext.sendBroadcast(intent);
    }
}

