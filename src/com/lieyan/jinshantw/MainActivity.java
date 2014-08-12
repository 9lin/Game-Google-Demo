package com.lieyan.jinshantw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.trivialdrivesample.util.IabException;
import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabHelper.OnConsumeMultiFinishedListener;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.Purchase;
import com.ghostgames.GWZG.Google.R;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = "SanGuoWuYu";
	Button button1, button2, button3, button4, button5, button6, button7,
			button8,button9;
	TextView textView1;
	/**** 以下的支付框架需要的变量 ****/
	// The helper object
	IabHelper mHelper;
	// (arbitrary) request code for the purchase flow请求码
	static final int RC_REQUEST = 10001;
	private boolean iap_is_ok = false;

	/**** 以上的支付框架需要的变量 ****/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		showMessage(TAG, tm.getDeviceId());
		initGooglePlayInAppBilling();
		init();
	}
	
	/**
	 * in app billing初始化方法
	 */
	private void initGooglePlayInAppBilling() {
		/** google play in app billing初始化 **/
		String base64EncodedPublicKey = getResources().getString(R.string.base64EncodedPublicKey);// 此处请填写你应用的appkey
		// String base64EncodedPublicKey = "";//此处请填写你应用的appkey
		mHelper = new IabHelper(this, base64EncodedPublicKey);
		// enable debug logging (for a production application, you should set
		// this to false).
		mHelper.enableDebugLogging(false);
		// Start setup. This is asynchronous and the specified listener
		// will be called once setup completes.
		Log.d(TAG, "Starting setup.");
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				Log.d(TAG, "Setup finished.");
				Log.i(TAG, "IAP初始化成功！");
				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					complain("Problem setting up in-app billing: " + result);
					return;
				}
				iap_is_ok = true;
				// Hooray, IAB is fully set up. Now, let's get an inventory of
				// stuff we own.
				Log.d(TAG, "Setup successful. Querying inventory.");
			}
		});
	}
	
	/**
	 * 回掉必须重写onActivityResult
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + ","
				+ data);

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...
			super.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.d(TAG, "onActivityResult handled by IABUtil.");
		}
	}
	
	void complain(String message) {
		Log.e(TAG, "**** TrivialDrive Error: " + message);
		alert("Error: " + message);
	}

	void alert(String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(this);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		Log.d(TAG, "Showing alert dialog: " + message);
		bld.create().show();
	}

	private void showMessage(String title, String message) {
		new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message).setPositiveButton("确定", null).show();
	}
	
	/**
	 * 支付
	 * @param sku
	 */
	public void luca(String sku){
		if(iap_is_ok){
			List<String> list = new ArrayList<String>();
			list.add("gwzg01");
			list.add("gwzg02");
			list.add("gwzg03");
			list.add("gwzg04");
			list.add("gwzg05");
			list.add("gwzg06");

			mHelper.queryInventoryAsync(true,null,mGotInventoryListener);

			//mHelper.launchPurchaseFlow(this,sku,RC_REQUEST,mPurchaseFinishedListener);
			return;
//			try {
//				Bundle mBundle = mHelper.getService().getPurchases(3,"com.ghostgames.GWZG.Google","inapp", null);
//				showMessage("BBBBBBBBBB",mBundle.getString("RESPONSE_CODE"));
//				showMessage("BBBBBBBBBB",mBundle.getString("INAPP_PURCHASE_ITEM_LIST"));
//				showMessage("BBBBBBBBBB",mBundle.getString("INAPP_PURCHASE_DATA_LIST"));
//				showMessage("BBBBBBBBBB",mBundle.getString("INAPP_DATA_SIGNATURE_LIST"));
//				showMessage("BBBBBBBBBB",mBundle.getString("INAPP_CONTINUATION_TOKEN"));
//			} catch (RemoteException e2) {
//				e2.printStackTrace();
//			}
			
//			list = new ArrayList<Purchase>();
//			try {
//				list = mHelper.queryInventory(false,null).getAllPurchases();
//			} catch (IabException e1) {
//				e1.printStackTrace();
//			}
//			
//			for(int i=0;i<list.size();i++){
//				if(list.get(i).getSku().equals(sku)){
//					showMessage("提示","您有一件该类商品没有消耗！!!");
//					return;
//				}
//			}
//			List<String> list = new ArrayList<String>();
//			list.add("gwzg01");
//			list.add("gwzg02");
//			list.add("gwzg03");
//			list.add("gwzg04");
//			list.add("gwzg05");
//			list.add("gwzg06");
//			try {
//				Inventory inventory = mHelper.queryInventory(true,list);
//				textView1.setText(inventory.getAllPurchases().toString());
//			} catch (IabException e) {
//				e.printStackTrace();
//			}
			}else{
			showMessage("提升","您当前所处的环境不支持googleplay支付，请换一个支付环境！");
		}
	}
	
	public void xiaohao(){ 
		if(iap_is_ok){
			if(list.size()<=0){
				showMessage("提示","您没有需要消耗的商品！");
				return;
			}
			//mHelper.consumeAsync(list,ocm);
			for(int i=0;i<list.size();i++){
				mHelper.consumeAsync(list.get(i), mConsumeFinishedListener);
			}
		}else{
			showMessage("提升","您当前所处的环境不支持googleplay支付，请换一个支付环境！");
		}
	}
	
	IabHelper.OnConsumeMultiFinishedListener ocm = new OnConsumeMultiFinishedListener() {
		
		@Override
		public void onConsumeMultiFinished(List<Purchase> purchases,
				List<IabResult> results) {
			// TODO Auto-generated method stub
			String str = "";
			for(int i=0;i<results.size();i++){
				if(results.get(i).isFailure()){
					str = str+purchases.get(i).getSku();
				}
			}
			textView1.setText(str);
			if(str.equals("")){
				showMessage("提示","商品全部消耗完毕！");
			}
		}
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mHelper != null)
			mHelper.dispose();
		mHelper = null;

	}
	
	// Listener that's called when we finish querying the items we own
		IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
			public void onQueryInventoryFinished(IabResult result,
					Inventory inventory) {
				Log.d(TAG, "Query inventory finished.");
				
				if (result.isFailure()) {
					complain("Failed to query inventory: " + result);
					return;
				}
				
				
				
				Log.d(TAG, "Query inventory was successful.");
				String sku="";
				if(inventory.hasPurchase("gwzg01")){
					showMessage("xswdwe", "gwzg01");
					sku=sku+"gwzg01";
				}else if(inventory.hasPurchase("gwzg02")){
					showMessage("xswdwe", "gwzg01");
					sku=sku+"gwzg02";
				}else if(inventory.hasPurchase("gwzg03")){
					showMessage("xswdwe", "gwzg01");
					sku=sku+"gwzg03";
				}else if(inventory.hasPurchase("gwzg04")){
					showMessage("xswdwe", "gwzg01");
					sku=sku+"gwzg04";
				}else if(inventory.hasPurchase("gwzg05")){
					showMessage("xswdwe", "gwzg01");
					sku=sku+"gwzg05";
				}else if(inventory.hasPurchase("gwzg06")){
					showMessage("xswdwe", "gwzg01");
					sku=sku+"gwzg06";
				}else{
					textView1.setText("您当前没有需要消耗的商品！");
					return;
				}
				
				textView1.setText(sku);
				list = new ArrayList<Purchase>();
				list = inventory.getAllPurchases();

//				mPurchases = new ArrayList<Purchase>();
//				for(int i=0;i<mPurchases.size();i++){
//					String purchase_strs = mPurchases.get(i).getToken()+","+mPurchases.get(i).getSku();
//					CallCocos.googlePlaySuccessed(purchase_strs);
//					mPurchase = inventory.getPurchase(mPurchases.get(i).getSku());
//				}
			}
		};
	
	public void chaxun(){
		if(iap_is_ok){
			try {
				list = new ArrayList<Purchase>();
				String str = "";
				list = mHelper.queryInventory(false,null).getAllPurchases();
				if(list.size()<=0){
					textView1.setText("没有需要消耗的！");
					return;
				}
				for(int i=0;i<list.size();i++){
					str = str+list.get(i).getSku();
					textView1.setText(str);
				}
			} catch (IabException e) {
				e.printStackTrace();
			}
			
		}else{
			showMessage("提升","您当前所处的环境不支持googleplay支付，请换一个支付环境！");
		}
	}
	
	public void chaxun2(){
		if(iap_is_ok){
			mHelper.queryInventoryAsync(mGotInventoryListener);
		}else{
			showMessage("提升","您当前所处的环境不支持googleplay支付，请换一个支付环境！");
		}
	}
	List<Purchase> list;
	
	// Callback for when a purchase is finished
		IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
			public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
				Log.d(TAG, "Purchase finished: " + result + ", purchase: "
						+ purchase);
				showMessage("ssssss", "如果失败的话，返回的purchase："+purchase);
				
				if (!result.isSuccess()) {
					// Oh noes!
//					showMessage("sssss","失败了，走进来了！！！！");
//					String token ="inapp:com.ghostgames.GWZG.Google:gwzg01";
//					try {
//						showMessage("sssss","执行了！！！！！");
//						int response = mHelper.getService().consumePurchase(3,"com.ghostgames.GWZG.Google","");
//						showMessage("sssss","执行了！！！！！22222:"+response);
//						if(response==1){
//							showMessage("sssss","成功了！！！！！");
//						}	
//					} catch (RemoteException e) {
//						e.printStackTrace();
//					}
					return;
				}
				Log.d(TAG, "Purchase successful.");
				list = new ArrayList<Purchase>();
				try {
					list = mHelper.queryInventory(false,null).getAllPurchases();
				} catch (IabException e) {
					e.printStackTrace();
				}
			}
		};
	
	public void init() {
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		button7 = (Button) findViewById(R.id.button7);
		button8 = (Button) findViewById(R.id.button8);
		button9 = (Button) findViewById(R.id.button9);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button6.setOnClickListener(this);
		button7.setOnClickListener(this);
		button8.setOnClickListener(this);
		button9.setOnClickListener(this);
		textView1 = (TextView) findViewById(R.id.textView1);
	}

	// Called when consumption is complete
		IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
			public void onConsumeFinished(Purchase purchase, IabResult result) {
				// Log.d(TAG, "Consumption finished. Purchase: " + purchase +
				// ", result: " + result);
				// We know this is the "gas" sku because it's the only one we
				// consume,
				// so we don't check which sku was consumed. If you have more than
				// one
				// sku, you probably should check...
				
				
				if (result.isSuccess()) {
					// successfully consumed, so we apply the effects of the item in
					// our
					// game world's logic, which in our case means filling the gas
					// tank a bit
					Log.i(TAG, "Google商品完成消耗！");
					String str = "";
					for(int i=0;i<list.size();i++){
						str=str+list.get(i).getSku();
					}
					showMessage("提示","商品已经完成来消耗！"+str);
					list = null;
				} else {
					complain("Error while consuming: " + result);
				}
			}
		};
	
		public String getHttpConnection(String url){
			HttpGet hg = new HttpGet(url);
			String str = "";
			try {
				HttpClient hc = new DefaultHttpClient();
				HttpResponse mHttpResponse = hc.execute(hg);
				if(mHttpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
					str = mHttpResponse.getEntity().toString();
					return str;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
		
		
	public void chaxunweixiaohao(){
		Bundle mBundle = new Bundle();
		try {
			mBundle = mHelper.getService().getPurchases(3,"com.lieyan.jinshantw","inapp","com.sanguoyiwenlu.45");
			if (!mBundle.containsKey("INAPP_PURCHASE_DATA_LIST")) {
                return;
            }
			ArrayList<String> purchaseDataList = mBundle.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
			String str = "";
			for(int i=0;i<purchaseDataList.size();i++){
				str = str+","+purchaseDataList.get(i);
			}
			showMessage("哈哈哈哈哈",str);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.button1:
			luca("gwzg01");
			break;
		case R.id.button2:
			luca("gwzg02");
			break;
		case R.id.button3:
			luca("gwzg03");
			break;
		case R.id.button4:
			luca("gwzg04");
			break;
		case R.id.button5:
			luca("gwzg05");
			break;
		case R.id.button6:
			luca("gwzg06");
			break;
		case R.id.button7:
			chaxun();
			break;
		case R.id.button8:
			xiaohao();
			break;
		case R.id.button9:
			chaxunweixiaohao();
			break;
		default:
			break;
		}

	}

}
