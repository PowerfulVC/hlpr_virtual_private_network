package de.blinkt.openvpn.core;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class DisconnectReceiver extends BroadcastReceiver {
    protected static OpenVPNService mService;
    private Context context;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
//            // We've bound to LocalService, cast the IBinder and get LocalService instance
            OpenVPNService.LocalBinder binder = (OpenVPNService.LocalBinder) service;
            mService = binder.getService();
            stopVpn();

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
        }
    };

    @Override
    public void onReceive(Context context, Intent i) {
        this.context = context;
        Intent intent = new Intent(context, OpenVPNService.class);
        intent.setAction(OpenVPNService.START_SERVICE);
        context.getApplicationContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void stopVpn() {
        ProfileManager.setConntectedVpnProfileDisconnected(context);
        if (mService != null && mService.getManagement() != null) {
            mService.getManagement().stopVPN(false);
        }
    }
}
