package com.liyongyue.xposedwz;

import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by yli on 2015/6/29.
 */
public class IMEIXposed implements IXposedHookLoadPackage{
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        try{

            Class<?> classBuild = XposedHelpers.findClass("android.os.Build", loadPackageParam.classLoader);
            String saveModel        = "Samsung Galaxy S6";
            String saveManufacutrer = "Sumsung";
            String saveBrand = "Galaxy";
            if (!saveManufacutrer.equals("null") && !saveManufacutrer.equals("")) XposedHelpers.setStaticObjectField(classBuild, "MANUFACTURER", saveManufacutrer);
            if (!saveModel.equals("null") && !saveModel.equals("")) XposedHelpers.setStaticObjectField(classBuild, "MODEL", saveModel);
            if (!saveBrand.equals("null") && !saveBrand.equals("")) XposedHelpers.setStaticObjectField(classBuild, "BRAND", saveBrand);

//            Class<?> classSetting = XposedHelpers.findClass("android.provider.Setting.System",loadPackageParam.classLoader);
//            String saveAndroidId = "1234567890abcdef";//len is 16
//            if (!saveAndroidId.equals("null") && !saveAndroidId.equals("")) XposedHelpers.setStaticObjectField(classSetting, "ANDROID_ID", saveAndroidId);

            Class<?> classVERSION = XposedHelpers.findClass("android.os.Build.VERSION",loadPackageParam.classLoader);
            String saveSDK = "19";
            String saveVersion = "4.4.5";
            if (!saveSDK.equals("null") && !saveSDK.equals("")) XposedHelpers.setStaticObjectField(classVERSION, "SDK", saveSDK);
            if (!saveSDK.equals("null") && !saveSDK.equals("")) XposedHelpers.setStaticObjectField(classVERSION, "RELEASE", saveVersion);



            Class<?> classTelephonyManager = XposedHelpers.findClass("android.telephony.TelephonyManager", loadPackageParam.classLoader);
            Class<?> classWifiInfo         = XposedHelpers.findClass("android.net.wifi.WifiInfo", loadPackageParam.classLoader);
            Class<?> classSettingsSecure   = XposedHelpers.findClass("android.provider.Settings.Secure", loadPackageParam.classLoader);

            XposedHelpers.findAndHookMethod(classTelephonyManager, "getDeviceId", new XC_MethodHook()
            {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable
                {
                    String saveValue = "111111111111111";
                    Log.e("IMEI",param.getResult().toString());
                    if (!saveValue.equals("null") && !saveValue.equals("")) param.setResult(saveValue);
                    super.afterHookedMethod(param);
                    Log.e("IMEI", param.getResult().toString());
                }
            });


            XposedHelpers.findAndHookMethod(classWifiInfo, "getMacAddress", new XC_MethodHook()
            {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable
                {
                    String saveValue = "12:23:34:45:56:67";
                    if (!saveValue.equals("null") && !saveValue.equals("")) param.setResult(saveValue);
                    super.afterHookedMethod(param);
                }
            });

            XposedHelpers.findAndHookMethod(classSettingsSecure, "getString", android.content.ContentResolver.class , java.lang.String.class, new XC_MethodHook()
            {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable
                {
                    String saveValue = "1234567890abcdef";
                    if (param.args[1].equals("android_id") && !saveValue.equals("null")  && !saveValue.equals("")) param.setResult(saveValue);
                    super.afterHookedMethod(param);
                }
            });
        }catch (Exception e){
            Log.e("IMEI",e.getStackTrace().toString());
        }

    }
}
